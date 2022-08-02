package com.alireza.nikeiran.view.scrool

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.alireza.nikeiran.R

class NikeToolbar(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    var onBackClicked: View.OnClickListener?=null
    set(value) {
        field=value
        val backIcBtn=findViewById<View>(R.id.btnBack)
        backIcBtn.setOnClickListener(onBackClicked)
    }
    init {
        inflate(context, R.layout.view_toolbar,this)
        if(attrs!=null){
            val a= context.obtainStyledAttributes(attrs,R.styleable.NikeToolbar)
            val title=a.getString(R.styleable.NikeToolbar_nt_title)
            if (title!= null && title.isNotEmpty()){
                val tittleTv=findViewById<TextView>(R.id.tvTitleToolbar)
                tittleTv.text=title
            }
            a.recycle()
        }
    }
}