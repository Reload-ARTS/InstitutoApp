package com.arts.institutoapp

import java.io.Serializable

data class Event(
    val title: String,
    val description: String,
    val date: String
) : Serializable
