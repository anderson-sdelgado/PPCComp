package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.IHeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.variable.HeaderSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.HeaderSharedPreferencesModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Rule
import javax.inject.Inject
import kotlin.test.Test

@HiltAndroidTest
class ICheckPlotTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckPlot

    @Inject
    lateinit var headerSharedPreferencesDatasource: IHeaderSharedPreferencesDatasource

    @Inject
    lateinit var sectionDao: SectionDao

    @Inject
    lateinit var plotDao: PlotDao

    @Test
    fun check_return_failure_if_nroPlot_is_invalid() =
        runTest {

            hiltRule.inject()

            val result = usecase("2a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPlot"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"2a\""
            )
        }

    @Test
    fun check_return_failure_if_header_shared_preferences_not_have_data() =
        runTest {

            hiltRule.inject()

            val result = usecase("20")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckPlot -> IAnalysisRepository.getSectionHeader -> IHeaderSharedPreferencesDatasource.getSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NullPointerException"
            )
        }

    @Test
    fun check_return_false_if_section_room_not_have_data() =
        runTest {

            hiltRule.inject()

            initialRegister()

            val result = usecase("20")
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
    fun check_return_false_if_plot_room_not_have_data() =
        runTest {

            hiltRule.inject()

            initialRegister(2)

            val result = usecase("20")
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
    fun check_return_true_if_data_is_correct() =
        runTest {

            hiltRule.inject()

            initialRegister(3)

            val result = usecase("20")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }



    private fun initialRegister(level: Int = 1) {

        headerSharedPreferencesDatasource.save(
            HeaderSharedPreferencesModel(
                codSection = 20
            )
        )

        if (level == 1) return

        sectionDao.insertAll(
            listOf(
                SectionRoomModel(
                    idSection = 1,
                    codSection = 20
                )
            )
        )

        if (level == 2) return

        plotDao.insertAll(
            listOf(
                PlotRoomModel(
                    idPlot = 1,
                    nroPlot = 20,
                    idSection = 1,
                )
            )
        )

        if (level == 3) return

    }
}