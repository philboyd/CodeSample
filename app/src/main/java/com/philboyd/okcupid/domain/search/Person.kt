package com.philboyd.okcupid.domain.search

data class Person(
    val id: String,
    val userName: String,
    val matchPercentage: Int,
    val isLiked: Boolean,
    val age: Int,
    val city: String,
    val region: String,
    val image: String
)
