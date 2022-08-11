package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.DashboardAdapter
import project.yenguema.yenguema.model.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment(), View.OnClickListener {

    interface OnClickListener{
        fun userPersonalInfo()
        fun launchServiceFragment(serviceName: String)
    }
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var recyclerView: RecyclerView
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null
    //private lateinit var _binding: FragmentUserDashboardBinding
    private lateinit var userName:TextView
    private lateinit var userAv:ImageView
    var onClickListener:OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_dashboard, container, false)
        userName = view.findViewById(R.id.user_name)
        userAv = view.findViewById(R.id.user_av)
        dashboardAdapter = DashboardAdapter()
        recyclerView = view.findViewById(R.id.dashboard_items_recyclerview)
        recyclerView.adapter = dashboardAdapter

        dashboardAdapter.setData(profileViewModel.getServicesTitle().value!!, profileViewModel.getServicesImg().value!!)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        dashboardAdapter.newActivityListener = object : DashboardAdapter.NewActivityByService{
            override fun newActivityByService(serviceName: String) {
                Toast.makeText(context, serviceName, Toast.LENGTH_SHORT).show()
                launchServiceItem(serviceName)
            }
        }
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        getUserInfo(email!!)
        userAv.setOnClickListener {
            onClickListener?.userPersonalInfo()
        }
        return view
    }

    private fun launchServiceItem(serviceName: String) {
        when(serviceName){
            "PrestS"->{
                onClickListener?.launchServiceFragment(serviceName)
            }
        }
    }
    override fun onClick(view: View?) {
        //
    }

    private fun getUserInfo(email:String){
        profileViewModel.getUserInfo(email).observe(viewLifecycleOwner){ resp->
            val user = resp?.user_infos
            if(user?.gender!! > 0){
                userName.text = "Mr" + " "+user.name
            }else{
                userName.text = "Mme" + " "+user.name
            }
            if(user.avatar.isNotEmpty()){
                val avatar = user.avatarURL+user.avatar
                Picasso.get()
                    .load(avatar)
                    .error(R.drawable.user_av_default)
                    .fit()
                    .placeholder(R.drawable.user_av_default)
                    .into(userAv)
            }
        }
    }
}