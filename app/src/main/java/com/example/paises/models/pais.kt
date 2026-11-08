package com.example.paises.models

data class Pais(
    val name: String,
    val alpha3Code: String,
    val translations: Traducciones?,
    val flags: Bandera,
    val capital: String?,
    val region: String,
    val population: Long,
    val languages: List<Idioma>?,
    val currencies: List<Moneda>?
) {
    val nombreEs: String
        get() = translations?.es ?: name
}

data class Traducciones(val es: String?)
data class Bandera(val png: String)
data class Idioma(val name: String)
data class Moneda(val name: String, val symbol: String?)