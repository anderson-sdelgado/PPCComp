package br.com.usinasantafe.ppc.di.repository

import br.com.usinasantafe.ppc.domain.repositories.variable.*
import br.com.usinasantafe.ppc.infra.repositories.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRepositoryModule {

    @Binds
    @Singleton
    fun bindConfigRepository(repository: IConfigRepository): ConfigRepository

    @Binds
    @Singleton
    fun bindAnalysisRepository(repository: IAnalysisRepository): AnalysisRepository
}