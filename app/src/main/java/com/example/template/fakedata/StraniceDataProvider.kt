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
            projekatId = 1L,
            dnevnikId = 1L,
            danStrane = "Ponedeljak",
            datumStrane = createDate(2023, 3, 15),
            smenaRadnici = arrayOf(smena1, smena2, smena1), // Array of shifts
            smenaTemp = arrayOf( // Array of temperature records
                TempRecord(temp = 15.5f, tempVreme = createDate(2023, 3, 15, 11, 0)),
                TempRecord(temp = 18.0f, tempVreme = createDate(2023, 3, 15, 14, 0)),
                TempRecord(temp = 20.3f, tempVreme = createDate(2023, 3, 15, 17, 0))
            ),
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
            projekatId = 2L,
            dnevnikId = 1L,
            danStrane = "Utorak",
            datumStrane = createDate(2023, 3, 16),
            smenaRadnici = arrayOf(smena1, smena3, smena4), // Adjusted to array of shifts
            smenaTemp = arrayOf( // Adjusted to array of temperature records
                TempRecord(temp = 18.5f, tempVreme = createDate(2023, 3, 16, 11, 0)),
                TempRecord(temp = 14.0f, tempVreme = createDate(2023, 3, 16, 14, 0)),
                TempRecord(temp = 21.3f, tempVreme = createDate(2023, 3, 16, 17, 0))
            ),
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
            projekatId = 3L,
            dnevnikId = 1L,
            danStrane = "Sreda",
            datumStrane = createDate(2023, 3, 17),
            smenaRadnici = arrayOf(smena3, smena4, smena1), // Adjusted to array of shifts
            smenaTemp = arrayOf( // Adjusted to array of temperature records
                TempRecord(temp = 12.5f, tempVreme = createDate(2023, 3, 17, 11, 0)),
                TempRecord(temp = 11.0f, tempVreme = createDate(2023, 3, 17, 14, 0)),
                TempRecord(temp = 13.3f, tempVreme = createDate(2023, 3, 17, 17, 0))
            ),
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
            projekatId = 4L,
            dnevnikId = 1L,
            danStrane = "Ponedeljak",
            datumStrane = createDate(2023, 3, 18),
            smenaRadnici = arrayOf(smena2, smena2, smena4), // Adjusted to array of shifts
            smenaTemp = arrayOf( // Adjusted to array of temperature records
                TempRecord(temp = 16.8f, tempVreme = createDate(2023, 3, 18, 11, 0)),
                TempRecord(temp = 14.0f, tempVreme = createDate(2023, 3, 18, 14, 0)),
                TempRecord(temp = 22.3f, tempVreme = createDate(2023, 3, 18, 17, 0))
            ),
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
        Strana(
            stranaId = 5L,
            projekatId = 5L,
            dnevnikId = 2L,
            danStrane = "Utorak",
            datumStrane = createDate(2023, 3, 19),
            smenaRadnici = arrayOf(smena1, smena3, smena2),
            smenaTemp = arrayOf(
                TempRecord(temp = 17.2f, tempVreme = createDate(2023, 3, 19, 10, 0)),
                TempRecord(temp = 15.4f, tempVreme = createDate(2023, 3, 19, 13, 0)),
                TempRecord(temp = 21.6f, tempVreme = createDate(2023, 3, 19, 16, 0))
            ),
            sunacnoOblacnoKisa = "Oblacno",
            brzinaVetra = "Slab",
            nivoPodzemnihVoda = "Visok",
            opisRada = "Izgradnja parka",
            primedbe = "Zakašnjenje materijala",
            prilog = "Plan parka",
            vodeDnevnik = arrayOf("Ivan Ivanovic", "Petar Petrovic"),
            izvodjacRadova = "Izvođač5",
            nadzorniOrgan = "Nadzorni organ5"
        ),
        Strana(
            stranaId = 6L,
            projekatId = 6L,
            dnevnikId = 2L,
            danStrane = "Sreda",
            datumStrane = createDate(2023, 3, 20),
            smenaRadnici = arrayOf(smena4, smena1, smena3),
            smenaTemp = arrayOf(
                TempRecord(temp = 18.5f, tempVreme = createDate(2023, 3, 20, 9, 30)),
                TempRecord(temp = 16.3f, tempVreme = createDate(2023, 3, 20, 12, 45)),
                TempRecord(temp = 19.8f, tempVreme = createDate(2023, 3, 20, 15, 15))
            ),
            sunacnoOblacnoKisa = "Sunčano",
            brzinaVetra = "Jak",
            nivoPodzemnihVoda = "Srednji",
            opisRada = "Postavljanje temelja",
            primedbe = "Nema",
            prilog = "Specifikacija materijala",
            vodeDnevnik = arrayOf("Jovan Jovanovic", "Nikola Nikolic"),
            izvodjacRadova = "Izvođač6",
            nadzorniOrgan = "Nadzorni organ6"
        ),
        Strana(
            stranaId = 7L,
            projekatId = 7L,
            dnevnikId = 2L,
            danStrane = "Četvrtak",
            datumStrane = createDate(2023, 3, 21),
            smenaRadnici = arrayOf(smena2, smena4, smena2),
            smenaTemp = arrayOf(
                TempRecord(temp = 14.8f, tempVreme = createDate(2023, 3, 21, 11, 30)),
                TempRecord(temp = 13.7f, tempVreme = createDate(2023, 3, 21, 14, 20)),
                TempRecord(temp = 18.2f, tempVreme = createDate(2023, 3, 21, 16, 40))
            ),
            sunacnoOblacnoKisa = "Kišovito",
            brzinaVetra = "Umeren",
            nivoPodzemnihVoda = "Nizak",
            opisRada = "Renoviranje fasade",
            primedbe = "Oštećenje opreme",
            prilog = "Izveštaj o oštećenju",
            vodeDnevnik = arrayOf("Milos Milosevic", "Zoran Zoranovic"),
            izvodjacRadova = "Izvođač7",
            nadzorniOrgan = "Nadzorni organ7"
        ),
        Strana(
            stranaId = 8L,
            projekatId = 8L,
            dnevnikId = 2L,
            danStrane = "Petak",
            datumStrane = createDate(2023, 3, 22),
            smenaRadnici = arrayOf(smena3, smena1, smena4),
            smenaTemp = arrayOf(
                TempRecord(temp = 20.0f, tempVreme = createDate(2023, 3, 22, 10, 10)),
                TempRecord(temp = 18.6f, tempVreme = createDate(2023, 3, 22, 13, 40)),
                TempRecord(temp = 22.5f, tempVreme = createDate(2023, 3, 22, 17, 20))
            ),
            sunacnoOblacnoKisa = "Vetrovito",
            brzinaVetra = "Nepostojeci",
            nivoPodzemnihVoda = "Visok",
            opisRada = "Izgradnja puta",
            primedbe = "Ograničen pristup",
            prilog = "Mapa pristupa",
            vodeDnevnik = arrayOf("Dragan Draganovic", "Boris Boric"),
            izvodjacRadova = "Izvođač8",
            nadzorniOrgan = "Nadzorni organ8"
        )
    )

    fun getLastStranica() = allStranice.last()

    fun getStranaById(stranaId: Long): Strana? {
        return allStranice.find { it.stranaId == stranaId }
    }

    fun getStranaByIdAndDnevnikId(stranaId: Long, dnevnikId: Long): Strana? {
        return allStranice.find { it.stranaId == stranaId && it.dnevnikId == dnevnikId }
    }

}