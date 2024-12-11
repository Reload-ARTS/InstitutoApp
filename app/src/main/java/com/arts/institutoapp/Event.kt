package com.arts.institutoapp

import java.io.Serializable

data class Event(
    val title: String,
    val description: String,
    val date: String,
    val id: Int? = null // El id es opcional y puede ser nulo
) : Serializable
