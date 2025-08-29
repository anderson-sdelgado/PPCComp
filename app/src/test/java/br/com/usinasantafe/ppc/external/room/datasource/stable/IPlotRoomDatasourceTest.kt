package br.com.usinasantafe.ppc.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
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
class IPlotRoomDatasourceTest {

    private lateinit var plotDao: PlotDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IPlotRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        plotDao = db.plotDao()
        datasource = IPlotRoomDatasource(plotDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = plotDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PlotRoomModel(
                        idPlot = 1,
                        nroPlot = 1,
                        idSection = 1
                    ),
                    PlotRoomModel(
                        idPlot = 1,
                        nroPlot = 1,
                        idSection = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "IPlotRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_plot` (`idPlot`,`codPlot`,`idSection`) VALUES (?,?,?)]DB[1][C] [UNIQUE constraint failed: tb_plot.idPlot] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = plotDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = plotDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    PlotRoomModel(
                        idPlot = 1,
                        nroPlot = 1,
                        idSection = 1
                    ),
                    PlotRoomModel(
                        idPlot = 2,
                        nroPlot = 2,
                        idSection = 1
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
            val qtdAfter = plotDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = plotDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idPlot,
                1
            )
            assertEquals(
                entity1.nroPlot,
                1
            )
            assertEquals(
                entity1.idSection,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idPlot,
                2
            )
            assertEquals(
                entity2.nroPlot,
                2
            )
            assertEquals(
                entity2.idSection,
                1
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            plotDao.insertAll(
                listOf(
                    PlotRoomModel(
                        idPlot = 1,
                        nroPlot = 1,
                        idSection = 1
                    )
                )
            )
            val qtdBefore = plotDao.all().size
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
            val qtdAfter = plotDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

}