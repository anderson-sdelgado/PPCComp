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


}