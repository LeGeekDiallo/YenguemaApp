package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentUserInfoBinding
import project.yenguema.yenguema.model.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        val data = profileViewModel.getUserInfo(email!!).value
        val username = view.findViewById<TextView>(R.id.user_name)
        val userName = view.findViewById<EditText>(R.id.user_name_edit)
        val userAv = view.findViewById<ImageView>(R.id.user_av)
        val userEmail = view.findViewById<EditText>(R.id.user_email)
        val userPhone = view.findViewById<EditText>(R.id.user_phone_number)
        val user = data?.user_infos!!
        username.text = user.name
        userName.setText(user.name)
        userEmail.setText(user.email)
        userPhone.setText(user.phoneNumber)
        if(user.avatar.isNotEmpty()){
            val avatar = user.avatarURL+user.avatar
            Picasso.get()
                .load(avatar)
                .error(R.drawable.user_av_default)
                .fit()
                .placeholder(R.drawable.user_av_default)
                .into(userAv)
        }
        return view
    }

}