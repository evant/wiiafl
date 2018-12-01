package me.tatarka.wiiafl

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

fun View.wiiafl(count: Int = 1, recursive: Boolean = false, childId: Int): View {
    val child = findViewById<View>(childId) ?: return this
    return if (this == child) {
        wiiafl(count = count, recursive = recursive)
    } else {
        child.wiiafl(count = count, recursive = recursive)
        this
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
    val lp = layoutParams
    val parent = child.parent as ViewGroup?
    val index = parent?.indexOfChild(child) ?: -1
    parent?.removeViewAt(index)
    for (i in 0 until count) {
        val fl = FrameLayout(context)
        if (lp != null) {
            fl.addView(child, layoutParams)
        } else {
            fl.addView(child)
        }
        child = fl
    }
    if (lp != null) {
        parent?.addView(child, index, lp)
    } else {
        parent?.addView(child, index)
    }
    return child as FrameLayout
}
