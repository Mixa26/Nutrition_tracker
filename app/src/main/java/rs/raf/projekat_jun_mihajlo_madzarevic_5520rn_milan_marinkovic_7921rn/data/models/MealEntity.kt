package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meals")
data class MealEntity (
    @PrimaryKey(autoGenerate = true)
    val idMeal: Int,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String
)