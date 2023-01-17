package com.app.acrominetest.models

data class AcromineResponseLf(
    val freq: Int,
    val lf: String,
    val since: Int,
    val vars: List<AcromineResponseVar>
)