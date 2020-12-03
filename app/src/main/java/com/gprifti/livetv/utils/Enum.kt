package com.gprifti.livetv.utils

enum class FieldType(val field: String) {
    USERNAME("username"), EMAIL("email"), PASS("pass"), SURNAME("surname"), PHONE("phone")
}

enum class FilterType(val filter: String) {
    ALL("all"), INTERNATIONAL("international"), NATIONAL("national")
}
