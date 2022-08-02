package com.alireza.nikeiran.comman

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.util.DisplayMetrics
import androidx.browser.customtabs.CustomTabsIntent
import com.alireza.nikeiran.R
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun convertToPixel(dp:Float ,context: Context):Float {
    return if (context != null) {
        val resource = context.resources
        val metrics = resource.displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } else{
        val metrics= Resources.getSystem().displayMetrics
        dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}
}

fun formatPrice(price:Number ,
                unitRelativeSizeFactor:Float = 0.7f) :SpannableString{
    val currencyLable="تومان"
    val spannableString=SpannableString("$price $currencyLable")
    spannableString.setSpan(
        RelativeSizeSpan(unitRelativeSizeFactor),
        spannableString.indexOf(currencyLable),
        spannableString.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

fun <T> Single<T>.async():Single<T>{
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}


fun openUrlInCustomTab(context: Context,url:String){
    try {
        val uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setStartAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
        intentBuilder.setExitAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
        val customTab = intentBuilder.build()
        customTab.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        customTab.launchUrl(context,uri)

    }catch (e:Exception){

    }

}