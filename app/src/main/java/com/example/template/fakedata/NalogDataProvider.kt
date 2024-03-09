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

import com.example.template.fakedata.entities.Dnevnik
import com.example.template.fakedata.entities.Nalog

/**
 * An static data store of [Account]s. This includes both [Account]s owned by the current user and
 * all [Account]s of the current user's contacts.
 */
object NalogDataProvider {

    var allNalozi = mutableListOf(
        Nalog(
            nalogId = 1L,
            dnevnikId = 1L,
            ime = "Ana",
            prezime = "Petrovic",
            email = "ana.petrovic@example.com",
            brojTelefona = "+381601234567",
            licenca = "12345-67890",
            potpis = "APetrovic",
            lozinka = "anaPassword123",
            permisije = "read-write"
        ),
        Nalog(
            nalogId = 2L,
            dnevnikId = 1L,
            ime = "Marko",
            prezime = "Jankovic",
            email = "marko.jankovic@example.com",
            brojTelefona = "+381601234568",
            licenca = "22345-67890",
            potpis = "MJankovic",
            lozinka = "markoPassword123",
            permisije = "read-only"
        ),
        Nalog(
            nalogId = 3L,
            dnevnikId = 1L,
            ime = "Jelena",
            prezime = "Ilic",
            email = "jelena.ilic@example.com",
            brojTelefona = "+381601234569",
            licenca = "32345-67890",
            potpis = "JIlic",
            lozinka = "jelenaPassword123",
            permisije = "admin"
        ),
        Nalog(
            nalogId = 4L,
            dnevnikId = 2L,
            ime = "Nikola",
            prezime = "Simic",
            email = "nikola.simic@example.com",
            brojTelefona = "+381601234570",
            licenca = "42345-67890",
            potpis = "NSimic",
            lozinka = "nikolaPassword123",
            permisije = "read-write"
        ),
        Nalog(
            nalogId = 5L,
            dnevnikId = 1L,
            ime = "Sara",
            prezime = "Zivkovic",
            email = "sara.zivkovic@example.com",
            brojTelefona = "+381601234571",
            licenca = "52345-67890",
            potpis = "SZivkovic",
            lozinka = "saraPassword123",
            permisije = "editor"
        )
    )

    fun getLastNalog() = allNalozi.last()

}