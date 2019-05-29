package com.odin.movielist.widgets.bottombar.utils

typealias Scope = () -> Unit

internal inline fun applyForApiLAndHigher(scope: Scope) {
        scope()
}
