package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.NinjaMealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.Resource
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.SavedMealEntity

interface MealRepository {

    fun fetchAllByName(name: String): Observable<Resource<Unit>>

    fun getAllByName(name: String): Observable<List<MealEntity>>

    fun fetchAllByCategory(category: String): Observable<Resource<Unit>>

    fun getAll(): Observable<List<MealEntity>>

    fun ninjaFetchAllByTitle(title: String): Observable<Resource<Unit>>

    fun ninjaGetAll(): Observable<List<NinjaMealEntity>>

    fun saveMeal(meal: SavedMealEntity): Completable

    fun getAllSaved(): Observable<List<SavedMealEntity>>

    fun getAllSavedByName(name: String): Observable<List<SavedMealEntity>>

    fun getAllSavedAsMealEntity(): Observable<List<MealEntity>>

    fun getAllSavedByNameAsMealEntity(name: String): Observable<List<MealEntity>>
//    fun ninjaFetchAllByTitle(title: String): Observable<Resource<Unit>>
//
//    fun ninjaGetAll(): Observable<List<NinjaMealEntity>>

    fun fetchAllByArea(area: String): Observable<Resource<Unit>>
    fun fetchAllByIngredient(ingredient: String): Observable<Resource<Unit>>

    fun fetchAllByNameForTag(name: String): Observable<Resource<Unit>>
    fun getAllByTag(tag: String):Observable<List<MealEntity>>
}