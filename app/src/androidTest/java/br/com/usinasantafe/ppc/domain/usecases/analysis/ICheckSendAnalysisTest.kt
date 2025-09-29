package br.com.usinasantafe.ppc.domain.usecases.analysis

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend
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
class ICheckSendAnalysisTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckSendAnalysis

    @Inject
    lateinit var headerDao: HeaderDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_false_if_not_have_data_to_send() =
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
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val check = result.getOrNull()!!
            assertEquals(
                check,
                false
            )
        }

    @Test
    fun check_return_true_if_have_data_to_send() =
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
                    status = Status.FINISH,
                    statusSend = StatusSend.SEND
                )
            )
            val result = usecase()
            assertEquals(
                result.isSuccess,
                true
            )
            val check = result.getOrNull()!!
            assertEquals(
                check,
                true
            )
        }

}