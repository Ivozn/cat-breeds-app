package com.ivonicchio.catbreedsapp.core.network

class Resource<T> private constructor(
    val status: Status,
    val data: T? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data = data
            )
        }

        fun <T> error(): Resource<T> {
            return Resource(
                Status.ERROR
            )
        }
    }

    enum class Status {
        SUCCESS, ERROR
    }
}