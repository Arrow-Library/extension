package com.arrow.next.core.ext

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.reactivex.Flowable

inline fun <reified T> mockTypeOf(
    rawType: Class<*>,
    name: String
): Flowable<T> {
    val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    val type = moshi.adapter<T>(rawType).fromJson(
        MockResponseFileReader("$name.json").content)!!
    return Flowable.just(type)
}