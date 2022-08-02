package com.alireza.nikeiran.comman

import androidx.annotation.StringRes

class NikeException(val type:Type , @StringRes val errorFriendlyMessage: Int = 0 ,val errorServerMessage:String? = null)
    :Throwable() {

    enum class Type{
        SIMPLE,DIALOG,AUTH
    }
}