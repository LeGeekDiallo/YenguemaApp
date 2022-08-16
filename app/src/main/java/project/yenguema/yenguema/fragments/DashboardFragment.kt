package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.DashboardAdapter
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.entity.User
import project.yenguema.yenguema.model.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment(), View.OnClickListener {
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var recyclerView: RecyclerView
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null
    private lateinit var userName:TextView
    private lateinit var userAv:ImageView
    private lateinit var user:User
    private  var prest: PrestS? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_dashboard, container, false)
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        userName = view.findViewById(R.id.user_name)
        userAv = view.findViewById(R.id.user_av)
        dashboardAdapter = DashboardAdapter()
        recyclerView = view.findViewById(R.id.dashboard_items_recyclerview)
        recyclerView.adapter = dashboardAdapter

        dashboardAdapter.setData(profileViewModel.getServicesTitle().value!!, profileViewModel.getServicesImg().value!!)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        dashboardAdapter.newActivityListener = object : DashboardAdapter.NewActivityByService{
            override fun newActivityByService(serviceName: String) {
                launchServiceItem(serviceName, view)
            }
        }
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        getUserInfo(email!!)
        userAv.setOnClickListener {
            val action = DashboardFragmentDirections.navigateToUserInfoFragment(user)
            Navigation.findNavController(it).navigate(action)
        }
        return view
    }

    private fun launchServiceItem(serviceName: String, view: View) {
        when(serviceName){
            "PrestS"->{
                if(prest!=null) {
                    val action = DashboardFragmentDirections.navigateToPrestSFragment(prest!!)
                    Navigation.findNavController(view).navigate(action)
                }else{
                    val action = DashboardFragmentDirections.navigateToNoPrestSYetFragment(user)
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
    }
    private fun startAnActivity(intent: Intent){
        startActivity(intent)
    }
    override fun onClick(view: View?) {
        //
    }

    private fun getUserInfo(email:String){
        profileViewModel.getUserInfo(email).observe(viewLifecycleOwner){ resp->
            user = resp?.user_infos!!
            if(resp.prestS != null)
                prest = resp.prestS
            if(user.gender > 0){
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