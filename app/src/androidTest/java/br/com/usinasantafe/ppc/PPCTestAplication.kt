package br.com.usinasantafe.ppc

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.components.SingletonComponent

@CustomTestApplication(PPCHiltTestApp::class)
interface PPCTestApplication

open class PPCHiltTestApp : Application(), Configuration.Provider {

    override val workManagerConfiguration:  Configuration get() {
        val entryPoint = EntryPointAccessors.fromApplication(
            this,
            WorkManagerEntryPoint::class.java
        )

        return Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .setWorkerFactory(entryPoint.workerFactory())
            .build()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WorkManagerEntryPoint {
    fun workerFactory(): HiltWorkerFactory
}