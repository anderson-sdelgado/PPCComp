package br.com.usinasantafe.ppc.domain.usecases.sample

import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.external.sharedpreferences.datasource.variable.ISampleSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.variable.SampleSharedPreferencesModel
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
class ISetSubObsSampleTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: SetSubObsSample

    @Inject
    lateinit var sampleSharedPreferencesDatasource: ISampleSharedPreferencesDatasource

    @Inject
    lateinit var sampleDao: SampleDao

    @Inject
    lateinit var headerDao: HeaderDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_failure_if_not_have_data_in_sample_shared_preferences() =
        runTest {
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = true,
                silkGrass = false
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSubObsSample -> IAnalysisRepository.saveSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalArgumentException: Field 'tare' cannot be null."
            )
            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                null
            )
            assertEquals(
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                false
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                true
            )
            assertEquals(
                modelAfter.silkGrass,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun check_failure_if_not_have_header_open() =
        runTest {
            initialRegister()
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = true,
                silkGrass = false
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISetSubObsSample -> IAnalysisRepository.saveSample"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.IllegalArgumentException: Field 'idHeader' cannot be null."
            )
            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                1.0
            )
            assertEquals(
                modelAfter.stalk,
                2.0
            )
            assertEquals(
                modelAfter.wholeCane,
                3.0
            )
            assertEquals(
                modelAfter.stump,
                4.0
            )
            assertEquals(
                modelAfter.piece,
                5.0
            )
            assertEquals(
                modelAfter.tip,
                6.0
            )
            assertEquals(
                modelAfter.slivers,
                7.0
            )
            assertEquals(
                modelAfter.stone,
                true
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                true
            )
            assertEquals(
                modelAfter.anthill,
                false
            )
            assertEquals(
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                false
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                true
            )
            assertEquals(
                modelAfter.silkGrass,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                0
            )
        }

    @Test
    fun check_success_if_have_header_open() =
        runTest {
            initialRegister(2)
            val result = usecase(
                guineaGrass = true,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = true,
                silkGrass = false
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val resultGetAfter = sampleSharedPreferencesDatasource.get()
            assertEquals(
                resultGetAfter.isSuccess,
                true
            )
            val modelAfter = resultGetAfter.getOrNull()!!
            assertEquals(
                modelAfter.tare,
                1.0
            )
            assertEquals(
                modelAfter.stalk,
                2.0
            )
            assertEquals(
                modelAfter.wholeCane,
                3.0
            )
            assertEquals(
                modelAfter.stump,
                4.0
            )
            assertEquals(
                modelAfter.piece,
                5.0
            )
            assertEquals(
                modelAfter.tip,
                6.0
            )
            assertEquals(
                modelAfter.slivers,
                7.0
            )
            assertEquals(
                modelAfter.stone,
                true
            )
            assertEquals(
                modelAfter.treeStump,
                false
            )
            assertEquals(
                modelAfter.weed,
                true
            )
            assertEquals(
                modelAfter.anthill,
                false
            )
            assertEquals(
                modelAfter.guineaGrass,
                true
            )
            assertEquals(
                modelAfter.castorOilPlant,
                false
            )
            assertEquals(
                modelAfter.signalGrass,
                false
            )
            assertEquals(
                modelAfter.mucuna,
                true
            )
            assertEquals(
                modelAfter.silkGrass,
                false
            )
            val list = sampleDao.all()
            assertEquals(
                list.size,
                1
            )
            val model = list[0]
            assertEquals(
                model.idHeader,
                1
            )
            assertEquals(
                model.tare,
                1.0
            )
            assertEquals(
                model.stalk,
                2.0
            )
            assertEquals(
                model.wholeCane,
                3.0
            )
            assertEquals(
                model.stump,
                4.0
            )
            assertEquals(
                model.piece,
                5.0
            )
            assertEquals(
                model.tip,
                6.0
            )
            assertEquals(
                model.slivers,
                7.0
            )
            assertEquals(
                model.stone,
                true
            )
            assertEquals(
                model.treeStump,
                false
            )
            assertEquals(
                model.weed,
                true
            )
            assertEquals(
                model.anthill,
                false
            )
            assertEquals(
                model.guineaGrass,
                true
            )
            assertEquals(
                model.castorOilPlant,
                false
            )
            assertEquals(
                model.signalGrass,
                false
            )
            assertEquals(
                model.mucuna,
                true
            )
            assertEquals(
                model.silkGrass,
                false
            )
        }

    private suspend fun initialRegister(level: Int = 1) {

        sampleSharedPreferencesDatasource.save(
            SampleSharedPreferencesModel(
                tare = 1.0,
                stalk = 2.0,
                wholeCane = 3.0,
                stump = 4.0,
                piece = 5.0,
                tip = 6.0,
                slivers = 7.0,
                stone = true,
                treeStump = false,
                weed = true,
                anthill = false,
                guineaGrass = false,
                castorOilPlant = false,
                signalGrass = false,
                mucuna = false,
                silkGrass = false
            )
        )

        if (level == 1) return

        headerDao.insert(
            HeaderRoomModel(
                regAuditor1 = 1,
                regAuditor2 = 2,
                regAuditor3 = 3,
                date = Date(),
                nroTurn = 4,
                codSection = 5,
                nroPlot = 6,
                nroOS = 7,
                codFront = 8,
                nroHarvester = 9,
                regOperator = 10,
                status = Status.OPEN
            )
        )

        if (level == 2) return

    }

}