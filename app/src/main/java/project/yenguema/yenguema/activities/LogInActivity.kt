package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityLogInBinding
import project.yenguema.yenguema.model.LogInViewModel

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding
    private lateinit var logInViewModel: LogInViewModel

    private lateinit var email:String
    private lateinit var password:String

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        supportActionBar?.title = getString(R.string.logInActivity)

        logInViewModel = ViewModelProvider(this)[LogInViewModel::class.java]

        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)

        binding.loginBtn.setOnClickListener {
            logIn()
        }

    }

    private fun inputControl():Boolean{
        email = binding.userEmail.text.toString().trim()
        password = binding.userPassword.text.toString().trim()
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun logIn(){
        val control = inputControl()
        if (control){
            logInViewModel.logInProcess(email, password).observe(this){resp ->
                binding.loginBtn.isEnabled = false
                if (resp.logIn != null && resp.logIn){
                    val editor:SharedPreferences.Editor = sharedPref.edit()
                    editor.putString(getString(R.string.user_email_shared), email)
                    editor.putString(getString(R.string.user_passwd_shared), password)
                    editor.apply()
                    val intent = Intent(this, UserProfileActivity::class.java)
                    startAnActivity(intent)
                    finish()
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.login_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home->{
                val intent = Intent(this, MainActivity::class.java)
                startAnActivity(intent)
                true

            }
            R.id.new_user->{
                val intent = Intent(this, SignInActivity::class.java)
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