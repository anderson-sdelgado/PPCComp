package br.com.usinasantafe.ppc.di.datasource.retrofit

import br.com.usinasantafe.ppc.external.retrofit.datasource.variable.IConfigRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.retrofit.variable.ConfigRetrofitDatasource
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

}