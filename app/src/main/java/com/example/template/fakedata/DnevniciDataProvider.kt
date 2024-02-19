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

import com.example.template.R

/**
 * An static data store of [Account]s. This includes both [Account]s owned by the current user and
 * all [Account]s of the current user's contacts.
 */
object DnevniciDataProvider {

    var allDnevnici = mutableListOf(
        Dnevnik(
            1,
            "Izgradnja i rekonstrukcija poletno sletnih staza",
            "Izgradnja piste aerodroma",
            "01.02.2024",
            "Serbiaprojekt inzenjering doo",
            "Tehnoprojekt kopring a.d."
        ),
        Dnevnik(2,"magna adipiscing adipiscing consectetur", "sit tempor labore", "01.02.2024", "incididunt magna labore", "consectetur elit eiusmod magna eiusmod"),
        Dnevnik(3,"elit Lorem sit consectetur", "elit et consectetur aliqua", "03.04.2023", "amet elit Lorem sit aliqua", "tempor amet aliqua"),
        Dnevnik(4,"dolor sit amet consectetur", "adipiscing elit sed", "05.06.2023", "do eiusmod tempor", "incididunt ut labore"),
        Dnevnik(5,"et dolore magna aliqua", "Lorem ipsum dolor", "07.08.2023", "sit amet consectetur", "adipiscing elit sed"),
        Dnevnik(6,"do eiusmod tempor incididunt", "ut labore et", "09.10.2023", "dolore magna aliqua", "Lorem ipsum dolor"),
        Dnevnik(7,"sit amet consectetur adipiscing", "elit sed do", "11.12.2023", "eiusmod tempor incididunt", "ut labore et"),
        Dnevnik(8,"dolore magna aliqua Lorem", "ipsum dolor sit", "13.02.2023", "amet consectetur adipiscing", "elit sed do"),
        Dnevnik(9,"eiusmod tempor incididunt ut", "labore et dolore", "15.04.2023", "magna aliqua Lorem", "ipsum dolor sit"),
        Dnevnik(10,"amet consectetur adipiscing elit", "sed do eiusmod", "17.06.2023", "tempor incididunt ut", "labore et dolore"),
        Dnevnik(11,"magna aliqua Lorem ipsum", "dolor sit amet", "19.08.2023", "consectetur adipiscing elit", "sed do eiusmod"),
        Dnevnik(12,"tempor incididunt ut labore", "et dolore magna", "21.10.2023", "aliqua Lorem ipsum", "dolor sit amet"),
        Dnevnik(13,"consectetur adipiscing elit sed", "do eiusmod tempor", "23.12.2023", "incididunt ut labore", "et dolore magna"),
        Dnevnik(14,"aliqua Lorem ipsum dolor", "sit amet consectetur", "25.02.2023", "adipiscing elit sed", "do eiusmod tempor"),
        Dnevnik(15,"incididunt ut labore et", "dolore magna aliqua", "27.04.2023", "Lorem ipsum dolor", "sit amet consectetur"),
        Dnevnik(16,"adipiscing elit sed do", "eiusmod tempor incididunt", "29.06.2023", "ut labore et", "dolore magna aliqua"),
        Dnevnik(17,"Lorem ipsum dolor sit", "amet consectetur adipiscing", "31.08.2023", "elit sed do", "eiusmod tempor incididunt")

    )

    fun getDefaultDnevnik() = allDnevnici.first()

}