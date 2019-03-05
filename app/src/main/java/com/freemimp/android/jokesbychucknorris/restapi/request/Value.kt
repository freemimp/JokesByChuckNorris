package com.freemimp.android.jokesbychucknorris.restapi.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Value(@SerializedName("id") @Expose val id: Int,
                 @SerializedName("joke") @Expose val joke: String,
                 @SerializedName("categories") @Expose val categories: ArrayList<String>)