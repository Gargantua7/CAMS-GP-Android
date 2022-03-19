package com.gargantua7.cams.android.logic.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * @author Gargantua7
 */
object NetworkServiceCreator {

    private const val BASE_URL = "https://gargantua7.com/"

    private val gson = GsonBuilder().run {
        registerTypeAdapter(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime> {
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDateTime {
                return LocalDateTime.parse(
                    json?.asJsonPrimitive?.asString,
                    DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                )
            }
        })
        create()
    }

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> create(clazz: Class<T>): T = retrofit.create(clazz)

    inline fun <reified T> create(): T = create(T::class.java)

}
