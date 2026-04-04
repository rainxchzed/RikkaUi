package zed.rainxch.rikkaui.components.ui.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.foundation.modifier.minTouchTarget

// ─── Variant ────────────────────────────────────────────────

/** Button visual variants.
 * @property Default Solid primary background.
 * @property Outline Bordered, transparent background.
 * @property Secondary Subtle filled background.
 * @property Ghost No background or border.
 * @property Destructive Soft red tint for dangerous actions.
 * @property Link Text link style.
 */
public enum class ButtonVariant {
    Default,
    Outline,
    Secondary,
    Ghost,
    Destructive,
    Link,
}

// ─── Size ───────────────────────────────────────────────────

/** Button sizes.
 * @property Default Standard size.
 * @property Sm Compact size.
 * @property Lg Large touch target.
 * @property Icon Square, icon-only.
 */
public enum class ButtonSize {
    Default,
    Sm,
    Lg,
    Icon,
}

// ─── Animation ──────────────────────────────────────────────

/** Press animation style.
 * @property None No animation.
 * @property Scale Subtle scale-down on press.
 * @property Bounce Playful spring bounce on press.
 */
public enum class ButtonAnimation {
    None,
    Scale,
    Bounce,
}

// ─── Colors ─────────────────────────────────────────────────

/**
 * Resolved container, content, and border colors for a button.
 *
 * Callers should use [ButtonDefaults.colors] to create instances — that
 * path caches the default resolution per theme, avoiding allocations on
 * every recomposition.
 *
 * @param containerColor Background color when enabled.
 * @param contentColor Foreground (text/icon) color when enabled.
 * @param borderColor Border color when enabled (Transparent = no border).
 * @param disabledContainerColor Background color when disabled.
 * @param disabledContentColor Foreground color when disabled.
 * @param disabledBorderColor Border color when disabled.
 * @param hoverContainerColor Background color on hover. [Color.Unspecified] = compute via lerp.
 * @param pressedContainerColor Background color on press. [Color.Unspecified] = compute via lerp.
 */
@Immutable
public class ButtonColorValues internal constructor(
    public val containerColor: Color,
    public val contentColor: Color,
    public val borderColor: Color,
    public val disabledContainerColor: Color,
    public val disabledContentColor: Color,
    public val disabledBorderColor: Color,
    public val hoverContainerColor: Color = Color.Unspecified,
    public val pressedContainerColor: Color = Color.Unspecified,
) {
    /** Resolved container color for the given [enabled] state. */
    @Stable
    internal fun container(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor

    /** Resolved content color for the given [enabled] state. */
    @Stable
    internal fun content(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor

    /** Resolved border color for the given [enabled] state. */
    @Stable
    internal fun border(enabled: Boolean): Color = if (enabled) borderColor else disabledBorderColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ButtonColorValues) return false
        return containerColor == other.containerColor &&
            contentColor == other.contentColor &&
            borderColor == other.borderColor &&
            disabledContainerColor == other.disabledContainerColor &&
            disabledContentColor == other.disabledContentColor &&
            disabledBorderColor == other.disabledBorderColor &&
            hoverContainerColor == other.hoverContainerColor &&
            pressedContainerColor == other.pressedContainerColor
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + borderColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        result = 31 * result + disabledBorderColor.hashCode()
        result = 31 * result + hoverContainerColor.hashCode()
        result = 31 * result + pressedContainerColor.hashCode()
        return result
    }
}

// ─── Size Values ────────────────────────────────────────────

@Immutable
internal data class SizeValues(
    val minHeight: Dp,
    val minWidth: Dp,
    val horizontalPadding: Dp,
    val verticalPadding: Dp,
    val contentSpacing: Dp,
)

// ─── Defaults ───────────────────────────────────────────────

/** Default values for [Button] configuration. */
public object ButtonDefaults {
    /**
     * Creates [ButtonColorValues] for the given [variant].
     *
     * Prefer calling with only [variant] — the result is cached per theme
     * to avoid allocations on every recomposition.
     */
    @Composable
    public fun colors(variant: ButtonVariant = ButtonVariant.Default): ButtonColorValues = resolveVariantColors(variant)

    /**
     * Returns the default [Shape] for the given [size].
     */
    @Composable
    public fun shape(size: ButtonSize = ButtonSize.Default): Shape =
        when (size) {
            ButtonSize.Sm -> RikkaTheme.shapes.md
            else -> RikkaTheme.shapes.lg
        }

    /**
     * Returns the pressed [Shape] for the given [size].
     *
     * Slightly more rounded than the resting shape, giving a soft
     * "cushion" feel when pressed.
     */
    @Composable
    public fun pressedShape(size: ButtonSize = ButtonSize.Default): Shape =
        when (size) {
            ButtonSize.Sm -> RikkaTheme.shapes.lg
            else -> RikkaTheme.shapes.xl
        }

    /**
     * Returns the resolved [SizeValues] for the given [size].
     */
    @Composable
    internal fun sizeValues(size: ButtonSize): SizeValues =
        when (size) {
            ButtonSize.Default ->
                SizeValues(
                    minHeight = 36.dp,
                    minWidth = 0.dp,
                    horizontalPadding = RikkaTheme.spacing.md,
                    verticalPadding = RikkaTheme.spacing.sm,
                    contentSpacing = 6.dp,
                )

            ButtonSize.Sm ->
                SizeValues(
                    minHeight = 28.dp,
                    minWidth = 0.dp,
                    horizontalPadding = RikkaTheme.spacing.sm,
                    verticalPadding = 4.dp,
                    contentSpacing = 4.dp,
                )

            ButtonSize.Lg ->
                SizeValues(
                    minHeight = 44.dp,
                    minWidth = 0.dp,
                    horizontalPadding = RikkaTheme.spacing.lg,
                    verticalPadding = RikkaTheme.spacing.sm,
                    contentSpacing = 6.dp,
                )

            ButtonSize.Icon ->
                SizeValues(
                    minHeight = 36.dp,
                    minWidth = 36.dp,
                    horizontalPadding = 0.dp,
                    verticalPadding = 0.dp,
                    contentSpacing = 0.dp,
                )
        }
}

// ─── Component ──────────────────────────────────────────────

/**
 * RikkaUi button — the primary interactive element.
 *
 * Supports 6 visual [variant]s, 4 [size]s, 3 press [animation]s,
 * a [loading] spinner state, and optional shape morphing on press.
 *
 * Children automatically inherit the correct foreground color via
 * [LocalContentColor] and the correct text style via [LocalTextStyle],
 * so bare `Text("Save")` and `Icon(...)` inside the content lambda
 * "just work" without explicit color or style parameters.
 *
 * ```
 * Button(onClick = { save() }) {
 *     Icon(imageVector = RikkaIcons.Check, contentDescription = null)
 *     Text("Save")
 * }
 * ```
 *
 * @param onClick Called when the button is clicked.
 * @param modifier Modifier applied to the button container.
 * @param variant Visual style — [ButtonVariant.Default], Outline, Secondary, Ghost, Destructive, Link.
 * @param size Touch target and padding — [ButtonSize.Default], Sm, Lg, Icon.
 * @param animation Press feedback — [ButtonAnimation.Scale], Bounce, None.
 * @param enabled Whether the button responds to input.
 * @param loading Shows a spinner alongside content when true.
 * @param label Accessibility content description.
 * @param colors Override resolved colors. Defaults to [ButtonDefaults.colors] for the [variant].
 * @param interactionSource Optional hoisted [MutableInteractionSource] for observing or
 *   emitting interactions. Pass your own to share hover/press state with sibling components,
 *   or leave null (default) for a private internal source.
 * @param content Composable content displayed inside the button.
 */
@Composable
public fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
    animation: ButtonAnimation = ButtonAnimation.Scale,
    enabled: Boolean = true,
    loading: Boolean = false,
    label: String = "",
    colors: ButtonColorValues = ButtonDefaults.colors(variant),
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isEffectivelyEnabled = enabled && !loading
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // ─── Resolved values ────────────────────────────────
    val motion = RikkaTheme.motion
    val sizeValues = ButtonDefaults.sizeValues(size)
    val restingShape = ButtonDefaults.shape(size)
    val pressShape = ButtonDefaults.pressedShape(size)

    val containerColor = colors.container(isEffectivelyEnabled)
    val contentColor = colors.content(isEffectivelyEnabled)
    val borderColor = colors.border(isEffectivelyEnabled)

    // ─── Hover/press color modulation ───────────────────
    // Uses theme hover/press tokens when specified, falls back to lerp.
    // Transparent variants use alpha-matched rest color to prevent
    // dark-flash during animation.
    val themeColors = RikkaTheme.colors
    val isTransparentVariant = containerColor == Color.Transparent

    val modulatedContainer =
        when {
            !isEffectivelyEnabled -> containerColor
            isTransparentVariant && isPressed -> {
                if (colors.pressedContainerColor != Color.Unspecified) {
                    colors.pressedContainerColor
                } else {
                    lerp(themeColors.muted, themeColors.onBackground, 0.08f)
                }
            }
            isTransparentVariant && isHovered -> {
                if (colors.hoverContainerColor != Color.Unspecified) {
                    colors.hoverContainerColor
                } else {
                    themeColors.muted
                }
            }
            isTransparentVariant -> {
                themeColors.muted.copy(alpha = 0f)
            }
            isPressed -> {
                if (colors.pressedContainerColor != Color.Unspecified) {
                    colors.pressedContainerColor
                } else {
                    lerp(containerColor, themeColors.onBackground, 1f - motion.pressAlpha)
                }
            }
            isHovered -> {
                if (colors.hoverContainerColor != Color.Unspecified) {
                    colors.hoverContainerColor
                } else {
                    lerp(containerColor, themeColors.onBackground, 1f - motion.hoverAlpha)
                }
            }
            else -> containerColor
        }

    // ─── Animation (from theme motion tokens) ──────────

    val animationSpec =
        when (animation) {
            ButtonAnimation.None -> null
            ButtonAnimation.Scale -> motion.springDefault
            ButtonAnimation.Bounce -> motion.springBouncy
        }

    val targetScale =
        when {
            !isEffectivelyEnabled || animation == ButtonAnimation.None -> 1f
            isPressed ->
                when (animation) {
                    ButtonAnimation.Scale -> motion.pressScaleSubtle
                    ButtonAnimation.Bounce -> motion.pressScaleBouncy
                    ButtonAnimation.None -> 1f
                }
            else -> 1f
        }

    val scale by animateFloatAsState(
        targetValue = targetScale,
        animationSpec = animationSpec ?: spring(),
    )

    val animatedBackground by animateColorAsState(
        targetValue = modulatedContainer,
        animationSpec = tween(motion.durationDefault),
    )

    // ─── Shape morphing on press ────────────────────────
    // Interpolate corner radius: resting → pressed shape.
    // Uses the same spring as the scale animation for cohesion.
    val shapeMorphProgress by animateFloatAsState(
        targetValue = if (isPressed && isEffectivelyEnabled) 1f else 0f,
        animationSpec = animationSpec ?: spring(),
    )
    val currentShape = lerpShape(restingShape, pressShape, shapeMorphProgress)

    // ─── Modifiers ──────────────────────────────────────
    // Always apply background — alpha=0 is invisible but ensures
    // smooth animation without conditional modifier changes.
    val backgroundModifier = Modifier.background(animatedBackground, currentShape)

    val borderModifier =
        if (borderColor != Color.Transparent) {
            Modifier.border(1.dp, borderColor, currentShape)
        } else {
            Modifier
        }

    val animationModifier =
        if (animation != ButtonAnimation.None) {
            Modifier.graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
        } else {
            Modifier
        }

    Row(
        modifier =
            modifier
                .minTouchTarget()
                .then(animationModifier)
                .semantics(mergeDescendants = true) {
                    role = Role.Button
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    if (!isEffectivelyEnabled) {
                        disabled()
                    }
                }.then(borderModifier)
                .then(backgroundModifier)
                .clip(currentShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = isEffectivelyEnabled,
                    role = Role.Button,
                    onClick = onClick,
                ).defaultMinSize(
                    minHeight = sizeValues.minHeight,
                    minWidth = sizeValues.minWidth,
                ).padding(
                    horizontal = sizeValues.horizontalPadding,
                    vertical = sizeValues.verticalPadding,
                ),
        horizontalArrangement =
            Arrangement.spacedBy(
                sizeValues.contentSpacing,
                Alignment.CenterHorizontally,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Provide both foreground color AND default text style to children.
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides RikkaTheme.typography.small,
        ) {
            if (loading) {
                Spinner(
                    size = SpinnerSize.Sm,
                    trackColor = null,
                    label = "Loading",
                )
            }
            content()
        }
    }
}

/**
 * Convenience overload that renders a text label with optional leading/trailing icons.
 *
 * ```
 * Button(
 *     text = "Save",
 *     onClick = { save() },
 *     leadingIcon = { Icon(RikkaIcons.Check, "Saved") },
 * )
 * ```
 *
 * @param text The button label.
 * @param onClick Called when the button is clicked.
 * @param modifier Modifier applied to the button container.
 * @param variant Visual style.
 * @param size Touch target and padding.
 * @param animation Press feedback.
 * @param enabled Whether the button responds to input.
 * @param loading Shows a spinner alongside content when true.
 * @param colors Override resolved colors.
 * @param interactionSource Optional hoisted interaction source.
 * @param leadingIcon Optional composable before the label (hidden during loading).
 * @param trailingIcon Optional composable after the label (hidden during loading).
 */
@Composable
public fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Default,
    size: ButtonSize = ButtonSize.Default,
    animation: ButtonAnimation = ButtonAnimation.Scale,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: ButtonColorValues = ButtonDefaults.colors(variant),
    interactionSource: MutableInteractionSource? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = size,
        animation = animation,
        enabled = enabled,
        loading = loading,
        label = text,
        colors = colors,
        interactionSource = interactionSource,
    ) {
        if (!loading) leadingIcon?.invoke()

        val textStyle =
            if (variant == ButtonVariant.Link) {
                TextStyle(textDecoration = TextDecoration.None)
            } else {
                TextStyle.Default
            }

        Text(
            text = text,
            style = RikkaTheme.typography.small.merge(textStyle),
        )

        if (!loading) trailingIcon?.invoke()
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

/**
 * Resolves [ButtonColorValues] for a given [variant] using the current theme.
 *
 * Each variant defines both enabled AND disabled colors explicitly,
 * giving per-variant control over the disabled appearance rather than
 * a blanket alpha dim.
 */
@Composable
private fun resolveVariantColors(variant: ButtonVariant): ButtonColorValues {
    val colors = RikkaTheme.colors
    val disabledAlpha = 0.5f

    return when (variant) {
        ButtonVariant.Default ->
            ButtonColorValues(
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                borderColor = Color.Transparent,
                disabledContainerColor = colors.primary.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onPrimary.copy(alpha = 0.7f),
                disabledBorderColor = Color.Transparent,
                hoverContainerColor = colors.primaryHover,
                pressedContainerColor = colors.primaryPressed,
            )

        ButtonVariant.Outline ->
            ButtonColorValues(
                containerColor = Color.Transparent,
                contentColor = colors.onBackground,
                borderColor = colors.border,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.onBackground.copy(alpha = disabledAlpha),
                disabledBorderColor = colors.border.copy(alpha = disabledAlpha),
                hoverContainerColor = colors.secondaryHover,
                pressedContainerColor = colors.secondaryPressed,
            )

        ButtonVariant.Secondary ->
            ButtonColorValues(
                containerColor = colors.secondary,
                contentColor = colors.onSecondary,
                borderColor = Color.Transparent,
                disabledContainerColor = colors.secondary.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onSecondary.copy(alpha = 0.7f),
                disabledBorderColor = Color.Transparent,
                hoverContainerColor = colors.secondaryHover,
                pressedContainerColor = colors.secondaryPressed,
            )

        ButtonVariant.Ghost ->
            ButtonColorValues(
                containerColor = Color.Transparent,
                contentColor = colors.onBackground,
                borderColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.onBackground.copy(alpha = disabledAlpha),
                disabledBorderColor = Color.Transparent,
                hoverContainerColor = colors.secondaryHover,
                pressedContainerColor = colors.secondaryPressed,
            )

        ButtonVariant.Destructive ->
            ButtonColorValues(
                containerColor = colors.destructive.copy(alpha = 0.1f),
                contentColor = colors.destructive,
                borderColor = Color.Transparent,
                disabledContainerColor = colors.destructive.copy(alpha = 0.05f),
                disabledContentColor = colors.destructive.copy(alpha = 0.4f),
                disabledBorderColor = Color.Transparent,
                hoverContainerColor = colors.destructiveHover,
                pressedContainerColor = colors.destructivePressed,
            )

        ButtonVariant.Link ->
            ButtonColorValues(
                containerColor = Color.Transparent,
                contentColor = colors.primary,
                borderColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = colors.primary.copy(alpha = disabledAlpha),
                disabledBorderColor = Color.Transparent,
                hoverContainerColor = colors.primaryHover,
                pressedContainerColor = colors.primaryPressed,
            )
    }
}

// ─── Internal: Shape Interpolation ──────────────────────────

/**
 * Linearly interpolates between two [Shape]s.
 *
 * If both are [androidx.compose.foundation.shape.RoundedCornerShape],
 * this interpolates corner radii for a smooth morph. Otherwise falls
 * back to a discrete swap at the midpoint.
 */
@Composable
private fun lerpShape(
    start: Shape,
    end: Shape,
    @Suppress("UNUSED_PARAMETER") fraction: Float,
): Shape {
    if (fraction <= 0f) return start
    if (fraction >= 1f) return end

    // Both shapes must be RoundedCornerShape for smooth interpolation.
    val startRounded =
        start as? androidx.compose.foundation.shape.RoundedCornerShape
            ?: return if (fraction < 0.5f) start else end
    val endRounded =
        end as? androidx.compose.foundation.shape.RoundedCornerShape
            ?: return if (fraction < 0.5f) start else end

    // Interpolate corner percentages.
    val topStart =
        lerp(
            startRounded.topStart.toPx(100f),
            endRounded.topStart.toPx(100f),
            fraction,
        )
    val topEnd =
        lerp(
            startRounded.topEnd.toPx(100f),
            endRounded.topEnd.toPx(100f),
            fraction,
        )
    val bottomEnd =
        lerp(
            startRounded.bottomEnd.toPx(100f),
            endRounded.bottomEnd.toPx(100f),
            fraction,
        )
    val bottomStart =
        lerp(
            startRounded.bottomStart.toPx(100f),
            endRounded.bottomStart.toPx(100f),
            fraction,
        )

    return androidx.compose.foundation.shape.RoundedCornerShape(
        topStart = topStart.dp,
        topEnd = topEnd.dp,
        bottomEnd = bottomEnd.dp,
        bottomStart = bottomStart.dp,
    )
}

private fun lerp(
    start: Float,
    stop: Float,
    fraction: Float,
): Float = start + (stop - start) * fraction

/**
 * Resolves a [androidx.compose.foundation.shape.CornerSize] to pixels
 * for a given reference size. This is a best-effort extraction —
 * percent-based corners are resolved against [referenceSize].
 */
private fun androidx.compose.foundation.shape.CornerSize.toPx(referenceSize: Float): Float {
    // CornerSize doesn't expose its value directly, but toPx() needs a
    // Size and Density. We use a simplified approach: resolve using a
    // square reference and extract the pixel value.
    return try {
        val size =
            androidx.compose.ui.geometry
                .Size(referenceSize, referenceSize)
        val density =
            androidx.compose.ui.unit
                .Density(1f)
        toPx(size, density)
    } catch (_: Exception) {
        0f
    }
}
