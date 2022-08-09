package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityUserProfileBinding
import project.yenguema.yenguema.fragments.UserDashboard

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.profile)
        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val dashboardFragment = UserDashboard()
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.dashboard_items, dashboardFragment)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startAnActivity(intent)
                true
            }
            R.id.logOut->{
                val editor:SharedPreferences.Editor = sharedPref.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startAnActivity(intent)
                finish()
                true
            }
            R.id.user_activities->{
                true
            }
            else->false
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }
}