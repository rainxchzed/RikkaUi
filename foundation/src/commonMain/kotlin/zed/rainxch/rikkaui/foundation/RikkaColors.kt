package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * RikkaColors defines all semantic color tokens for the design system.
 *
 * Tokens are organized as `surface` / `on*` pairs — the surface token is the
 * background color, the `on*` token is the text/icon color that sits on it.
 *
 * ### Token groups
 * | Group | Surface | Content |
 * |-------|---------|---------|
 * | App background | `background` | `onBackground` |
 * | Elevated surface (cards, popovers, dialogs, sheets) | `surface` | `onSurface` |
 * | Primary action | `primary` | `onPrimary` |
 * | Secondary/subtle action | `secondary` | `onSecondary` |
 * | Muted/disabled | `muted` | `onMuted` |
 * | Destructive | `destructive` | `onDestructive` |
 * | Warning | `warning` | `onWarning` |
 * | Success | `success` | `onSuccess` |
 * | Tinted primary | `primaryTinted` | `onPrimaryTinted` |
 * | Tinted destructive | `destructiveTinted` | `onDestructiveTinted` |
 * | Inverse contrast | `inverseSurface` | `onInverseSurface` |
 */
@Immutable
data class RikkaColors(
    // ── App background ──────────────────────────────────────────────────
    /** The root background of the app. */
    val background: Color,
    /** Default text/icon color on [background]. */
    val onBackground: Color,
    // ── Elevated surface ────────────────────────────────────────────────
    /** Elevated surfaces: cards, popovers, dialogs, sheets, dropdown menus. */
    val surface: Color,
    /** Text/icon color on [surface]. */
    val onSurface: Color,
    // ── Primary action ──────────────────────────────────────────────────
    /** Main action color — filled buttons, links, active indicators. */
    val primary: Color,
    /** Text/icon color on [primary]. */
    val onPrimary: Color,
    // ── Secondary action ────────────────────────────────────────────────
    /** Less prominent actions, subtle backgrounds, hover highlights. */
    val secondary: Color,
    /** Text/icon color on [secondary]. */
    val onSecondary: Color,
    // ── Muted ───────────────────────────────────────────────────────────
    /** Subtle backgrounds for disabled states, helper areas. */
    val muted: Color,
    /** Subdued text/icon color — placeholders, helper text, captions. */
    val onMuted: Color,
    // ── Destructive ─────────────────────────────────────────────────────
    /** Danger/error/delete action color. */
    val destructive: Color,
    /** Text/icon color on [destructive]. */
    val onDestructive: Color,
    // ── Status / feedback ───────────────────────────────────────────────
    /** Warning indicator color. */
    val warning: Color,
    /** Text/icon color on [warning]. */
    val onWarning: Color,
    /** Success indicator color. */
    val success: Color,
    /** Text/icon color on [success]. */
    val onSuccess: Color,
    // ── Utility (standalone, no pairs) ──────────────────────────────────
    /** Default border color for inputs, cards, separators. */
    val border: Color,
    /** Subtler border for decorative/separating lines. Lighter than [border]. */
    val borderSubtle: Color = border.copy(alpha = 0.5f),
    /** Focus ring color. */
    val ring: Color,
    /** Semi-transparent overlay behind dialogs and sheets. */
    val scrim: Color = Color.Black.copy(alpha = 0.5f),
    // ── Inverse contrast ────────────────────────────────────────────────
    /** A surface that contrasts sharply with [background] (e.g., snackbar on light theme). */
    val inverseSurface: Color,
    /** Text/icon color on [inverseSurface]. */
    val onInverseSurface: Color,
    // ── Tinted containers ───────────────────────────────────────────────
    /** Subtle primary-tinted background for selected states, tonal buttons. */
    val primaryTinted: Color,
    /** Text/icon color on [primaryTinted]. */
    val onPrimaryTinted: Color,
    /** Subtle destructive-tinted background for error banners, alerts. */
    val destructiveTinted: Color,
    /** Text/icon color on [destructiveTinted]. */
    val onDestructiveTinted: Color,
    // ── Interaction states ──────────────────────────────────────────────
    /** Primary button/link background on hover. Unspecified = component computes via lerp. */
    val primaryHover: Color = Color.Unspecified,
    /** Primary button/link background on press. Unspecified = component computes via lerp. */
    val primaryPressed: Color = Color.Unspecified,
    /** Destructive button background on hover. */
    val destructiveHover: Color = Color.Unspecified,
    /** Destructive button background on press. */
    val destructivePressed: Color = Color.Unspecified,
    /** Secondary/muted surface on hover (ghost buttons, list items). */
    val secondaryHover: Color = Color.Unspecified,
    /** Secondary/muted surface on press. */
    val secondaryPressed: Color = Color.Unspecified,
)

val LocalRikkaColors =
    staticCompositionLocalOf<RikkaColors> {
        error("No RikkaColors provided. Wrap your content in RikkaTheme { ... }")
    }

val LocalContentColor =
    staticCompositionLocalOf { Color.Unspecified }
