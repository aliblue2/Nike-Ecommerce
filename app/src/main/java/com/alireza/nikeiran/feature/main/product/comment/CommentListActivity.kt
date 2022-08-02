package com.alireza.nikeiran.feature.main.product.comment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.comman.KEY_EXTRA_ID
import com.alireza.nikeiran.comman.NikeActivity
import com.alireza.nikeiran.data.source.comment.Comment
import com.alireza.nikeiran.view.scrool.NikeToolbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class CommentListActivity : NikeActivity() {

    val viewModel:CommentListViewModel by inject { parametersOf(intent.extras!!.getInt(
        KEY_EXTRA_ID)) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_list)
        val rvAllComments=findViewById<RecyclerView>(R.id.rvCommentsAll)

        viewModel.commentListLiveData.observe(this){
            val adapter= CommentsListAdapter(true)
            adapter.comments= it as ArrayList<Comment>
            rvAllComments.adapter=adapter
            rvAllComments.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        }

        viewModel.progressIndicatorLiveData.observe(this){
            setProgressIndicator(it)
        }

        val toolbarComments=findViewById<NikeToolbar>(R.id.toolbarComments)
        toolbarComments.onBackClicked=View.OnClickListener {
            finish()
        }
    }
}