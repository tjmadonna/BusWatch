package com.madonnaapps.buswatch.ui.common.extension

// Function used to unwrap two parameters at once and execute if both not null
inline fun <A, B, R> ifNotNull(a: A?, b: B?, action: (A, B) -> R) {
    if (a != null && b != null) {
        action(a, b)
    }
}