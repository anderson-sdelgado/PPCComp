package br.com.usinasantafe.ppc.di.repository

import br.com.usinasantafe.ppc.domain.repositories.stable.*
import br.com.usinasantafe.ppc.infra.repositories.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRepositoryModule {

    @Binds
    @Singleton
    fun bindColabRepository(repository: IColabRepository): ColabRepository

    @Binds
    @Singleton
    fun bindHarvesterRepository(repository: IHarvesterRepository): HarvesterRepository

    @Binds
    @Singleton
    fun bindOSRepository(repository: IOSRepository): OSRepository

    @Binds
    @Singleton
    fun bindPlotRepository(repository: IPlotRepository): PlotRepository

    @Binds
    @Singleton
    fun bindSectionRepository(repository: ISectionRepository): SectionRepository

}