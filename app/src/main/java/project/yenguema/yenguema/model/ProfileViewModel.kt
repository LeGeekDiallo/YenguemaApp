package project.yenguema.yenguema.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.yenguema.yenguema.R
import project.yenguema.yenguema.entity.User
import project.yenguema.yenguema.library.LogInResponse
import project.yenguema.yenguema.library.UserInfos
import project.yenguema.yenguema.library.UserInfosRespWrapper
import project.yenguema.yenguema.library.baseURL
import project.yenguema.yenguema.services.YenguemaService
import project.yenguema.yenguema.services.getUserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileViewModel(app:Application):AndroidViewModel(app) {
    private var resp: MutableLiveData<UserInfos> = MutableLiveData()
    val userInfos :LiveData<UserInfos> get() = resp
    private val httpClient: OkHttpClient
    private val retrofit: Retrofit
    private val yenguemaService: YenguemaService
    private val serviceTitles: MutableLiveData<List<String>> = MutableLiveData()
    private val serviceImgs: MutableLiveData<List<Int>> = MutableLiveData()

    init {
        httpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        retrofit = Retrofit.Builder()
            .client(httpClient)
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        yenguemaService = retrofit.create(YenguemaService::class.java)

        serviceTitles.value = arrayListOf(
            app.getString(R.string.sprest),
            app.getString(R.string.apply),
            app.getString(R.string.estate),
            app.getString(R.string.sale),
            app.getString(R.string.car_dealership),
            app.getString(R.string.teaching),
            app.getString(R.string.carpooling),
        )
        serviceImgs.value = arrayListOf(
            R.mipmap.sprest_foreground,
            R.drawable.jobs_off_foreground,
            R.drawable.real_estate_foreground,
            R.drawable.makiti,
            R.drawable.park_auto_foreground,
            R.drawable.doudhal,
            R.drawable.traveling
        )
    }

    fun getUserInfo(email:String): MutableLiveData<UserInfos> {
        viewModelScope.launch(Dispatchers.IO) {
            val call = yenguemaService.getUserInfos(email)
            call.enqueue(object : Callback<UserInfosRespWrapper>{
                override fun onResponse(call: Call<UserInfosRespWrapper>, response: Response<UserInfosRespWrapper>) {
                    response.body()?.let {
                        val respData = getUserInfoResponse(it)
                        resp.value = respData
                    }
                }
                override fun onFailure(call: Call<UserInfosRespWrapper>, t: Throwable) {
                    resp.value = UserInfos(
                        User("error", t.message!!, "", 0, "", "",""),
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null
                    )
                }

            })
        }
        return resp
    }

    fun getServicesTitle():MutableLiveData<List<String>>{
        return serviceTitles
    }

    fun getServicesImg():MutableLiveData<List<Int>>{
        return serviceImgs
    }
}