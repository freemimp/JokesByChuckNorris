package com.freemimp.android.jokesbychucknorris.restapi.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JokeCountModel (@SerializedName("type") @Expose val type: String,
                           @SerializedName("value") @Expose val value : Int)