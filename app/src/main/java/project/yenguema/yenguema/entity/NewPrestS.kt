package project.yenguema.yenguema.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import project.yenguema.yenguema.library.CreatedAt

@Parcelize
data class NewPrestS(
    val user_email:String,
    val activity_name: String,
    val category:String,
    val city:String?=null,
    val municipality:String?=null,
    val district:String?=null,
    val email:String?=null,
    val phone_number: String?=null,
    val details: String?=null,
):Parcelable
