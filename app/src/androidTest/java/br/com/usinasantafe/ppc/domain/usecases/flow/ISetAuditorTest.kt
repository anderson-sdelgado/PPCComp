package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ISetAuditorTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetAuditorHeader

    @Inject
    lateinit var datasource: IHeaderSharedPreferencesDatasource

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_insert_data_if_table_is_empty() =
        runTest {
            val resultGet = datasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val modelBefore = resultGet.getOrNull()!!
            assertEquals(
                modelBefore.regAuditor1,
                null
            )
            assertEquals(
                modelBefore.regAuditor2,
                null
            )
            assertEquals(
                modelBefore.regAuditor3,
                null
            )
            val result = usecase(
                pos = 1,
                regAuditor = "12345"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.regAuditor1,
                12345L
            )
            assertEquals(
                modelAfter.regAuditor2,
                null
            )
            assertEquals(
                modelAfter.regAuditor3,
                null
            )
        }

    @Test
    fun check_insert_data_in_position_2() =
        runTest {
            datasource.save(
                HeaderSharedPreferencesModel(
                    regAuditor1 = 12345L
                )
            )
            val resultGet = datasource.get()
            assertEquals(
                resultGet.isSuccess,
                true
            )
            val modelBefore = resultGet.getOrNull()!!
            assertEquals(
                modelBefore.regAuditor1,
                12345L
            )
            assertEquals(
                modelBefore.regAuditor2,
                null
            )
            assertEquals(
                modelBefore.regAuditor3,
                null
            )
            val result = usecase(
                pos = 2,
                regAuditor = "19759"
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = datasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.regAuditor1,
                12345L
            )
            assertEquals(
                modelAfter.regAuditor2,
                19759L
            )
            assertEquals(
                modelAfter.regAuditor3,
                null
            )
        }

}