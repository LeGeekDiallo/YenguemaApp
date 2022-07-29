package project.yenguema.yenguema.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.servicesItem.columnCount=2
        binding.servicesItem.rowCount = 4
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
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
            else -> false
        }
    }

    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }
}
