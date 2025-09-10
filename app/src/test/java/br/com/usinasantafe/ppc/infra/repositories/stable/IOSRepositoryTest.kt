package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.OSRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.stable.OSSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.OSRetrofitModel
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.stable.OSSharedPreferencesModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class IOSRepositoryTest {

    private val osSharedPreferencesDatasource = mock<OSSharedPreferencesDatasource>()
    private val osRetrofitDatasource = mock<OSRetrofitDatasource>()
    private val repository = IOSRepository(
        osSharedPreferencesDatasource = osSharedPreferencesDatasource,
        osRetrofitDatasource = osRetrofitDatasource
    )

    @Test
    fun `deleteAll - Check return failure if have error in OSSharePreferencesDatasource clean`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.clean()
            ).thenReturn(
                resultFailure(
                    "IOSSharePreferencesDatasource.clean",
                    "-",
                    Exception()
                )
            )
            val result = repository.deleteAll()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.deleteAll -> IOSSharePreferencesDatasource.clean"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.clean()
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.deleteAll()
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
    fun `getByNroOS - Check return failure if have error in osRetrofitDatasource getByNroOS`() =
        runTest {
            whenever(
                osRetrofitDatasource.getByNroOS(
                    token = "token",
                    nroOS = 1
                )
            ).thenReturn(
                resultFailure(
                    "IosRetrofitDatasource.getByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = repository.getByNroOS(
                token = "token",
                nroOS = 1
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.getByNroOS -> IosRetrofitDatasource.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getByNroOS - Check return data if function execute successfully`() =
        runTest {
            whenever(
                osRetrofitDatasource.getByNroOS(
                    token = "token",
                    nroOS = 1
                )
            ).thenReturn(
                Result.success(
                    OSRetrofitModel(
                        nroOS = 1,
                        idSection = 1
                    )
                )
            )
            val result = repository.getByNroOS(
                token = "token",
                nroOS = 1
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                OS(
                    nroOS = 1,
                    idSection = 1
                )
            )
        }
    
    @Test
    fun `add - Check return failure if have error in OSSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.save(
                    OSSharedPreferencesModel(
                        nroOS = 1,
                        idSection = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IOSSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.add(
                OS(
                    nroOS = 1,
                    idSection = 1
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.add -> IOSSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `add - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.save(
                    OSSharedPreferencesModel(
                        nroOS = 1,
                        idSection = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.add(
                OS(
                    nroOS = 1,
                    idSection = 1
                )
            )
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
    fun `checkSectionAndOS - Check return failure if have error in OSSharedPreferencesDatasource checkHas`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.checkHas()
            ).thenReturn(
                resultFailure(
                    "IOSSharedPreferencesDatasource.checkHas",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkSectionAndOS(
                idSection = 1,
                nroOS = 123456
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.checkSectionAndOS -> IOSSharedPreferencesDatasource.checkHas"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkSectionAndOS - Check return true if not have data in OS shared preferences`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.checkHas()
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkSectionAndOS(
                idSection = 1,
                nroOS = 123456
            )
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
    fun `checkSectionAndOS - Check return failure if have error in OSSharedPreferencesDatasource checkNroAndIdSection`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.checkHas()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osSharedPreferencesDatasource.checkNroAndIdSection(
                    nroOS = 123456,
                    idSection = 1
                )
            ).thenReturn(
                resultFailure(
                    "IOSSharedPreferencesDatasource.checkNroAndIdSection",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkSectionAndOS(
                idSection = 1,
                nroOS = 123456
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IOSRepository.checkSectionAndOS -> IOSSharedPreferencesDatasource.checkNroAndIdSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkSectionAndOS - Check return true if function execute successfully and return true`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.checkHas()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osSharedPreferencesDatasource.checkNroAndIdSection(
                    nroOS = 123456,
                    idSection = 1
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkSectionAndOS(
                idSection = 1,
                nroOS = 123456
            )
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
    fun `checkSectionAndOS - Check return false if function execute successfully and return false`() =
        runTest {
            whenever(
                osSharedPreferencesDatasource.checkHas()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                osSharedPreferencesDatasource.checkNroAndIdSection(
                    nroOS = 123456,
                    idSection = 1
                )
            ).thenReturn(
                Result.success(false)
            )
            val result = repository.checkSectionAndOS(
                idSection = 1,
                nroOS = 123456
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }
}