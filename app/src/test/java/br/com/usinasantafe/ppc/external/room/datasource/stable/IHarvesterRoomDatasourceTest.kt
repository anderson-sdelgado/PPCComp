package br.com.usinasantafe.ppc.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
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
class IHarvesterRoomDatasourceTest {

    private lateinit var harvesterDao: HarvesterDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IHarvesterRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        harvesterDao = db.harvesterDao()
        datasource = IHarvesterRoomDatasource(harvesterDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = harvesterDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 1
                    ),
                    HarvesterRoomModel(
                        nroHarvester = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IHarvesterRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_harvester` (`nroHarvester`) VALUES (?)]DB[1][C] [UNIQUE constraint failed: tb_harvester.nroHarvester] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = harvesterDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = harvesterDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 1
                    ),
                    HarvesterRoomModel(
                        nroHarvester = 2
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
            val qtdAfter = harvesterDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = harvesterDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.nroHarvester,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.nroHarvester,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            harvesterDao.insertAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 1,
                    )
                )
            )
            val qtdBefore = harvesterDao.all().size
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
            val qtdAfter = harvesterDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `checkNroHarvester - Check return false if not have data in table Harvester`() =
        runTest {
            val result = datasource.checkNroHarvester(1)
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
    fun `checkNroHarvester - Check return true if have data fielded in table Harvester`() =
        runTest {
            harvesterDao.insertAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 1,
                    )
                )
            )
            val result = datasource.checkNroHarvester(1)
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