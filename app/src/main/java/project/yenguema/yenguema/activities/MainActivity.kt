package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var email: String? = null

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.servicesItem.columnCount=2
        binding.servicesItem.rowCount = 4

        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)

        email = sharedPref.getString(getString(R.string.user_email_shared), null)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(email ==null)
            menuInflater.inflate(R.menu.main_menu, menu)
        else
            menuInflater.inflate(R.menu.main_menu_log_in, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startAnActivity(intent)
                true
            }
            R.id.account->{
                val intent = Intent(this, LogInActivity::class.java)
                startAnActivity(intent)
                true
            }
            R.id.sign_in->{
                val intent = Intent(this, SignInActivity::class.java)
                startAnActivity(intent)
                true
            }
            R.id.profile->{
                val intent = Intent(this, UserProfileActivity::class.java)
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
