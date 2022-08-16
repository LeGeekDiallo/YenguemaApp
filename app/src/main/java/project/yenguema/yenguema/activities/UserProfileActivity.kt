package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.setPadding
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityUserProfileBinding
import project.yenguema.yenguema.fragments.DashboardFragment
import project.yenguema.yenguema.fragments.PrestSFragment
import project.yenguema.yenguema.fragments.UserInfoFragment
import project.yenguema.yenguema.model.ProfileViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.profile)
        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)

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
            else->false
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }


}