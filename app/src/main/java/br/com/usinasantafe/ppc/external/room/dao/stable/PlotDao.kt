package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel

@Dao
interface PlotDao {

    @Insert
    fun insertAll(list: List<PlotRoomModel>)

    @Query("DELETE FROM TB_PLOT")
    suspend fun deleteAll()

    @Query("SELECT * FROM TB_PLOT")
    suspend fun all(): List<PlotRoomModel>

}