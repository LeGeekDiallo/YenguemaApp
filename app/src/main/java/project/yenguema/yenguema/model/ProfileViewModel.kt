package project.yenguema.yenguema.model

import android.app.Application
import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import project.yenguema.yenguema.R
import project.yenguema.yenguema.entity.NewPrestS
import project.yenguema.yenguema.entity.User
import project.yenguema.yenguema.library.*
import project.yenguema.yenguema.services.YenguemaService
import project.yenguema.yenguema.services.changeUserAvatarRespMapper
import project.yenguema.yenguema.services.getUserInfoResponse
import project.yenguema.yenguema.services.newPrestSResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.channels.Channel

class ProfileViewModel(app:Application):AndroidViewModel(app) {
    private var resp: MutableLiveData<UserInfos> = MutableLiveData()
    private var _response: MutableLiveData<Boolean> = MutableLiveData()
    private val response:LiveData<Boolean> get() = _response
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
    fun changeUserAvatar(userEmail:String, context: Context, avatarParamName:String, uri:Uri):LiveData<Boolean>{
        viewModelScope.launch(Dispatchers.IO){
            val response = uploadAvatar(userEmail, context, avatarParamName, uri, httpClient)
            _response.postValue(response.isSuccessful)
        }
        return response
    }

    fun newPrestS(newPrestS: NewPrestS, uriList:List<Uri>, context: Context): NewPrestSResponse{
        var newPrestSResp:NewPrestSResponse?= null
        val multipartBody = prepareFilesPart(context, "images", uriList)
        /*viewModelScope.launch (Dispatchers.IO){
            val call = yenguemaService.newPrestSQuery(
                newPrestS.user_email,
                newPrestS.activity_name,
                newPrestS.category,
                newPrestS.city,
                newPrestS.municipality,
                newPrestS.district,
                newPrestS.email,
                newPrestS.phone_number,
                newPrestS.details,
                multipartBody
            )

            call.enqueue(object : Callback<NewPrestSRespWrapper>{
                override fun onResponse(
                    call: Call<NewPrestSRespWrapper>,
                    response: Response<NewPrestSRespWrapper>
                ) {
                    response.body()?.let {
                        val resp = newPrestSResponse(it)
                        newPrestSResp = resp
                        println("******** ${newPrestSResp!!.status}*****")
                    }
                }

                override fun onFailure(call: Call<NewPrestSRespWrapper>, t: Throwable) {
                    newPrestSResp = null
                    println("*********** on Failure ${t.message}*******")
                }

            })
        }*/
        return newPrestSResp!!
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