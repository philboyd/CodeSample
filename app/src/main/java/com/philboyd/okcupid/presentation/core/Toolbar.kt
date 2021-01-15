package com.philboyd.okcupid.presentation.core

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.updateToolbar(config: Toolbar.() -> Toolbar) {
    (activity as? ToolbarOwner)?.updateToolbar(config)
}

interface ToolbarOwner {
    fun updateToolbar(config: Toolbar.() -> Toolbar)
}