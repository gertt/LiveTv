package com.gprifti.livetv.utils

enum class FileldType(val field: String) {
    USERNAME("username"), EMAIL("email"), PASS("pass"), SURNAME("surname"), PHONE("phone")
}

enum class FilterType(val filter: String) {
    ALL("all"), INTERNACIONAL("internacional"), NATIONAL("national")
}
