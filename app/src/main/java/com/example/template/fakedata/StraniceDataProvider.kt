/*
 * Copyright 2022 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.template.fakedata

import java.util.Calendar
import java.util.Date

/**
 * An static data store of [Account]s. This includes both [Account]s owned by the current user and
 * all [Account]s of the current user's contacts.
 */
object StraniceDataProvider {

    private fun createDate(year: Int, month: Int, day: Int, hour: Int = 0, minute: Int = 0): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day, hour, minute) // Month is 0-based
        return calendar.time
    }

    val smena1 = Smena(
        datumOd = createDate(2023, 3, 15, 8, 0),
        datumDo = createDate(2023, 3, 15, 16, 0),
        brGradjRadnika = 10,
        brZanatlija = 5,
        brTehOsoblja = 2,
        brOstali = 3
    )
    val smena2 = Smena(
        datumOd = createDate(2023, 3, 15, 16, 0),
        datumDo = createDate(2023, 3, 16, 0, 0),
        brGradjRadnika = 8,
        brZanatlija = 4,
        brTehOsoblja = 3,
        brOstali = 2
    )
    val smena3 = Smena(
        datumOd = createDate(2023, 3, 16, 8, 0),
        datumDo = createDate(2023, 3, 16, 16, 0),
        brGradjRadnika = 14,
        brZanatlija = 2,
        brTehOsoblja = 7,
        brOstali = 7
    )
    val smena4 = Smena(
        datumOd = createDate(2023, 3, 17, 16, 0),
        datumDo = createDate(2023, 3, 17, 23, 0),
        brGradjRadnika = 2,
        brZanatlija = 6,
        brTehOsoblja = 7,
        brOstali = 15
    )

    var allStranice = mutableListOf(
        Strana(
            stranaId = 1L,
            projekatId = 1,
            dnevnikId = 1,
            danStrane = "Ponedeljak",
            datumStrane = createDate(2023, 3, 15),
            smenaPrva = smena1,
            smenaDruga = smena2,
            smenaTreca = smena1, // Reusing smena1 for simplicity
            temp1 = 15.5f,
            tempVreme1 = createDate(2023, 3, 15, 11, 0),
            temp2 = 18.0f,
            tempVreme2 = createDate(2023, 3, 15, 14, 0),
            temp3 = 20.3f,
            tempVreme3 = createDate(2023, 3, 15, 17, 0),
            sunacnoOblacnoKisa = "Sunčano",
            brzinaVetra = "Slab",
            nivoPodzemnihVoda = "Normalan",
            opisRada = "Izgradnja temelja",
            primedbe = "Nema",
            prilog = "Fotografije rada",
            vodeDnevnik = arrayOf("Voda1", "Voda2"),
            izvodjacRadova = "Izvođač1",
            nadzorniOrgan = "Nadzorni organ1"
        ),
        Strana(
            stranaId = 2L,
            projekatId = 2,
            dnevnikId = 1,
            danStrane = "Utorak",
            datumStrane = createDate(2023, 3, 16),
            smenaPrva = smena1,
            smenaDruga = smena3,
            smenaTreca = smena4, // Reusing smena1 for simplicity
            temp1 = 18.5f,
            tempVreme1 = createDate(2023, 3, 16, 11, 0),
            temp2 = 14.0f,
            tempVreme2 = createDate(2023, 3, 16, 14, 0),
            temp3 = 21.3f,
            tempVreme3 = createDate(2023, 3, 16, 17, 0),
            sunacnoOblacnoKisa = "Sunčano",
            brzinaVetra = "Jak",
            nivoPodzemnihVoda = "Normalan",
            opisRada = "Izgradnja Zgrade",
            primedbe = "Veliki opseg posla",
            prilog = "Fotografije rada",
            vodeDnevnik = arrayOf("Nikola Markovic", "Marko Nikolic"),
            izvodjacRadova = "Izvođač2",
            nadzorniOrgan = "Nadzorni organ2"
        ),
        Strana(
            stranaId = 3L,
            projekatId = 3,
            dnevnikId = 1,
            danStrane = "Sreda",
            datumStrane = createDate(2023, 3, 17),
            smenaPrva = smena3,
            smenaDruga = smena4,
            smenaTreca = smena1, // Reusing smena1 for simplicity
            temp1 = 12.5f,
            tempVreme1 = createDate(2023, 3, 15, 11, 0),
            temp2 = 11.0f,
            tempVreme2 = createDate(2023, 3, 17, 14, 0),
            temp3 = 13.3f,
            tempVreme3 = createDate(2023, 3, 17, 17, 0),
            sunacnoOblacnoKisa = "Oblacno",
            brzinaVetra = "Jak",
            nivoPodzemnihVoda = "Visok",
            opisRada = "Nosece Grede",
            primedbe = "Nema",
            prilog = "Fotografije rada",
            vodeDnevnik = arrayOf("Miodrag Petrovic", "Vojislav Vojo"),
            izvodjacRadova = "Izvođač4",
            nadzorniOrgan = "Nadzorni organ4"
        ),
        Strana(
            stranaId = 4L,
            projekatId = 4,
            dnevnikId = 2,
            danStrane = "Ponedeljak",
            datumStrane = createDate(2023, 3, 18),
            smenaPrva = smena2,
            smenaDruga = smena2,
            smenaTreca = smena4, // Reusing smena1 for simplicity
            temp1 = 16.8f,
            tempVreme1 = createDate(2023, 3, 18, 11, 0),
            temp2 = 14.0f,
            tempVreme2 = createDate(2023, 3, 18, 14, 0),
            temp3 = 22.3f,
            tempVreme3 = createDate(2023, 3, 18, 17, 0),
            sunacnoOblacnoKisa = "Sunčano",
            brzinaVetra = "Nepostojeci",
            nivoPodzemnihVoda = "Nizak",
            opisRada = "Izgradnja garaze",
            primedbe = "Nema",
            prilog = "Fotografije rada",
            vodeDnevnik = arrayOf("Marko Nikolic", "Miodrag Petrovic"),
            izvodjacRadova = "Izvođač4",
            nadzorniOrgan = "Nadzorni organ4"
        ),
    )

    fun getLastStranica() = allStranice.last()

    fun getStranaById(stranaId: Long): Strana? {
        return allStranice.find { it.stranaId == stranaId }
    }

    fun getStranaByIdAndDnevnikId(stranaId: Long, dnevnikId: Long): Strana? {
        return allStranice.find { it.stranaId == stranaId && it.dnevnikId == dnevnikId }
    }

}