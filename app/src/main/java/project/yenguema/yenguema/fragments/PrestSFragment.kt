package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.setPadding
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.ImageAdapter
import project.yenguema.yenguema.databinding.FragmentPrestSBinding
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.model.ProfileViewModel


/**
 * A simple [Fragment] subclass.
 */
class PrestSFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private var _binding:FragmentPrestSBinding?=null
    private val binding get() = _binding!!
    private lateinit var sharedPref: SharedPreferences
    private var userEmail: String? = null
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var dotIndicatorsLayout:LinearLayout
    private lateinit var prestS: PrestS
    private val args:PrestSFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        userEmail = sharedPref.getString(getString(R.string.user_email_shared), null)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentPrestSBinding.inflate(inflater, container, false)
        val view = binding.root
        val inflate = TransitionInflater.from(requireContext())
        val exitTransition = inflate.inflateTransition(R.transition.slide_in)
        setExitTransition(exitTransition)
        enterTransition = inflate.inflateTransition(R.transition.slide_out)
        imageAdapter = ImageAdapter()
        val extendedFab:ExtendedFloatingActionButton = view.findViewById(R.id.edit_floating_action_button)
        dotIndicatorsLayout = view.findViewById<LinearLayout>(R.id.dots_indicators)
        val viewPager2 = view.findViewById<ViewPager2>(R.id.prests_image_viewpager)
        prestS = args.prestS
        binding.prestsTitle.text = prestS.activity_name
        binding.city.text = prestS.city
        binding.municipality.text = prestS.municipality
        binding.district.text = prestS.address
        binding.email.text = prestS.email
        binding.phonenumber.text = prestS.phone_number
        setPrestSRate(prestS, binding.like, binding.unlike)
        imageAdapter.setImagesList(getImages(prestS.images, prestS.imagesURL))
        viewPager2.adapter = imageAdapter
        addDotToTheLayout(prestS.images.size)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val dotIndicCurrent:ImageView = dotIndicatorsLayout[position] as ImageView
                dotIndicCurrent.setImageResource(R.drawable.active_dot)
                if(position > 0) {
                    val dotIndicPrev: ImageView = dotIndicatorsLayout[position - 1] as ImageView
                    dotIndicPrev.setImageResource(R.drawable.non_active_dot)
                }
                if(position+1 < prestS.images.size){
                    val dotIndicNext: ImageView = dotIndicatorsLayout[position + 1] as ImageView
                    dotIndicNext.setImageResource(R.drawable.non_active_dot)
                }
            }
        })
        extendedFab.setOnClickListener {
            val action = PrestSFragmentDirections.navigateToEditPrestSFragment(prestS)
            Navigation.findNavController(it).navigate(action)
        }
        return view
    }

    private fun getImages(prestSImages: Array<String>, url:String):List<String>{
        val urlList = emptyList<String>().toMutableList()
        for (img in prestSImages){
            val imgURL = url+img
            urlList.add(imgURL)
        }
        return urlList
    }

    private fun addDotToTheLayout(imgListSize:Int){
        for (i in 0 until imgListSize){
            val imgView = ImageView(requireContext())
            imgView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            imgView.setPadding(5)
            imgView.setImageResource(R.drawable.non_active_dot)
            dotIndicatorsLayout.addView(imgView)
        }
    }

    private fun setPrestSRate(prestS: PrestS, likes:TextView, unlikes:TextView){
        if(prestS.likes != null && prestS.unlikes!=null){
            likes.text = prestS.likes.toString()
            unlikes.text = prestS.unlikes.toString()
        }else{
            if (prestS.likes != null){
                likes.text = prestS.likes.toString()
            }else{
                likes.text = "0"
            }
            if(prestS.unlikes != null){
                unlikes.text = prestS.unlikes.toString()
            }else{
                unlikes.text = "0"
            }
        }
    }
}