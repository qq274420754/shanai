package com.mm.shanai.common.utils

import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * @Author: lzm
 * @Date: 2024-10-11 17:05
 * @Description: TODO
 */
class RetrofitUtil {

    companion object {
        private val MEDIA_TYPE_JSON = "application/json;charset=UTF-8".toMediaType()

        fun createJsonRequest(paramsMap: HashMap<String, Any>): RequestBody {
            val gson = Gson()
            val strEntity = gson.toJson(paramsMap)
            return strEntity.toRequestBody(MEDIA_TYPE_JSON)
        }
    }
}