package project.yenguema.yenguema.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import project.yenguema.yenguema.R
import project.yenguema.yenguema.databinding.FragmentUserInfoBinding
import project.yenguema.yenguema.library.READ_EXTERNAL_STORAGE
import project.yenguema.yenguema.library.requestPermission
import project.yenguema.yenguema.model.ProfileViewModel
import project.yenguema.yenguema.utils.RealPathUtil
import java.io.File

/**
 * A simple [Fragment] subclass.
 */
class UserInfoFragment : Fragment() {
    private val profileViewModel: ProfileViewModel by activityViewModels()
    private var _binding: FragmentUserInfoBinding?= null
    private val binding get() = _binding!!
    private val args:UserInfoFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        val view = binding.root
        val user = args.user
        binding.userName.text = user.name
        binding.userNameEdit.setText(user.name)
        binding.userEmail.setText(user.email)
        binding.userPhoneNumber.setText(user.phoneNumber)
        if(user.avatar.isNotEmpty()){
            val avatar = user.avatarURL+user.avatar
            Picasso.get()
                .load(avatar)
                .error(R.drawable.user_av_default)
                .fit()
                .placeholder(R.drawable.user_av_default)
                .into(binding.userAv)
        }
        binding.changeAvatar.setOnClickListener {
            if (requestPermission(requireContext(), requireActivity()))
                launchTheProcess()
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
                if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    launchTheProcess()
                }
            }
        }
    }
    private val launchProcess: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result?.data?.data as Uri
                profileViewModel.changeUserAvatar(args.user.email, requireContext(), getString(R.string.avatar_param_name), uri).observe(viewLifecycleOwner){
                    if(it){
                        Toast.makeText(requireContext(), "The avatar has been updated successfully", Toast.LENGTH_SHORT).show()
                        Handler().postDelayed({
                            Navigation.findNavController(requireView()).navigate(R.id.dashboardFragment)
                        }, 3000)
                    }else{
                        Toast.makeText(requireContext(), "Something wrong with the update", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    private fun launchTheProcess() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        launchProcess.launch(intent)
    }

}