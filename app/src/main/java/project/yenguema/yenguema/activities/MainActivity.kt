package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
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
        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.fragmentContainerView2)
        bottomNavigationView.setupWithNavController(navController)
        val appBarConfig = AppBarConfiguration(setOf(R.id.logInFragment, R.id.homeFragment, R.id.signInFragment))
        setupActionBarWithNavController(navController, appBarConfig)
        if(!email.isNullOrBlank()){
            bottomNavigationView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if(email != null)
            menuInflater.inflate(R.menu.main_menu_log_in, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
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
