package project.yenguema.yenguema.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import project.yenguema.yenguema.R

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.title = getString(R.string.logInActivity)
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