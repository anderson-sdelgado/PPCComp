package br.com.usinasantafe.ppc.external.room.datasource.stable

import br.com.usinasantafe.ppc.infra.datasource.room.stable.SectionRoomDatasource
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import javax.inject.Inject

class ISectionRoomDatasource @Inject constructor(

): SectionRoomDatasource {

    override suspend fun addAll(list: List<SectionRoomModel>): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAll(): Result<Boolean> {
        TODO("Not yet implemented")
    }

}