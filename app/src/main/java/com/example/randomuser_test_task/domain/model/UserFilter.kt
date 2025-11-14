package com.example.randomuser_test_task.domain.model

data class UserFilter(
    val gender: Gender? = null,
    val nationalities: Set<Nationality> = emptySet(),
    val resultsCount: Int = 1
)

enum class Gender(
    val apiValue: String,
    val displayName: String
) {
    MALE("male", "Male"),
    FEMALE("female",  "Female"),
    ANY("", "All")
}

enum class Nationality(
    val code: String,
    val displayName: String
) {
    AU("AU", "Australian"),
    BR("BR", "Brazilian"),
    CA("CA", "Canadian"),
    CH("CH", "Swiss"),
    DE("DE", "German"),
    DK("DK", "Danish"),
    ES("ES", "Spanish"),
    FI("FI", "Finnish"),
    FR("FR", "French"),
    GB("GB", "British"),
    IE("IE", "Irish"),
    IR("IR", "Iranian"),
    NL("NL", "Dutch"),
    NZ("NZ", "New Zealander"),
    TR("TR", "Turkish"),
    US("US", "American"),
    NO("NO", "Norwegian"),
    IN("IN", "Indian"),
    MX("MX", "Mexican"),
    RS("RS", "Serbian"),
    UA("UA", "Ukrainian");

    companion object {
        fun fromCode(code: String): Nationality? {
            return entries.find { it.code == code }
        }

        fun getAll(): List<Nationality> = entries.toList()
    }
}