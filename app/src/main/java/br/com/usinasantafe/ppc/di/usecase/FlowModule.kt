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
    fun bindSetAuditorHeader(usecase: ISetAuditorHeader): SetAuditorHeader

    @Binds
    @Singleton
    fun bindCheckColab(usecase: ICheckColab): CheckColab

    @Binds
    @Singleton
    fun bindSetDateHeader(usecase: ISetDateHeader): SetDateHeader

    @Binds
    @Singleton
    fun bindSetTurnHeader(usecase: ISetTurnHeader): SetTurnHeader

    @Binds
    @Singleton
    fun bindCheckNroOS(usecase: ICheckOS): CheckOS

    @Binds
    @Singleton
    fun bindSetNroOSHeader(usecase: ISetOSHeader): SetOSHeader

    @Binds
    @Singleton
    fun bindCheckSection(usecase: ICheckSection): CheckSection

    @Binds
    @Singleton
    fun bindSetSectionHeader(usecase: ISetSectionHeader): SetSectionHeader

}