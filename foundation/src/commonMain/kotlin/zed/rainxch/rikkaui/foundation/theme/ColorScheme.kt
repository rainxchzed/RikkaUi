package zed.rainxch.rikkaui.components.theme

import androidx.compose.ui.graphics.Color

/**
 * Built-in color palettes for RikkaUI, matching shadcn/ui v4 exactly.
 *
 * shadcn v4 separates theming into **base palettes** (gray scale) and
 * **accent colors** (primary color override). Each base palette provides
 * light and dark variants with all 19 semantic tokens. Accent colors can
 * then be layered on top via [withAccent] to override `primary`,
 * `primaryForeground`, and `ring`.
 *
 * ### Quick start
 * ```
 * // Use a base palette as-is (primary = gray)
 * RikkaTheme(colors = RikkaPalettes.ZincDark) { ... }
 *
 * // Apply an accent color on top
 * val colors = RikkaPalettes.ZincDark.withAccent(RikkaAccentDark.Blue)
 * RikkaTheme(colors = colors) { ... }
 * ```
 *
 * All hex values are taken directly from the shadcn/ui v4 source and
 * converted from HSL to sRGB hex.
 */
object RikkaPalettes {

    // ═══════════════════════════════════════════════
    //  Zinc — Cool, modern, blue-tinted gray
    // ═══════════════════════════════════════════════

    /** Zinc base palette, light variant. */
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
        ring = Color(0xFF09090B),
    )

    /** Zinc base palette, dark variant. */
    val ZincDark = RikkaColors(
        background = Color(0xFF09090B),
        foreground = Color(0xFFFAFAFA),
        card = Color(0xFF09090B),
        cardForeground = Color(0xFFFAFAFA),
        popover = Color(0xFF09090B),
        popoverForeground = Color(0xFFFAFAFA),
        primary = Color(0xFFFAFAFA),
        primaryForeground = Color(0xFF18181B),
        secondary = Color(0xFF27272A),
        secondaryForeground = Color(0xFFFAFAFA),
        muted = Color(0xFF27272A),
        mutedForeground = Color(0xFFA1A1AA),
        accent = Color(0xFF27272A),
        accentForeground = Color(0xFFFAFAFA),
        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAFA),
        border = Color(0xFF27272A),
        input = Color(0xFF27272A),
        ring = Color(0xFFD4D4D8),
    )

    // ═══════════════════════════════════════════════
    //  Slate — Sophisticated, blue-gray undertone
    // ═══════════════════════════════════════════════

    /** Slate base palette, light variant. Blue-gray undertone. */
    val SlateLight = RikkaColors(
        background = Color(0xFFF8FAFC),
        foreground = Color(0xFF020817),
        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF020817),
        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF020817),
        primary = Color(0xFF0F172A),
        primaryForeground = Color(0xFFF8FAFC),
        secondary = Color(0xFFE2E8F0),
        secondaryForeground = Color(0xFF0F172A),
        muted = Color(0xFFF1F5F9),
        mutedForeground = Color(0xFF64748B),
        accent = Color(0xFFE2E8F0),
        accentForeground = Color(0xFF0F172A),
        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFF8FAFC),
        border = Color(0xFFCBD5E1),
        input = Color(0xFFCBD5E1),
        ring = Color(0xFF020817),
    )

    /** Slate base palette, dark variant. */
    val SlateDark = RikkaColors(
        background = Color(0xFF020817),
        foreground = Color(0xFFF8FAFC),
        card = Color(0xFF020817),
        cardForeground = Color(0xFFF8FAFC),
        popover = Color(0xFF020817),
        popoverForeground = Color(0xFFF8FAFC),
        primary = Color(0xFFF8FAFC),
        primaryForeground = Color(0xFF0F172A),
        secondary = Color(0xFF1E293B),
        secondaryForeground = Color(0xFFF8FAFC),
        muted = Color(0xFF1E293B),
        mutedForeground = Color(0xFF94A3B8),
        accent = Color(0xFF1E293B),
        accentForeground = Color(0xFFF8FAFC),
        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFF8FAFC),
        border = Color(0xFF1E293B),
        input = Color(0xFF1E293B),
        ring = Color(0xFFCBD5E1),
    )

    // ═══════════════════════════════════════════════
    //  Stone — Warm, earthy, brown-tinted gray
    // ═══════════════════════════════════════════════

    /** Stone base palette, light variant. Warm, earthy undertone. */
    val StoneLight = RikkaColors(
        background = Color(0xFFFAFAF9),
        foreground = Color(0xFF0C0A09),
        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF0C0A09),
        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF0C0A09),
        primary = Color(0xFF1C1917),
        primaryForeground = Color(0xFFFAFAF9),
        secondary = Color(0xFFE7E5E4),
        secondaryForeground = Color(0xFF1C1917),
        muted = Color(0xFFF5F5F4),
        mutedForeground = Color(0xFF78716C),
        accent = Color(0xFFE7E5E4),
        accentForeground = Color(0xFF1C1917),
        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFFAFAF9),
        border = Color(0xFFD6D3D1),
        input = Color(0xFFD6D3D1),
        ring = Color(0xFF0C0A09),
    )

    /** Stone base palette, dark variant. */
    val StoneDark = RikkaColors(
        background = Color(0xFF0C0A09),
        foreground = Color(0xFFFAFAF9),
        card = Color(0xFF0C0A09),
        cardForeground = Color(0xFFFAFAF9),
        popover = Color(0xFF0C0A09),
        popoverForeground = Color(0xFFFAFAF9),
        primary = Color(0xFFFAFAF9),
        primaryForeground = Color(0xFF1C1917),
        secondary = Color(0xFF292524),
        secondaryForeground = Color(0xFFFAFAF9),
        muted = Color(0xFF292524),
        mutedForeground = Color(0xFFA8A29E),
        accent = Color(0xFF292524),
        accentForeground = Color(0xFFFAFAF9),
        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAF9),
        border = Color(0xFF292524),
        input = Color(0xFF292524),
        ring = Color(0xFFD6D3D1),
    )

    // ═══════════════════════════════════════════════
    //  Gray — Balanced, true gray with slight cool cast
    // ═══════════════════════════════════════════════

    /** Gray base palette, light variant. Balanced cool cast. */
    val GrayLight = RikkaColors(
        background = Color(0xFFF9FAFB),
        foreground = Color(0xFF030712),
        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF030712),
        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF030712),
        primary = Color(0xFF111827),
        primaryForeground = Color(0xFFF9FAFB),
        secondary = Color(0xFFE5E7EB),
        secondaryForeground = Color(0xFF111827),
        muted = Color(0xFFF3F4F6),
        mutedForeground = Color(0xFF6B7280),
        accent = Color(0xFFE5E7EB),
        accentForeground = Color(0xFF111827),
        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFF9FAFB),
        border = Color(0xFFD1D5DB),
        input = Color(0xFFD1D5DB),
        ring = Color(0xFF030712),
    )

    /** Gray base palette, dark variant. */
    val GrayDark = RikkaColors(
        background = Color(0xFF030712),
        foreground = Color(0xFFF9FAFB),
        card = Color(0xFF030712),
        cardForeground = Color(0xFFF9FAFB),
        popover = Color(0xFF030712),
        popoverForeground = Color(0xFFF9FAFB),
        primary = Color(0xFFF9FAFB),
        primaryForeground = Color(0xFF111827),
        secondary = Color(0xFF1F2937),
        secondaryForeground = Color(0xFFF9FAFB),
        muted = Color(0xFF1F2937),
        mutedForeground = Color(0xFF9CA3AF),
        accent = Color(0xFF1F2937),
        accentForeground = Color(0xFFF9FAFB),
        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFF9FAFB),
        border = Color(0xFF1F2937),
        input = Color(0xFF1F2937),
        ring = Color(0xFFD1D5DB),
    )

    // ═══════════════════════════════════════════════
    //  Neutral — Pure achromatic, no hue
    // ═══════════════════════════════════════════════

    /** Neutral base palette, light variant. Pure achromatic. */
    val NeutralLight = RikkaColors(
        background = Color(0xFFFAFAFA),
        foreground = Color(0xFF0A0A0A),
        card = Color(0xFFFFFFFF),
        cardForeground = Color(0xFF0A0A0A),
        popover = Color(0xFFFFFFFF),
        popoverForeground = Color(0xFF0A0A0A),
        primary = Color(0xFF171717),
        primaryForeground = Color(0xFFFAFAFA),
        secondary = Color(0xFFE5E5E5),
        secondaryForeground = Color(0xFF171717),
        muted = Color(0xFFF5F5F5),
        mutedForeground = Color(0xFF737373),
        accent = Color(0xFFE5E5E5),
        accentForeground = Color(0xFF171717),
        destructive = Color(0xFFEF4444),
        destructiveForeground = Color(0xFFFAFAFA),
        border = Color(0xFFD4D4D4),
        input = Color(0xFFD4D4D4),
        ring = Color(0xFF0A0A0A),
    )

    /** Neutral base palette, dark variant. */
    val NeutralDark = RikkaColors(
        background = Color(0xFF0A0A0A),
        foreground = Color(0xFFFAFAFA),
        card = Color(0xFF0A0A0A),
        cardForeground = Color(0xFFFAFAFA),
        popover = Color(0xFF0A0A0A),
        popoverForeground = Color(0xFFFAFAFA),
        primary = Color(0xFFFAFAFA),
        primaryForeground = Color(0xFF171717),
        secondary = Color(0xFF262626),
        secondaryForeground = Color(0xFFFAFAFA),
        muted = Color(0xFF262626),
        mutedForeground = Color(0xFFA3A3A3),
        accent = Color(0xFF262626),
        accentForeground = Color(0xFFFAFAFA),
        destructive = Color(0xFFDC2626),
        destructiveForeground = Color(0xFFFAFAFA),
        border = Color(0xFF262626),
        input = Color(0xFF262626),
        ring = Color(0xFFD4D4D4),
    )
}

/**
 * Defines an accent color override for the primary action tokens.
 *
 * Accent colors are applied on top of a base palette to give the theme
 * a distinctive primary color while keeping all other tokens (background,
 * secondary, muted, destructive, border, etc.) from the base palette.
 *
 * @property primary The primary action color (buttons, links, active states).
 * @property primaryForeground Text/icon color that sits on the [primary] surface.
 * @property ring Focus ring color, typically matching [primary].
 *
 * @see RikkaAccent for light-mode accent presets.
 * @see RikkaAccentDark for dark-mode accent presets.
 * @see withAccent to apply an accent to a [RikkaColors] instance.
 */
data class RikkaAccentColor(
    val primary: Color,
    val primaryForeground: Color,
    val ring: Color,
)

/**
 * Accent color presets for **light** base palettes.
 *
 * These map directly to shadcn/ui v4's accent color options.
 * Each accent overrides `primary`, `primaryForeground`, and `ring`.
 *
 * ### Usage
 * ```
 * val colors = RikkaPalettes.ZincLight.withAccent(RikkaAccent.Blue)
 * RikkaTheme(colors = colors) { ... }
 * ```
 */
object RikkaAccent {

    /** Vibrant red accent. */
    val Red = RikkaAccentColor(
        primary = Color(0xFFDC2626),
        primaryForeground = Color(0xFFFAFAFA),
        ring = Color(0xFFDC2626),
    )

    /** Warm rose/pink accent. */
    val Rose = RikkaAccentColor(
        primary = Color(0xFFE11D48),
        primaryForeground = Color(0xFFFFF1F2),
        ring = Color(0xFFE11D48),
    )

    /** Energetic orange accent. */
    val Orange = RikkaAccentColor(
        primary = Color(0xFFF97316),
        primaryForeground = Color(0xFFFAFAFA),
        ring = Color(0xFFF97316),
    )

    /** Fresh green accent. */
    val Green = RikkaAccentColor(
        primary = Color(0xFF16A34A),
        primaryForeground = Color(0xFFFAFAFA),
        ring = Color(0xFF16A34A),
    )

    /** Classic blue accent. */
    val Blue = RikkaAccentColor(
        primary = Color(0xFF2563EB),
        primaryForeground = Color(0xFFEFF6FF),
        ring = Color(0xFF2563EB),
    )

    /** Sunny yellow accent. */
    val Yellow = RikkaAccentColor(
        primary = Color(0xFFFACC15),
        primaryForeground = Color(0xFF422006),
        ring = Color(0xFFFACC15),
    )

    /** Rich violet/purple accent. */
    val Violet = RikkaAccentColor(
        primary = Color(0xFF7C3AED),
        primaryForeground = Color(0xFFF5F3FF),
        ring = Color(0xFF7C3AED),
    )
}

/**
 * Accent color presets for **dark** base palettes.
 *
 * Some accent colors use different values in dark mode for better
 * contrast and readability on dark surfaces.
 *
 * ### Usage
 * ```
 * val colors = RikkaPalettes.ZincDark.withAccent(RikkaAccentDark.Blue)
 * RikkaTheme(colors = colors) { ... }
 * ```
 */
object RikkaAccentDark {

    /** Vibrant red accent (dark mode). */
    val Red = RikkaAccentColor(
        primary = Color(0xFFDC2626),
        primaryForeground = Color(0xFFFAFAFA),
        ring = Color(0xFFDC2626),
    )

    /** Warm rose/pink accent (dark mode). */
    val Rose = RikkaAccentColor(
        primary = Color(0xFFE11D48),
        primaryForeground = Color(0xFFFFF1F2),
        ring = Color(0xFFE11D48),
    )

    /** Energetic orange accent (dark mode, slightly deeper). */
    val Orange = RikkaAccentColor(
        primary = Color(0xFFEA580C),
        primaryForeground = Color(0xFFFAFAFA),
        ring = Color(0xFFEA580C),
    )

    /** Fresh green accent (dark mode, brighter for contrast). */
    val Green = RikkaAccentColor(
        primary = Color(0xFF22C55E),
        primaryForeground = Color(0xFF052E16),
        ring = Color(0xFF22C55E),
    )

    /** Classic blue accent (dark mode, brighter for contrast). */
    val Blue = RikkaAccentColor(
        primary = Color(0xFF3B82F6),
        primaryForeground = Color(0xFFEFF6FF),
        ring = Color(0xFF3B82F6),
    )

    /** Sunny yellow accent (dark mode). */
    val Yellow = RikkaAccentColor(
        primary = Color(0xFFFACC15),
        primaryForeground = Color(0xFF422006),
        ring = Color(0xFFFACC15),
    )

    /** Rich violet/purple accent (dark mode, slightly deeper). */
    val Violet = RikkaAccentColor(
        primary = Color(0xFF6D28D9),
        primaryForeground = Color(0xFFF5F3FF),
        ring = Color(0xFF6D28D9),
    )
}

/**
 * Applies an [accent] color override to this [RikkaColors] instance.
 *
 * This replaces [RikkaColors.primary], [RikkaColors.primaryForeground],
 * and [RikkaColors.ring] with the values from the given [RikkaAccentColor],
 * while keeping all other tokens unchanged.
 *
 * ### Example
 * ```
 * // Light theme with blue primary
 * val lightBlue = RikkaPalettes.SlateLight.withAccent(RikkaAccent.Blue)
 *
 * // Dark theme with violet primary
 * val darkViolet = RikkaPalettes.SlateDark.withAccent(RikkaAccentDark.Violet)
 * ```
 *
 * @param accent The accent color to apply.
 * @return A new [RikkaColors] with the accent's primary tokens applied.
 */
fun RikkaColors.withAccent(accent: RikkaAccentColor): RikkaColors = copy(
    primary = accent.primary,
    primaryForeground = accent.primaryForeground,
    ring = accent.ring,
)

/**
 * Type-safe enum for selecting a base color palette.
 *
 * Each entry maps to a light/dark pair in [RikkaPalettes].
 * Use [resolve] to obtain the corresponding [RikkaColors].
 *
 * ### Usage
 * ```
 * val colors = RikkaPalette.Zinc.resolve(isDark = true)
 * RikkaTheme(colors = colors) { ... }
 * ```
 */
enum class RikkaPalette(val label: String) {
    Zinc("Zinc"),
    Slate("Slate"),
    Stone("Stone"),
    Gray("Gray"),
    Neutral("Neutral"),
    ;

    /** Resolves to a [RikkaColors] for the given dark-mode flag. */
    fun resolve(isDark: Boolean): RikkaColors = when (this) {
        Zinc -> if (isDark) RikkaPalettes.ZincDark else RikkaPalettes.ZincLight
        Slate -> if (isDark) RikkaPalettes.SlateDark else RikkaPalettes.SlateLight
        Stone -> if (isDark) RikkaPalettes.StoneDark else RikkaPalettes.StoneLight
        Gray -> if (isDark) RikkaPalettes.GrayDark else RikkaPalettes.GrayLight
        Neutral -> if (isDark) RikkaPalettes.NeutralDark else RikkaPalettes.NeutralLight
    }
}

/**
 * Type-safe enum for selecting an accent color.
 *
 * [Default] means no accent override (use the palette's built-in primary).
 * All other entries map to light/dark pairs in [RikkaAccent] / [RikkaAccentDark].
 *
 * ### Usage
 * ```
 * val base = RikkaPalette.Zinc.resolve(isDark = true)
 * val colors = RikkaAccentPreset.Blue.applyTo(base, isDark = true)
 * RikkaTheme(colors = colors) { ... }
 * ```
 */
enum class RikkaAccentPreset(val label: String) {
    Default("Default"),
    Blue("Blue"),
    Green("Green"),
    Orange("Orange"),
    Red("Red"),
    Rose("Rose"),
    Violet("Violet"),
    Yellow("Yellow"),
    ;

    /**
     * Returns a preview swatch [Color] for this accent,
     * or `null` for [Default].
     */
    val previewColor: Color?
        get() = when (this) {
            Default -> null
            Red -> Color(0xFFDC2626)
            Rose -> Color(0xFFE11D48)
            Orange -> Color(0xFFF97316)
            Green -> Color(0xFF16A34A)
            Blue -> Color(0xFF2563EB)
            Yellow -> Color(0xFFFACC15)
            Violet -> Color(0xFF7C3AED)
        }

    /**
     * Applies this accent to a base [RikkaColors].
     * Returns the base unchanged for [Default].
     */
    fun applyTo(base: RikkaColors, isDark: Boolean): RikkaColors {
        if (this == Default) return base
        val accent = if (isDark) {
            when (this) {
                Red -> RikkaAccentDark.Red
                Rose -> RikkaAccentDark.Rose
                Orange -> RikkaAccentDark.Orange
                Green -> RikkaAccentDark.Green
                Blue -> RikkaAccentDark.Blue
                Yellow -> RikkaAccentDark.Yellow
                Violet -> RikkaAccentDark.Violet
                Default -> error("unreachable")
            }
        } else {
            when (this) {
                Red -> RikkaAccent.Red
                Rose -> RikkaAccent.Rose
                Orange -> RikkaAccent.Orange
                Green -> RikkaAccent.Green
                Blue -> RikkaAccent.Blue
                Yellow -> RikkaAccent.Yellow
                Violet -> RikkaAccent.Violet
                Default -> error("unreachable")
            }
        }
        return base.withAccent(accent)
    }
}
