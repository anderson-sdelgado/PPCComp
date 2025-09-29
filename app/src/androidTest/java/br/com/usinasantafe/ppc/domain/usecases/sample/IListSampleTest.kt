package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.presenter.model.SampleScreenModel
import br.com.usinasantafe.ppc.utils.Status
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.util.Date
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class IListSampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: ListSample

    @Inject
    lateinit var headerDao: HeaderDao

    @Inject
    lateinit var sampleDao: SampleDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_empty_list_if_not_have_data_in_header_room() =
        runTest {
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<SampleScreenModel>()
            )
        }

    @Test
    fun check_return_failure_if_not_have_data_in_sample_room() =
        runTest {
            initialRegister(1)
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                emptyList<SampleScreenModel>()
            )
        }


    private suspend fun initialRegister(level: Int) {

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
                status = Status.FINISH
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
                status = Status.OPEN
            ),
        )

        if (level == 1) return

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
                idHeader = 3,
                pos = 1,
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
        sampleDao.insert(
            SampleRoomModel(
                idHeader = 3,
                pos = 2,
                tare = 4.0,
                stalk = 4.0,
                wholeCane = 4.0,
                stump = 4.0,
                piece = 4.0,
                tip = 4.0,
                slivers = 4.0,
                stone = false,
                treeStump = true,
                weed = true,
                anthill = false,
                guineaGrass = true,
                castorOilPlant = true,
                signalGrass = false,
                mucuna = false,
                silkGrass = true,
            )
        )

        if (level == 2) return

    }

}