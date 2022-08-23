package project.yenguema.yenguema.fragments

import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentSignInBinding
import project.yenguema.yenguema.library.formController
import project.yenguema.yenguema.model.SignInViewModel

class SignInFragment : Fragment() {
    private var _binding:FragmentSignInBinding?=null
    private val binding get() = _binding!!
    private lateinit var signInViewModel: SignInViewModel
    private var  gender:String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        signInViewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        gender = getGender()

        binding.signBtn.setOnClickListener {
            signIn(gender)
        }

        return view
    }
    private fun getGender():String?{
        binding.maleGender.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                gender = "M"
            }
        }
        binding.femaleGender.setOnCheckedChangeListener { compoundButton, b ->
            if(b){
                gender = "F"
            }
        }
        return gender
    }
    private fun signIn(gender:String?) {
        if (gender != null && formController(getString(R.string.emptyInput), getString(R.string.wrongFormat),
                binding.userEmail,
                binding.userName,
                binding.userPassword,
                binding.phonenumber) && passwordCheck(binding.userPassword.text.toString(), binding.passwordConfirm)) {
            signInViewModel.newUser(gender,
                binding.userEmail.text.toString(),
                binding.userName.text.toString(),
                binding.userPassword.text.toString(),
                binding.phonenumber.text.toString()
            ).observe(viewLifecycleOwner) { resp ->
                binding.signBtn.isEnabled = false
                if (resp!=null && resp.registered) {
                    Toast.makeText(requireContext(), resp.response, Toast.LENGTH_LONG).show()
                    //action
                } else {
                    Toast.makeText(requireContext(), resp.response, Toast.LENGTH_LONG).show()
                    binding.signBtn.isEnabled = true
                }
            }
        }
    }

    private fun passwordCheck(passwd:String, confirm:EditText):Boolean{
        if(passwd != confirm.text.toString()){
            confirm.error = getString(R.string.err_passC)
            return false
        }
        return true
    }
    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }
}