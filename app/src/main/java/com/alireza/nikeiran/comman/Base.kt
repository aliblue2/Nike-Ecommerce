package com.alireza.nikeiran.comman

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alireza.nikeiran.R
import com.alireza.nikeiran.feature.Auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.IllegalStateException
import java.time.Duration
import java.time.ZoneId

abstract class NikeActivity :AppCompatActivity(),NikeView{
    override var rootView: CoordinatorLayout?
        get() {
        val viewGroup=window.decorView.findViewById(android.R.id.content) as ViewGroup
            if (viewGroup !is CoordinatorLayout) {
                viewGroup.children.forEach {
                    if (it is CoordinatorLayout)
                        return it
                }
                throw IllegalStateException("RootView Must Be A CoordinatorLayout")
            } else
                return viewGroup
        }
        set(value) {}

    override var viewContext: Context?
        get() = this
        set(value) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}

abstract class NikeFragment : Fragment(),NikeView{
    override var viewContext: Context?
        get() = context
        set(value) {}

    override var rootView: CoordinatorLayout?
        get() = view as CoordinatorLayout?
        set(value) {}

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}


interface NikeView{
  var rootView:CoordinatorLayout?
  var viewContext:Context?
  fun setProgressIndicator(show : Boolean){
      rootView?.let { rootView ->
          viewContext?.let { context ->
              var viewLoading=rootView.findViewById<View>(R.id.viewLoading)
              if (viewLoading==null && show){
                  viewLoading=LayoutInflater.from(context).inflate(R.layout.view_loading,rootView,false)
                  rootView.addView(viewLoading)
              }
              viewLoading?.visibility =if (show) View.VISIBLE else View.GONE
          }
      }
  }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun showError(nikeException: NikeException){
        viewContext?.let {
            when(nikeException.type){
                NikeException.Type.SIMPLE -> showSnackBar(nikeException.errorServerMessage?:it.getString(nikeException.errorFriendlyMessage))
                NikeException.Type.AUTH -> {
                    Toast.makeText(viewContext,nikeException.errorServerMessage,Toast.LENGTH_SHORT).show()
                    val mIntent = Intent(it.applicationContext, AuthActivity::class.java)
                    mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    it.startActivity(mIntent)
                }
            }
        }
    }

    fun showSnackBar(message:String , duration :Int = Snackbar.LENGTH_SHORT){
        rootView?.let {
            Snackbar.make(it,message,duration).show()
        }
    }

   fun showEmptyState(layoutResId:Int):View?{
       rootView?.let {
           viewContext?.let { context ->
               var emptyState = it.findViewById<View>(R.id.emptyStateLayout)
               if(emptyState == null){
                   emptyState = LayoutInflater.from(context).inflate(layoutResId,it,false)
                   it.addView(emptyState)
               }
               emptyState.visibility = View.VISIBLE
               return emptyState
           }
       }
       return null
   }

}


abstract class NikeViewModel :ViewModel(){
    val compositeDisposable= CompositeDisposable()
    val progressIndicatorLiveData= MutableLiveData<Boolean>()
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()

    }
}