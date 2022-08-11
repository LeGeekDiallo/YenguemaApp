package project.yenguema.yenguema.entity

import android.os.Parcel
import android.os.Parcelable

data class User(
    val name:String,
    val email:String,
    val phoneNumber:String,
    val gender:Int,
    val role:String,
    val avatar:String,
    val avatarURL: String
):Parcelable {
    constructor(parcel: Parcel):this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
    ){

    }
    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, flag: Int) {
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(phoneNumber)
        parcel.writeInt(gender)
        parcel.writeString(role)
        parcel.writeString(avatar)
        parcel.writeString(avatarURL)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}