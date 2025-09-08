package br.com.usinasantafe.ppc.di.datasource.sharedpreferences

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.*
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface VariableSharedPreferencesDatasourceModule {

    @Binds
    @Singleton
    fun bindConfigSharedPreferencesDatasource(dataSource: IConfigSharedPreferencesDatasource): ConfigSharedPreferencesDatasource

    @Binds
    @Singleton
    fun bindHeaderSharedPreferencesDatasource(dataSource: IHeaderSharedPreferencesDatasource): HeaderSharedPreferencesDatasource

}