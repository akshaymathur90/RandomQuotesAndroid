package com.akshay.randomquotes.network

interface ResponseHandler<T> {

    fun onSuccess(data : T?)
    fun onFailure(t:Throwable? = null)
}