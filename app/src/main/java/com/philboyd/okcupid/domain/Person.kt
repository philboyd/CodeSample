package com.philboyd.okcupid.domain

import java.util.*

data class Person(
    val id: Int = UUID.randomUUID().variant(),
    val userName: String,
    val matchPercentage: Int,
    val isLiked: Boolean,
    val age: Int,
    val city: String,
    val region: String,
    val image: String
)
