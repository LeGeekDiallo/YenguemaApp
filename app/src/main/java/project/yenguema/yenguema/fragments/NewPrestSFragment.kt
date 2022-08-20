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
import project.yenguema.yenguema.databinding.FragmentNewPrestSBinding
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.library.formController


/**
 * create an instance of this fragment.
 */
class NewPrestSFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private val args: NewPrestSFragmentArgs by navArgs()
    private var _binding:FragmentNewPrestSBinding?=null
    private val binding get() = _binding!!
    private lateinit var category:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewPrestSBinding.inflate(inflater, container, false)
        val view = binding.root
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
            /*if(formController(
                    getString(R.string.emptyInput),
                    getString(R.string.wrongFormat),
                    binding.prestsTitle,
                    binding.city,
                    binding.municipality,
                    binding.district,
                    binding.email,
                    binding.phonenumber,
                    binding.details)){
                    val action = NewPrestSFragmentDirections.navigateFromNewPrestSFragmentToChoosePrestSImagesFragment(
                        NewPrestS(
                            args.userId,
                            binding.prestsTitle.text.toString(),
                            category,
                            binding.email.text.toString(),
                            binding.phonenumber.text.toString(),
                            binding.details.text.toString(),
                            binding.district.text.toString(),
                            binding.city.text.toString(),
                            binding.municipality.text.toString()
                        )
                    )
                Navigation.findNavController(view).navigate(action)
            }*/
            val action = NewPrestSFragmentDirections.navigateFromNewPrestSFragmentToChoosePrestSImagesFragment(
                NewPrestS(
                    args.userId,
                    binding.prestsTitle.text.toString(),
                    category,
                    binding.email.text.toString(),
                    binding.phonenumber.text.toString(),
                    binding.details.text.toString(),
                    binding.district.text.toString(),
                    binding.city.text.toString(),
                    binding.municipality.text.toString()
                )
            )
            Navigation.findNavController(view).navigate(R.id.choosePrestSImagesFragment)
        }
        return view
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        category = parent!!.getItemAtPosition(position).toString()
        Toast.makeText(requireContext(), category, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}