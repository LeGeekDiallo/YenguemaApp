package project.yenguema.yenguema.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import project.yenguema.yenguema.library.CreatedAt

@Parcelize
data class Ad(
    val id:Int,
    val adTitle: String,
    val adPrice: String,
    val adCategory: String,
    val adType: String,
    val brand: String?,
    val model: String?,
    val mileage: String?,
    val year: CreatedAt?,
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
):Parcelable
