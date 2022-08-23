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
import project.yenguema.yenguema.databinding.FragmentNewPrestSFormStepContactBinding
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.library.formController


class NewPrestSFormStepContact : Fragment() {
    private var _binding:FragmentNewPrestSFormStepContactBinding?=null
    private val binding get() = _binding!!
    private val args: NewPrestSFormStepContactArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewPrestSFormStepContactBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        binding.nextBtn.setOnClickListener {
            if(formController(getString(R.string.emptyInput), getString(R.string.wrongFormat), binding.email, binding.phonenumber, binding.details)){
                val action = NewPrestSFormStepContactDirections.navigateToChoosePrestSImagesFragment(
                    NewPrestS(
                        args.prestS.user_email,
                        args.prestS.activity_name,
                        args.prestS.category,
                        args.prestS.city,
                        args.prestS.municipality,
                        args.prestS.district,
                        binding.email.text.toString(),
                        binding.phonenumber.text.toString(),
                        binding.details.text.toString()
                    )
                )
                Navigation.findNavController(view).navigate(action)
            }
        }
        return view
    }
}