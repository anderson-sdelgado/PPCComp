package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.SectionRoomDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable.OSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class ICheckSectionTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckSection

    @Inject
    lateinit var sectionDao: SectionDao

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var osSharedPreferencesDatasource: OSSharedPreferencesDatasource

    @Test
    fun check_return_failure_if_codSection_is_incorrect() =
        runTest {

            hiltRule.inject()

            val result = usecase(codSection = "200a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"200a\""
            )
        }

    @Test
    fun check_return_false_if_not_have_data_in_section_room() =
        runTest {

            hiltRule.inject()

            val result = usecase(codSection = "200")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )

        }

    @Test
    fun check_return_failure_if_not_have_data_in_header_shared_preferences() =
        runTest {

            hiltRule.inject()

            initialRegister()

            val result = usecase(codSection = "200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckSection -> IAnalysisRepository.getOSHeaderOpen -> IHeaderSharedPreferencesDatasource.getOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_true_if_not_have_data_in_os_shared_preferences() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            val result = usecase(codSection = "200")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )

        }

    @Test
    fun check_return_false_if_os_shared_preferences_is_different() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            val result = usecase(codSection = "500")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )

        }

    @Test
    fun check_return_true_if_os_shared_preferences_is_equals() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            val result = usecase(codSection = "200")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )

        }

    private suspend fun initialRegister(level: Int = 1) {

        sectionDao.insertAll(
            listOf(
                SectionRoomModel(
                    idSection = 1,
                    codSection = 200
                ),
                SectionRoomModel(
                    idSection = 2,
                    codSection = 500
                )
            )
        )

        if (level == 1) return

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                nroOS = 123456
            )
        )

        if (level == 2) return

        osSharedPreferencesDatasource.save(
            OSSharedPreferencesModel(
                nroOS = 123456,
                idSection = 1
            )
        )

        if (level == 3) return

    }

}