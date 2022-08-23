package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import project.yenguema.yenguema.R
import project.yenguema.yenguema.activities.UserProfileActivity
import project.yenguema.yenguema.databinding.FragmentLogInBinding
import project.yenguema.yenguema.library.formController
import project.yenguema.yenguema.model.LogInViewModel

class LogInFragment : Fragment() {
    private var _binding:FragmentLogInBinding? = null
    private val binding get() = _binding!!
    private lateinit var logInViewModel: LogInViewModel
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        logInViewModel = ViewModelProvider(this)[LogInViewModel::class.java]

        sharedPref = requireActivity().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)

        binding.loginBtn.setOnClickListener {
            logIn()
        }
        return view
    }
    private fun logIn(){
        if (formController(getString(R.string.emptyInput), getString(R.string.wrongFormat), binding.userEmail, binding.userPassword)){
            logInViewModel.logInProcess(binding.userEmail.text.toString(), binding.userPassword.text.toString()).observe(viewLifecycleOwner){resp ->
                binding.loginBtn.isEnabled = false
                if (resp.logIn != null && resp.logIn){
                    val editor:SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(getString(R.string.user_email_shared), binding.userEmail.text.toString())
                    editor.putString(getString(R.string.user_passwd_shared), binding.userPassword.text.toString())
                    editor.apply()
                    val intent = Intent(requireContext(), UserProfileActivity::class.java)
                    startAnActivity(intent)
                    requireActivity().finish()
                }else{
                    if(resp.logIn == null){
                        binding.userEmail.error = getString(R.string.no_email)
                        binding.loginBtn.isEnabled = true
                    }else{
                        binding.userPassword.error = getString(R.string.bad_pwd)
                        binding.loginBtn.isEnabled = true
                    }
                }
            }
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }

}