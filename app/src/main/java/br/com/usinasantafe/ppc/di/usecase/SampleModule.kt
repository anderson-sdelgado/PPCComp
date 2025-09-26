package br.com.usinasantafe.ppc.di.usecase

import br.com.usinasantafe.ppc.domain.usecases.sample.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface SampleModule {

    @Binds
    @Singleton
    fun bindDeleteSample(usecase: IDeleteSample): DeleteSample

    @Binds
    @Singleton
    fun bindListSample(usecase: IListSample): ListSample

    @Binds
    @Singleton
    fun bindSetFieldSample(usecase: ISetWeightSample): SetWeightSample

    @Binds
    @Singleton
    fun bindCheckTare(usecase: ICheckWeightRelationTare): CheckWeightRelationTare

    @Binds
    @Singleton
    fun bindSetObsSample(usecase: ISetObsSample): SetObsSample

    @Binds
    @Singleton
    fun bindSetSubObsSample(usecase: ISetSubObsSample): SetSubObsSample
}