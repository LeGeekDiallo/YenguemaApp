package project.yenguema.yenguema.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivitySignInBinding
import project.yenguema.yenguema.model.SignInViewModel
import java.util.regex.Pattern

class SignInActivity : AppCompatActivity() {
    private val PHONNUMBERPARTTERN: Pattern = Pattern.compile("^[6][2-9][0-9][0-9]{2}[0-9]{2}[0-9]{2}\$")
    private lateinit var binding:ActivitySignInBinding
    private lateinit var signInViewModel: SignInViewModel
    private var  gender:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.signInActivity)
        signInViewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        gender = getGender()

        binding.signBtn.setOnClickListener {
            signIn(gender)
        }
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
    class Form(
        val flag:Boolean,
        val username:String,
        val password:String,
        val phoneNumber:String,
        val email:String
    )
    private fun formControl():Form{
        var flag = false
        gender = getGender()
        val email = binding.userEmail.text.toString().trim()
        val username = binding.userName.text.toString().trim()
        val password = binding.userPassword.text.toString().trim()
        val confirmP = binding.passwordConfirm.text.toString().trim()
        val phoneNumber = binding.phonenumber.text.toString().trim()
        if (email.isEmpty()){
            binding.userEmail.setError(getString(R.string.err_email))
        }else if(username.isEmpty()){
            binding.userName.setError(getString(R.string.err_name))
        }else if(password.isEmpty()){
            binding.userPassword.setError(getString(R.string.err_pass))
        }else if(confirmP.isEmpty() || !passwordCheck(password, confirmP)){
            binding.passwordConfirm.setError(getString(R.string.err_passC))
        }else if(phoneNumber.isEmpty()){
            binding.phonenumber.setError(getString(R.string.err_phone))
        }else if(!phoneNumberCheck()){
            binding.phonenumber.setError(getString(R.string.err_phone_reg))
        } else{
            flag = true
        }
        return Form(flag, username, password, phoneNumber, email)
    }
    private fun signIn(gender:String?) {
        val form = formControl()
        Toast.makeText(this, gender, Toast.LENGTH_LONG).show()
        if (gender != null && form.flag) {
            Toast.makeText(this, gender+ form.email, Toast.LENGTH_LONG).show()
            signInViewModel.newUser(gender, form.email, form.username, form.password, form.phoneNumber).observe(this) { resp ->
                if (resp!=null && resp.registered) {
                    Toast.makeText(this, resp.response, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, null, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun passwordCheck(passwd:String, confirm:String):Boolean{
        return passwd == confirm
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.signin_menu, menu)
        return true
    }
    private fun phoneNumberCheck():Boolean{
        return PHONNUMBERPARTTERN.matcher(binding.phonenumber.text.toString().trim()).matches()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home->{
                val intent = Intent(this, MainActivity::class.java)
                startAnActivity(intent)
                true

            }
            R.id.logIn->{
                val intent = Intent(this, LogInActivity::class.java)
                startAnActivity(intent)
                true
            }
            else -> false
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }
}