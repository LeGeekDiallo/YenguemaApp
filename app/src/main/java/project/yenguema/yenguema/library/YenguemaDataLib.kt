package project.yenguema.yenguema.library

import project.yenguema.yenguema.entity.User

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
data class PrestS(
    val id: Int,
    val activity_name: String,
    val category:String,
    val address:String,
    val email:String,
    val phone_number: String,
    val details: String,
    val createdAt: CreatedAt,
    val city:String,
    val municipality:String,
    val images: Array<String>,
    val imagesURL: String,
    val likes:Int?,
    val unlikes:Int?
)
data class Ad(
    val id:Int,
    val adTitle: String,
    val adPrice: String,
    val adCategory: String,
    val adType: String,
    val brand: String?,
    val model: String?,
    val mileage: String?,
    val year:CreatedAt?,
    val city:String,
    val municipality:String,
    val address:String,
    val email:String,
    val phoneNumber:String,
    val details:String,
    val createdAt: CreatedAt,
    val vehicle_type:String?,
    val adState:String,
    val transmission_type: String,
    val images: Array<String>,
    val imagesURL: String
)

data class CreatedAt(
    val date:String,
    val timezone_type: Int,
    val timezone:String
)
const val baseURL = "https://www.leyenguema.com/"