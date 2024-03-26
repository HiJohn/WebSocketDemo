package me.cpm.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author gaozp
 * at 2024/3/14
 */
object NetReqManager {

    const val BASE_URL = "https://your-api-base-url.com"

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        // 可以添加更多自定义配置
        .build()

    // 4. 创建 Retrofit 实例，并设置 Gson 转换器和 OkHttpClient
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

}