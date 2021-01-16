package com.philboyd.okcupid.domain

data class Person (
        val userName: String,
        val matchPercentage: String,
        val isLiked: Boolean,
        val age: Int,
        val city: String,
        val region: String,
        val image: String
)
