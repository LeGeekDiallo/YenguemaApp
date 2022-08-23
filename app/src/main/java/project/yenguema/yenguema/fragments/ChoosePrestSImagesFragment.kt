package project.yenguema.yenguema.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentChoosePrestSImagesBinding
import project.yenguema.yenguema.library.READ_EXTERNAL_STORAGE
import project.yenguema.yenguema.library.requestPermission
import project.yenguema.yenguema.model.ProfileViewModel
import project.yenguema.yenguema.utils.RealPathUtil
import java.io.File

class ChoosePrestSImagesFragment : Fragment(), View.OnClickListener {
    private var _binding:FragmentChoosePrestSImagesBinding? = null
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private val binding get() = _binding!!
    private val uriList = mutableListOf<Uri>()

    private val RESULT_CODE = 200
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChoosePrestSImagesBinding.inflate(inflater, container, false)
        val view = binding.root

        /*binding.registerBtn.setOnClickListener {
            val resp = profileViewModel.newPrestS(args.prestS, uriList, requireContext())
            if(resp.status != null && resp.status){
                Navigation.findNavController(view).navigate(R.id.go_back_to_dashboardFragment)
            }else{
                Toast.makeText(requireContext(), "something wrong", Toast.LENGTH_SHORT).show()
            }
        }*/
        binding.chooseImg.setOnClickListener {
            if (requestPermission(requireContext(), requireActivity()))
                chooseImage()
        }
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            READ_EXTERNAL_STORAGE->{
                if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    chooseImage()
                }
            }
        }
    }

    private val launchProcess:ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val dataClip = result.data?.clipData
            if (dataClip != null){
                val numberOfImages = dataClip.itemCount
                for(i in 0 until numberOfImages){
                    val currentUri:Uri = dataClip.getItemAt(i).uri
                    val path = RealPathUtil.getRealPath(requireContext(), currentUri)
                    val file = File(path)
                    println("********${file.isFile} ${file.name}*************")
                    uriList.add(currentUri)
                }
            }else{
                val uri = result?.data?.data as Uri
                uriList.add(uri)
            }
        }

    }

    private fun chooseImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        launchProcess.launch(intent)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        //
    }
}