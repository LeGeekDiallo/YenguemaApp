package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding:FragmentHomeBinding?=null
    private val binding get() = _binding!!
    private var email: String? = null
    private lateinit var sharedPref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        binding.servicesItem.columnCount=2
        binding.servicesItem.rowCount = 4
        sharedPref = requireActivity().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)
        return view
    }
}