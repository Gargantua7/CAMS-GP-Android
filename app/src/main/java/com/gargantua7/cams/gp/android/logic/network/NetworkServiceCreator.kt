package com.gargantua7.cams.gp.android.logic.network

import com.gargantua7.cams.gp.android.ui.util.networkFormat
import com.gargantua7.cams.gp.android.ui.util.toLocalDateTime
import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.time.LocalDateTime

/**
 * @author Gargantua7
 */
object NetworkServiceCreator {

    private const val BASE_URL = "https://gargantua7.com/"

    private val gson = GsonBuilder().run {
        registerTypeAdapter(LocalDateTime::class.java, object : JsonDeserializer<LocalDateTime> {
            override fun deserialize(
                json: JsonElement,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): LocalDateTime {
                return json.asJsonPrimitive.asString.toLocalDateTime()
            }
        })
        registerTypeAdapter(LocalDateTime::class.java, object : JsonSerializer<LocalDateTime> {
            override fun serialize(
                src: LocalDateTime?,
                typeOfSrc: Type?,
                context: JsonSerializationContext?
            ): JsonElement {
                return JsonPrimitive(src?.networkFormat())
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
