package br.com.usinasantafe.ppc.di.external.room

import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.variable.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VariableRoomModule {

    @Provides
    @Singleton
    fun provideHeaderDao(database: DatabaseRoom): HeaderDao {
        return database.headerDao()
    }

    @Provides
    @Singleton
    fun provideSampleDao(database: DatabaseRoom): SampleDao {
        return database.sampleDao()
    }


}