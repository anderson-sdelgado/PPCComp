package br.com.usinasantafe.ppc.infra.repositories.variable

import br.com.usinasantafe.ppc.domain.entities.variable.Config
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.sharedpreferences.ConfigSharedPreferencesDatasource
import br.com.usinasantafe.ppc.infra.models.sharedpreferences.ConfigSharedPreferencesModel
import br.com.usinasantafe.ppc.utils.StatusSend
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.text.get

class IConfigRepositoryTest {

    private val configSharedPreferencesDatasource = mock<ConfigSharedPreferencesDatasource>()
    private val repository = IConfigRepository(
        configSharedPreferencesDatasource = configSharedPreferencesDatasource
    )

    @Test
    fun `hasConfig - Check return failure if have error in ConfigSharedPreferencesDatasource has`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.has",
                    "-",
                    Exception()
                )
            )
            val result = repository.hasConfig()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.hasConfig -> IConfigSharedPreferencesDatasource.has"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `hasConfig - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.has()
            ).thenReturn(
                Result.success(
                    false
                )
            )
            val result = repository.hasConfig()
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
    fun `getPassword - Check return failure if have error in ConfigSharedPreferencesDatasource getPassword`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.getPassword",
                    "-",
                    Exception()
                )
            )
            val result = repository.getPassword()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.getPassword -> IConfigSharedPreferencesDatasource.getPassword"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `getPassword - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.getPassword()
            ).thenReturn(
                Result.success("12345")
            )
            val result = repository.getPassword()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                "12345"
            )
        }

    @Test
    fun `get - Check return failure if have error in ConfigSharedPreferencesDatasource get`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.get",
                    "-",
                    Exception()
                )
            )
            val result = repository.get()
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.get -> IConfigSharedPreferencesDatasource.get"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `get - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.get()
            ).thenReturn(
                Result.success(
                    ConfigSharedPreferencesModel(
                        statusSend = StatusSend.SENT
                    )
                )
            )
            val result = repository.get()
            assertEquals(
                result.isSuccess,
                true
            )
            val sharedPreferencesModel = result.getOrNull()!!
            assertEquals(
                sharedPreferencesModel.statusSend,
                StatusSend.SENT
            )
        }

    @Test
    fun `save - Check return failure if have error in ConfigSharedPreferencesDatasource save`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                resultFailure(
                    "IConfigSharedPreferencesDatasource.save",
                    "-",
                    Exception()
                )
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IConfigRepository.save -> IConfigSharedPreferencesDatasource.save"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `save - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                configSharedPreferencesDatasource.save(
                    ConfigSharedPreferencesModel(
                        number = 16997417840,
                        password = "12345",
                        version = "1.00",
                        idBD = 1
                    )
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.save(
                Config(
                    number = 16997417840,
                    password = "12345",
                    version = "1.00",
                    idBD = 1
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

}