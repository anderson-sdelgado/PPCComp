package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.OSRoomModel

@Dao
interface OSDao {

    @Insert
    fun insert(model: OSRoomModel)

    @Query("DELETE FROM tb_os")
    suspend fun deleteAll()

    @Query("SELECT * FROM tb_os")
    suspend fun all(): List<OSRoomModel>
}