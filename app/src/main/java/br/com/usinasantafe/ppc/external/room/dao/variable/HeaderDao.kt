package br.com.usinasantafe.ppc.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status

@Dao
interface HeaderDao {

    @Insert
    suspend fun insert(header: HeaderRoomModel)

    @Query("SELECT * FROM TB_HEADER")
    suspend fun all(): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE status = :status")
    suspend fun listByStatus(status: Status): List<HeaderRoomModel>


}