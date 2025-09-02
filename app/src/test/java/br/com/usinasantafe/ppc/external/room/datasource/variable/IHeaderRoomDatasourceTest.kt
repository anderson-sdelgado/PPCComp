package br.com.usinasantafe.ppc.external.room.datasource.variable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.utils.Status
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.util.Date
import kotlin.intArrayOf
import kotlin.test.Test
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class IHeaderRoomDatasourceTest {

    private lateinit var headerDao: HeaderDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: IHeaderRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        headerDao = db.headerDao()
        datasource = IHeaderRoomDatasource(headerDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `listByStatus - Check return list correct`() =
        runTest {
            headerDao.insert(
                HeaderRoomModel(
                    regAuditor1 = 1,
                    regAuditor2 = 2,
                    regAuditor3 = 3,
                    date = Date(),
                    nroTurn = 1,
                    codSection = 1,
                    nroPlot = 1,
                    nroOS = 1,
                    codFront = 1,
                    nroHarvester = 1,
                    regOperator = 1,
                ),
            )
            headerDao.insert(
                HeaderRoomModel(
                    regAuditor1 = 10,
                    regAuditor2 = 20,
                    regAuditor3 = 30,
                    date = Date(),
                    nroTurn = 2,
                    codSection = 2,
                    nroPlot = 2,
                    nroOS = 2,
                    codFront = 2,
                    nroHarvester = 2,
                    regOperator = 2,
                    status = Status.CLOSE
                ),
            )
            val result = datasource.listByStatus(Status.OPEN)
            assertEquals(
                result.isSuccess,
                true
            )
            val list = result.getOrNull()!!
            assertEquals(
                list.size,
                1
            )
            val entity = list[0]
            assertEquals(
                entity.regAuditor1,
                1
            )
            assertEquals(
                entity.regAuditor2,
                2
            )
            assertEquals(
                entity.regAuditor3,
                3
            )
            assertEquals(
                entity.nroTurn,
                1
            )
            assertEquals(
                entity.codSection,
                1
            )
            assertEquals(
                entity.nroPlot,
                1
            )
            assertEquals(
                entity.nroOS,
                1
            )
            assertEquals(
                entity.codFront,
                1
            )
            assertEquals(
                entity.nroHarvester,
                1
            )
            assertEquals(
                entity.regOperator,
                1
            )
        }

}