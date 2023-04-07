package com.example.newsapp.model

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("id"                 ) var id                 : String?  = null,
    @SerializedName("type"               ) var type               : String?  = null,
    @SerializedName("sectionId"          ) var sectionId          : String?  = null,
    @SerializedName("sectionName"        ) var sectionName        : String?  = null,
    @SerializedName("webPublicationDate" ) var webPublicationDate : String?  = null,
    @SerializedName("webTitle"           ) var webTitle           : String?  = null,
    @SerializedName("webUrl"             ) var webUrl             : String?  = null,
    @SerializedName("apiUrl"             ) var apiUrl             : String?  = null,
    @SerializedName("isHosted"           ) var isHosted           : Boolean? = null,
    @SerializedName("pillarId"           ) var pillarId           : String?  = null,
    @SerializedName("pillarName"         ) var pillarName         : String?  = null
)
