package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IDeleteSampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: DeleteSample

    @Inject
    lateinit var sampleDao: SampleDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_delete_data() =
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
                    pos = 3,
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
            val result = usecase(1)
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

}