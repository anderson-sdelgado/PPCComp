package br.com.usinasantafe.ppc.di.external.retrofit

import br.com.usinasantafe.ppc.di.provider.DefaultRetrofit
import br.com.usinasantafe.ppc.external.retrofit.api.stable.ColabApi
import br.com.usinasantafe.ppc.external.retrofit.api.stable.HarvesterApi
import br.com.usinasantafe.ppc.external.retrofit.api.stable.OSApi
import br.com.usinasantafe.ppc.external.retrofit.api.stable.PlotApi
import br.com.usinasantafe.ppc.external.retrofit.api.stable.SectionApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRetrofitModule {

    @Provides
    @Singleton
    fun colabApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): ColabApi = retrofit.create(ColabApi::class.java)

    @Provides
    @Singleton
    fun harvesterApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): HarvesterApi = retrofit.create(HarvesterApi::class.java)

    @Provides
    @Singleton
    fun osApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): OSApi = retrofit.create(OSApi::class.java)

    @Provides
    @Singleton
    fun plotApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): PlotApi = retrofit.create(PlotApi::class.java)

    @Provides
    @Singleton
    fun sectionApiRetrofit(
        @DefaultRetrofit retrofit: Retrofit
    ): SectionApi = retrofit.create(SectionApi::class.java)

}