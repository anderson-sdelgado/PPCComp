package br.com.usinasantafe.ppc.di.datasource.sharedpreferences

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.stable.IOSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable.OSSharedPreferencesDatasource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableSharedPreferencesDatasourceModule {

    @Binds
    @Singleton
    fun bindOSSharedPreferencesDatasource(dataSource: IOSSharedPreferencesDatasource): OSSharedPreferencesDatasource

}