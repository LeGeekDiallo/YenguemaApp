package project.yenguema.yenguema.fragments.form.prestSForm

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentNewPrestSFormStepTwoBinding
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.library.formController


class NewPrestSFormStepTwo : Fragment() {
    private var _binding:FragmentNewPrestSFormStepTwoBinding?=null
    private val args:NewPrestSFormStepTwoArgs by navArgs()
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewPrestSFormStepTwoBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        binding.nextBtn.setOnClickListener {
            if(formController(getString(R.string.emptyInput), getString(R.string.wrongFormat), binding.city, binding.municipality, binding.district)){
                val action = NewPrestSFormStepTwoDirections.navigateToNewPrestSFormStepContact(
                    NewPrestS(
                        args.prestS.user_email,
                        args.prestS.activity_name,
                        args.prestS.category,
                        binding.city.text.toString(),
                        binding.municipality.text.toString(),
                        binding.district.text.toString()
                    )
                )
                Navigation.findNavController(view).navigate(action)
            }
        }
        return view
    }
}