package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ninjameals")
data class NinjaMealEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val calories: Int,
    val serving_size_g: Int,
    val fat_total_g: Int,
    val fat_saturated_g: Int,
    val protein_g: Int,
    val sodium_mg: Int,
    val potassium_mg: Int,
    val cholesterol_mg: Int,
    val carbohydrates_total_g: Int,
    val fiber_g: Int,
    val sugar_g: Int
)