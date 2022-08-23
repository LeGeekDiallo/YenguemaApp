package project.yenguema.yenguema.fragments.form.prestSForm

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentNewPrestSFormStepOneBinding
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.library.formController

class NewPrestSFormStepOne() : Fragment(), AdapterView.OnItemSelectedListener {
    private var _binding:FragmentNewPrestSFormStepOneBinding?=null
    private val binding get() = _binding!!
    private lateinit var category:String
    private val args:NewPrestSFormStepOneArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewPrestSFormStepOneBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.prests_category,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.prestsCategory.adapter = it
        }
        binding.prestsCategory.onItemSelectedListener = this
        binding.nextBtn.setOnClickListener {
            if(formController(getString(R.string.emptyInput), getString(R.string.wrongFormat), binding.prestsTitle)){
                val prestS = NewPrestS(args.userEmail, binding.prestsTitle.text.toString(), category)
                val action = NewPrestSFormStepOneDirections.navigateToNewPrestSFormStepTwo(prestS)
                Navigation.findNavController(view).navigate(action)
            }
        }
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent!!.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}