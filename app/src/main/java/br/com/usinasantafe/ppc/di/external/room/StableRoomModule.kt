package br.com.usinasantafe.ppc.di.external.room

import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.external.room.dao.stable.OSDao
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StableRoomModule {

    @Provides
    @Singleton
    fun provideColabDao(database: DatabaseRoom): ColabDao {
        return database.colabDao()
    }

    @Provides
    @Singleton
    fun provideHarvesterDao(database: DatabaseRoom): HarvesterDao {
        return database.harvesterDao()
    }

    @Provides
    @Singleton
    fun provideOSDao(database: DatabaseRoom): OSDao {
        return database.osDao()
    }

    @Provides
    @Singleton
    fun providePlotDao(database: DatabaseRoom): PlotDao {
        return database.plotDao()
    }

    @Provides
    @Singleton
    fun provideSectionDao(database: DatabaseRoom): SectionDao {
        return database.sectionDao()
    }

}