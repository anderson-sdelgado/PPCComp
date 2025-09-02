package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.ColabRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckColabTest {

    private val colabRepository = mock<ColabRepository>()
    private val usecase = ICheckColab(
        colabRepository = colabRepository
    )

    @Test
    fun `Check return failure if regAuditor is incorrect`() =
        runTest {
            val result = usecase("19759a")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckColab"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.NumberFormatException: For input string: \"19759a\""
            )
        }

    @Test
    fun `Check return failure if have error in ColabRepository check`() =
        runTest {
            whenever(
                colabRepository.check(19759)
            ).thenReturn(
                resultFailure(
                    "IColabRepository.check",
                    "-",
                    Exception()
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckColab -> IColabRepository.check"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return correct if function execute successfully`() =
        runTest {
            whenever(
                colabRepository.check(19759)
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("19759")
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