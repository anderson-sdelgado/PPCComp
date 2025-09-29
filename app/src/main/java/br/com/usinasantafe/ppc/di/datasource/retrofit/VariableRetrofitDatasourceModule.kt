package br.com.usinasantafe.ppc.di.datasource.retrofit

import br.com.usinasantafe.ppc.external.retrofit.datasource.variable.*
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableRetrofitDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigRetrofitDatasource(dataSource: IConfigRetrofitDatasource): ConfigRetrofitDatasource

    @Binds
    @Singleton
    fun bindAnalysisRetrofitDatasource(dataSource: IAnalysisRetrofitDatasource): AnalysisRetrofitDatasource

}