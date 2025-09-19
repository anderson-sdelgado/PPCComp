package br.com.usinasantafe.ppc.external.room.dao.variable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel

@Dao
interface SampleDao {

    @Insert
    suspend fun insert(sample: SampleRoomModel)

    @Query("SELECT * FROM TB_SAMPLE")
    suspend fun all(): List<SampleRoomModel>

    @Query("SELECT COUNT(*) FROM TB_SAMPLE WHERE idHeader = :idHeader")
    suspend fun countByIdHeader(idHeader: Int): Int

    @Query("SELECT * FROM TB_SAMPLE WHERE idHeader = :idHeader")
    suspend fun listByIdHeader(idHeader: Int): List<SampleRoomModel>

    @Query("DELETE FROM TB_SAMPLE WHERE idHeader = :idHeader")
    suspend fun deleteByIdHeader(idHeader: Int)

    @Query("DELETE FROM TB_SAMPLE WHERE id = :id")
    suspend fun deleteById(id: Int)

}