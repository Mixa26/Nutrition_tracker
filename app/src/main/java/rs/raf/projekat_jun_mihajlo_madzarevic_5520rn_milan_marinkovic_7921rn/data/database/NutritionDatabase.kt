package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.UserDao
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false)
abstract class NutritionDatabase : RoomDatabase() {
    abstract fun getUserDao(): UserDao
}