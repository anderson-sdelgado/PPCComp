package br.com.usinasantafe.ppc.domain.usecases.background

import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import br.com.usinasantafe.ppc.domain.usecases.analysis.CheckSendAnalysis
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface StartWorkManager {
    suspend operator fun invoke()
}

class IStartWorkManager @Inject constructor(
    private val workManager: WorkManager,
    private val checkSendAnalysis: CheckSendAnalysis
): StartWorkManager {

    override suspend fun invoke() {
        val result = checkSendAnalysis()
        if (result.isFailure) {
            val error = result.exceptionOrNull()!!
            val failure =
                "${getClassAndMethod()} -> ${error.message} -> ${error.cause.toString()}"
            Timber.e(failure)
            return
        }
        val check = result.getOrNull()!!
        if(!check) return
        val constraints = Constraints
            .Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest = OneTimeWorkRequest
            .Builder(ProcessWorkManager::class.java)
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                2, TimeUnit.MINUTES
            )
            .build()
        workManager.enqueueUniqueWork("WORK-MANAGER-PPC", ExistingWorkPolicy.REPLACE, workRequest)
    }

}