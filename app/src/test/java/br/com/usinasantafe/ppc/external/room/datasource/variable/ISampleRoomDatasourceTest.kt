package br.com.usinasantafe.ppc.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ISampleRoomDatasourceTest {

    private lateinit var sampleDao: SampleDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: ISampleRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        sampleDao = db.sampleDao()
        datasource = ISampleRoomDatasource(sampleDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `countByIdHeader - Check return count correct`() =
        runTest {
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 1,
                    tare = 1,
                    billet = 1,
                    wholeCane = 1,
                    stump = 1,
                    chunk = 1,
                    pointer = 1,
                    chips = 1,
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 2,
                    tare = 2,
                    billet = 2,
                    wholeCane = 2,
                    stump = 2,
                    chunk = 2,
                    pointer = 2,
                    chips = 2,
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = true,
                    guineaGrass = false,
                    castorOilPlant = true,
                    signalGrass = false,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 2,
                    tare = 3,
                    billet = 2,
                    wholeCane = 2,
                    stump = 2,
                    chunk = 3,
                    pointer = 2,
                    chips = 2,
                    stone = true,
                    treeStump = false,
                    weed = true,
                    anthill = true,
                    guineaGrass = false,
                    castorOilPlant = true,
                    signalGrass = false,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            val result = datasource.countByIdHeader(2)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                2
            )
        }

}