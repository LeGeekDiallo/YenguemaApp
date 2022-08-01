package project.yenguema.yenguema.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.yenguema.yenguema.entity.User
import project.yenguema.yenguema.library.SignInRespWrapper
import project.yenguema.yenguema.library.baseURLDev
import project.yenguema.yenguema.services.YenguemaService
import project.yenguema.yenguema.services.mapYenguemaLibraryDataToUser
import project.yenguema.yenguema.tools.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignInViewModel(application: Application):AndroidViewModel(application) {
    private var resp:MutableLiveData<SignInResponse> = MutableLiveData()
    private val httpClient: OkHttpClient
    private val retrofit: Retrofit
    private val yenguemaService: YenguemaService


    init {
        httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseURLDev)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        yenguemaService = retrofit.create(YenguemaService::class.java)
    }

    fun newUser(gender:String, email:String, username:String, password:String, phoneNumber:String):MutableLiveData<SignInResponse>{
        viewModelScope.launch(Dispatchers.IO){
            val call = yenguemaService.newUserQuery(gender, email, username, password, phoneNumber)
            call.enqueue(object : Callback<SignInRespWrapper>{
                override fun onResponse(call: Call<SignInRespWrapper>, response: Response<SignInRespWrapper>) {
                    response.body()?.let {
                        val respData = mapYenguemaLibraryDataToUser(it)
                        resp.value = respData
                    }
                }

                override fun onFailure(call: Call<SignInRespWrapper>, t: Throwable) {
                    resp.value = SignInResponse("error", false, "error", "error")
                }

            })
        }
        return resp
    }
}