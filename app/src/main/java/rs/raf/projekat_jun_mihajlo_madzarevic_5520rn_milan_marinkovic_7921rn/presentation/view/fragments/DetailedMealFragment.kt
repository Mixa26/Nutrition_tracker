package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.databinding.FragmentDetailedMealBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.activities.MainActivity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.MealViewModel
import timber.log.Timber

class DetailedMealFragment(private val meal: MealEntity) : Fragment() {
    private lateinit var binding : FragmentDetailedMealBinding

    private val mealViewModel: MainContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var mealResponse: MealEntity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailedMealBinding.inflate(layoutInflater)
        activity?.title = meal.strMeal

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initListeners()
        initObservers()
    }

    private fun initListeners(){
        //Back button returns us to categories list
        binding.detailedMealBackButton.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace((context as MainActivity).binding.fragmentContainer.id, MealListFragment(meal.strCategory)).commit()
        }
    }

    private fun initObservers(){
        mealViewModel.mealState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mealViewModel.getAllByName(meal.strMeal)
        mealViewModel.fetchAllByName(meal.strMeal)
    }

    private fun renderState(state: MealState) {
        when (state) {
            is MealState.Success -> {
                showLoadingState(false)
                mealResponse = state.meals[0]
                fillData(mealResponse)
            }
            is MealState.Error -> {
                showLoadingState(false)
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
                showLoadingState(true)
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealState.Loading -> {
                showLoadingState(true)
            }
        }
    }

    private fun fillData(data: MealEntity){
        Picasso.get().load(data.strMealThumb).into(binding.detailedMealPic)
        binding.detailedMealCategory.text = getString(R.string.detailedMealCategory) + " " + data.strCategory

        if (data.strArea != null)
            binding.detailedMealArea.text = getString(R.string.detailedMealArea) + " " + data.strArea
        else
            binding.detailedMealArea.text = getString(R.string.detailedMealArea) + " " + getString(R.string.data_not_available)

        if (data.strInstructions != null)
            binding.detailedMealInstructions.text = getString(R.string.detailedMealInstructions) + "\n" + data.strInstructions
        else
            binding.detailedMealInstructions.text = getString(R.string.detailedMealInstructions) + "\n" + getString(R.string.data_not_available)

        if (data.strTags != null)
            binding.detailedMealTags.text = getString(R.string.detailedMealTags) + "\n" + data.strTags
        else
            binding.detailedMealTags.text = getString(R.string.detailedMealTags) + "\n" + getString(R.string.data_not_available)

        if (data.strYoutube != null)
            binding.detailedMealVideo.text = getString(R.string.detailedMealVideo) + "\n" + data.strYoutube
        else
            binding.detailedMealVideo.text = getString(R.string.detailedMealVideo) + "\n" + getString(R.string.data_not_available)

        var ingredients: MutableList<String?> = mutableListOf()
        var measures: MutableList<String?> = mutableListOf()

        var ingredientsMeasures: String = getString(R.string.detailedMealIngredients) + "\n"

        ingredients.addAll(listOf(
            data.strIngredient1,
            data.strIngredient2,
            data.strIngredient3,
            data.strIngredient4,
            data.strIngredient5,
            data.strIngredient6,
            data.strIngredient7,
            data.strIngredient8,
            data.strIngredient9,
            data.strIngredient10,
            data.strIngredient11,
            data.strIngredient12,
            data.strIngredient13,
            data.strIngredient14,
            data.strIngredient15,
            data.strIngredient16,
            data.strIngredient17,
            data.strIngredient18,
            data.strIngredient19,
            data.strIngredient20
        ))

        measures.addAll(listOf(
            data.strMeasure1,
            data.strMeasure2,
            data.strMeasure3,
            data.strMeasure4,
            data.strMeasure5,
            data.strMeasure6,
            data.strMeasure7,
            data.strMeasure8,
            data.strMeasure9,
            data.strMeasure10,
            data.strMeasure11,
            data.strMeasure12,
            data.strMeasure13,
            data.strMeasure14,
            data.strMeasure15,
            data.strMeasure16,
            data.strMeasure17,
            data.strMeasure18,
            data.strMeasure19,
            data.strMeasure20
        ))

        for (i in 1..19){
            if (ingredients[i] == null){
                if (i == 1)ingredientsMeasures += getString(R.string.data_not_available)
                break
            }
            ingredientsMeasures += ingredients[i] + " " + measures[i] + "\n"
        }

        binding.detailedMealAllIngredients.text = ingredientsMeasures
    }

    private fun showLoadingState(loading: Boolean) {
        binding.detailedMealBackButton.isVisible = !loading
        binding.detailedMealPic.isVisible = !loading
        binding.detailedMealCategory.isVisible = !loading
        binding.detailedMealArea.isVisible = !loading
        binding.detailedMealInstructions.isVisible = !loading
        binding.detailedMealTags.isVisible = !loading
        binding.detailedMealVideo.isVisible = !loading
        binding.detailedMealAllIngredients.isVisible = !loading
        binding.detailedMealSaveMealButton.isVisible = !loading
        binding.loadingDetailedMeal.isVisible = loading
    }
}