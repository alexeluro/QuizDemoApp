package com.inspiredcoda.quizdemoapp.domain.remote_model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse<T>(
    @Json(name = "status") val status: Boolean = false,
    @Json(name = "responseMessage") val responseMessage: String,
    @Json(name = "responseBody") val responseBody: T?
)
