package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

data class HeadResponse(
    @SerializedName("response" ) var response : NewsResponse? = NewsResponse()

)
