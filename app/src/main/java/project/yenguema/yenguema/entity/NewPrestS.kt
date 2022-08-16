package project.yenguema.yenguema.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import project.yenguema.yenguema.library.CreatedAt

@Parcelize
data class NewPrestS(
    val activity_name: String,
    val category:String,
    val email:String,
    val phone_number: String,
    val details: String,
    val district:String,
    val city:String,
    val municipality:String,
):Parcelable
