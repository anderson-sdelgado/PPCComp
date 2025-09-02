package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.utils.TB_COLAB

@Dao
interface ColabDao {

    @Insert
    fun insertAll(list: List<ColabRoomModel>)

    @Query("DELETE FROM $TB_COLAB")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_COLAB")
    suspend fun all(): List<ColabRoomModel>

    @Query("SELECT count(*) FROM $TB_COLAB WHERE regColab = :regColab")
    suspend fun count(regColab: Int): Int

}