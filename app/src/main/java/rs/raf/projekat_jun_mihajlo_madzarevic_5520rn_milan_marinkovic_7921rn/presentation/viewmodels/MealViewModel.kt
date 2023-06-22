package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.Resource
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.SavedMealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.repository.MealRepository
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.AddMealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState
import timber.log.Timber
import java.util.concurrent.TimeUnit

class MealViewModel (
    private val mealRepository: MealRepository
) : ViewModel(), MainContract.MealViewModel {

    private val subscriptions = CompositeDisposable()
    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    override val mealState: MutableLiveData<MealState> = MutableLiveData()
    override val savedMealState: MutableLiveData<MealState> = MutableLiveData()
    override val addMeal: MutableLiveData<AddMealState> = MutableLiveData()

    init {
        val subscription = publishSubject
            .debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
            .switchMap {
                mealRepository
                    .fetchAllByName(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnError {
                        Timber.e("Error in category view model")
                        Timber.e(it)
                    }
            }
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value = MealState.Loading
                        is Resource.Success -> mealState.value = MealState.DataFetched
                        is Resource.Error -> mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllByNameSearch(name: String) {
        publishSubject.onNext(name)
    }

    override fun getAllByName(name: String) {
        val subscription = mealRepository
            .getAllByName(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByName(name: String) {
        val subscription = mealRepository
            .fetchAllByName(name)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value = MealState.Loading
                        is Resource.Success -> mealState.value = MealState.DataFetched
                        is Resource.Error -> mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun fetchAllByCategory(category: String) {
        val subscription = mealRepository
            .fetchAllByCategory(category)
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    when(it) {
                        is Resource.Loading -> mealState.value = MealState.Loading
                        is Resource.Success -> mealState.value = MealState.DataFetched
                        is Resource.Error -> mealState.value = MealState.Error("Error happened while fetching data from the server")
                    }
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from the server")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAll() {
        val subscription = mealRepository
            .getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    mealState.value = MealState.Success(it)
                },
                {
                    mealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun saveMeal(meal: SavedMealEntity) {
        val subscription = mealRepository
            .saveMeal(meal)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    addMeal.value = AddMealState.Success
                },
                {
                    addMeal.value = AddMealState.Error("Error happened while adding movie")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

//    override fun getAllSaved() {
//        val subscription = mealRepository
//            .getAllSaved()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    savedMealState.value = MealState.Success(it)
//                },
//                {
//                    savedMealState.value = MealState.Error("Error happened while fetching data from db")
//                    Timber.e(it)
//                }
//            )
//        subscriptions.add(subscription)
//    }
//
//    override fun getAllSavedByName(name: String) {
//        val subscription = mealRepository
//            .getAllSavedByName(name)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(
//                {
//                    savedMealState.value = MealState.Success(it)
//                },
//                {
//                    savedMealState.value = MealState.Error("Error happened while fetching data from db")
//                    Timber.e(it)
//                }
//            )
//        subscriptions.add(subscription)
//    }

    override fun getAllSavedAsMealEntity() {
        val subscription = mealRepository
            .getAllSavedAsMealEntity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedMealState.value = MealState.Success(it)
                },
                {
                    savedMealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun getAllSavedByNameAsMealEntity(name: String) {
        val subscription = mealRepository
            .getAllSavedByNameAsMealEntity(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    savedMealState.value = MealState.Success(it)
                },
                {
                    savedMealState.value = MealState.Error("Error happened while fetching data from db")
                    Timber.e(it)
                }
            )
        subscriptions.add(subscription)
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }
}