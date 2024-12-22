package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable

data class ListItem(
    val title: String,
    val subtitle: String,
    val date: Long,
    val imageResId: Int,
    val text: String

) : Parcelable {
    // Конструктор для создания объекта из Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong(),
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeLong(date)
        parcel.writeInt(imageResId)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListItem> {
        override fun createFromParcel(parcel: Parcel): ListItem {
            return ListItem(parcel)
        }

        override fun newArray(size: Int): Array<ListItem?> {
            return arrayOfNulls(size)
        }
    }
}
