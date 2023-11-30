package com.inspiredcoda.quizdemoapp.domain.repository

import com.inspiredcoda.quizdemoapp.domain.remote_model.BaseResponse
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

open class BaseRepository {

    suspend fun <T> request(work: suspend () -> Response<T>, onSuccess: (T) -> Unit, onFailure: (BaseResponse<Any>) -> Unit) {
        val response = work.invoke()

        if (response.isSuccessful) {
            val responseBody = response.body()
            responseBody?.let {
                onSuccess(it)
            }
        } else {
            val errorBody = response.errorBody()?.string()
            val jsonObject = JSONObject(errorBody)
            val payload: Any? = try {
                jsonObject.getString("responseBody")
            } catch (ex: JSONException) {
                ex.printStackTrace()
                null
            }

            val responseMessage = try {
                jsonObject.getString("responseMessage")
            } catch (ex: JSONException) {
                ex.printStackTrace()
                ""
            }

            val finalResponse = BaseResponse(
                status = false,
                responseMessage = responseMessage,
                responseBody = payload
            )

            onFailure(finalResponse)
        }
    }


    /**
     * This is the expected syntax of this function
     * request{
     *      someOperation()
     * }
     * .onSuccess {
     *      handleSuccess here
     * }
     * .onFailure {
     *      handleError here
     * }
     * */
    suspend fun <T> request(work: suspend () -> Response<T>): T {
        val response = work.invoke()

        return if (response.isSuccessful) {
            val responseBody = response.body()
            responseBody!!
        } else {
            val errorBody = response.errorBody()?.string()
            val jsonObject = JSONObject(errorBody)
            val payload: Any? = try {
                jsonObject.getString("responseBody")
            } catch (ex: JSONException) {
                ex.printStackTrace()
                null
            }

            val responseMessage = try {
                jsonObject.getString("responseMessage")
            } catch (ex: JSONException) {
                ex.printStackTrace()
                ""
            }

            val finalResponse = BaseResponse(
                status = false,
                responseMessage = responseMessage,
                responseBody = payload
            )

            finalResponse as T
        }
    }


    interface Process<T> {

        fun <T> onSuccess(result: () -> T): Process<T>

        fun onFailure(error: (Throwable) -> Unit)

    }

}