package com.rrat.doggydex.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Dog(
    val id: Long,
    val index: Int,
    val name: String,
    val type: String,
    val heightFemale: Double,
    val heightMale: Double,
    val imageUrl: String,
    val lifeExpectancy: String,
    val temperament: String,
    val weightFemale: String,
    val weightMale: String,
    val inCollection: Boolean = true
) : Parcelable, Comparable<Dog>{
    override fun compareTo(other: Dog): Int {
        return if(this.index > other.index){
            1
        }else{
            -1
        }
    }

}