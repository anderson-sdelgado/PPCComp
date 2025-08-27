package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.utils.TB_SECTION

@Dao
interface SectionDao {

    @Insert
    fun insertAll(list: List<SectionRoomModel>)

    @Query("DELETE FROM $TB_SECTION")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TB_SECTION")
    suspend fun all(): List<SectionRoomModel>
}