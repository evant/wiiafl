package me.tatarka.wiiafl

import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WiiaflTest {
    @Test
    fun wraps_single_view_in_FrameLayout() {
        val context = InstrumentationRegistry.getTargetContext()
        val button = Button(context)
        val frameLayout = button.wiiafl()

        assertEquals(1, frameLayout.childCount)
        assertSame(button, frameLayout.getChildAt(0))
    }

    @Test
    fun wraps_single_view_in_multiple_FrameLayouts() {
        val context = InstrumentationRegistry.getTargetContext()
        val button = Button(context)
        val frameLayout1 = button.wiiafl(count = 2)

        assertEquals(1, frameLayout1.childCount)

        val frameLayout2 = frameLayout1.getChildAt(0) as ViewGroup

        assertEquals(FrameLayout::class, frameLayout2::class)
        assertEquals(1, frameLayout2.childCount)
        assertSame(button, frameLayout2.getChildAt(0))
    }

    @Test
    fun updates_child_to_FrameLayout_if_in_parent() {
        val context = InstrumentationRegistry.getTargetContext()
        val button = Button(context)
        val linearLayout = LinearLayout(context).apply { addView(button) }

        button.wiiafl()

        val frameLayout = linearLayout.getChildAt(0) as ViewGroup
        assertEquals(FrameLayout::class, frameLayout::class)
        assertEquals(1, frameLayout.childCount)
        assertSame(button, frameLayout.getChildAt(0))
    }

    @Test
    fun recursively_wraps_views_in_FrameLayouts() {
        val context = InstrumentationRegistry.getTargetContext()
        val button1 = Button(context)
        val button2 = Button(context)
        val linearLayout = LinearLayout(context).apply {
            addView(button1)
            addView(button2)
        }

        val frameLayout1 = linearLayout.wiiafl(recursive = true)

        assertEquals(1, frameLayout1.childCount)
        assertSame(linearLayout, frameLayout1.getChildAt(0))
        assertEquals(2, linearLayout.childCount)

        val frameLayout2 = linearLayout.getChildAt(0) as ViewGroup
        val frameLayout3 = linearLayout.getChildAt(1) as ViewGroup

        assertEquals(FrameLayout::class, frameLayout2::class)
        assertEquals(1, frameLayout2.childCount)
        assertSame(button1, frameLayout2.getChildAt(0))
        assertEquals(FrameLayout::class, frameLayout3::class)
        assertEquals(1, frameLayout3.childCount)
        assertSame(button2, frameLayout3.getChildAt(0))
    }

    @Test
    fun wraps_child_in_FrameLayout() {
        val context = InstrumentationRegistry.getTargetContext()
        val button = Button(context).apply { id = 1 }
        val linearLayout1 = LinearLayout(context).apply { addView(button) }

        val linearLayout2 = linearLayout1.wiiafl(childId = 1) as LinearLayout

        assertSame(linearLayout1, linearLayout2)

        val frameLayout = linearLayout2.getChildAt(0) as ViewGroup

        assertEquals(FrameLayout::class, frameLayout::class)
        assertEquals(1, frameLayout.childCount)
        assertSame(button, frameLayout.getChildAt(0))
    }

    @Test
    fun preserves_layout_params_when_wrapping_child() {
        val context = InstrumentationRegistry.getTargetContext()
        val button = Button(context).apply { id = 1 }
        val linearLayout = LinearLayout(context).apply {
            addView(
                button,
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            )
        }
        linearLayout.wiiafl(childId = 1)

        val layoutParams = linearLayout.getChildAt(0).layoutParams
        assertEquals(LinearLayout.LayoutParams.MATCH_PARENT, layoutParams.width)
        assertEquals(LinearLayout.LayoutParams.WRAP_CONTENT, layoutParams.height)
    }
}
