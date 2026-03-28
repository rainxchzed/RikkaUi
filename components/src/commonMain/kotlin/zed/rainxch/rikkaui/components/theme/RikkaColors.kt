package zed.rainxch.rikkaui.components.theme

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
    // Standalone utility tokens
    val border: Color,
    val input: Color,
    val ring: Color,
)

val LocalRikkaColors =
    staticCompositionLocalOf<RikkaColors> {
        error("No RikkaColors provided. Wrap your content in RikkaTheme { ... }")
    }
