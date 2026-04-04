package zed.rainxch.rikkaui.components.ui.fab

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.disabled
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize
import zed.rainxch.rikkaui.components.ui.spinner.Spinner
import zed.rainxch.rikkaui.components.ui.spinner.SpinnerSize
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.LocalTextStyle
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Variant ────────────────────────────────────────────────

/**
 * FAB visual variants.
 *
 * @property Default Solid primary background — the main action on a screen.
 * @property Secondary Subtle secondary background — complementary actions.
 * @property Surface Surface-colored — blends with the page, low emphasis.
 * @property Destructive Danger-tinted — delete, remove, discard actions.
 */
public enum class FabVariant {
    Default,
    Secondary,
    Surface,
    Destructive,
}

// ─── Size ───────────────────────────────────────────────────

/**
 * FAB sizes.
 *
 * @property Default Standard 56dp FAB — the most common floating action.
 * @property Small Compact 40dp FAB — secondary or space-constrained actions.
 * @property Large Prominent 72dp FAB — hero actions.
 */
public enum class FabSize {
    Default,
    Small,
    Large,
}

// ─── Animation ──────────────────────────────────────────────

/**
 * FAB press animation style.
 *
 * @property None No animation.
 * @property Scale Subtle scale-down on press.
 * @property Bounce Playful spring bounce on press.
 */
public enum class FabAnimation {
    None,
    Scale,
    Bounce,
}

// ─── Colors ─────────────────────────────────────────────────

/**
 * Resolved container, content, and shadow colors for a FAB.
 *
 * Use [FabDefaults.colors] to create instances — that path caches
 * the default resolution per theme to avoid allocations.
 *
 * @param containerColor Background color when enabled.
 * @param contentColor Foreground (icon/text) color when enabled.
 * @param disabledContainerColor Background color when disabled.
 * @param disabledContentColor Foreground color when disabled.
 * @param hoverContainerColor Background on hover. [Color.Unspecified] = compute via lerp.
 * @param pressedContainerColor Background on press. [Color.Unspecified] = compute via lerp.
 */
@Immutable
public class FabColorValues internal constructor(
    public val containerColor: Color,
    public val contentColor: Color,
    public val disabledContainerColor: Color,
    public val disabledContentColor: Color,
    public val hoverContainerColor: Color = Color.Unspecified,
    public val pressedContainerColor: Color = Color.Unspecified,
) {
    @Stable
    internal fun container(enabled: Boolean): Color = if (enabled) containerColor else disabledContainerColor

    @Stable
    internal fun content(enabled: Boolean): Color = if (enabled) contentColor else disabledContentColor

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FabColorValues) return false
        return containerColor == other.containerColor &&
            contentColor == other.contentColor &&
            disabledContainerColor == other.disabledContainerColor &&
            disabledContentColor == other.disabledContentColor &&
            hoverContainerColor == other.hoverContainerColor &&
            pressedContainerColor == other.pressedContainerColor
    }

    override fun hashCode(): Int {
        var result = containerColor.hashCode()
        result = 31 * result + contentColor.hashCode()
        result = 31 * result + disabledContainerColor.hashCode()
        result = 31 * result + disabledContentColor.hashCode()
        result = 31 * result + hoverContainerColor.hashCode()
        result = 31 * result + pressedContainerColor.hashCode()
        return result
    }
}

// ─── Size Values ────────────────────────────────────────────

@Immutable
internal data class FabSizeValues(
    val minSize: Dp,
    val iconSize: IconSize,
    val spinnerSize: SpinnerSize,
    val horizontalPadding: Dp,
    val contentSpacing: Dp,
)

// ─── Defaults ───────────────────────────────────────────────

/** Default values for [Fab] configuration. */
public object FabDefaults {
    /**
     * Creates [FabColorValues] for the given [variant].
     */
    @Composable
    public fun colors(variant: FabVariant = FabVariant.Default): FabColorValues = resolveVariantColors(variant)

    /**
     * Returns the resolved [FabSizeValues] for the given [size].
     */
    @Composable
    internal fun sizeValues(size: FabSize): FabSizeValues =
        when (size) {
            FabSize.Small ->
                FabSizeValues(
                    minSize = 40.dp,
                    iconSize = IconSize.Sm,
                    spinnerSize = SpinnerSize.Sm,
                    horizontalPadding = RikkaTheme.spacing.sm,
                    contentSpacing = 6.dp,
                )

            FabSize.Default ->
                FabSizeValues(
                    minSize = 56.dp,
                    iconSize = IconSize.Default,
                    spinnerSize = SpinnerSize.Default,
                    horizontalPadding = RikkaTheme.spacing.md,
                    contentSpacing = RikkaTheme.spacing.sm,
                )

            FabSize.Large ->
                FabSizeValues(
                    minSize = 72.dp,
                    iconSize = IconSize.Lg,
                    spinnerSize = SpinnerSize.Lg,
                    horizontalPadding = RikkaTheme.spacing.lg,
                    contentSpacing = RikkaTheme.spacing.sm,
                )
        }
}

// ─── Component ──────────────────────────────────────────────

/**
 * RikkaUI Floating Action Button — a prominent, elevated button for the
 * primary action on a screen.
 *
 * Supports 4 visual [variant]s, 3 [size]s, 3 press [animation]s, an
 * [expanded] text label mode, a [loading] spinner state, and theme-driven
 * shadow elevation.
 *
 * Children inherit the correct foreground color via [LocalContentColor]
 * and text style via [LocalTextStyle].
 *
 * ```
 * // Icon-only FAB
 * Fab(
 *     icon = RikkaIcons.Plus,
 *     label = "Add item",
 *     onClick = { addItem() },
 * )
 *
 * // Extended FAB with text
 * Fab(
 *     icon = RikkaIcons.Plus,
 *     label = "New note",
 *     onClick = { addNote() },
 *     expanded = true,
 * )
 * ```
 *
 * @param icon The icon to display.
 * @param label Accessibility content description. Also displayed as text when [expanded] is true.
 * @param onClick Called when the FAB is clicked.
 * @param modifier Modifier applied to the FAB container.
 * @param variant Visual style — [FabVariant.Default], Secondary, Surface, Destructive.
 * @param size Touch target — [FabSize.Default], Small, Large.
 * @param animation Press feedback — [FabAnimation.Scale], Bounce, None.
 * @param expanded When true, shows [label] as text next to the icon.
 * @param enabled Whether the FAB responds to input.
 * @param loading Shows a spinner instead of the icon when true.
 * @param colors Override resolved colors. Defaults to [FabDefaults.colors] for the [variant].
 * @param elevation Shadow elevation. Defaults to [RikkaTheme.elevation.high].
 * @param interactionSource Optional hoisted [MutableInteractionSource].
 */
@Composable
public fun Fab(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: FabVariant = FabVariant.Default,
    size: FabSize = FabSize.Default,
    animation: FabAnimation = FabAnimation.Scale,
    expanded: Boolean = false,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: FabColorValues = FabDefaults.colors(variant),
    elevation: Dp = RikkaTheme.elevation.high,
    interactionSource: MutableInteractionSource? = null,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isEffectivelyEnabled = enabled && !loading
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    // ─── Resolved values ────────────────────────────────
    val motion = RikkaTheme.motion
    val sizeValues = FabDefaults.sizeValues(size)
    val shape = RikkaTheme.shapes.xl

    val containerColor = colors.container(isEffectivelyEnabled)
    val contentColor = colors.content(isEffectivelyEnabled)

    // ─── Hover/press color modulation ───────────────────
    val themeColors = RikkaTheme.colors

    val modulatedContainer =
        when {
            !isEffectivelyEnabled -> containerColor
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
            FabAnimation.None -> null
            FabAnimation.Scale -> motion.springDefault
            FabAnimation.Bounce -> motion.springBouncy
        }

    val targetScale =
        when {
            !isEffectivelyEnabled || animation == FabAnimation.None -> 1f
            isPressed ->
                when (animation) {
                    FabAnimation.Scale -> motion.pressScaleSubtle
                    FabAnimation.Bounce -> motion.pressScaleBouncy
                    FabAnimation.None -> 1f
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

    // ─── Hover elevation lift ───────────────────────────
    val hoverElevation = if (isHovered && isEffectivelyEnabled) 2.dp else 0.dp
    val animatedElevation by animateFloatAsState(
        targetValue = (elevation + hoverElevation).value,
        animationSpec = tween(motion.durationDefault),
    )

    // ─── Modifiers ──────────────────────────────────────
    val animationModifier =
        if (animation != FabAnimation.None) {
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
                .then(animationModifier)
                .semantics(mergeDescendants = true) {
                    role = Role.Button
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    if (!isEffectivelyEnabled) {
                        disabled()
                    }
                }.shadow(animatedElevation.dp, shape)
                .background(animatedBackground, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = isEffectivelyEnabled,
                    role = Role.Button,
                    onClick = onClick,
                ).defaultMinSize(
                    minHeight = sizeValues.minSize,
                    minWidth = sizeValues.minSize,
                ).padding(horizontal = sizeValues.horizontalPadding),
        horizontalArrangement =
            Arrangement.spacedBy(
                sizeValues.contentSpacing,
                Alignment.CenterHorizontally,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides RikkaTheme.typography.small,
        ) {
            if (loading) {
                Spinner(
                    size = sizeValues.spinnerSize,
                    trackColor = null,
                    label = "Loading",
                )
            } else {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    size = sizeValues.iconSize,
                )
            }

            if (expanded && label.isNotEmpty()) {
                Text(text = label)
            }
        }
    }
}

/**
 * RikkaUI Floating Action Button with custom content.
 *
 * Use this overload when you need full control over the FAB's content
 * (e.g., custom icon + text arrangements, badges, etc.).
 *
 * ```
 * Fab(
 *     label = "Create",
 *     onClick = { create() },
 * ) {
 *     Icon(RikkaIcons.Plus, contentDescription = null)
 *     Text("Create")
 * }
 * ```
 *
 * @param label Accessibility content description.
 * @param onClick Called when the FAB is clicked.
 * @param modifier Modifier applied to the FAB container.
 * @param variant Visual style.
 * @param size Touch target.
 * @param animation Press feedback.
 * @param enabled Whether the FAB responds to input.
 * @param loading Shows a spinner when true; content is still rendered.
 * @param colors Override resolved colors.
 * @param elevation Shadow elevation.
 * @param interactionSource Optional hoisted interaction source.
 * @param content Composable content displayed inside the FAB.
 */
@Composable
public fun Fab(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: FabVariant = FabVariant.Default,
    size: FabSize = FabSize.Default,
    animation: FabAnimation = FabAnimation.Scale,
    enabled: Boolean = true,
    loading: Boolean = false,
    colors: FabColorValues = FabDefaults.colors(variant),
    elevation: Dp = RikkaTheme.elevation.high,
    interactionSource: MutableInteractionSource? = null,
    content: @Composable () -> Unit,
) {
    @Suppress("NAME_SHADOWING")
    val interactionSource = interactionSource ?: remember { MutableInteractionSource() }
    val isEffectivelyEnabled = enabled && !loading
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val motion = RikkaTheme.motion
    val sizeValues = FabDefaults.sizeValues(size)
    val shape = RikkaTheme.shapes.xl

    val containerColor = colors.container(isEffectivelyEnabled)
    val contentColor = colors.content(isEffectivelyEnabled)

    val themeColors = RikkaTheme.colors
    val modulatedContainer =
        when {
            !isEffectivelyEnabled -> containerColor
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

    val animationSpec =
        when (animation) {
            FabAnimation.None -> null
            FabAnimation.Scale -> motion.springDefault
            FabAnimation.Bounce -> motion.springBouncy
        }

    val targetScale =
        when {
            !isEffectivelyEnabled || animation == FabAnimation.None -> 1f
            isPressed ->
                when (animation) {
                    FabAnimation.Scale -> motion.pressScaleSubtle
                    FabAnimation.Bounce -> motion.pressScaleBouncy
                    FabAnimation.None -> 1f
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

    val hoverElevation = if (isHovered && isEffectivelyEnabled) 2.dp else 0.dp
    val animatedElevation by animateFloatAsState(
        targetValue = (elevation + hoverElevation).value,
        animationSpec = tween(motion.durationDefault),
    )

    val animationModifier =
        if (animation != FabAnimation.None) {
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
                .then(animationModifier)
                .semantics(mergeDescendants = true) {
                    role = Role.Button
                    if (label.isNotEmpty()) {
                        contentDescription = label
                    }
                    if (!isEffectivelyEnabled) {
                        disabled()
                    }
                }.shadow(animatedElevation.dp, shape)
                .background(animatedBackground, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = isEffectivelyEnabled,
                    role = Role.Button,
                    onClick = onClick,
                ).defaultMinSize(
                    minHeight = sizeValues.minSize,
                    minWidth = sizeValues.minSize,
                ).padding(horizontal = sizeValues.horizontalPadding),
        horizontalArrangement =
            Arrangement.spacedBy(
                sizeValues.contentSpacing,
                Alignment.CenterHorizontally,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CompositionLocalProvider(
            LocalContentColor provides contentColor,
            LocalTextStyle provides RikkaTheme.typography.small,
        ) {
            if (loading) {
                Spinner(
                    size = sizeValues.spinnerSize,
                    trackColor = null,
                    label = "Loading",
                )
            }
            content()
        }
    }
}

// ─── Internal: Color Resolution ─────────────────────────────

@Composable
private fun resolveVariantColors(variant: FabVariant): FabColorValues {
    val colors = RikkaTheme.colors
    val disabledAlpha = 0.5f

    return when (variant) {
        FabVariant.Default ->
            FabColorValues(
                containerColor = colors.primary,
                contentColor = colors.onPrimary,
                disabledContainerColor = colors.primary.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onPrimary.copy(alpha = 0.7f),
                hoverContainerColor = colors.primaryHover,
                pressedContainerColor = colors.primaryPressed,
            )

        FabVariant.Secondary ->
            FabColorValues(
                containerColor = colors.secondary,
                contentColor = colors.onSecondary,
                disabledContainerColor = colors.secondary.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onSecondary.copy(alpha = 0.7f),
                hoverContainerColor = colors.secondaryHover,
                pressedContainerColor = colors.secondaryPressed,
            )

        FabVariant.Surface ->
            FabColorValues(
                containerColor = colors.surface,
                contentColor = colors.onSurface,
                disabledContainerColor = colors.surface.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onSurface.copy(alpha = 0.7f),
            )

        FabVariant.Destructive ->
            FabColorValues(
                containerColor = colors.destructive,
                contentColor = colors.onDestructive,
                disabledContainerColor = colors.destructive.copy(alpha = disabledAlpha),
                disabledContentColor = colors.onDestructive.copy(alpha = 0.7f),
                hoverContainerColor = colors.destructiveHover,
                pressedContainerColor = colors.destructivePressed,
            )
    }
}
