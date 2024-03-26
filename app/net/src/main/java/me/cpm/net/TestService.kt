package me.cpm.net

import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author gaozp
 * at 2024/3/14
 */
interface TestService {

    @POST("user/login")
    fun login(@Body body: RequestBody): Flow<BaseResponse<LoginUser>>
}