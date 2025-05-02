package com.tedmoon99.domain.search.utils

sealed interface SearchTabType {
    data object Diary: SearchTabType
    data object Place: SearchTabType
    data object User: SearchTabType
    data object WrongTypeSearch: SearchTabType
}