package com.example.spaceinformer.repository

data class RepositoryResponse <T>(var status: Status, var data: T?, val message: String?) {

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