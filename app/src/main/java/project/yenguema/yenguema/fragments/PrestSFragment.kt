package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import project.yenguema.yenguema.R
import project.yenguema.yenguema.model.ProfileViewModel


/**
 * A simple [Fragment] subclass.
 */
class PrestSFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prest_s, container, false)
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        val data = profileViewModel.getUserInfo(email!!).value
        val prestS = data?.prestS!!
        val prestSName = view.findViewById<TextView>(R.id.prests_title)
        prestSName.text = prestS.activity_name
        return view
    }
}