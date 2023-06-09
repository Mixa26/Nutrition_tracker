package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract

import io.reactivex.Single
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity

interface MainContract {
    interface ViewModel {
        fun insertUsers(users : List<UserEntity>)
        fun getByUsername(username : String): Single<UserEntity?>
    }
}