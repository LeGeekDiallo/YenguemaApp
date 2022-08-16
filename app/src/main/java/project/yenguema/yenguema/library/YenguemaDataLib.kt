package project.yenguema.yenguema.library

import android.os.Parcelable
import android.text.InputType.TYPE_CLASS_PHONE
import android.widget.EditText
import kotlinx.parcelize.Parcelize
import project.yenguema.yenguema.entity.Ad
import project.yenguema.yenguema.entity.PrestS
import project.yenguema.yenguema.entity.User
import java.util.regex.Pattern

private val PHONNUMBERPARTTERN: Pattern = Pattern.compile("^[6][2-9][0-9][0-9]{2}[0-9]{2}[0-9]{2}\$")

private fun phoneNumberCheck(input:EditText):Boolean{
    return PHONNUMBERPARTTERN.matcher(input.text.toString().trim()).matches()
}
fun formController(emptyMsg:String, wrongFormat:String, vararg inputs:EditText):Boolean{
    for (input in inputs){
        if(input.text.isEmpty()){
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