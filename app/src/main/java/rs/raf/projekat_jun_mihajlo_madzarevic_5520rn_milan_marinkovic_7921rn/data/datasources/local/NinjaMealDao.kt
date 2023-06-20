package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.NinjaMealEntity

@Dao
abstract class NinjaMealDao {

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(meals: List<NinjaMealEntity>): Completable

    @Query("SELECT * FROM ninjameals")
    abstract fun getAll(): Observable<List<NinjaMealEntity>>

    @Query("DELETE FROM ninjameals")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAndInsertAll(entities: List<NinjaMealEntity>) {
        deleteAll()
        insertAll(entities).blockingAwait()
    }
}