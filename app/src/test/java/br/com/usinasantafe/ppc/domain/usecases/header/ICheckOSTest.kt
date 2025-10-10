package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.domain.entities.stable.OS
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.usecases.config.GetToken
import br.com.usinasantafe.ppc.presenter.model.ResultCheckDataWebServiceModel
import br.com.usinasantafe.ppc.utils.CheckNetwork
import br.com.usinasantafe.ppc.utils.StatusCon
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.net.SocketTimeoutException
import kotlin.test.Test

class ICheckOSTest {

    private val checkNetwork = mock<CheckNetwork>()
    private val getToken = mock<GetToken>()
    private val osRepository = mock<OSRepository>()
    private val usecase = ICheckOS(
        checkNetwork = checkNetwork,
        getToken = getToken,
        osRepository = osRepository
    )

    @Test
    fun `Check return failure if have error in OSRepository deleteAll`() =
        runTest {
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                resultFailure(
                    "IOSRepository.deleteAll",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return StatusCon WITHOUT if not have connection internet `() =
        runTest {
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                false
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.WITHOUT
                )
            )
        }

    @Test
    fun `Check return failure if have error in GetToken`() =
        runTest {
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                resultFailure(
                    "IGetToken",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IGetToken"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return StatusCon SLOW if web service is slow`() =
        runTest {
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success(
                    "token"
                )
            )
            whenever(
                osRepository.getByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IOSRepository.getByNroOS",
                    "-",
                    SocketTimeoutException()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull(),
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.SLOW
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository getByNroOS`() =
        runTest {
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success(
                    "token"
                )
            )
            whenever(
                osRepository.getByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IOSRepository.getByNroOS",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.getByNroOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return false if nroOS is inexistent`() =
        runTest {
            val entity = OS(
                nroOS = 0,
                idSection = 0
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success(
                    "token"
                )
            )
            whenever(
                osRepository.getByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(entity)
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = false
                )
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository add`() =
        runTest {
            val entity = OS(
                nroOS = 123456,
                idSection = 1
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success(
                    "token"
                )
            )
            whenever(
                osRepository.getByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(entity)
            )
            whenever(
                osRepository.add(entity)
            ).thenReturn(
                resultFailure(
                    "IOSRepository.add",
                    "-",
                    Exception()
                )
            )
            val result = usecase("123456")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckNroOS -> IOSRepository.add"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return true if function execute successfully`() =
        runTest {
            val entity = OS(
                nroOS = 123456,
                idSection = 1
            )
            whenever(
                osRepository.deleteAll()
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                checkNetwork()
            ).thenReturn(
                true
            )
            whenever(
                getToken()
            ).thenReturn(
                Result.success(
                    "token"
                )
            )
            whenever(
                osRepository.getByNroOS(
                    token = "token",
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(entity)
            )
            whenever(
                osRepository.add(entity)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("123456")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                ResultCheckDataWebServiceModel(
                    statusCon = StatusCon.OK,
                    check = true
                )
            )
        }

}