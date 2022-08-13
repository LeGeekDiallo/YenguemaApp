package project.yenguema.yenguema.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
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
import androidx.viewpager2.widget.ViewPager2
import project.yenguema.yenguema.R
import project.yenguema.yenguema.adapter.ImageAdapter
import project.yenguema.yenguema.model.ProfileViewModel


/**
 * A simple [Fragment] subclass.
 */
class PrestSFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private lateinit var sharedPref: SharedPreferences
    private var email: String? = null
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var dot_indicatorsLayout:LinearLayout
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_prest_s, container, false)
        sharedPref = requireContext().getSharedPreferences(getString(R.string.credentials), Context.MODE_PRIVATE)
        email = sharedPref.getString(getString(R.string.user_email_shared), null)

        imageAdapter = ImageAdapter()
        val data = profileViewModel.getUserInfo(email!!).value
        val prestS = data?.prestS!!
        val prestSName = view.findViewById<TextView>(R.id.prests_title)
        val city = view.findViewById<TextView>(R.id.city_name)
        val municipality = view.findViewById<TextView>(R.id.municipality_name)
        val district = view.findViewById<TextView>(R.id.district_name)
        val email = view.findViewById<TextView>(R.id.email_address)
        val phoneNumber = view.findViewById<TextView>(R.id.p_phonenumber)
        val likes = view.findViewById<TextView>(R.id.like)
        val unlikes = view.findViewById<TextView>(R.id.unlike)

        dot_indicatorsLayout = view.findViewById<LinearLayout>(R.id.dots_indicators)
        prestSName.text = prestS.activity_name
        city.text = prestS.city
        municipality.text = prestS.municipality
        district.text = prestS.address
        email.text = prestS.email
        phoneNumber.text = prestS.phone_number

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


        imageAdapter.setImagesList(getImages(prestS.images, prestS.imagesURL))
        val viewPager2 = view.findViewById<ViewPager2>(R.id.prests_image_viewpager)
        viewPager2.adapter = imageAdapter

        addDotToTheLayout(prestS.images.size)
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val dotIndicCurrent:ImageView = dot_indicatorsLayout[position] as ImageView
                dotIndicCurrent.setImageResource(R.drawable.active_dot)
                if(position > 0) {
                    val dotIndicPrev: ImageView = dot_indicatorsLayout[position - 1] as ImageView
                    dotIndicPrev.setImageResource(R.drawable.non_active_dot)
                }
                if(position+1 < prestS.images.size){
                    val dotIndicNext: ImageView = dot_indicatorsLayout[position + 1] as ImageView
                    dotIndicNext.setImageResource(R.drawable.non_active_dot)
                }
            }
        })
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
            dot_indicatorsLayout.addView(imgView)
        }
    }
}