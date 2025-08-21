package br.com.usinasantafe.ppc.external.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import br.com.usinasantafe.ppc.infra.models.room.stable.ColabRoomModel
import br.com.usinasantafe.ppc.utils.VERSION_DB
import java.util.Date

@Database(
    entities = [
        ColabRoomModel::class
    ],
    version = VERSION_DB, exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class DatabaseRoom : RoomDatabase() {
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