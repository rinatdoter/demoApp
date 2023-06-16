package com.example.testtask.extensions

inline fun <T> List<T>.indexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    val index = this.indexOfFirst(predicate)
    return if (index == -1) null else index
}