package br.com.usinasantafe.ppc.di.provider

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.work.WorkManager
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.utils.CheckNetwork
import br.com.usinasantafe.ppc.utils.DownloadAndInstallApk
import br.com.usinasantafe.ppc.utils.ICheckNetwork
import br.com.usinasantafe.ppc.utils.IDownloadAndInstallApk
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PersistenceModule::class]
)
class PersistenceModuleTest {

    @Singleton
    @Provides
    @DefaultHttpClient
    fun provideHttpClient(): OkHttpClient {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @ShortTimeoutHttpClient
    fun provideShortTimeoutHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .build()
    }

    @Singleton
    @Provides
    @DefaultRetrofit
    fun provideRetrofit(
        @ShortTimeoutHttpClient client: OkHttpClient,
        url: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    @ShortTimeoutRetrofit
    fun provideShortTimeoutRetrofit(
        @ShortTimeoutHttpClient client: OkHttpClient,
        url: String
    ): Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext appContext: Context): DatabaseRoom {
        return Room.inMemoryDatabaseBuilder(
            appContext,
            DatabaseRoom::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return appContext.getSharedPreferences("test", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
        return WorkManager.getInstance(context)
    }


    @Provides
    @Singleton
    fun provideCheckNetwork(): CheckNetwork {
        return IFakeCheckNetwork()
    }

    @Provides
    @Singleton
    fun provideDownloadAndInstallApk(@ApplicationContext context: Context): DownloadAndInstallApk {
        return IDownloadAndInstallApk(context)
    }

}

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [BaseUrlModule::class]
)
object BaseUrlModuleTest {

    var url: String = "http://localhost:8080/"

    @Provides
    @Singleton
    fun provideUrl(): String {
        Log.d("TestRetrofit", "Base URL: $url")
        return url
    }
}

class IFakeCheckNetwork @Inject constructor(
): CheckNetwork {
    private var connected: Boolean = true

    fun setConnected(value: Boolean) {
        connected = value
    }

    override fun invoke(): Boolean {
        return connected
    }
}
