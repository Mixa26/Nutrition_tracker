package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Entity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.CategoryEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.databinding.FragmentTabItemBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter.MealAdapter
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.differ.MealDiffItemCallback
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.MealViewModel
import timber.log.Timber

class TabCategoryFragment : Fragment() {

    private lateinit var binding: FragmentTabItemBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: MealAdapter

    private val mealViewModel: MainContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var allMeals: List<MealEntity>

    private lateinit var allMealsByName: List<MealEntity>

    private var countDownTimer: CountDownTimer? = null

    private var isTagSearch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTabItemBinding.inflate(layoutInflater)
        recyclerView = binding.tabItemRv

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initReycler()
        initObservers()
        initListeners()
    }

    private fun initReycler(){
        mealAdapter = MealAdapter(MealDiffItemCallback(), null)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mealAdapter
    }

    private fun initListeners(){

//        binding.inputCriteria.doAfterTextChanged {
//            val filter = it.toString()
//            if(filter.isEmpty()){
//                mealAdapter.submitList(allMeals)
//            }else{
//                mealViewModel.getAll()
//                mealViewModel.fetchAllByCategory(filter)
//            }
//        }

        binding.inputCriteria.doAfterTextChanged { editable ->
            countDownTimer?.cancel() // Prekida prethodni tajmer ako postoji

            countDownTimer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Ne radi ništa tokom odbrojavanja
                }

                override fun onFinish() {
                    val filter = editable.toString()
                    if (filter.isEmpty()) {
                        mealAdapter.submitList(allMeals)
                    } else {
                        mealViewModel.getAll()
                        mealViewModel.fetchAllByCategory(filter)
                    }
                }
            }

            countDownTimer?.start() // Pokreće tajmer za 3 sekunde
        }

        binding.inputName.doAfterTextChanged {
            val filter = it.toString()
            if(filter.isEmpty()){
                mealAdapter.submitList(allMeals)
            }else{
//                mealViewModel.getAll()
//                mealViewModel.fetchAllByName(filter)
                val filteredList = filterByName(allMeals, filter)
                mealAdapter.submitList(filteredList)
            }
        }
        binding.inputTag.doAfterTextChanged {editable->

            countDownTimer?.cancel() // Prekida prethodni tajmer ako postoji

            countDownTimer = object : CountDownTimer(2000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    // Ne radi ništa tokom odbrojavanja
                }

                override fun onFinish() {
                    val filter = editable.toString()
                    if (filter.isEmpty()) {
                        isTagSearch = false
                        mealAdapter.submitList(allMeals)
                    } else {
                        isTagSearch = true
                        mealViewModel.getAllByTag(filter)
                        for(meal in allMeals){
                            mealViewModel.fetchAllByNameForTag(meal.strMeal)
                        }
                    }
                }
            }

            countDownTimer?.start() // Pokreće tajmer za 3 sekunde



//
//            val filter = it.toString()
//            if(filter.isEmpty()){
//                isTagSearch = false
//                mealAdapter.submitList(allMeals)
//            }else{
//                isTagSearch = true
//                mealViewModel.getAllByTag(filter)
//                for(meal in allMeals){
//                    mealViewModel.fetchAllByNameForTag(meal.strMeal)
//                }
//            }
        }
        binding.sortBtn.setOnClickListener {
            var sortedMeals : List<MealEntity>
            sortedMeals = sortMealsByName(allMeals)
            mealAdapter.submitList(sortedMeals)
        }

    }

    private fun initObservers(){
        mealViewModel.mealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
    }

    private fun renderState(state: MealState){
        when(state){
            is MealState.Success -> {
                showLoadingState(false)
                if(!isTagSearch){
                    allMeals = state.meals
                }
                mealAdapter.submitList(state.meals)
                //mealAdapter.currentList.add(state.meals[0])
            }
            is MealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean){
        binding.tabItemRv.isVisible = !loading
        binding.loadingTabMeals.isVisible = loading
    }

    private fun sortMealsByName(meals: List<MealEntity>): List<MealEntity>{
        val sortedList = meals.toMutableList() // Stvaranje nove liste s istim vrijednostima
        sortedList.sortBy { it.strMeal } // Sortiranje nove liste po imenu
        return sortedList
    }

    private fun filterByName(meals: List<MealEntity>, name: String): List<MealEntity>{
        val filteredList = mutableListOf<MealEntity>()

        for(meal in meals){
            if(meal.strMeal.contains(name, ignoreCase = true)){
                filteredList.add(meal)
            }
        }

        return filteredList
    }

//    private fun filterByTag(tags: List<String>): List<MealEntity>{
//
//        for(meal in allMeals){
//            val mealsByName: List<MealEntity> = mealViewModel.fetchAllByName(meal.strMeal)
//        }
//
//    }
}