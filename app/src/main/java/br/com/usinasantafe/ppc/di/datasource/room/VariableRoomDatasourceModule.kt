package br.com.usinasantafe.ppc.di.datasource.room

import br.com.usinasantafe.ppc.external.room.datasource.variable.*
import br.com.usinasantafe.ppc.infra.datasource.room.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindHeaderRoomDatasource(dataSource: IHeaderRoomDatasource): HeaderRoomDatasource

    @Binds
    @Singleton
    fun bindSampleRoomDatasource(dataSource: ISampleRoomDatasource): SampleRoomDatasource


}