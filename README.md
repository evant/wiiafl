# WIIAFL

> Wrap it in a FrameLayout

-- Expert Andriod Dev

Pretty much any layout problem in Android can be solved by wrapping your view in a FrameLayout, this lib makes that a piece of cake!

## Usage

For the simple case, you just call the awesomely-named `wiiafl()` extension function:
```kotlin
val myFixedLayout = view.wiiafl()
```

You can find a specific view to wrap with `childId`.
```kotlin
view.wiifl(childId = R.id.broken_button)
```

Sometimes, 1 FrameLayout just isn't enough to fix your bugs, you can wrap it in as many as you like by passing in a `count`.

```kotlin
val extraFrameLayouts = view.wiiafl(count = 3)
```

Want to just wrap _everything_ in a FrameLayout? Just use the `recursive` flag. This is an easy way to get rid of all of your bugs!

```kotlin
view.wiiafl(recursive = true)
```
