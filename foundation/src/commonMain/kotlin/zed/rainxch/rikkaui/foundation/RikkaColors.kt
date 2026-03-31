package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * RikkaColors defines all semantic color tokens for the design system.
 *
 * Every token comes in background/foreground pairs — the background token
 * is the surface color, the foreground token is the text/icon color that sits on it.
 *
 * This mirrors shadcn/ui's color token system, adapted for Compose.
 */
@Immutable
data class RikkaColors(
    // Core surface
    val background: Color,
    val foreground: Color,
    // Card surfaces
    val card: Color,
    val cardForeground: Color,
    // Popover / dropdown / dialog surfaces
    val popover: Color,
    val popoverForeground: Color,
    // Primary action (buttons, links, active states)
    val primary: Color,
    val primaryForeground: Color,
    // Secondary action (outline buttons, less prominent)
    val secondary: Color,
    val secondaryForeground: Color,
    // Muted (disabled states, subtle backgrounds, helper text)
    val muted: Color,
    val mutedForeground: Color,
    // Accent (hover highlights, subtle interactive feedback)
    val accent: Color,
    val accentForeground: Color,
    // Destructive (delete, error, danger)
    val destructive: Color,
    val destructiveForeground: Color,
    // Status / feedback
    val warning: Color,
    val warningForeground: Color,
    val success: Color,
    val successForeground: Color,
    // Standalone utility tokens
    val border: Color,
    /** Subtler border for decorative/separating lines. Lighter than [border]. */
    val borderVariant: Color = border.copy(alpha = 0.5f),
    val input: Color,
    val ring: Color,
    /** Semi-transparent overlay behind dialogs and sheets. */
    val scrim: Color = Color.Black.copy(alpha = 0.5f),
    /** A surface color that contrasts sharply with [background] (e.g., snackbar on light theme). */
    val inverseSurface: Color = Color.Unspecified,
    /** Content color for text/icons on [inverseSurface]. */
    val inverseOnSurface: Color = Color.Unspecified,
    /** Subtle primary-tinted background for selected states, tonal buttons. */
    val primaryContainer: Color = Color.Unspecified,
    /** Text/icon color on [primaryContainer]. */
    val primaryContainerForeground: Color = Color.Unspecified,
    /** Subtle destructive-tinted background for error banners, alerts. */
    val destructiveContainer: Color = Color.Unspecified,
    /** Text/icon color on [destructiveContainer]. */
    val destructiveContainerForeground: Color = Color.Unspecified,
)

val LocalRikkaColors =
    staticCompositionLocalOf<RikkaColors> {
        error("No RikkaColors provided. Wrap your content in RikkaTheme { ... }")
    }

val LocalContentColor =
    staticCompositionLocalOf { Color.Unspecified }
