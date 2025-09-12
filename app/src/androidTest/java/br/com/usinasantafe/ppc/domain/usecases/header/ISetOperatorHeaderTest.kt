package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
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
class ISetOperatorHeaderTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetOperatorHeader

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var headerDao: HeaderDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_failure_return_if_not_have_data_in_header_shared_preferences() =
        runTest {
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetOperatorHeader -> IAnalysisRepository.setOperatorHeader"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
            val resultGet = headerSharedPreferencesDatasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val model = resultGet.getOrNull()!!
            assertEquals(
                model.regOperator,
                19759L
            )
            assertEquals(
                model.regAuditor1,
                null
            )
        }

    @Test
    fun check_data_if_process_execute_successfully() =
        runTest {
            val date = Date()
            headerSharedPreferencesDatasource.save(
                HeaderSharedPreferencesModel(
                    regAuditor1 = 19759L,
                    date = date,
                    nroTurn = 2,
                    codSection = 35,
                    nroPlot = 2,
                    nroOS = 213456,
                    codFront = 5,
                    nroHarvester = 2200,
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isSuccess,
                true
            )
            val list = headerDao.listByStatus(Status.OPEN)
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.regOperator,
                19759L
            )
            assertEquals(
                model.regAuditor1,
                19759L
            )
            assertEquals(
                model.regAuditor2,
                null
            )
            assertEquals(
                model.regAuditor3,
                null
            )
            assertEquals(
                model.nroTurn,
                2
            )
            assertEquals(
                model.codSection,
                35
            )
            assertEquals(
                model.nroPlot,
                2
            )
            assertEquals(
                model.nroOS,
                213456
            )
            assertEquals(
                model.codFront,
                5
            )
            assertEquals(
                model.nroHarvester,
                2200
            )
            assertEquals(
                model.regOperator,
                19759L
            )
            assertEquals(
                model.status,
                Status.OPEN
            )
        }
}