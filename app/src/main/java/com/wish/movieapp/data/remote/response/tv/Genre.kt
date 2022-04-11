package com.wish.movieapp.data.remote.response.tv


import com.google.gson.annotations.SerializedName

data class Genre(
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
)