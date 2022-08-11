package project.yenguema.yenguema.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.yenguema.yenguema.library.LogInRespWrapper
import project.yenguema.yenguema.library.LogInResponse
import project.yenguema.yenguema.library.baseURL
import project.yenguema.yenguema.services.YenguemaService
import project.yenguema.yenguema.services.logInDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LogInViewModel(app: Application):AndroidViewModel(app) {
    private var resp: MutableLiveData<LogInResponse> = MutableLiveData()
    private val httpClient: OkHttpClient
    private val retrofit: Retrofit
    private val yenguemaService: YenguemaService


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
    }

    fun logInProcess(email:String, password:String):MutableLiveData<LogInResponse>{
        viewModelScope.launch(Dispatchers.IO) {
            val call = yenguemaService.logInQuery(email, password)
            call.enqueue(object : Callback<LogInRespWrapper>{
                override fun onResponse(
                    call: Call<LogInRespWrapper>,
                    response: Response<LogInRespWrapper>
                ) {
                    response.body()?.let {
                        val respData = logInDataResponse(it)
                        resp.value = respData
                    }
                }

                override fun onFailure(call: Call<LogInRespWrapper>, t: Throwable) {
                    resp.value = LogInResponse(null, null, null, null, null, null)
                }

            })
        }
        return resp
    }
}