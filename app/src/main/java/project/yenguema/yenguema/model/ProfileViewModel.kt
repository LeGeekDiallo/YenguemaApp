package project.yenguema.yenguema.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import project.yenguema.yenguema.R
import project.yenguema.yenguema.library.LogInResponse
import project.yenguema.yenguema.library.baseURLDev
import project.yenguema.yenguema.services.YenguemaService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProfileViewModel(app:Application):AndroidViewModel(app) {
    private var resp: MutableLiveData<LogInResponse> = MutableLiveData()
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
            .baseUrl(baseURLDev)
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

    fun getServicesTitle():MutableLiveData<List<String>>{
        return serviceTitles
    }

    fun getServicesImg():MutableLiveData<List<Int>>{
        return serviceImgs
    }
}