package br.com.usinasantafe.ppc.di.usecase

import br.com.usinasantafe.ppc.domain.usecases.analysis.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AnalysisModule {

    @Binds
    @Singleton
    fun bindCloseAnalysis(usecase: IFinishAnalysis): FinishAnalysis

    @Binds
    @Singleton
    fun bindDeleteAnalysis(usecase: IDeleteAnalysis): DeleteAnalysis

    @Binds
    @Singleton
    fun bindCheckSendAnalysis(usecase: ICheckSendAnalysis): CheckSendAnalysis

    @Binds
    @Singleton
    fun bindSendAnalysis(usecase: ISendAnalysis): SendAnalysis

}