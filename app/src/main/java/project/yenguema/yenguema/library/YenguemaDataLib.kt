package project.yenguema.yenguema.library

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Parcelable
import android.text.InputType.TYPE_CLASS_PHONE
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import kotlinx.parcelize.Parcelize
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import project.yenguema.yenguema.entity.Ad
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.entity.User
import project.yenguema.yenguema.utils.RealPathUtil
import java.io.File
import java.util.regex.Pattern

private val PHONNUMBERPARTTERN: Pattern = Pattern.compile("^[6][2-9][0-9][0-9]{2}[0-9]{2}[0-9]{2}\$")
const val READ_EXTERNAL_STORAGE = 0


private fun phoneNumberCheck(input:EditText):Boolean{
    return PHONNUMBERPARTTERN.matcher(input.text.toString().trim()).matches()
}
fun requestPermission(context: Context, activity:Activity):Boolean{
    if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED){
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            READ_EXTERNAL_STORAGE
        )
        return false
    }
    return true
}
fun prepareFilesPart(context: Context, partName:String, uriList: List<Uri>):MutableList<MultipartBody.Part>{
    val multipartBodyPartList = mutableListOf<MultipartBody.Part>()
    for (uri in uriList){
        val file = File(RealPathUtil.getRealPath(context, uri))
        val requestFile = file.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
        val multipartBodyItem = MultipartBody.Part.createFormData(partName, file.name, requestFile)
        multipartBodyPartList.add(multipartBodyItem)
    }
    return multipartBodyPartList
}
fun prepareFilePart(context: Context, partName:String, uri: Uri):MultipartBody.Part{
    val file = File(RealPathUtil.getRealPath(context, uri))
    val requestFile = file.asRequestBody(context.contentResolver.getType(uri)?.toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(partName, file.name, requestFile)
}
fun formController(emptyMsg:String, wrongFormat:String, vararg inputs:EditText):Boolean{
    for (input in inputs){
        if(input.text.isEmpty()){
            input.requestFocus()
            input.error = emptyMsg
            return false
        }
        if(input.inputType == TYPE_CLASS_PHONE && !phoneNumberCheck(input)){
            input.error = wrongFormat
            return false
        }
    }
    return true
}

data class NewPrestSRespWrapper(
    val resp:NewPrestSResponse
)
data class ChangeUserAvatarRespWrapper(
    val resp:ChangeUserAvatarResponse
)
data class ChangeUserAvatarResponse(
    val status: Boolean?
)
data class NewPrestSResponse(
    val status: Boolean?
)

data class SignInRespWrapper(
    val resp: SignInResponse
)

data class SignInResponse(
    val response:String,
    val registered:Boolean,
    val useremail:String,
    val password:String,
)

data class LogInRespWrapper(
    val resp: LogInResponse
)

data class LogInResponse(
    val logIn: Boolean?,
    val u_id: Int?,
    val email: String?,
    val usergender: Int?,
    val avatar: String?,
    val avatar_URL: String?
)

data class UserInfosRespWrapper(
    val resp: UserInfos
)
data class UserInfos(
    val user_infos: User,
    val carShops: Array<String>?,
    val trips_posted: Array<String>?,
    val prestS: PrestS?,
    val ads_posted: Array<Ad>?,
    val aparts_posted: Array<String>?,
    val officeShopLand_posted: Array<String>?,
    val taxi: Array<String>?,
    val studios_posted: Array<String>?,
    val houses_posted: Array<String>?,
    val jobs_posted: Array<String>?,
    val jobs_applied: Array<String>?,
    val courses_posted:Array<String>?
) {
}


@Parcelize
data class CreatedAt(
    val date:String,
    val timezone_type: Int,
    val timezone:String
):Parcelable
const val baseURL = "https://www.leyenguema.com/"