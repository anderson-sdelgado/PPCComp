package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.utils.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Date
import javax.inject.Inject

@HiltAndroidTest
class IListHeaderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListHeader

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun check_return_list_if_have_data_only_header_database() =
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
                    status = Status.CLOSE
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val modelScreen = list[0]
            assertEquals(
                modelScreen.id,
                1
            )
            assertEquals(
                modelScreen.harvester,
                1
            )
            assertEquals(
                modelScreen.turn,
                1
            )
            assertEquals(
                modelScreen.operator,
                1L
            )
            assertEquals(
                modelScreen.front,
                1
            )
            assertEquals(
                modelScreen.qtdSample,
                0
            )
            val modelScreen2 = list[1]
            assertEquals(
                modelScreen2.id,
                2
            )
            assertEquals(
                modelScreen2.harvester,
                2
            )
            assertEquals(
                modelScreen2.turn,
                2
            )
            assertEquals(
                modelScreen2.operator,
                2L
            )
            assertEquals(
                modelScreen2.front,
                2
            )
            assertEquals(
                modelScreen2.qtdSample,
                0
            )
        }

    @Test
    fun check_return_list_if_have_data_header_database_and_sample_database() =
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
                    status = Status.CLOSE
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
                    idHeader = 1,
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
                    slivers = 1.0,
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                2
            )
            val modelScreen = list[0]
            assertEquals(
                modelScreen.id,
                1
            )
            assertEquals(
                modelScreen.harvester,
                1
            )
            assertEquals(
                modelScreen.turn,
                1
            )
            assertEquals(
                modelScreen.operator,
                1L
            )
            assertEquals(
                modelScreen.front,
                1
            )
            assertEquals(
                modelScreen.qtdSample,
                2
            )
            val modelScreen2 = list[1]
            assertEquals(
                modelScreen2.id,
                2
            )
            assertEquals(
                modelScreen2.harvester,
                2
            )
            assertEquals(
                modelScreen2.turn,
                2
            )
            assertEquals(
                modelScreen2.operator,
                2L
            )
            assertEquals(
                modelScreen2.front,
                2
            )
            assertEquals(
                modelScreen2.qtdSample,
                1
            )
        }
}