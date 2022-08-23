package project.yenguema.yenguema.fragments

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentNoPrestSYetBinding

/**
 * create an instance of this fragment.
 */
class NoPrestSYetFragment : Fragment() {
    private var _binding:FragmentNoPrestSYetBinding?=null
    private val binding get() = _binding!!
    private val args:NoPrestSYetFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoPrestSYetBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        binding.registration.setOnClickListener {
            val action = NoPrestSYetFragmentDirections.navigateToNewPrestSFormStepOne(args.user.email)
            Navigation.findNavController(view).navigate(action)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}