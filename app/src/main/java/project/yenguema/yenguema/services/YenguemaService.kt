package project.yenguema.yenguema.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import project.yenguema.yenguema.library.*
import retrofit2.Call
import retrofit2.http.*

interface YenguemaService {

    @GET("new_user?")
    fun newUserQuery(
        @Query("gender")gender:String,
        @Query("email")email:String,
        @Query("username")username:String,
        @Query("password")password:String,
        @Query("phoneNumber")phoneNumber:String
    ):Call<SignInRespWrapper>

    @GET("security/login_app")
    fun logInQuery(
        @Query("email")email: String,
        @Query("password")password:String
    ):Call<LogInRespWrapper>

    @GET("user_infos?")
    fun getUserInfos(
        @Query("email")email: String
    ):Call<UserInfosRespWrapper>

    @GET("new_activity_from_app?")
    @Multipart
    fun newPrestSQuery(
        @Query("user_id")email:String,
        @Query("prestS_name")prestSName:String,
        @Query("category")category:String,
        @Query("city")city:String,
        @Query("municipality")municipality:String,
        @Query("district")district:String,
        @Query("prestS_email")prestSEmail:String,
        @Query("phone_number")phoneNumber:String,
        @Query("details")details:String,
        @Part("images")images: List<MultipartBody.Part>
    ):Call<NewPrestSRespWrapper>

    @Multipart
    @POST("security/upload_user_avatar")
    fun changeUserAvatar(
        @Part("user_email") userEmail: RequestBody,
        @Part("imageName") imageName: RequestBody
    ):Call<ChangeUserAvatarRespWrapper>
}