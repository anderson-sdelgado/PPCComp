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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
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
                    idHeader = 1,
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
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
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
        }
}