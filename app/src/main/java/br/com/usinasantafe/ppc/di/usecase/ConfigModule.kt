package br.com.usinasantafe.ppc.di.usecase

import br.com.usinasantafe.ppc.domain.usecases.config.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConfigModule {

    @Binds
    @Singleton
    fun bindCheckAccessInitial(usecase: ICheckAccessInitial): CheckAccessInitial

    @Binds
    @Singleton
    fun bindCheckPassword(usecase: ICheckPassword): CheckPassword

    @Binds
    @Singleton
    fun bindGetConfigInternal(usecase: IGetConfigInternal): GetConfigInternal

    @Binds
    @Singleton
    fun bindSaveDataConfig(usecase: ISaveDataConfig): SaveDataConfig

    @Binds
    @Singleton
    fun bindSendDataConfig(usecase: ISendDataConfig): SendDataConfig

    @Binds
    @Singleton
    fun bindSetFinishUpdateAllTable(usecase: ISetFinishUpdateAllTable): SetFinishUpdateAllTable

    @Binds
    @Singleton
    fun bindGetToken(usecase: IGetToken): GetToken

    @Binds
    @Singleton
    fun bindGetStatusSend(usecase: IGetStatusSend): GetStatusSend

    @Binds
    @Singleton
    fun bindCheckUpdateApp(usecase: ICheckUpdateApp): CheckUpdateApp

    @Binds
    @Singleton
    fun bindUpdateApp(usecase: IUpdateApp): UpdateApp

}