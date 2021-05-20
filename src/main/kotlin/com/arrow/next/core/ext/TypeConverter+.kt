package com.arrow.next.core.ext

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


inline fun <reified T> toTypeOf(
    rawType: Class<*>,
    typeArgument: Class<*>? = null,
    json: String
): T {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val adapter =
        if (typeArgument != null) {
            val type = Types.newParameterizedType(rawType, typeArgument)
            moshi.adapter<T>(type)
        } else {
            moshi.adapter<T>(rawType)
        }
    return adapter.fromJson(json)!!
}
