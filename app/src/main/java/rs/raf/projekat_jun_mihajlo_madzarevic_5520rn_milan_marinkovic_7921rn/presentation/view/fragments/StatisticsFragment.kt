package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.SavedMealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.databinding.FragmentStatisticsBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.activities.MainActivity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.SavedMealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.MealViewModel
import timber.log.Timber
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class StatisticsFragment(private val category: String) : Fragment(){
    private lateinit var binding: FragmentStatisticsBinding

    private val mealViewModel: MainContract.MealViewModel by viewModel<MealViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStatisticsBinding.inflate(layoutInflater)
        activity?.title = getString(R.string.mealStatisticTitle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initObservers()
        initListeners()
    }

    private fun initListeners(){
        binding.chartBackButton.setOnClickListener{
            (requireActivity() as MainActivity).supportFragmentManager.beginTransaction().replace((requireActivity() as MainActivity).binding.fragmentContainer.id , MealListFragment(category)).commit()
        }
    }

    private fun initObservers(){
        mealViewModel.savedMealOriginalState.observe(viewLifecycleOwner, Observer {
            Timber.e(it.toString())
            renderState(it)
        })
        mealViewModel.getAllSaved()
    }

    private fun renderState(state: SavedMealState) {
        when (state) {
            is SavedMealState.Success -> {
                initGraph(state.meals)
            }
            is SavedMealState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initGraph(meals: List<SavedMealEntity>){
        val lineChart: LineChart = binding.chart

        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.isDragEnabled = true

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val yAxis = lineChart.axisLeft
        yAxis.setDrawGridLines(false)

        lineChart.axisRight.isEnabled = false

        val mealsAdded = mealsAddedInLast7Days(meals)

        val entries = listOf(Entry(1f, mealsAdded[6].toFloat()), Entry(2f, mealsAdded[5].toFloat()), Entry(3f, mealsAdded[4].toFloat()), Entry(4f, mealsAdded[3].toFloat()), Entry(5f, mealsAdded[2].toFloat()), Entry(6f, mealsAdded[1].toFloat()), Entry(7f, mealsAdded[0].toFloat()))
        val dataSet = LineDataSet(entries, "Meals added")
        val lineData = LineData(dataSet)

        lineChart.data = lineData
        lineChart.invalidate()
    }

    private fun mealsAddedInLast7Days(meals: List<SavedMealEntity>): List<Int>{

        val mealsAdded: MutableList<Int> = mutableListOf(0,0,0,0,0,0,0)
        val now: LocalDate = LocalDate.now()

        for (meal in meals){
            val mealDate: LocalDate = Instant.ofEpochMilli(meal.datePlaned).atZone(ZoneId.systemDefault()).toLocalDate()
            if ((mealDate.year == now.year && mealDate.month == now.month) && (now.dayOfMonth - mealDate.dayOfMonth in 0..6) ){
                mealsAdded[now.dayOfMonth - mealDate.dayOfMonth] += 1
            }
        }

        return mealsAdded.toList()
    }
}