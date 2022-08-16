package project.yenguema.yenguema.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentEditPrestSBinding
import project.yenguema.yenguema.model.ProfileViewModel


/**
 * A simple [Fragment] subclass.
 */
class EditPrestSFragment : Fragment() {
    private val args:EditPrestSFragmentArgs by navArgs()
    private var _binding:FragmentEditPrestSBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditPrestSBinding.inflate(inflater, container, false)
        val prestS = args.prest
        val view = binding.root

        binding.prestsTitle.setText(prestS.activity_name)
        binding.city.setText(prestS.city)
        binding.municipality.setText(prestS.municipality)
        binding.district.setText(prestS.address)
        binding.email.setText(prestS.email)
        binding.phonenumber.setText(prestS.phone_number)
        binding.details.setText(HtmlCompat.fromHtml(prestS.details, HtmlCompat.FROM_HTML_MODE_LEGACY))
        binding.editBtn.setOnClickListener {

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}