package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.databinding.ActivityMainBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.fragments.LoginFragment
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.LoginViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private val loginViewModel: MainContract.ViewModel by viewModel<LoginViewModel>()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if (sharedPreferences.getString("loggedIn", "").equals("")){
            val login = LoginFragment()
            supportFragmentManager.beginTransaction()
                .add(binding.fragmentContainer.id, login)
                .commit()
        }

        setContentView(binding.root)

        init()
    }

    /**
     * Initializes all the necessary components
     */
    private fun init(){
        initDB()
    }

    /**
     * Inserts predefined users into the database
     */
    private fun initDB(){

        val list = mutableListOf(
            UserEntity(
                0,
                "mixa",
                "1234"
            )
        )

        loginViewModel.insertUsers(list)
    }


}