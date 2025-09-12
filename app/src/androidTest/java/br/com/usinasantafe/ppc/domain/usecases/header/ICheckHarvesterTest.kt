package br.com.usinasantafe.ppc.domain.usecases.header

import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ICheckHarvesterTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var usecase: CheckHarvester

    @Inject
    lateinit var harvesterDao: HarvesterDao

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun check_return_failure_if_not_have_data() =
        runTest {
            val result = usecase("19759")
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
    fun check_return_true_if_have_data_reg_harvester() =
        runTest {
            harvesterDao.insertAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 19759
                    )
                )
            )
            val result = usecase("19759")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                true
            )
        }

    @Test
    fun check_return_true_if_not_have_data_reg_harvester() =
        runTest {
            harvesterDao.insertAll(
                listOf(
                    HarvesterRoomModel(
                        nroHarvester = 19759
                    )
                )
            )
            val result = usecase("12345")
            assertEquals(
                result.isSuccess,
                true
            )
            assertEquals(
                result.getOrNull()!!,
                false
            )
        }

}