package br.com.usinasantafe.ppc.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.intArrayOf

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IColabRoomDatasourceTest {

    private lateinit var colabDao: ColabDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IColabRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        colabDao = db.colabDao()
        datasource = IColabRoomDatasource(colabDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = colabDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1
                    ),
                    ColabRoomModel(
                        regColab = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IColabRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_colab` (`regColab`) VALUES (?)]DB[1][C] [UNIQUE constraint failed: tb_colab.regColab] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = colabDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1
                    ),
                    ColabRoomModel(
                        regColab = 2
                    ),
                )
            )
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = colabDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.regColab,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.regColab,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                    )
                )
            )
            val qtdBefore = colabDao.all().size
            assertEquals(
                qtdBefore,
                1
            )
            val result = datasource.deleteAll()
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
            val qtdAfter = colabDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `check - Check return false if not have data in table Colab`() =
        runTest {
            val result = datasource.check(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

    @Test
    fun `check - Check return true if have data fielded in table Colab`() =
        runTest {
            colabDao.insertAll(
                listOf(
                    ColabRoomModel(
                        regColab = 1,
                    )
                )
            )
            val result = datasource.check(1)
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

}