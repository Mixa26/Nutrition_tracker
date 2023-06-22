package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.repository

import io.reactivex.Observable
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.local.MealDao
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.local.NinjaMealDao
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.remote.MealService
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.remote.NinjaMealService
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.NinjaMealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.NinjaMealResponse
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.Resource

class MealRepositoryImpl (
    private val localDataSource: MealDao,
//    private val ninjaLocalDataSource: NinjaMealDao,
    private val remoteDataSource: MealService,
//    private val ninjaRemoteDataSource: NinjaMealService
) : MealRepository {

//    override fun ninjaFetchAllByTitle(title: String): Observable<Resource<Unit>> {
//        return ninjaRemoteDataSource
//            .getAllByTitle(title)
//            .doOnNext{
//                val entities = it.meals.map{
//                    NinjaMealEntity(
//                        0,
//                        it.name,
//                        it.calories,
//                        it.serving_size_g,
//                        it.fat_total_g,
//                        it.fat_saturated_g,
//                        it.protein_g,
//                        it.sodium_mg,
//                        it.potassium_mg,
//                        it.cholesterol_mg,
//                        it.carbohydrates_total_g,
//                        it.fiber_g,
//                        it.sugar_g
//                    )
//                }
//                ninjaLocalDataSource.deleteAndInsertAll(entities)
//            }
//            .map {
//                Resource.Success(Unit)
//            }
//    }
//
//    override fun ninjaGetAll(): Observable<List<NinjaMealEntity>> {
//        return ninjaLocalDataSource
//            .getAll()
//            .map{
//                it.map{
//                    NinjaMealEntity(
//                        it.id,
//                        it.name,
//                        it.calories,
//                        it.serving_size_g,
//                        it.fat_total_g,
//                        it.fat_saturated_g,
//                        it.protein_g,
//                        it.sodium_mg,
//                        it.potassium_mg,
//                        it.cholesterol_mg,
//                        it.carbohydrates_total_g,
//                        it.fiber_g,
//                        it.sugar_g
//                    )
//                }
//            }
//    }

    override fun fetchAllByName(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByName(name)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByCategory(category: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByCategory(category)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        null,
                        category,
                        null,
                        null,
                        it.strMealThumb,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAllByName(name: String): Observable<List<MealEntity>> {
        return localDataSource
            .getAllByName(name)
            .map{
                it.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                    )
                }
            }
    }

    override fun getAll(): Observable<List<MealEntity>> {
        return localDataSource
            .getAll()
            .map{
                it.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                    )
                }
            }
    }

    override fun fetchAllByArea(area: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByArea(area)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        null,
                        null,
                        area,
                        null,
                        it.strMealThumb,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByIngredient(ingredient)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        null,
                        null,
                        null,
                        null,
                        it.strMealThumb,
                        null,
                        null,
                        ingredient,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                }
                localDataSource.deleteAndInsertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun fetchAllByNameForTag(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByName(name)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                    )
                }
                localDataSource.insertAll(entities)
            }
            .map {
                Resource.Success(Unit)
            }
    }

    override fun getAllByTag(tag: String): Observable<List<MealEntity>> {
        return localDataSource
            .getAllByTag(tag)
            .map{
                it.map {
                    MealEntity(
                        it.idMeal,
                        it.strMeal,
                        it.strDrinkAlternate,
                        it.strCategory,
                        it.strArea,
                        it.strInstructions,
                        it.strMealThumb,
                        it.strTags,
                        it.strYoutube,
                        it.strIngredient1,
                        it.strIngredient2,
                        it.strIngredient3,
                        it.strIngredient4,
                        it.strIngredient5,
                        it.strIngredient6,
                        it.strIngredient7,
                        it.strIngredient8,
                        it.strIngredient9,
                        it.strIngredient10,
                        it.strIngredient11,
                        it.strIngredient12,
                        it.strIngredient13,
                        it.strIngredient14,
                        it.strIngredient15,
                        it.strIngredient16,
                        it.strIngredient17,
                        it.strIngredient18,
                        it.strIngredient19,
                        it.strIngredient20,
                        it.strMeasure1,
                        it.strMeasure2,
                        it.strMeasure3,
                        it.strMeasure4,
                        it.strMeasure5,
                        it.strMeasure6,
                        it.strMeasure7,
                        it.strMeasure8,
                        it.strMeasure9,
                        it.strMeasure10,
                        it.strMeasure11,
                        it.strMeasure12,
                        it.strMeasure13,
                        it.strMeasure14,
                        it.strMeasure15,
                        it.strMeasure16,
                        it.strMeasure17,
                        it.strMeasure18,
                        it.strMeasure19,
                        it.strMeasure20,
                        it.strSource,
                        it.strImageSource,
                        it.strCreativeCommonsConfirmed,
                        it.dateModified,
                    )
                }
            }
    }

}