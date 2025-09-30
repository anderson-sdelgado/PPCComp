package br.com.usinasantafe.ppc.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import br.com.usinasantafe.ppc.utils.StatusSend

@Dao
interface HeaderDao {

    @Insert
    suspend fun insert(header: HeaderRoomModel)

    @Update
    suspend fun update(header: HeaderRoomModel)

    @Query("SELECT * FROM TB_HEADER")
    suspend fun all(): List<HeaderRoomModel>

    @Query("SELECT * FROM TB_HEADER WHERE status = :status")
    suspend fun listByStatus(status: Status): List<HeaderRoomModel>

    @Query("SELECT count(*) FROM TB_HEADER WHERE statusSend = :statusSend")
    suspend fun checkStatusSend(statusSend: StatusSend): Int

    @Query("UPDATE TB_HEADER SET status = :statusClose WHERE status != :statusFinish")
    suspend fun updateStatus(statusClose: Status = Status.CLOSE, statusFinish: Status = Status.FINISH)

    @Query("UPDATE TB_HEADER SET status = :status WHERE id = :id")
    suspend fun updateStatusById(status: Status, id: Int)

    @Query("SELECT id FROM TB_HEADER WHERE status = :status")
    suspend fun getIdByStatus(status: Status): Int

    @Query("DELETE FROM TB_HEADER WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM TB_HEADER WHERE id = :id")
    suspend fun getById(id: Int): HeaderRoomModel

    @Query("SELECT * FROM TB_HEADER WHERE statusSend = :statusSend")
    suspend fun listByStatusSend(statusSend: StatusSend): List<HeaderRoomModel>

    @Query("UPDATE TB_HEADER SET idServ = :idServ, statusSend = :statusSend WHERE id = :id")
    suspend fun setIdServAndSentById(id: Int, idServ: Int, statusSend: StatusSend = StatusSend.SENT)


}