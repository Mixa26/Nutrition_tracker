package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.Single
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.data.models.UserEntity
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.databinding.ActivityLoginBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private val loginViewModel: MainContract.ViewModel by viewModel<LoginViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init(){
        initDB()
        initUi()
    }

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

    private fun initUi(){
        binding.button.setOnClickListener {
            loginViewModel.getByUsername(binding.loginUsername.text.toString())
                .subscribe({
                    userEntity ->
                        if (userEntity == null) {
                            Toast.makeText(this, R.string.bad_credentials, Toast.LENGTH_LONG).show()
                        }
                    },
                    {error -> Toast.makeText(this,  R.string.error, Toast.LENGTH_LONG).show()})
        }
    }
}