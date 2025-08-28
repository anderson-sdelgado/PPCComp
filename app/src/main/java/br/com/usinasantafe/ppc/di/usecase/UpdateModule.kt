package br.com.usinasantafe.ppc.di.usecase

import br.com.usinasantafe.ppc.domain.usecases.update.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UpdateModule {

    @Binds
    @Singleton
    fun bindUpdateTableColab(usecase: IUpdateTableColab): UpdateTableColab

    @Binds
    @Singleton
    fun bindUpdateTableHarvester(usecase: IUpdateTableHarvester): UpdateTableHarvester

    @Binds
    @Singleton
    fun bindUpdateTableOSByNro(usecase: IUpdateTableOSByNro): UpdateTableOSByNro

    @Binds
    @Singleton
    fun bindUpdateTablePlot(usecase: IUpdateTablePlot): UpdateTablePlot

    @Binds
    @Singleton
    fun bindUpdateTableSection(usecase: IUpdateTableSection): UpdateTableSection

}