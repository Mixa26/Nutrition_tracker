package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract

import androidx.lifecycle.LiveData
import io.reactivex.Single
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.CategoryState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState

interface MainContract {
    interface UserViewModel {
        fun insertUsers(users : List<UserEntity>)
        fun getByUsernameAndPassword(username : String, password: String): Single<UserEntity?>
    }

    interface CategoryViewModel {

        val categoryState: LiveData<CategoryState>

        fun fetchAll()
        fun getAll()
    }

    interface MealViewModel {

        val mealState: LiveData<MealState>

        fun fetchAllByName(name: String)

        fun fetchAllByCategory(category: String)

        fun getAllByNameSearch(name: String)

        fun getAllByName(name: String)

        fun getAll()
    }
}