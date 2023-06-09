package rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.R
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.databinding.FragmentLoginBinding
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.contract.MainContract
import rs.raf.projekat_jun_mihajlo_madzarevic_5520rn_milan_marinkovic_7921rn.presentation.viewmodels.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    private val loginViewModel: MainContract.ViewModel by viewModel<LoginViewModel>()
    private val sharedPreferences: SharedPreferences by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.button.setOnClickListener {
            loginViewModel.getByUsernameAndPassword(
                binding.loginUsername.text.toString(),
                binding.loginPassword.text.toString()
            )
                .subscribe(
                    {
                        if (it == null) {
                            Toast.makeText(requireContext(), R.string.bad_credentials, Toast.LENGTH_LONG).show()
                        }
                        else{
                            sharedPreferences.edit().putString("loggedIn", binding.loginUsername.text.toString()).apply()
                            activity?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
                        }
                    },
                    { error ->
                        Toast.makeText(requireContext(), R.string.bad_credentials, Toast.LENGTH_LONG).show()
                    }
                )
        }
    }
}