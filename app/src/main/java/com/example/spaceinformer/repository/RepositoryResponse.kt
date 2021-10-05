package com.example.spaceinformer.repository

data class RepositoryResponse <out T>(val status: Status, val data: T?, val message: String?) {

    enum class Status{
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object{

        fun <T> success(data: T): RepositoryResponse<T>{
            return RepositoryResponse(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): RepositoryResponse<T>{
            return RepositoryResponse(Status.ERROR, data, message)
        }

        fun <T> loading(data: T? = null): RepositoryResponse<T>{
            return RepositoryResponse(Status.LOADING, data, null)
        }

    }
}