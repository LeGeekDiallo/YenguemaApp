package project.yenguema.yenguema.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import project.yenguema.yenguema.library.CreatedAt

@Parcelize
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
):Parcelable
