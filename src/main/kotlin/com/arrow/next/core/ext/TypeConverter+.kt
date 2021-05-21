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

/**
 * For some reason that API response key with UpperCase Moshi could not
 * Convert so we have to format key to lowercase
 */
inline fun <reified T> toTypeOfForceMoshi(json: String): T? {
    val reg = ",\"[A-Z]|\\{\"[A-Z]".toRegex()
    val obj = reg.replace(json) { value ->
        value.value.lowercase()
    }
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter<T>(T::class.java)
    return adapter.fromJson(obj)
}

inline fun <reified T> toJson(obj: T): String {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter<T>(T::class.java)
    return adapter.toJson(obj)
}