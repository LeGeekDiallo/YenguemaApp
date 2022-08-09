package project.yenguema.yenguema.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.DashboardAdapter
import project.yenguema.yenguema.model.ProfileViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [UserDashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserDashboard : Fragment(), View.OnClickListener {
    private lateinit var dashboardAdapter: DashboardAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_user_dashboard, container, false)
        dashboardAdapter = DashboardAdapter()

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        recyclerView = view.findViewById(R.id.dashboard_items_recyclerview)
        recyclerView.adapter = dashboardAdapter

        dashboardAdapter.setData(profileViewModel.getServicesTitle().value!!, profileViewModel.getServicesImg().value!!)
        recyclerView.layoutManager = GridLayoutManager(context, 3)

        dashboardAdapter.newActivityListener = object : DashboardAdapter.NewActivityByService{
            override fun newActivityByService(serviceName: String) {
                Toast.makeText(context, serviceName, Toast.LENGTH_SHORT).show()
            }

        }
        return view
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }


}