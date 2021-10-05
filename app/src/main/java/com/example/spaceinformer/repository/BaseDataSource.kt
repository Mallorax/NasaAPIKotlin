package com.example.spaceinformer.repository

import android.util.Log
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

abstract class BaseDataSource {

    suspend fun <T> getResult(call: suspend() -> Response<T>):RepositoryResponse<T>{
        try {
            val response = call()
            if (response.isSuccessful){
                val body = response.body()
                if (body != null){
                    return RepositoryResponse.success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        }catch (e: Exception){
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): RepositoryResponse<T>{
        return RepositoryResponse.error(message)
    }
}