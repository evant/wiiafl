package me.tatarka.wiiafl

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

fun View.wiiafl(count: Int = 1, recursive: Boolean = false, childId: Int): View {
    val child = findViewById<View>(childId) ?: return this
    return if (this == child) {
        wiiafl(count = count, recursive = recursive)
    } else {
        val parent = child.parent as ViewGroup
        val index = parent.indexOfChild(child)
        parent.removeViewAt(index)
        val fl = child.wiiafl(count = count, recursive = recursive)
        parent.addView(fl, index, child.layoutParams)
        parent
    }
}

fun View.wiiafl(count: Int = 1, recursive: Boolean = false): FrameLayout {
    if (recursive && this is ViewGroup) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            removeViewAt(i)
            addView(child.wiiafl(count, recursive), i)
        }
    }
    return wiiafl(count)
}

private fun View.wiiafl(count: Int): FrameLayout {
    var child = this
    for (i in 0 until count) {
        val fl = FrameLayout(context)
        fl.addView(child)
        child = fl
    }
    return child as FrameLayout
}
