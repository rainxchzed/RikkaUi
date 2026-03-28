# RikkaUI Component Conventions

## File Structure

```kotlin
@file:OptIn(ExperimentalLayoutApi::class) // only if needed

package zed.rainxch.rikkaui.components.ui.{name}

// Foundation imports only — NEVER Material3
import androidx.compose.foundation.*
import androidx.compose.runtime.*
import androidx.compose.ui.*

/**
 * {Name} displays ...
 *
 * Usage:
 * ```
 * {Name}(
 *     variant = {Name}Variant.Default,
 *     modifier = Modifier.fillMaxWidth(),
 * ) {
 *     Text("Content")
 * }
 * ```
 *
 * @param variant Visual style variant.
 * @param modifier Modifier applied to root element.
 * @param label Accessibility label (contentDescription).
 * @param content Slot for child composables.
 */
@Composable
fun {Name}(
    // 1. Required params
    variant: {Name}Variant = {Name}Variant.Default,
    // 2. Modifier (first optional, default Modifier)
    modifier: Modifier = Modifier,
    // 3. Optional params
    label: String = "",
    enabled: Boolean = true,
    // 4. Trailing content lambda
    content: @Composable () -> Unit,
) {
    // Implementation
}
```

## Enum-Based Configuration

```kotlin
enum class {Name}Variant { Default, Secondary, Destructive, Outline }
enum class {Name}Size { Sm, Default, Lg }
```

NEVER use booleans for variants. NEVER use strings.

## Color Resolution Pattern

```kotlin
@Composable
private fun resolveColors(variant: {Name}Variant): {Name}Colors {
    val colors = RikkaTheme.colors
    return when (variant) {
        {Name}Variant.Default -> {Name}Colors(
            background = colors.primary,
            foreground = colors.primaryForeground,
            border = colors.border,
        )
        // ...
    }
}

@Immutable
private data class {Name}Colors(
    val background: Color,
    val foreground: Color,
    val border: Color,
)
```

## Animation Pattern

```kotlin
val motion = RikkaTheme.motion  // NEVER hardcode spring/tween params

val scale by animateFloatAsState(
    targetValue = if (pressed) motion.pressScaleSubtle else 1f,
    animationSpec = motion.springDefault,
)
```

## Accessibility

```kotlin
Box(
    modifier = modifier
        .semantics {
            role = Role.Button
            contentDescription = label
            if (!enabled) disabled()
        }
        .clickable(
            interactionSource = interactionSource,
            indication = RikkaIndication,
            enabled = enabled,
            role = Role.Button,
            onClick = onClick,
        )
)
```

## Interaction State Tracking

```kotlin
val interactionSource = remember { MutableInteractionSource() }
val isHovered by interactionSource.collectIsHoveredAsState()
val isPressed by interactionSource.collectIsPressedAsState()
val isFocused by interactionSource.collectIsFocusedAsState()
```

## Overloads

Always provide both:

```kotlin
// Generic (content lambda)
@Composable
fun {Name}(modifier: Modifier = Modifier, content: @Composable () -> Unit)

// Convenience (string text)
@Composable
fun {Name}(text: String, modifier: Modifier = Modifier)
```
