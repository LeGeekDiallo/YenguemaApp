package project.yenguema.yenguema.services

import project.yenguema.yenguema.library.LogInRespWrapper
import project.yenguema.yenguema.library.SignInRespWrapper
import project.yenguema.yenguema.library.UserInfosRespWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

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
}