package com.example.itoken.features.trades.domain.model

import android.os.Parcel
import android.os.Parcelable

data class Auctioneer(
    val name: String?,
    var price: Long?,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeValue(price)
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
