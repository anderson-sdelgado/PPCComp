package br.com.usinasantafe.ppc.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import br.com.usinasantafe.ppc.R
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.security.cert.X509Certificate
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


interface DownloadAndInstallApk {
    operator fun invoke(
        url: String
    ): Flow<Result<Float>>
}

class IDownloadAndInstallApk @Inject constructor(
    @ApplicationContext private val context: Context
): DownloadAndInstallApk {

    override fun invoke(url: String): Flow<Result<Float>> = flow {
        try {
            val urlAll = context.getString(R.string.base_url) + url
            val client = getUnsafeOkHttpClient()
            val request = Request.Builder().url(urlAll).build()

            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                emit(
                    resultFailure(
                        context = getClassAndMethod(),
                        cause = Exception("Erro ao baixar APK: ${response.code}")
                    )
                )
                return@flow
            }
            if(response.body == null) {
                resultFailure(
                    context = getClassAndMethod(),
                    cause = Exception("Corpo da resposta está nulo")
                )
                return@flow
            }
            val body = response.body
            val contentLength = body!!.contentLength()
            val apkFile = File(context.getExternalFilesDir(null), "update.apk")

            emit(Result.success(0f))

            apkFile.outputStream().use { output ->
                val buffer = ByteArray(4 * 1024)
                var bytesCopied: Long = 0
                var bytes = body.byteStream().read(buffer)
                while (bytes >= 0) {
                    output.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    bytes = body.byteStream().read(buffer)

                    if (contentLength > 0) {
                        val progress = bytesCopied.toFloat() / contentLength.toFloat()
                        emit(Result.success(progress))
                    }
                }
            }

            emit(
                Result.success(1f)
            )

            val apkUri: Uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                apkFile
            )
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(apkUri, "application/vnd.android.package-archive")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
            }
            context.startActivity(intent)

        } catch (e: Exception) {
            emit(
                resultFailure(
                    context = getClassAndMethod(),
                    cause = e
                )
            )
        }

    }
    .flowOn(Dispatchers.IO)
    .distinctUntilChanged()

}

fun getUnsafeOkHttpClient(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(
        @SuppressLint("CustomX509TrustManager")
        object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .build()
}
