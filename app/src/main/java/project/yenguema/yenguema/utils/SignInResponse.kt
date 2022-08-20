package project.yenguema.yenguema.utils

import android.os.Parcel
import android.os.Parcelable

data class SignInResponse(
    val response:String,
    val registered:Boolean,
    val useremail: String,
    val password: String
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(response)
        parcel.writeByte(if (registered) 1 else 0)
        parcel.writeString(useremail)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignInResponse> {
        override fun createFromParcel(parcel: Parcel): SignInResponse {
            return SignInResponse(parcel)
        }

        override fun newArray(size: Int): Array<SignInResponse?> {
            return arrayOfNulls(size)
        }
    }

}
