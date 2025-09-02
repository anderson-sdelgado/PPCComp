package br.com.usinasantafe.ppc.di.usecase

import br.com.usinasantafe.ppc.domain.usecases.flow.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface FlowModule {

    @Binds
    @Singleton
    fun bindListHeader(usecase: IListHeader): ListHeader

    @Binds
    @Singleton
    fun bindSetAuditor(usecase: ISetAuditor): SetAuditor

    @Binds
    @Singleton
    fun bindCheckColab(usecase: ICheckColab): CheckColab

}