package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.MealEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.databinding.FragmentPlanBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter.MealAdapter
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.adapter.PlanMealAdapter
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.recycler.differ.MealDiffItemCallback
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.states.MealState
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.MealViewModel
import timber.log.Timber

class WeeklyPlanFragment: Fragment() {

    private lateinit var binding: FragmentPlanBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var mealAdapter: PlanMealAdapter

    private val mealViewModel: MainContract.MealViewModel by viewModel<MealViewModel>()

    private lateinit var allMeals: List<MealEntity>
    private var countDownTimer: CountDownTimer? = null

    val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")


    companion object{

        private var mealsPerDay: List<MealEntity> = mutableListOf()

        private var dayCounter: Int = 1

        private val hashMap: HashMap<Int, List<MealEntity>> = HashMap()

        init{
            for(i in 1..7){
                hashMap[i] = mutableListOf()
            }
        }

        fun addMealToDay(meal: MealEntity) {
            if (!mealsPerDay.contains(meal)) {
                mealsPerDay = mealsPerDay + meal
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlanBinding.inflate(layoutInflater)
        recyclerView = binding.weeklyPlanRv

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init(){
        initUI()
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun initUI(){
        binding.dayTv.text = daysOfWeek.get(dayCounter-1)
        binding.weeklyPlanEmail.isEnabled = false;
        binding.sendPlanBtn.isEnabled = false
    }

    private fun initRecycler(){
        mealAdapter = PlanMealAdapter(MealDiffItemCallback(), null)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mealAdapter
    }

    private fun initListeners(){
        binding.weeklyPlanInputCategory.doAfterTextChanged { editable ->
            countDownTimer?.cancel()

            countDownTimer = object: CountDownTimer(2000, 1000){
                override fun onTick(millisUntilFinished: Long) {
                    //Ne radi nista
                }

                override fun onFinish() {
                    val filter = editable.toString()
                    if(filter.isEmpty()){
                        mealAdapter.submitList(allMeals)
                    }else{
                        mealViewModel.getAll()
                        mealViewModel.fetchAllByCategory(filter)
                    }
                }
            }
            countDownTimer?.start()
        }
        binding.nextDayBtn.setOnClickListener {



            hashMap[dayCounter] = mealsPerDay

            if(dayCounter >= 1 && dayCounter <= 7){
                Toast.makeText(context,"You added meals for ${daysOfWeek.get(dayCounter-1)}", Toast.LENGTH_SHORT).show()
            }

            mealsPerDay = mutableListOf()

            dayCounter++

            if(dayCounter >= 1 && dayCounter <= 7){
                binding.dayTv.text = daysOfWeek.get(dayCounter-1)
            }

            if(dayCounter == 8){
                binding.nextDayBtn.isEnabled = false
                Toast.makeText(context,"Now you can enter the email and click send button to send plan", Toast.LENGTH_SHORT).show()
                binding.weeklyPlanEmail.isEnabled = true
                binding.sendPlanBtn.isEnabled = true
                dayCounter = 1
            }

        }
        binding.sendPlanBtn.setOnClickListener {

            val stringBuilder = StringBuilder()

            if(binding.weeklyPlanEmail.toString().isEmpty()){
               Toast.makeText(context, "You have to enter email before click on Send button", Toast.LENGTH_SHORT).show()
            }else{
                var day: String
                hashMap.forEach{(key, value)->
                    day = daysOfWeek[key-1]
                    stringBuilder.append(day)
                    stringBuilder.append(":")
                    stringBuilder.append("/n")
                    value.forEach{meal ->
                        stringBuilder.append(meal.strMeal)
                        stringBuilder.append("/n")
                    }
                    stringBuilder.append("/n/n")
                }

                stringBuilder.append("You can open application by clicking on following link: ")
                stringBuilder.append("<a href='https://www.nutrition_tracker.rs'>https://www.nutrition_tracker.rs</a>")

                val emailIntent = Intent(Intent.ACTION_SEND)
                emailIntent.data = Uri.parse("mailto:")
                emailIntent.type = "text/plain"

                var email: String = binding.weeklyPlanEmail.toString().trim()

                emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Weekly meal plan") // Unesite naslov emaila
                emailIntent.putExtra(Intent.EXTRA_TEXT, stringBuilder.toString())

                try {
                    //start email intent
                    startActivity(Intent.createChooser(emailIntent, "Choose Email Client..."))
                }
                catch (e: Exception){
                    //if any thing goes wrong for example no email client application or any exception
                    //get and show exception message
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }

                Timber.e(stringBuilder.toString())

                mealsPerDay = mutableListOf()
                for(i in 1..7){
                    hashMap[i] = mutableListOf()
                }

                binding.nextDayBtn.isEnabled = true
                Toast.makeText(context, "You created weekly meal plan succesfully!", Toast.LENGTH_SHORT).show()
                binding.sendPlanBtn.isEnabled = false
                binding.weeklyPlanEmail.text.clear()
                binding.weeklyPlanEmail.isEnabled = false
            }

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
                allMeals = state.meals
                mealAdapter.submitList(state.meals)
            }
            is MealState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            }
            is MealState.DataFetched -> {
                Toast.makeText(context, "Fresh data fetched from the server", Toast.LENGTH_LONG).show()
            }
            is MealState.Loading -> {

            }
        }
    }


}