package com.example.testtask.extensions

import com.example.testtask.domain.models.ErrorList
import com.google.gson.Gson
import retrofit2.HttpException

val HttpException.serializedMessage: String?
    get() {

        var errorMessage: String? = null

        try {
            when (this.code()) {
                400 -> {
                    errorMessage = response()!!.errorBody()?.let {
                        Gson().fromJson(it.string(), ErrorList::class.java).errors[0].message
                    }
                }
                else -> {
                    errorMessage = try {
                        response()!!.errorBody()?.let {
                            Gson().fromJson(it.string(), Error::class.java).message
                        }

                    } catch (e: Exception) {
                        response()!!.errorBody()?.let {
                            Gson().fromJson(it.string(), ErrorList::class.java).errors[0].message

                        }
                    }
                }
            }

        } catch (e: Exception) {

        }
        return errorMessage
    }

val HttpException.serializedErrorList: List<com.example.testtask.domain.models.Error>?
    get() {
        return  response()!!.errorBody()?.let {
            Gson().fromJson(it.string(), ErrorList::class.java).errors
        }
    }