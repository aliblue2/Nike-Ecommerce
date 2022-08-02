package com.alireza.nikeiran.data.source.comment

data class Comment(
    val author: Author,
    val content: String,
    val date: String,
    val id: Int,
    val title: String
)