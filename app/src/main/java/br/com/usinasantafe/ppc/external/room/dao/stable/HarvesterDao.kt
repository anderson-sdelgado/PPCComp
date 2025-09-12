package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel

@Dao
interface HarvesterDao {

    @Insert
    fun insertAll(list: List<HarvesterRoomModel>)

    @Query("DELETE FROM TB_HARVESTER")
    suspend fun deleteAll()

    @Query("SELECT * FROM TB_HARVESTER")
    suspend fun all(): List<HarvesterRoomModel>

    @Query("SELECT count(*) FROM TB_HARVESTER WHERE nroHarvester = :nroHarvester")
    suspend fun checkNroHarvester(nroHarvester: Int): Int
}