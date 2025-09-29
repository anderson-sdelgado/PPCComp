package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.utils.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test
import kotlin.test.assertEquals

@HiltAndroidTest
class IDeleteAnalysisTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: DeleteAnalysis

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_alter_data() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    regAuditor1 = 1,
                    regAuditor2 = 2,
                    regAuditor3 = 3,
                    date = Date(),
                    nroTurn = 1,
                    codSection = 1,
                    nroPlot = 1,
                    nroOS = 1,
                    codFront = 1,
                    nroHarvester = 1,
                    regOperator = 1,
                ),
            )
            headerDao.insert(
                HeaderRoomModel(
                    regAuditor1 = 10,
                    regAuditor2 = 20,
                    regAuditor3 = 30,
                    date = Date(),
                    nroTurn = 2,
                    codSection = 2,
                    nroPlot = 2,
                    nroOS = 2,
                    codFront = 2,
                    nroHarvester = 2,
                    regOperator = 2,
                    status = Status.OPEN
                ),
            )
            headerDao.insert(
                HeaderRoomModel(
                    regAuditor1 = 100,
                    regAuditor2 = 200,
                    regAuditor3 = 300,
                    date = Date(),
                    nroTurn = 3,
                    codSection = 3,
                    nroPlot = 3,
                    nroOS = 3,
                    codFront = 3,
                    nroHarvester = 3,
                    regOperator = 3,
                    status = Status.FINISH
                ),
            )
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val list = headerDao.all()
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
                model1.regAuditor1,
                1L
            )
            assertEquals(
                model1.regAuditor2,
                2L
            )
            assertEquals(
                model1.regAuditor3,
                3L
            )
            assertEquals(
                model1.nroTurn,
                1
            )
            assertEquals(
                model1.codSection,
                1
            )
            assertEquals(
                model1.nroPlot,
                1
            )
            assertEquals(
                model1.nroOS,
                1
            )
            assertEquals(
                model1.codFront,
                1
            )
            assertEquals(
                model1.nroHarvester,
                1
            )
            assertEquals(
                model1.regOperator,
                1L
            )
            assertEquals(
                model1.status,
                Status.CLOSE
            )
            val model2 = list[1]
            assertEquals(
                model2.id,
                3
            )
            assertEquals(
                model2.regAuditor1,
                100L
            )
            assertEquals(
                model2.regAuditor2,
                200L
            )
            assertEquals(
                model2.regAuditor3,
                300L
            )
            assertEquals(
                model2.nroTurn,
                3
            )
            assertEquals(
                model2.codSection,
                3
            )
            assertEquals(
                model2.nroPlot,
                3
            )
            assertEquals(
                model2.nroOS,
                3
            )
            assertEquals(
                model2.codFront,
                3
            )
            assertEquals(
                model2.nroHarvester,
                3
            )
            assertEquals(
                model2.regOperator,
                3L
            )
            assertEquals(
                model2.status,
                Status.FINISH
            )
            val listSample = sampleDao.all()
            assertEquals(
                listSample.size,
                2
            )
            val modelSample1 = listSample[0]
            assertEquals(
                modelSample1.idHeader,
                1
            )
            assertEquals(
                modelSample1.tare,
                1.0
            )
            assertEquals(
                modelSample1.stalk,
                1.0
            )
            assertEquals(
                modelSample1.wholeCane,
                1.0
            )
            assertEquals(
                modelSample1.stump,
                1.0
            )
            assertEquals(
                modelSample1.piece,
                1.0
            )
            assertEquals(
                modelSample1.tip,
                1.0
            )
            assertEquals(
                modelSample1.slivers,
                1.0
            )
            assertEquals(
                modelSample1.stone,
                true
            )
            assertEquals(
                modelSample1.treeStump,
                true
            )
            assertEquals(
                modelSample1.weed,
                true
            )
            assertEquals(
                modelSample1.anthill,
                true
            )
            assertEquals(
                modelSample1.guineaGrass,
                true
            )
            assertEquals(
                modelSample1.castorOilPlant,
                true
            )
            assertEquals(
                modelSample1.signalGrass,
                true
            )
            assertEquals(
                modelSample1.mucuna,
                true
            )
            assertEquals(
                modelSample1.silkGrass,
                true
            )
            val modelSample2 = listSample[1]
            assertEquals(
                modelSample2.idHeader,
                1
            )
            assertEquals(
                modelSample2.tare,
                3.0
            )
            assertEquals(
                modelSample2.stalk,
                3.0
            )
            assertEquals(
                modelSample2.wholeCane,
                3.0
            )
            assertEquals(
                modelSample2.stump,
                3.0
            )
            assertEquals(
                modelSample2.piece,
                3.0
            )
            assertEquals(
                modelSample2.tip,
                3.0
            )
            assertEquals(
                modelSample2.slivers,
                3.0
            )
            assertEquals(
                modelSample2.stone,
                false
            )
            assertEquals(
                modelSample2.treeStump,
                true
            )
            assertEquals(
                modelSample2.weed,
                true
            )
            assertEquals(
                modelSample2.anthill,
                false
            )
            assertEquals(
                modelSample2.guineaGrass,
                true
            )
            assertEquals(
                modelSample2.castorOilPlant,
                true
            )
            assertEquals(
                modelSample2.signalGrass,
                true
            )
            assertEquals(
                modelSample2.mucuna,
                true
            )
            assertEquals(
                modelSample2.silkGrass,
                true
            )
        }

}