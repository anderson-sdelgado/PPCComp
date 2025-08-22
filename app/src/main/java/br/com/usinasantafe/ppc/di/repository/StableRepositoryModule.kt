package br.com.usinasantafe.ppc.di.repository

import br.com.usinasantafe.ppc.domain.repositories.stable.ColabRepository
import br.com.usinasantafe.ppc.infra.repositories.stable.IColabRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StableRepositoryModule {

    @Binds
    @Singleton
    fun bindColabRepository(repository: IColabRepository): ColabRepository

}