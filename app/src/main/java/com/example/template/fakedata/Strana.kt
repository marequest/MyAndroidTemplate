package com.example.template.fakedata

import java.util.Date

data class Strana(
    val stranaId: Long,
    val projekatId: Long,
    val dnevnikId: Long,

    val danStrane: String,
    val datumStrane: Date,

    val smenaRadnici: Array<Smena>,
    val smenaTemp: Array<TempRecord>,

    val sunacnoOblacnoKisa: String,
    val brzinaVetra: String,
    val nivoPodzemnihVoda: String,

    val opisRada: String,
    val primedbe: String,

    val prilog: String,

    val vodeDnevnik: Array<String>,
    val izvodjacRadova: String,
    val nadzorniOrgan: String,
)

data class Smena(
    val datumOd: Date,
    val datumDo: Date,
    val brGradjRadnika: Int,
    val brZanatlija: Int,
    val brTehOsoblja: Int,
    val brOstali: Int
)
data class TempRecord(
    val temp: Float,
    val tempVreme: Date
)