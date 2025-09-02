package br.com.usinasantafe.ppc.external.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.usinasantafe.ppc.external.room.dao.stable.ColabDao
import br.com.usinasantafe.ppc.external.room.dao.stable.HarvesterDao
import br.com.usinasantafe.ppc.external.room.dao.stable.OSDao
import br.com.usinasantafe.ppc.external.room.dao.stable.PlotDao
import br.com.usinasantafe.ppc.external.room.dao.stable.SectionDao
import br.com.usinasantafe.ppc.external.room.dao.variable.HeaderDao
import br.com.usinasantafe.ppc.external.room.dao.variable.SampleDao
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.HarvesterRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.OSRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.PlotRoomModel
import br.com.usinasantafe.ppc.infra.models.room.stable.SectionRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.HeaderRoomModel
import br.com.usinasantafe.ppc.infra.models.room.variable.SampleRoomModel
import br.com.usinasantafe.ppc.utils.VERSION_DB
import java.util.Date

@Database(
    entities = [
        ColabRoomModel::class,
        HarvesterRoomModel::class,
        OSRoomModel::class,
        PlotRoomModel::class,
        SectionRoomModel::class,
        HeaderRoomModel::class,
        SampleRoomModel::class,
    ],
    version = VERSION_DB, exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {
    abstract fun colabDao(): ColabDao
    abstract fun harvesterDao(): HarvesterDao
    abstract fun osDao(): OSDao
    abstract fun plotDao(): PlotDao
    abstract fun sectionDao(): SectionDao
    abstract fun headerDao(): HeaderDao
    abstract fun sampleDao(): SampleDao
}

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}