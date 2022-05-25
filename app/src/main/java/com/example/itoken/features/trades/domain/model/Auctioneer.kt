package com.example.itoken.features.trades.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Auctioneer(
    val stringId: String?,
    val name: String?,
    var price: Long?,
    var imageUrl: String?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(stringId)
        parcel.writeString(name)
        parcel.writeValue(price)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Auctioneer> {
        override fun createFromParcel(parcel: Parcel): Auctioneer {
            return Auctioneer(parcel)
        }

        override fun newArray(size: Int): Array<Auctioneer?> {
            return arrayOfNulls(size)
        }
    }

}
