package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckSectionTest {

    private val sectionRepository = mock<SectionRepository>()
    private val osRepository = mock<OSRepository>()
    private val usecase: CheckSection = ICheckSection(
        sectionRepository = sectionRepository,
        osRepository = osRepository
    )

    @Test
    fun `Check return failure if nroSection is incorrect`() =
        runTest {
            val result = usecase("10a")
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
                "java.lang.NumberFormatException: For input string: \"10a\""
            )
        }

    @Test
    fun `Check return failure if have error in SectionRepository checkNroSection`() =
        runTest {
            whenever(
                sectionRepository.checkNro(200)
            ).thenReturn(
                resultFailure(
                    "ISectionRepository.checkNroSection",
                    "-",
                    Exception()
                )
            )
            val result = usecase("200")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ICheckSection -> ISectionRepository.checkNroSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check false return if SectionRepository checkNroSection returned false`() =
        runTest {
            whenever(
                sectionRepository.checkNro(200)
            ).thenReturn(
                Result.success(false)
            )
            val result = usecase("200")
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