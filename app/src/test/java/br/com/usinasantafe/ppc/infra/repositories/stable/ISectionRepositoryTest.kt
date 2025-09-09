package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Section
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.SectionRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.SectionRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.SectionRetrofitModel
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ISectionRepositoryTest {

    private val sectionRoomDatasource = mock<SectionRoomDatasource>()
    private val sectionRetrofitDatasource = mock<SectionRetrofitDatasource>()
    private val repository = ISectionRepository(
        sectionRetrofitDatasource = sectionRetrofitDatasource,
        sectionRoomDatasource = sectionRoomDatasource
    )

    @Test
    fun `addAll - Check return failure if have error`() =
        runTest {
            val roomModelList = listOf(
                SectionRoomModel(
                    idSection = 1,
                    codSection = 10
                )
            )
            val entityList = listOf(
                Section(
                    idSection = 1,
                    codSection = 10
                )
            )
            whenever(
                sectionRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                resultFailure(
                    "ISectionRoomDatasource.addAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.addAll(entityList)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISectionRepository.addAll -> ISectionRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `addAll - Check return true if function execute successfully`() =
        runTest {
            val roomModelList = listOf(
                SectionRoomModel(
                    idSection = 1,
                    codSection = 10
                )
            )
            val entityList = listOf(
                Section(
                    idSection = 1,
                    codSection = 10
                )
            )
            whenever(
                sectionRoomDatasource.addAll(roomModelList)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.addAll(entityList)
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
    fun `deleteAll - Check return failure if have error`() =
        runTest {
            whenever(
                sectionRoomDatasource.deleteAll()
            ).thenReturn(
                resultFailure(
                    "ISectionRoomDatasource.deleteAll",
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
                "ISectionRepository.deleteAll -> ISectionRoomDatasource.deleteAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `deleteAll - Check return true if function execute successfully`() =
        runTest {
            whenever(
                sectionRoomDatasource.deleteAll()
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
    fun `listAll - Check return failure if have error`() =
        runTest {
            whenever(
                sectionRetrofitDatasource.listAll("token")
            ).thenReturn(
                resultFailure(
                    "ISectionRetrofitDatasource.listAll",
                    "-",
                    Exception()
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISectionRepository.listAll -> ISectionRetrofitDatasource.listAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `listAll - Check return true if function execute successfully`() =
        runTest {
            val retrofitModelList = listOf(
                SectionRetrofitModel(
                    idSection = 1,
                    codSection = 10
                ),
                SectionRetrofitModel(
                    idSection = 2,
                    codSection = 20
                )
            )
            val entityList = listOf(
                Section(
                    idSection = 1,
                    codSection = 10
                ),
                Section(
                    idSection = 2,
                    codSection = 20
                )
            )
            whenever(
                sectionRetrofitDatasource.listAll("token")
            ).thenReturn(
                Result.success(
                    retrofitModelList
                )
            )
            val result = repository.listAll("token")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                entityList
            )
        }

    @Test
    fun `checkNroSection - Check return failure if have error in SectionDatasource checkNroSection`() =
        runTest {
            whenever(
                sectionRoomDatasource.checkNro(200)
            ).thenReturn(
                resultFailure(
                    "ISectionDatasource.checkNroSection",
                    "-",
                    Exception()
                )
            )
            val result = repository.checkNro(200)
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISectionRepository.checkNroSection -> ISectionDatasource.checkNroSection"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "java.lang.Exception"
            )
        }

    @Test
    fun `checkNroSection - Check return correct if function execute successfully`() =
        runTest {
            whenever(
                sectionRoomDatasource.checkNro(200)
            ).thenReturn(
                Result.success(true)
            )
            val result = repository.checkNro(200)
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