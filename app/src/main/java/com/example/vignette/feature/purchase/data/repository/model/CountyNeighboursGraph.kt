package com.example.vignette.feature.purchase.data.repository.model

val countyGraph = mapOf(
    // Bács-Kiskun: Baranya, Csongrád-Csanád, Fejér, Jász-Nagykun-Szolnok, Pest, Tolna
    "YEAR_11" to setOf("YEAR_12", "YEAR_15", "YEAR_16", "YEAR_20", "YEAR_23", "YEAR_26"),
    // Baranya: Bács-Kiskun, Somogy, Tolna
    "YEAR_12" to setOf("YEAR_11", "YEAR_24", "YEAR_26"),
    // Békés: Csongrád-Csanád, Hajdú-Bihar, Jász-Nagykun-Szolnok
    "YEAR_13" to setOf("YEAR_15", "YEAR_18", "YEAR_20"),
    // Borsod-Abaúj-Zemplén: Hajdú-Bihar, Heves, Jász-Nagykun-Szolnok, Nógrád, Szabolcs-Szatmár-Bereg
    "YEAR_14" to setOf("YEAR_18", "YEAR_19", "YEAR_20", "YEAR_22", "YEAR_25"),
    // Csongrád-Csanád: Bács-Kiskun, Békés, Jász-Nagykun-Szolnok
    "YEAR_15" to setOf("YEAR_11", "YEAR_13", "YEAR_20"),
    // Fejér: Bács-Kiskun, Komárom-Esztergom, Pest, Somogy, Tolna, Veszprém
    "YEAR_16" to setOf("YEAR_11", "YEAR_21", "YEAR_23", "YEAR_24", "YEAR_26", "YEAR_28"),
    // Győr-Moson-Sopron: Komárom-Esztergom, Vas, Veszprém
    "YEAR_17" to setOf("YEAR_21", "YEAR_27", "YEAR_28"),
    // Hajdú-Bihar: Békés, Borsod-Abaúj-Zemplén, Jász-Nagykun-Szolnok, Szabolcs-Szatmár-Bereg
    "YEAR_18" to setOf("YEAR_13", "YEAR_14", "YEAR_20", "YEAR_25"),
    // Heves: Borsod-Abaúj-Zemplén, Jász-Nagykun-Szolnok, Nógrád, Pest
    "YEAR_19" to setOf("YEAR_14", "YEAR_20", "YEAR_22", "YEAR_23"),
    // Jász-Nagykun-Szolnok: Bács-Kiskun, Békés, Borsod-Abaúj-Zemplén, Csongrád-Csanád, Hajdú-Bihar, Heves, Pest
    "YEAR_20" to setOf("YEAR_11", "YEAR_13", "YEAR_14", "YEAR_15", "YEAR_18", "YEAR_19", "YEAR_23"),
    // Komárom-Esztergom: Fejér, Győr-Moson-Sopron, Pest, Veszprém
    "YEAR_21" to setOf("YEAR_16", "YEAR_17", "YEAR_23", "YEAR_28"),
    // Nógrád: Borsod-Abaúj-Zemplén, Heves, Pest
    "YEAR_22" to setOf("YEAR_14", "YEAR_19", "YEAR_23"),
    // Pest: Bács-Kiskun, Fejér, Heves, Jász-Nagykun-Szolnok, Komárom-Esztergom, Nógrád
    "YEAR_23" to setOf("YEAR_11", "YEAR_16", "YEAR_19", "YEAR_20", "YEAR_21", "YEAR_22"),
    // Somogy: Baranya, Fejér, Tolna, Veszprém, Zala
    "YEAR_24" to setOf("YEAR_12", "YEAR_16", "YEAR_26", "YEAR_28", "YEAR_29"),
    // Szabolcs-Szatmár-Bereg: Borsod-Abaúj-Zemplén, Hajdú-Bihar
    "YEAR_25" to setOf("YEAR_14", "YEAR_18"),
    // Tolna: Bács-Kiskun, Baranya, Fejér, Somogy
    "YEAR_26" to setOf("YEAR_11", "YEAR_12", "YEAR_16", "YEAR_24"),
    // Vas: Győr-Moson-Sopron, Veszprém, Zala
    "YEAR_27" to setOf("YEAR_17", "YEAR_28", "YEAR_29"),
    // Veszprém: Fejér, Győr-Moson-Sopron, Komárom-Esztergom, Somogy, Vas, Zala
    "YEAR_28" to setOf("YEAR_16", "YEAR_17", "YEAR_21", "YEAR_24", "YEAR_27", "YEAR_29"),
    // Zala: Somogy, Vas, Veszprém
    "YEAR_29" to setOf("YEAR_24", "YEAR_27", "YEAR_28"),
)
