package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.databinding.FragmentMealListBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.activities.MainActivity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter.MealAdapter
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.differ.MealDiffItemCallback
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.MealViewModel
import timber.log.Timber

class MealListFragment(private val category: String) : Fragment() {
    private lateinit var binding : FragmentMealListBinding

    private lateinit var mealAdapter: MealAdapter
    private lateinit var recyclerView: RecyclerView

    private val mealViewModel: MainContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var allMeals: List<MealEntity>

    private var mealsPerPage = 10
    private var currentPage = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMealListBinding.inflate(layoutInflater)
        recyclerView = binding.mealsRV
        activity?.title = getString(R.string.mealsInCategory) + " " + category + " category"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun initRecycler(){
        mealAdapter = MealAdapter(MealDiffItemCallback())
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mealAdapter
    }

    private fun initListeners() {
        binding.searchMealList.doAfterTextChanged {
            val filter = it.toString()
            if (filter.isEmpty()){
                mealAdapter.submitList(allMeals.subList(0,mealsPerPage))
            }
            else{
                //TODO ovo ispraviti
                mealViewModel.fetchAllByName(filter)
                mealViewModel.getAllByName(filter)
            }
        }
    }

    private fun initObservers() {
        mealViewModel.mealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mealViewModel.getAll()
        mealViewModel.fetchAllByCategory(category)

        //Back button returns us to categories list
        binding.mealListBackButton.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace((context as MainActivity).binding.fragmentContainer.id, HomeFragment()).commit()
        }

        //Load previous 10 meals
        binding.mealBackwardPagination.setOnClickListener{
            if ((currentPage-1) >= 0){
                currentPage -= 1
                mealAdapter.submitList(allMeals.subList(currentPage * mealsPerPage, (currentPage+1) * mealsPerPage))
            }
        }

        //Load next 10 meals
        binding.mealForwardPagination.setOnClickListener{
            if ((currentPage+1) * mealsPerPage <= allMeals.size){
                currentPage += 1
                if ((currentPage+1) * mealsPerPage <= allMeals.size){
                    mealAdapter.submitList(allMeals.subList(currentPage * mealsPerPage, (currentPage+1) * mealsPerPage))
                }
                else {
                    mealAdapter.submitList(allMeals.subList(currentPage * mealsPerPage, allMeals.size))
                }
            }
        }
    }

    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                showLoadingState(false)
                allMeals = state.meals
                mealAdapter.submitList(state.meals.subList(0,mealsPerPage))
            }
            is MealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
                showLoadingState(false)
                Toast.makeText(context, getString(R.string.data_fetched), Toast.LENGTH_LONG).show()
            }
            is MealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun showLoadingState(loading: Boolean) {
        binding.searchMealList.isVisible = !loading
        binding.mealsRV.isVisible = !loading
        binding.loadingMeals.isVisible = loading
    }
}