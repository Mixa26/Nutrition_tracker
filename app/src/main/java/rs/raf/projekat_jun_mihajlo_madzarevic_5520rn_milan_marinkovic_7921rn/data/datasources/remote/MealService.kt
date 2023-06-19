package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.datasources.remote

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealResponse
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealResponseShort

interface MealService {

    @GET("search.php")
    fun getAllByName(@Query("s") name: String): Observable<List<MealResponse>>

    @GET("filter.php")
    fun getAllByCategory(@Query("c") category: String): Observable<List<MealResponseShort>>
}