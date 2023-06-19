package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.repository

import io.reactivex.Observable
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.local.MealDao
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.remote.MealService
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.Resource

class MealRepositoryImpl (
    private val localDataSource: MealDao,
    private val remoteDataSource: MealService
) : MealRepository {

    override fun fetchAllByName(name: String): Observable<Resource<Unit>> {
        return remoteDataSource
            .getAllByName(name)
            .doOnNext{
                val entities = it.meals.map{
                    MealEntity(
                        it.idMeal.toInt(),
                        it.strCategory,
                        it.strMealThumb,
                        it.strCategory
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
                        it.idMeal.toInt(),
                        it.strMeal,
                        it.strMealThumb,
                        category
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
                    MealEntity(it.idMeal, it.strMeal, it.strMealThumb, it.strCategory)
                }
            }
    }

    override fun getAll(): Observable<List<MealEntity>> {
        return localDataSource
            .getAll()
            .map{
                it.map {
                    MealEntity(it.idMeal, it.strMeal, it.strMealThumb, it.strCategory)
                }
            }
    }
}