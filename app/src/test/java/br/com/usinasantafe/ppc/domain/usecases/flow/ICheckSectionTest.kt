package br.com.usinasantafe.ppc.domain.usecases.flow

import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.OSRepository
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.domain.repositories.variable.AnalysisRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.test.Test

class ICheckSectionTest {

    private val sectionRepository = mock<SectionRepository>()
    private val osRepository = mock<OSRepository>()
    private val analysisRepository = mock<AnalysisRepository>()
    private val usecase = ICheckSection(
        sectionRepository = sectionRepository,
        osRepository = osRepository,
        analysisRepository = analysisRepository
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
    fun `Check return failure if have error in SectionRepository checkCod`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                resultFailure(
                    "ISectionRepository.checkCod",
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
                "ICheckSection -> ISectionRepository.checkCod"
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
                sectionRepository.checkCod(200)
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

    @Test
    fun `Check return failure if have error in SectionRepository getIdByNro`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                resultFailure(
                    "ISectionRepository.getIdByNro",
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
                "ICheckSection -> ISectionRepository.getIdByNro"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in AnalysisRepository getOSHeaderOpen`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.getOSHeaderOpen()
            ).thenReturn(
                resultFailure(
                    "IAnalysisRepository.getOSHeaderOpen",
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
                "ICheckSection -> IAnalysisRepository.getOSHeaderOpen"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check return failure if have error in OSRepository checkSectionAndOS`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.getOSHeaderOpen()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.checkSectionAndOS(
                    idSection = 1,
                    nroOS = 123456
                )
            ).thenReturn(
                resultFailure(
                    "IOSRepository.checkSectionAndOS",
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
                "ICheckSection -> IOSRepository.checkSectionAndOS"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `Check false return if OS and Section is incompatible`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.getOSHeaderOpen()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.checkSectionAndOS(
                    idSection = 1,
                    nroOS = 123456
                )
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

    @Test
    fun `Check true return if OS and Section is compatible`() =
        runTest {
            whenever(
                sectionRepository.checkCod(200)
            ).thenReturn(
                Result.success(true)
            )
            whenever(
                sectionRepository.getIdByCod(200)
            ).thenReturn(
                Result.success(1)
            )
            whenever(
                analysisRepository.getOSHeaderOpen()
            ).thenReturn(
                Result.success(123456)
            )
            whenever(
                osRepository.checkSectionAndOS(
                    idSection = 1,
                    nroOS = 123456
                )
            ).thenReturn(
                Result.success(true)
            )
            val result = usecase("200")
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