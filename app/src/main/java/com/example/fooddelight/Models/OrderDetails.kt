package com.example.fooddelight.Models

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable
import java.util.ArrayList

class OrderDetails():Serializable {
    var userId: String? = null
    var userName: String? = null
    var foodName: MutableList<String>? = null
    var foodPrice: MutableList<String>? = null
    var foodImage: MutableList<String>? = null
    var foodQuantity: MutableList<Int>? = null
    var address :String? =null
    var totalPrice :String? =null
    var phoneNumber :String? =null
    var orderAccepted :Boolean =false
    var paymentReceived :Boolean = false
    var itemPushKey :String? =null
    var currentTime :Long = 0

    constructor(parcel: Parcel) : this() {
        userId = parcel.readString()
        userName = parcel.readString()
        address = parcel.readString()
        totalPrice = parcel.readString()
        phoneNumber = parcel.readString()
        orderAccepted = parcel.readByte() != 0.toByte()
        paymentReceived = parcel.readByte() != 0.toByte()
        itemPushKey = parcel.readString()
        currentTime = parcel.readLong()
    }

    constructor(
        userId: String,
        name: String,
        foodItemName: ArrayList<String>,
        foodItemPrice: ArrayList<String>,
        foodItemImage: ArrayList<String>,
        foodItemQuantity: MutableList<Int>,
        b: Boolean,
        b1: Boolean,
        address: String,
        totalAmount: String,
        phone: String,
        time: Long,
        itemPushKey: String?
    ) : this(){
        this.userId = userId
        this.userName = name
        this.foodName = foodItemName
        this.foodPrice = foodItemPrice
        this.foodImage = foodItemImage
        this.foodQuantity = foodItemQuantity
        this.orderAccepted = b
        this.paymentReceived = b1
        this.address = address
        this.totalPrice = totalAmount
        this.phoneNumber = phone
        this.currentTime = time
        this.itemPushKey = itemPushKey
    }


     fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userId)
        parcel.writeString(userName)
        parcel.writeString(address)
        parcel.writeString(totalPrice)
        parcel.writeString(phoneNumber)
        parcel.writeByte(if (orderAccepted) 1 else 0)
        parcel.writeByte(if (paymentReceived) 1 else 0)
        parcel.writeString(itemPushKey)
        parcel.writeLong(currentTime)
    }

     fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderDetails> {
        override fun createFromParcel(parcel: Parcel): OrderDetails {
            return OrderDetails(parcel)
        }

        override fun newArray(size: Int): Array<OrderDetails?> {
            return arrayOfNulls(size)
        }
    }
}