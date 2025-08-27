package br.com.usinasantafe.ppc.di.datasource.retrofit

import br.com.usinasantafe.ppc.external.retrofit.datasource.stable.IColabRetrofitDatasource
import br.com.usinasantafe.ppc.external.retrofit.datasource.stable.IHarvesterRetrofitDatasource
import br.com.usinasantafe.ppc.external.retrofit.datasource.stable.IOSRetrofitDatasource
import br.com.usinasantafe.ppc.external.retrofit.datasource.stable.IPlotRetrofitDatasource
import br.com.usinasantafe.ppc.external.retrofit.datasource.stable.ISectionRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindColabRetrofitDatasource(dataSource: IColabRetrofitDatasource): ColabRetrofitDatasource

    @Binds
    @Singleton
    fun bindHarvesterRetrofitDatasource(dataSource: IHarvesterRetrofitDatasource): HarvesterRetrofitDatasource

    @Binds
    @Singleton
    fun bindOSRetrofitDatasource(dataSource: IOSRetrofitDatasource): OSRetrofitDatasource

    @Binds
    @Singleton
    fun bindPlotRetrofitDatasource(dataSource: IPlotRetrofitDatasource): PlotRetrofitDatasource

    @Binds
    @Singleton
    fun bindSectionRetrofitDatasource(dataSource: ISectionRetrofitDatasource): SectionRetrofitDatasource

}