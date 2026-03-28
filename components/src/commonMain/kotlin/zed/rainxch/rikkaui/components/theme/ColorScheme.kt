package zed.rainxch.rikkaui.components.theme

import androidx.compose.ui.graphics.Color

/**
 * Built-in color palettes for RikkaUi.
 *
 * Each palette provides light and dark variants with all semantic tokens.
 * Inspired by shadcn/ui's theming system.
 */
object RikkaPalettes {

    // ──────────────────────────────────────────────
    // Neutral — Pure achromatic, classic shadcn look
    // ──────────────────────────────────────────────

    val NeutralLight = RikkaColors(
        background = Color(0xFFFFFFFF),
        foreground = Color(0xFF0A0A0A),

        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF0A0A0A),

        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF0A0A0A),

        primary = Color(0xFF171717),
        primaryForeground = Color(0xFFFAFAFA),

        secondary = Color(0xFFF5F5F5),
        secondaryForeground = Color(0xFF171717),

        muted = Color(0xFFF5F5F5),
        mutedForeground = Color(0xFF737373),

        accent = Color(0xFFF5F5F5),
        accentForeground = Color(0xFF171717),

        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0xFFE5E5E5),
        input = Color(0xFFE5E5E5),
        ring = Color(0xFFA3A3A3),
    )

    val NeutralDark = RikkaColors(
        background = Color(0xFF0A0A0A),
        foreground = Color(0xFFFAFAFA),

        card = Color(0xFF171717),
        cardForeground = Color(0xFFFAFAFA),

        popover = Color(0xFF171717),
        popoverForeground = Color(0xFFFAFAFA),

        primary = Color(0xFFE5E5E5),
        primaryForeground = Color(0xFF171717),

        secondary = Color(0xFF262626),
        secondaryForeground = Color(0xFFFAFAFA),

        muted = Color(0xFF262626),
        mutedForeground = Color(0xFFA3A3A3),

        accent = Color(0xFF262626),
        accentForeground = Color(0xFFFAFAFA),

        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0x1AFFFFFF),
        input = Color(0x26FFFFFF),
        ring = Color(0xFF737373),
    )

    // ──────────────────────────────────────────────
    // Zinc — Cool, modern, blue-tinted gray
    // ──────────────────────────────────────────────

    val ZincLight = RikkaColors(
        background = Color(0xFFFFFFFF),
        foreground = Color(0xFF09090B),

        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF09090B),

        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF09090B),

        primary = Color(0xFF18181B),
        primaryForeground = Color(0xFFFAFAFA),

        secondary = Color(0xFFF4F4F5),
        secondaryForeground = Color(0xFF18181B),

        muted = Color(0xFFF4F4F5),
        mutedForeground = Color(0xFF71717A),

        accent = Color(0xFFF4F4F5),
        accentForeground = Color(0xFF18181B),

        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0xFFE4E4E7),
        input = Color(0xFFE4E4E7),
        ring = Color(0xFFA1A1AA),
    )

    val ZincDark = RikkaColors(
        background = Color(0xFF09090B),
        foreground = Color(0xFFFAFAFA),

        card = Color(0xFF18181B),
        cardForeground = Color(0xFFFAFAFA),

        popover = Color(0xFF18181B),
        popoverForeground = Color(0xFFFAFAFA),

        primary = Color(0xFFE4E4E7),
        primaryForeground = Color(0xFF18181B),

        secondary = Color(0xFF27272A),
        secondaryForeground = Color(0xFFFAFAFA),

        muted = Color(0xFF27272A),
        mutedForeground = Color(0xFFA1A1AA),

        accent = Color(0xFF27272A),
        accentForeground = Color(0xFFFAFAFA),

        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0x1AFFFFFF),
        input = Color(0x26FFFFFF),
        ring = Color(0xFF71717A),
    )

    // ──────────────────────────────────────────────
    // Rose — Warm, approachable, rose-tinted
    // ──────────────────────────────────────────────

    val RoseLight = RikkaColors(
        background = Color(0xFFFFFFFF),
        foreground = Color(0xFF0C0A09),

        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF0C0A09),

        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF0C0A09),

        primary = Color(0xFFE11D48),
        primaryForeground = Color(0xFFFFF1F2),

        secondary = Color(0xFFF5F5F4),
        secondaryForeground = Color(0xFF1C1917),

        muted = Color(0xFFF5F5F4),
        mutedForeground = Color(0xFF78716C),

        accent = Color(0xFFF5F5F4),
        accentForeground = Color(0xFF1C1917),

        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0xFFE7E5E4),
        input = Color(0xFFE7E5E4),
        ring = Color(0xFFE11D48),
    )

    val RoseDark = RikkaColors(
        background = Color(0xFF0C0A09),
        foreground = Color(0xFFFAFAF9),

        card = Color(0xFF1C1917),
        cardForeground = Color(0xFFFAFAF9),

        popover = Color(0xFF1C1917),
        popoverForeground = Color(0xFFFAFAF9),

        primary = Color(0xFFFB7185),
        primaryForeground = Color(0xFF1C1917),

        secondary = Color(0xFF292524),
        secondaryForeground = Color(0xFFFAFAF9),

        muted = Color(0xFF292524),
        mutedForeground = Color(0xFFA8A29E),

        accent = Color(0xFF292524),
        accentForeground = Color(0xFFFAFAF9),

        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAFA),

        border = Color(0x1AFFFFFF),
        input = Color(0x26FFFFFF),
        ring = Color(0xFFFB7185),
    )
}
