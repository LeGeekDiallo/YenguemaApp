package project.yenguema.yenguema.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityUserProfileBinding
import project.yenguema.yenguema.fragments.DashboardFragment
import project.yenguema.yenguema.fragments.PrestSFragment
import project.yenguema.yenguema.fragments.UserInfoFragment
import project.yenguema.yenguema.model.ProfileViewModel

class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var sharedPref: SharedPreferences
    private val profileViewModel: ProfileViewModel by viewModels()
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.title = getString(R.string.profile)
        sharedPref = getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)

        val dashboardFragment = DashboardFragment()
        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.dashboard_items, dashboardFragment)
                .commit()
        }
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        dashboardFragment.onClickListener = object : DashboardFragment.OnClickListener{
            override fun userPersonalInfo() {
                userInfoFragment()
            }

            override fun launchServiceFragment(serviceName: String) {
                when (serviceName){
                    "PrestS"->{
                        prestSFragment()
                    }
                }
            }
        }
    }

    private fun prestSFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace<PrestSFragment>(R.id.dashboard_items)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    private fun userInfoFragment() {
        supportFragmentManager.commit {
            setCustomAnimations(
                R.anim.slide_in,
                R.anim.fade_out,
                R.anim.fade_in,
                R.anim.slide_out
            )
            replace<UserInfoFragment>(R.id.dashboard_items)
            setReorderingAllowed(true)
            addToBackStack(null)
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
                userInfoFragment()
                true
            }
            else->false
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }


}