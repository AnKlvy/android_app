package com.example.myapplication

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class ListItem(
    val title: String,
    val subtitle: String,
    val date: Date,
    val imageResId: Int // Ресурс изображения
) : Parcelable {
    // Конструктор для создания объекта из Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        Date(parcel.readLong()),
        parcel.readInt() // Чтение идентификатора ресурса изображения из Parcel
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(subtitle)
        parcel.writeLong(date.time)
        parcel.writeInt(imageResId) // Запись ресурса изображения в Parcel
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