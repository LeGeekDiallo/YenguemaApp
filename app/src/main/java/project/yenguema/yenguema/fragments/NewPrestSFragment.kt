package project.yenguema.yenguema.fragments

import android.icu.number.Notation.simple
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.NewPrestSFormViewPager2Adapter
import project.yenguema.yenguema.databinding.FragmentNewPrestSBinding
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.fragments.form.prestSForm.NewPrestSFormStepContact
import project.yenguema.yenguema.fragments.form.prestSForm.NewPrestSFormStepOne
import project.yenguema.yenguema.fragments.form.prestSForm.NewPrestSFormStepTwo
import project.yenguema.yenguema.library.formController


/**
 * create an instance of this fragment.
 */
class NewPrestSFragment : Fragment()  {
    private var _binding:FragmentNewPrestSBinding?=null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPrestSBinding.inflate(inflater, container, false)
        val view = binding.root

        val listFormFragment = arrayOf<Fragment>(
            NewPrestSFormStepOne(),
            NewPrestSFormStepTwo(),
            NewPrestSFormStepContact(),
            ChoosePrestSImagesFragment()
        )

        val adapter = NewPrestSFormViewPager2Adapter(
            listFormFragment,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.newPrestSViewpager2.adapter = adapter
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}