package com.example.template.fakedata.entities

import java.util.Date

data class Nalog(
    val nalogId: Long,
    val dnevnikId: Long,
    val ime: String,
    val prezime: String,
    val email: String,
    val brojTelefona: String,
    val licenca: String,
    val potpis: String,
    val lozinka: String,
    val permisije: String
)
