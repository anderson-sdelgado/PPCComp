package br.com.usinasantafe.ppc.external.room.dao.stable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel

@Dao
interface SectionDao {

    @Insert
    fun insertAll(list: List<SectionRoomModel>)

    @Query("DELETE FROM TB_SECTION")
    suspend fun deleteAll()

    @Query("SELECT * FROM TB_SECTION")
    suspend fun all(): List<SectionRoomModel>

    @Query("SELECT count(*) FROM TB_SECTION WHERE codSection = :codSection")
    suspend fun count(codSection: Int): Int

    @Query("SELECT idSection FROM TB_SECTION WHERE codSection = :codSection")
    suspend fun getIdByCod(codSection: Int): Int
}