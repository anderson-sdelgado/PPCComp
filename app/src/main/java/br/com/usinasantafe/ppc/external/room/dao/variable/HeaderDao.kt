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

    @Query("UPDATE TB_HEADER SET status = :statusClose WHERE status != :statusFinish")
    suspend fun updateStatus(statusClose: Status = Status.CLOSE, statusFinish: Status = Status.FINISH)

    @Query("UPDATE TB_HEADER SET status = :status WHERE id = :id")
    suspend fun updateStatusById(status: Status, id: Int)

    @Query("SELECT id FROM TB_HEADER WHERE status = :status")
    suspend fun getIdByStatus(status: Status): Int

    @Query("DELETE FROM TB_HEADER WHERE id = :id")
    suspend fun deleteById(id: Int)

}