package com.alireza.nikeiran.comman

import com.alireza.nikeiran.R
import org.json.JSONObject
import retrofit2.HttpException

class NikeExceptionMapper {

    companion object{
        fun map( throwable: Throwable) :NikeException{
            if (throwable is HttpException){
                try {
                    val errorExceptionJson=JSONObject(throwable.response()!!.errorBody()!!.string())
                    val errorMessage=errorExceptionJson.getString("message")
                    return NikeException(if (throwable.code() == 401) NikeException.Type.AUTH else NikeException.Type.SIMPLE , errorServerMessage = errorMessage.toString() )

                }catch (e:Exception){

                }

            }
            return NikeException(NikeException.Type.SIMPLE, R.string.unknown_error)

        }
    }
}