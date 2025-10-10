package br.com.usinasantafe.ppc.domain.usecases.background

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import br.com.usinasantafe.ppc.domain.usecases.analysis.CheckSendAnalysis
import br.com.usinasantafe.ppc.domain.usecases.analysis.SendAnalysis
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class ProcessWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val checkSendAnalysis: CheckSendAnalysis,
    private val sendAnalysis: SendAnalysis
): CoroutineWorker(appContext, workerParams)  {

    override suspend fun doWork(): Result {
        val resultCheck = checkSendAnalysis()
        if (resultCheck.isFailure) {
            val error = resultCheck.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        val check = resultCheck.getOrNull()!!
        if(!check) return Result.success()
        val resultSend = sendAnalysis()
        if (resultSend.isFailure) {
            val error = resultSend.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return Result.retry()
        }
        return Result.success()
    }

}