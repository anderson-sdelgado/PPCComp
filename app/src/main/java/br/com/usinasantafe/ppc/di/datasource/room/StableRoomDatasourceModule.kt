package br.com.usinasantafe.ppc.di.datasource.room

import br.com.usinasantafe.ppc.external.room.datasource.stable.*
import br.com.usinasantafe.ppc.infra.datasource.room.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRoomDatasourceModule {

    @Binds
    @Singleton
    fun bindColabRoomDatasource(dataSource: IColabRoomDatasource): ColabRoomDatasource

    @Binds
    @Singleton
    fun bindHarvesterRoomDatasource(dataSource: IHarvesterRoomDatasource): HarvesterRoomDatasource

    @Binds
    @Singleton
    fun bindOSRoomDatasource(dataSource: IOSRoomDatasource): OSRoomDatasource

    @Binds
    @Singleton
    fun bindPlotRoomDatasource(dataSource: IPlotRoomDatasource): PlotRoomDatasource

    @Binds
    @Singleton
    fun bindSectionRoomDatasource(dataSource: ISectionRoomDatasource): SectionRoomDatasource


}