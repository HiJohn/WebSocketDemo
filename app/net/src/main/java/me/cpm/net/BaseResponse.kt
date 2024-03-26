package me.cpm.net

/**
 * @author gaozp
 * at 2024/3/14
 */
data class BaseResponse<T : Any?>(
    val code: String,
    val message: String,
    val data: T
)
