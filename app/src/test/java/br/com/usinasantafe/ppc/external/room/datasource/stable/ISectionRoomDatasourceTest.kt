package br.com.usinasantafe.ppc.external.room.datasource.stable

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.ppc.external.room.DatabaseRoom
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
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
class ISectionRoomDatasourceTest {

    private lateinit var sectionDao: SectionDao
    private lateinit var db: DatabaseRoom
    private lateinit var datasource: ISectionRoomDatasource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, DatabaseRoom::class.java
        ).allowMainThreadQueries().build()
        sectionDao = db.sectionDao()
        datasource = ISectionRoomDatasource(sectionDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `addAll - Check failure if have row repeated`() =
        runTest {
            val qtdBefore = sectionDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    SectionRoomModel(
                        idSection = 1,
                        codSection = 1
                    ),
                    SectionRoomModel(
                        idSection = 1,
                        codSection = 1
                    ),
                )
            )
            assertEquals(
                result.isFailure,
                true
            )
            assertEquals(
                result.exceptionOrNull()!!.message,
                "ISectionRoomDatasource.addAll"
            )
            assertEquals(
                result.exceptionOrNull()!!.cause.toString(),
                "android.database.sqlite.SQLiteConstraintException: DB[1] step() [INSERT OR ABORT INTO `tb_section` (`idSection`,`codSection`) VALUES (?,?)]DB[1][C] [UNIQUE constraint failed: tb_section.idSection] (code 1555 SQLITE_CONSTRAINT_PRIMARYKEY)"
            )
            val qtdAfter = sectionDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `addAll - Check success if have row is correct`() =
        runTest {
            val qtdBefore = sectionDao.all().size
            assertEquals(
                qtdBefore,
                0
            )
            val result = datasource.addAll(
                listOf(
                    SectionRoomModel(
                        idSection = 1,
                        codSection = 1
                    ),
                    SectionRoomModel(
                        idSection = 2,
                        codSection = 2
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
            val qtdAfter = sectionDao.all().size
            assertEquals(
                qtdAfter,
                2
            )
            val list = sectionDao.all()
            val entity1 = list[0]
            assertEquals(
                entity1.idSection,
                1
            )
            assertEquals(
                entity1.codSection,
                1
            )
            val entity2 = list[1]
            assertEquals(
                entity2.idSection,
                2
            )
            assertEquals(
                entity2.codSection,
                2
            )
        }

    @Test
    fun `deleteAll - Check execution correct`() =
        runTest {
            sectionDao.insertAll(
                listOf(
                    SectionRoomModel(
                        idSection = 1,
                        codSection = 1
                    )
                )
            )
            val qtdBefore = sectionDao.all().size
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
            val qtdAfter = sectionDao.all().size
            assertEquals(
                qtdAfter,
                0
            )
        }

    @Test
    fun `checkNroSection - Check return false if not have data in table Section`() =
        runTest {
            val result = datasource.checkNro(1)
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
    fun `checkNroSection - Check return correct if function execute successfully`() =
        runTest {
            sectionDao.insertAll(
                listOf(
                    SectionRoomModel(
                        idSection = 1,
                        codSection = 1
                    )
                )
            )
            val result = datasource.checkNro(1)
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