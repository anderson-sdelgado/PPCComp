package br.com.usinasantafe.ppc.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf
import kotlin.test.Test
import kotlin.test.assertEquals

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
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
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
                    pos = 2,
                    tare = 2.0,
                    stalk = 2.0,
                    wholeCane = 2.0,
                    stump = 2.0,
                    piece = 2.0,
                    tip = 2.0,
                    slivers = 2.0,
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
                    pos = 1,
                    tare = 3.0,
                    stalk = 3.0,
                    wholeCane = 3.0,
                    stump = 3.0,
                    piece = 3.0,
                    tip = 3.0,
                    slivers = 3.0,
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

    @Test
    fun `listByIdHeader - Check return list correct`() =
        runTest {
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 1,
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
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
                    pos = 1,
                    tare = 2.0,
                    stalk = 2.0,
                    wholeCane = 2.0,
                    stump = 2.0,
                    piece = 2.0,
                    tip = 2.0,
                    slivers = 2.0,
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
                    idHeader = 1,
                    pos = 2,
                    tare = 3.0,
                    stalk = 3.0,
                    wholeCane = 3.0,
                    stump = 3.0,
                    piece = 3.0,
                    tip = 3.0,
                    slivers = 3.0,
                    stone = false,
                    treeStump = true,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            val result = datasource.listByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.id,
                1
            )
            assertEquals(
                model1.tare,
                1.0
            )
            assertEquals(
                model1.stalk,
                1.0
            )
            assertEquals(
                model1.wholeCane,
                1.0
            )
            assertEquals(
                model1.stump,
                1.0
            )
            assertEquals(
                model1.piece,
                1.0
            )
            assertEquals(
                model1.tip,
                1.0
            )
            assertEquals(
                model1.slivers,
                1.0
            )
            assertEquals(
                model1.stone,
                true
            )
            assertEquals(
                model1.treeStump,
                true
            )
            assertEquals(
                model1.weed,
                true
            )
            assertEquals(
                model1.anthill,
                true
            )
            assertEquals(
                model1.guineaGrass,
                true
            )
            assertEquals(
                model1.castorOilPlant,
                true
            )
            assertEquals(
                model1.signalGrass,
                true
            )
            assertEquals(
                model1.mucuna,
                true
            )
            assertEquals(
                model1.silkGrass,
                true
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                3
            )
            assertEquals(
                model2.tare,
                3.0,
            )
            assertEquals(
                model2.stalk,
                3.0,
            )
            assertEquals(
                model2.wholeCane,
                3.0,
            )
            assertEquals(
                model2.stump,
                3.0,
            )
            assertEquals(
                model2.piece,
                3.0,
            )
            assertEquals(
                model2.tip,
                3.0,
            )
            assertEquals(
                model2.slivers,
                3.0,
            )
            assertEquals(
                model2.stone,
                false
            )
            assertEquals(
                model2.treeStump,
                true
            )
            assertEquals(
                model2.weed,
                true
            )
            assertEquals(
                model2.anthill,
                false
            )
            assertEquals(
                model2.guineaGrass,
                true
            )
            assertEquals(
                model2.castorOilPlant,
                true
            )
            assertEquals(
                model2.signalGrass,
                true
            )
            assertEquals(
                model2.mucuna,
                true
            )
            assertEquals(
                model2.silkGrass,
                true
            )
        }

    @Test
    fun `deleteByIdHeader - Check return list correct`() =
        runTest {
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 1,
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
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
                    pos = 2,
                    tare = 2.0,
                    stalk = 2.0,
                    wholeCane = 2.0,
                    stump = 2.0,
                    piece = 2.0,
                    tip = 2.0,
                    slivers = 2.0,
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
                    idHeader = 1,
                    pos = 2,
                    tare = 3.0,
                    stalk = 3.0,
                    wholeCane = 3.0,
                    stump = 3.0,
                    piece = 3.0,
                    tip = 3.0,
                    slivers = 3.0,
                    stone = false,
                    treeStump = true,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            val result = datasource.deleteByIdHeader(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                1
            )
            val model1 = list[0]
            assertEquals(
                model1.idHeader,
                2
            )
            assertEquals(
                model1.tare,
                2.0
            )
            assertEquals(
                model1.stalk,
                2.0
            )
            assertEquals(
                model1.wholeCane,
                2.0
            )
            assertEquals(
                model1.stump,
                2.0
            )
            assertEquals(
                model1.piece,
                2.0
            )
            assertEquals(
                model1.tip,
                2.0
            )
            assertEquals(
                model1.slivers,
                2.0
            )
            assertEquals(
                model1.stone,
                true
            )
            assertEquals(
                model1.treeStump,
                true
            )
            assertEquals(
                model1.weed,
                true
            )
            assertEquals(
                model1.anthill,
                true
            )
            assertEquals(
                model1.guineaGrass,
                true
            )
            assertEquals(
                model1.castorOilPlant,
                true
            )
            assertEquals(
                model1.signalGrass,
                true
            )
            assertEquals(
                model1.mucuna,
                true
            )
            assertEquals(
                model1.silkGrass,
                true
            )
        }

    @Test
    fun `deleteById - Check return list correct`() =
        runTest {
            sampleDao.insert(
                SampleRoomModel(
                    idHeader = 1,
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
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
                    pos = 1,
                    tare = 2.0,
                    stalk = 2.0,
                    wholeCane = 2.0,
                    stump = 2.0,
                    piece = 2.0,
                    tip = 2.0,
                    slivers = 2.0,
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
                    idHeader = 1,
                    pos = 2,
                    tare = 3.0,
                    stalk = 3.0,
                    wholeCane = 3.0,
                    stump = 3.0,
                    piece = 3.0,
                    tip = 3.0,
                    slivers = 3.0,
                    stone = false,
                    treeStump = true,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = true,
                    silkGrass = true,
                )
            )
            val result = datasource.deleteById(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                2
            )
            val model1 = list[0]
            assertEquals(
                model1.idHeader,
                2
            )
            assertEquals(
                model1.tare,
                2.0,
                0.0
            )
            assertEquals(
                model1.stalk,
                2.0
            )
            assertEquals(
                model1.wholeCane,
                2.0
            )
            assertEquals(
                model1.stump,
                2.0
            )
            assertEquals(
                model1.piece,
                2.0
            )
            assertEquals(
                model1.tip,
                2.0
            )
            assertEquals(
                model1.slivers,
                2.0
            )
            assertEquals(
                model1.stone,
                true
            )
            assertEquals(
                model1.treeStump,
                true
            )
            assertEquals(
                model1.weed,
                true
            )
            assertEquals(
                model1.anthill,
                true
            )
            assertEquals(
                model1.guineaGrass,
                true
            )
            assertEquals(
                model1.castorOilPlant,
                true
            )
            assertEquals(
                model1.signalGrass,
                true
            )
            assertEquals(
                model1.mucuna,
                true
            )
            assertEquals(
                model1.silkGrass,
                true
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                3
            )
            assertEquals(
                model2.tare,
                3.0
            )
            assertEquals(
                model2.stalk,
                3.0
            )
            assertEquals(
                model2.wholeCane,
                3.0
            )
            assertEquals(
                model2.stump,
                3.0
            )
            assertEquals(
                model2.piece,
                3.0
            )
            assertEquals(
                model2.tip,
                3.0
            )
            assertEquals(
                model2.slivers,
                3.0
            )
            assertEquals(
                model2.stone,
                false
            )
            assertEquals(
                model2.treeStump,
                true
            )
            assertEquals(
                model2.weed,
                true
            )
            assertEquals(
                model2.anthill,
                false
            )
            assertEquals(
                model2.guineaGrass,
                true
            )
            assertEquals(
                model2.castorOilPlant,
                true
            )
            assertEquals(
                model2.signalGrass,
                true
            )
            assertEquals(
                model2.mucuna,
                true
            )
            assertEquals(
                model2.silkGrass,
                true
            )
        }

    @Test
    fun `save - Check if data was saved correctly`() =
        runTest {
            val listBefore = sampleDao.all()
            assertEquals(
                listBefore.size,
                0
            )
            val result = datasource.save(
                SampleRoomModel(
                    idHeader = 1,
                    pos = 1,
                    tare = 1.0,
                    stalk = 1.0,
                    wholeCane = 1.0,
                    stump = 1.0,
                    piece = 1.0,
                    tip = 1.0,
                    slivers = 1.0,
                    stone = true,
                    treeStump = true,
                    weed = true,
                    anthill = false,
                    guineaGrass = true,
                    castorOilPlant = true,
                    signalGrass = true,
                    mucuna = false,
                    silkGrass = true,
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val listAfter = sampleDao.all()
            assertEquals(
                listAfter.size,
                1
            )
            val model = listAfter[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.tare,
                1.0
            )
            assertEquals(
                model.stalk,
                1.0
            )
            assertEquals(
                model.wholeCane,
                1.0
            )
            assertEquals(
                model.stump,
                1.0
            )
            assertEquals(
                model.piece,
                1.0
            )
            assertEquals(
                model.tip,
                1.0
            )
            assertEquals(
                model.slivers,
                1.0
            )
            assertEquals(
                model.stone,
                true
            )
            assertEquals(
                model.treeStump,
                true
            )
            assertEquals(
                model.weed,
                true
            )
            assertEquals(
                model.anthill,
                false
            )
            assertEquals(
                model.guineaGrass,
                true
            )
        }
}