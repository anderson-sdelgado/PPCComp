package br.com.usinasantafe.ppc.infra.repositories.stable

import br.com.usinasantafe.ppc.domain.entities.stable.Section
import br.com.usinasantafe.ppc.domain.errors.resultFailure
import br.com.usinasantafe.ppc.domain.repositories.stable.SectionRepository
import br.com.usinasantafe.ppc.infra.datasource.retrofit.stable.SectionRetrofitDatasource
import br.com.usinasantafe.ppc.infra.datasource.room.stable.SectionRoomDatasource
import br.com.usinasantafe.ppc.infra.models.retrofit.stable.retrofitModelToEntity
import br.com.usinasantafe.ppc.infra.models.room.stable.entityToRoomModel
import br.com.usinasantafe.ppc.utils.getClassAndMethod
import javax.inject.Inject

class ISectionRepository @Inject constructor(
    private val sectionRoomDatasource: SectionRoomDatasource,
    private val sectionRetrofitDatasource: SectionRetrofitDatasource
): SectionRepository {

    override suspend fun addAll(list: List<Section>): Result<Boolean> {
        try {
            val roomModelList = list.map { it.entityToRoomModel() }
            val result = sectionRoomDatasource.addAll(roomModelList)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            return result
        } catch (e: Exception){
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun deleteAll(): Result<Boolean> {
        val result = sectionRoomDatasource.deleteAll()
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun listAll(token: String): Result<List<Section>> {
        try {
            val result = sectionRetrofitDatasource.listAll(token)
            if (result.isFailure) {
                return resultFailure(
                    context = getClassAndMethod(),
                    cause = result.exceptionOrNull()!!
                )
            }
            val entityList = result.getOrNull()!!.map { it.retrofitModelToEntity() }
            return Result.success(entityList)
        } catch (e: Exception) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = e
            )
        }
    }

    override suspend fun checkCod(codSection: Int): Result<Boolean> {
        val result = sectionRoomDatasource.checkCod(codSection)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

    override suspend fun getIdByCod(codSection: Int): Result<Int> {
        val result = sectionRoomDatasource.getIdByCod(codSection)
        if (result.isFailure) {
            return resultFailure(
                context = getClassAndMethod(),
                cause = result.exceptionOrNull()!!
            )
        }
        return result
    }

}