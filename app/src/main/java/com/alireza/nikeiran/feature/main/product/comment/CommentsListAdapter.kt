package com.alireza.nikeiran.feature.main.product.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alireza.nikeiran.R
import com.alireza.nikeiran.data.source.comment.Comment

class CommentsListAdapter(val show:Boolean = false):RecyclerView.Adapter<CommentsListAdapter.CommentsViewHolder>() {
    var comments=ArrayList<Comment>()
    set(value) {
        field=value
        notifyDataSetChanged()
    }
    class CommentsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleComment=itemView.findViewById<TextView>(R.id.titleComment)
        val dateComment=itemView.findViewById<TextView>(R.id.dateComment)
        val bodyComment=itemView.findViewById<TextView>(R.id.bodyComment)
        val authorComment=itemView.findViewById<TextView>(R.id.authorComment)
        fun bindComments(comment: Comment){
            titleComment.text=comment.title
            authorComment.text= comment.author.email
            dateComment.text=comment.date
            bodyComment.text=comment.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_layout_comments,parent,false)
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bindComments(comments[position])
    }

    override fun getItemCount(): Int {
        return if(comments.size>3 && !show) 3 else comments.size
    }
}