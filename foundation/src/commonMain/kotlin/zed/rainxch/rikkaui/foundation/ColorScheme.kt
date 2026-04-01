package zed.rainxch.rikkaui.foundation

import androidx.compose.ui.graphics.Color

/**
 * Built-in color palettes for RikkaUI, matching shadcn/ui v4 exactly.
 *
 * shadcn v4 separates theming into **base palettes** (gray scale) and
 * **accent colors** (primary color override). Each base palette provides
 * light and dark variants with all semantic tokens. Accent colors can
 * then be layered on top via [withAccent] to override `primary`,
 * `onPrimary`, and `ring`.
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
    /** Zinc base palette, light variant. */
    val ZincLight =
        RikkaColors(
            background = Color(0xFFFFFFFF),
            onBackground = Color(0xFF09090B),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF09090B),
            primary = Color(0xFF18181B),
            onPrimary = Color(0xFFFAFAFA),
            secondary = Color(0xFFF4F4F5),
            onSecondary = Color(0xFF18181B),
            muted = Color(0xFFF4F4F5),
            onMuted = Color(0xFF71717A),
            destructive = Color(0xFFEF4444),
            onDestructive = Color(0xFFFAFAFA),
            border = Color(0xFFE4E4E7),
            borderSubtle = Color(0xFFF4F4F5),
            ring = Color(0xFF09090B),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFFFAFAFA),
            inverseSurface = Color(0xFF18181B),
            onInverseSurface = Color(0xFFFAFAFA),
            primaryTinted = Color(0xFFF4F4F5),
            onPrimaryTinted = Color(0xFF09090B),
            destructiveTinted = Color(0xFFFEE2E2),
            onDestructiveTinted = Color(0xFFDC2626),
            primaryHover = Color(0xFF27272A),
            primaryPressed = Color(0xFF3F3F46),
            destructiveHover = Color(0xFFDC2626),
            destructivePressed = Color(0xFFB91C1C),
            secondaryHover = Color(0xFFE4E4E7),
            secondaryPressed = Color(0xFFD4D4D8),
        )

    /** Zinc base palette, dark variant. */
    val ZincDark =
        RikkaColors(
            background = Color(0xFF09090B),
            onBackground = Color(0xFFFAFAFA),
            surface = Color(0xFF09090B),
            onSurface = Color(0xFFFAFAFA),
            primary = Color(0xFFFAFAFA),
            onPrimary = Color(0xFF18181B),
            secondary = Color(0xFF27272A),
            onSecondary = Color(0xFFFAFAFA),
            muted = Color(0xFF27272A),
            onMuted = Color(0xFFA1A1AA),
            destructive = Color(0xFFDC2626),
            onDestructive = Color(0xFFFAFAFA),
            border = Color(0xFF27272A),
            borderSubtle = Color(0xFF18181B),
            ring = Color(0xFFD4D4D8),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFF052E16),
            inverseSurface = Color(0xFFFAFAFA),
            onInverseSurface = Color(0xFF09090B),
            primaryTinted = Color(0xFF27272A),
            onPrimaryTinted = Color(0xFFFAFAFA),
            destructiveTinted = Color(0xFF450A0A),
            onDestructiveTinted = Color(0xFFFCA5A5),
            primaryHover = Color(0xFFE4E4E7),
            primaryPressed = Color(0xFFD4D4D8),
            destructiveHover = Color(0xFFEF4444),
            destructivePressed = Color(0xFFF87171),
            secondaryHover = Color(0xFF3F3F46),
            secondaryPressed = Color(0xFF52525B),
        )

    /** Slate base palette, light variant. Blue-gray undertone. */
    val SlateLight =
        RikkaColors(
            background = Color(0xFFF8FAFC),
            onBackground = Color(0xFF020817),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF020817),
            primary = Color(0xFF0F172A),
            onPrimary = Color(0xFFF8FAFC),
            secondary = Color(0xFFE2E8F0),
            onSecondary = Color(0xFF0F172A),
            muted = Color(0xFFF1F5F9),
            onMuted = Color(0xFF64748B),
            destructive = Color(0xFFEF4444),
            onDestructive = Color(0xFFF8FAFC),
            border = Color(0xFFCBD5E1),
            borderSubtle = Color(0xFFE2E8F0),
            ring = Color(0xFF020817),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFFFAFAFA),
            inverseSurface = Color(0xFF0F172A),
            onInverseSurface = Color(0xFFF8FAFC),
            primaryTinted = Color(0xFFF1F5F9),
            onPrimaryTinted = Color(0xFF020817),
            destructiveTinted = Color(0xFFFEE2E2),
            onDestructiveTinted = Color(0xFFDC2626),
            primaryHover = Color(0xFF1E293B),
            primaryPressed = Color(0xFF334155),
            destructiveHover = Color(0xFFDC2626),
            destructivePressed = Color(0xFFB91C1C),
            secondaryHover = Color(0xFFCBD5E1),
            secondaryPressed = Color(0xFF94A3B8),
        )

    /** Slate base palette, dark variant. */
    val SlateDark =
        RikkaColors(
            background = Color(0xFF020817),
            onBackground = Color(0xFFF8FAFC),
            surface = Color(0xFF020817),
            onSurface = Color(0xFFF8FAFC),
            primary = Color(0xFFF8FAFC),
            onPrimary = Color(0xFF0F172A),
            secondary = Color(0xFF1E293B),
            onSecondary = Color(0xFFF8FAFC),
            muted = Color(0xFF1E293B),
            onMuted = Color(0xFF94A3B8),
            destructive = Color(0xFFDC2626),
            onDestructive = Color(0xFFF8FAFC),
            border = Color(0xFF1E293B),
            borderSubtle = Color(0xFF0F172A),
            ring = Color(0xFFCBD5E1),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFF052E16),
            inverseSurface = Color(0xFFF8FAFC),
            onInverseSurface = Color(0xFF020817),
            primaryTinted = Color(0xFF1E293B),
            onPrimaryTinted = Color(0xFFF8FAFC),
            destructiveTinted = Color(0xFF450A0A),
            onDestructiveTinted = Color(0xFFFCA5A5),
            primaryHover = Color(0xFFE2E8F0),
            primaryPressed = Color(0xFFCBD5E1),
            destructiveHover = Color(0xFFEF4444),
            destructivePressed = Color(0xFFF87171),
            secondaryHover = Color(0xFF334155),
            secondaryPressed = Color(0xFF475569),
        )

    /** Stone base palette, light variant. Warm, earthy undertone. */
    val StoneLight =
        RikkaColors(
            background = Color(0xFFFAFAF9),
            onBackground = Color(0xFF0C0A09),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0C0A09),
            primary = Color(0xFF1C1917),
            onPrimary = Color(0xFFFAFAF9),
            secondary = Color(0xFFE7E5E4),
            onSecondary = Color(0xFF1C1917),
            muted = Color(0xFFF5F5F4),
            onMuted = Color(0xFF78716C),
            destructive = Color(0xFFEF4444),
            onDestructive = Color(0xFFFAFAF9),
            border = Color(0xFFD6D3D1),
            borderSubtle = Color(0xFFE7E5E4),
            ring = Color(0xFF0C0A09),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFFFAFAFA),
            inverseSurface = Color(0xFF1C1917),
            onInverseSurface = Color(0xFFFAFAF9),
            primaryTinted = Color(0xFFF5F5F4),
            onPrimaryTinted = Color(0xFF0C0A09),
            destructiveTinted = Color(0xFFFEE2E2),
            onDestructiveTinted = Color(0xFFDC2626),
            primaryHover = Color(0xFF292524),
            primaryPressed = Color(0xFF44403C),
            destructiveHover = Color(0xFFDC2626),
            destructivePressed = Color(0xFFB91C1C),
            secondaryHover = Color(0xFFD6D3D1),
            secondaryPressed = Color(0xFFA8A29E),
        )

    /** Stone base palette, dark variant. */
    val StoneDark =
        RikkaColors(
            background = Color(0xFF0C0A09),
            onBackground = Color(0xFFFAFAF9),
            surface = Color(0xFF0C0A09),
            onSurface = Color(0xFFFAFAF9),
            primary = Color(0xFFFAFAF9),
            onPrimary = Color(0xFF1C1917),
            secondary = Color(0xFF292524),
            onSecondary = Color(0xFFFAFAF9),
            muted = Color(0xFF292524),
            onMuted = Color(0xFFA8A29E),
            destructive = Color(0xFFDC2626),
            onDestructive = Color(0xFFFAFAF9),
            border = Color(0xFF292524),
            borderSubtle = Color(0xFF1C1917),
            ring = Color(0xFFD6D3D1),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFF052E16),
            inverseSurface = Color(0xFFFAFAF9),
            onInverseSurface = Color(0xFF0C0A09),
            primaryTinted = Color(0xFF292524),
            onPrimaryTinted = Color(0xFFFAFAF9),
            destructiveTinted = Color(0xFF450A0A),
            onDestructiveTinted = Color(0xFFFCA5A5),
            primaryHover = Color(0xFFE7E5E4),
            primaryPressed = Color(0xFFD6D3D1),
            destructiveHover = Color(0xFFEF4444),
            destructivePressed = Color(0xFFF87171),
            secondaryHover = Color(0xFF44403C),
            secondaryPressed = Color(0xFF57534E),
        )

    /** Gray base palette, light variant. Balanced cool cast. */
    val GrayLight =
        RikkaColors(
            background = Color(0xFFF9FAFB),
            onBackground = Color(0xFF030712),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF030712),
            primary = Color(0xFF111827),
            onPrimary = Color(0xFFF9FAFB),
            secondary = Color(0xFFE5E7EB),
            onSecondary = Color(0xFF111827),
            muted = Color(0xFFF3F4F6),
            onMuted = Color(0xFF6B7280),
            destructive = Color(0xFFEF4444),
            onDestructive = Color(0xFFF9FAFB),
            border = Color(0xFFD1D5DB),
            borderSubtle = Color(0xFFE5E7EB),
            ring = Color(0xFF030712),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFFFAFAFA),
            inverseSurface = Color(0xFF111827),
            onInverseSurface = Color(0xFFF9FAFB),
            primaryTinted = Color(0xFFF3F4F6),
            onPrimaryTinted = Color(0xFF030712),
            destructiveTinted = Color(0xFFFEE2E2),
            onDestructiveTinted = Color(0xFFDC2626),
            primaryHover = Color(0xFF1F2937),
            primaryPressed = Color(0xFF374151),
            destructiveHover = Color(0xFFDC2626),
            destructivePressed = Color(0xFFB91C1C),
            secondaryHover = Color(0xFFD1D5DB),
            secondaryPressed = Color(0xFF9CA3AF),
        )

    /** Gray base palette, dark variant. */
    val GrayDark =
        RikkaColors(
            background = Color(0xFF030712),
            onBackground = Color(0xFFF9FAFB),
            surface = Color(0xFF030712),
            onSurface = Color(0xFFF9FAFB),
            primary = Color(0xFFF9FAFB),
            onPrimary = Color(0xFF111827),
            secondary = Color(0xFF1F2937),
            onSecondary = Color(0xFFF9FAFB),
            muted = Color(0xFF1F2937),
            onMuted = Color(0xFF9CA3AF),
            destructive = Color(0xFFDC2626),
            onDestructive = Color(0xFFF9FAFB),
            border = Color(0xFF1F2937),
            borderSubtle = Color(0xFF111827),
            ring = Color(0xFFD1D5DB),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFF052E16),
            inverseSurface = Color(0xFFF9FAFB),
            onInverseSurface = Color(0xFF030712),
            primaryTinted = Color(0xFF1F2937),
            onPrimaryTinted = Color(0xFFF9FAFB),
            destructiveTinted = Color(0xFF450A0A),
            onDestructiveTinted = Color(0xFFFCA5A5),
            primaryHover = Color(0xFFE5E7EB),
            primaryPressed = Color(0xFFD1D5DB),
            destructiveHover = Color(0xFFEF4444),
            destructivePressed = Color(0xFFF87171),
            secondaryHover = Color(0xFF374151),
            secondaryPressed = Color(0xFF4B5563),
        )

    /** Neutral base palette, light variant. Pure achromatic. */
    val NeutralLight =
        RikkaColors(
            background = Color(0xFFFAFAFA),
            onBackground = Color(0xFF0A0A0A),
            surface = Color(0xFFFFFFFF),
            onSurface = Color(0xFF0A0A0A),
            primary = Color(0xFF171717),
            onPrimary = Color(0xFFFAFAFA),
            secondary = Color(0xFFE5E5E5),
            onSecondary = Color(0xFF171717),
            muted = Color(0xFFF5F5F5),
            onMuted = Color(0xFF737373),
            destructive = Color(0xFFEF4444),
            onDestructive = Color(0xFFFAFAFA),
            border = Color(0xFFD4D4D4),
            borderSubtle = Color(0xFFE5E5E5),
            ring = Color(0xFF0A0A0A),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFFFAFAFA),
            inverseSurface = Color(0xFF171717),
            onInverseSurface = Color(0xFFFAFAFA),
            primaryTinted = Color(0xFFF5F5F5),
            onPrimaryTinted = Color(0xFF0A0A0A),
            destructiveTinted = Color(0xFFFEE2E2),
            onDestructiveTinted = Color(0xFFDC2626),
            primaryHover = Color(0xFF262626),
            primaryPressed = Color(0xFF404040),
            destructiveHover = Color(0xFFDC2626),
            destructivePressed = Color(0xFFB91C1C),
            secondaryHover = Color(0xFFD4D4D4),
            secondaryPressed = Color(0xFFA3A3A3),
        )

    /** Neutral base palette, dark variant. */
    val NeutralDark =
        RikkaColors(
            background = Color(0xFF0A0A0A),
            onBackground = Color(0xFFFAFAFA),
            surface = Color(0xFF0A0A0A),
            onSurface = Color(0xFFFAFAFA),
            primary = Color(0xFFFAFAFA),
            onPrimary = Color(0xFF171717),
            secondary = Color(0xFF262626),
            onSecondary = Color(0xFFFAFAFA),
            muted = Color(0xFF262626),
            onMuted = Color(0xFFA3A3A3),
            destructive = Color(0xFFDC2626),
            onDestructive = Color(0xFFFAFAFA),
            border = Color(0xFF262626),
            borderSubtle = Color(0xFF171717),
            ring = Color(0xFFD4D4D4),
            warning = Color(0xFFF59E0B),
            onWarning = Color(0xFF422006),
            success = Color(0xFF22C55E),
            onSuccess = Color(0xFF052E16),
            inverseSurface = Color(0xFFFAFAFA),
            onInverseSurface = Color(0xFF0A0A0A),
            primaryTinted = Color(0xFF262626),
            onPrimaryTinted = Color(0xFFFAFAFA),
            destructiveTinted = Color(0xFF450A0A),
            onDestructiveTinted = Color(0xFFFCA5A5),
            primaryHover = Color(0xFFE5E5E5),
            primaryPressed = Color(0xFFD4D4D4),
            destructiveHover = Color(0xFFEF4444),
            destructivePressed = Color(0xFFF87171),
            secondaryHover = Color(0xFF404040),
            secondaryPressed = Color(0xFF525252),
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
 * @property onPrimary Text/icon color that sits on the [primary] surface.
 * @property ring Focus ring color, typically matching [primary].
 *
 * @see RikkaAccent for light-mode accent presets.
 * @see RikkaAccentDark for dark-mode accent presets.
 * @see withAccent to apply an accent to a [RikkaColors] instance.
 */
data class RikkaAccentColor(
    val primary: Color,
    val onPrimary: Color,
    val ring: Color,
    val primaryHover: Color = Color.Unspecified,
    val primaryPressed: Color = Color.Unspecified,
)

/**
 * Accent color presets for **light** base palettes.
 *
 * These map directly to shadcn/ui v4's accent color options.
 * Each accent overrides `primary`, `onPrimary`, and `ring`.
 *
 * ### Usage
 * ```
 * val colors = RikkaPalettes.ZincLight.withAccent(RikkaAccent.Blue)
 * RikkaTheme(colors = colors) { ... }
 * ```
 */
object RikkaAccent {
    /** Vibrant red accent. red-600 → hover red-700, press red-800 */
    val Red =
        RikkaAccentColor(
            primary = Color(0xFFDC2626),
            onPrimary = Color(0xFFFAFAFA),
            ring = Color(0xFFDC2626),
            primaryHover = Color(0xFFB91C1C),
            primaryPressed = Color(0xFF991B1B),
        )

    /** Warm rose/pink accent. rose-600 → hover rose-700, press rose-800 */
    val Rose =
        RikkaAccentColor(
            primary = Color(0xFFE11D48),
            onPrimary = Color(0xFFFFF1F2),
            ring = Color(0xFFE11D48),
            primaryHover = Color(0xFFBE123C),
            primaryPressed = Color(0xFF9F1239),
        )

    /** Energetic orange accent. orange-500 → hover orange-600, press orange-700 */
    val Orange =
        RikkaAccentColor(
            primary = Color(0xFFF97316),
            onPrimary = Color(0xFFFAFAFA),
            ring = Color(0xFFF97316),
            primaryHover = Color(0xFFEA580C),
            primaryPressed = Color(0xFFC2410C),
        )

    /** Fresh green accent. green-600 → hover green-700, press green-800 */
    val Green =
        RikkaAccentColor(
            primary = Color(0xFF16A34A),
            onPrimary = Color(0xFFFAFAFA),
            ring = Color(0xFF16A34A),
            primaryHover = Color(0xFF15803D),
            primaryPressed = Color(0xFF166534),
        )

    /** Classic blue accent. blue-600 → hover blue-700, press blue-800 */
    val Blue =
        RikkaAccentColor(
            primary = Color(0xFF2563EB),
            onPrimary = Color(0xFFEFF6FF),
            ring = Color(0xFF2563EB),
            primaryHover = Color(0xFF1D4ED8),
            primaryPressed = Color(0xFF1E40AF),
        )

    /** Sunny yellow accent. yellow-400 → hover yellow-500, press yellow-600 */
    val Yellow =
        RikkaAccentColor(
            primary = Color(0xFFFACC15),
            onPrimary = Color(0xFF422006),
            ring = Color(0xFFFACC15),
            primaryHover = Color(0xFFEAB308),
            primaryPressed = Color(0xFFCA8A04),
        )

    /** Rich violet/purple accent. violet-600 → hover violet-700, press violet-800 */
    val Violet =
        RikkaAccentColor(
            primary = Color(0xFF7C3AED),
            onPrimary = Color(0xFFF5F3FF),
            ring = Color(0xFF7C3AED),
            primaryHover = Color(0xFF6D28D9),
            primaryPressed = Color(0xFF5B21B6),
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
    val Red =
        RikkaAccentColor(
            primary = Color(0xFFDC2626),
            onPrimary = Color(0xFFFAFAFA),
            ring = Color(0xFFDC2626),
        )

    /** Warm rose/pink accent (dark mode). */
    val Rose =
        RikkaAccentColor(
            primary = Color(0xFFE11D48),
            onPrimary = Color(0xFFFFF1F2),
            ring = Color(0xFFE11D48),
        )

    /** Energetic orange accent (dark mode, slightly deeper). */
    val Orange =
        RikkaAccentColor(
            primary = Color(0xFFEA580C),
            onPrimary = Color(0xFFFAFAFA),
            ring = Color(0xFFEA580C),
        )

    /** Fresh green accent (dark mode, brighter for contrast). */
    val Green =
        RikkaAccentColor(
            primary = Color(0xFF22C55E),
            onPrimary = Color(0xFF052E16),
            ring = Color(0xFF22C55E),
        )

    /** Classic blue accent (dark mode, brighter for contrast). */
    val Blue =
        RikkaAccentColor(
            primary = Color(0xFF3B82F6),
            onPrimary = Color(0xFFEFF6FF),
            ring = Color(0xFF3B82F6),
        )

    /** Sunny yellow accent (dark mode). */
    val Yellow =
        RikkaAccentColor(
            primary = Color(0xFFFACC15),
            onPrimary = Color(0xFF422006),
            ring = Color(0xFFFACC15),
        )

    /** Rich violet/purple accent (dark mode, slightly deeper). */
    val Violet =
        RikkaAccentColor(
            primary = Color(0xFF6D28D9),
            onPrimary = Color(0xFFF5F3FF),
            ring = Color(0xFF6D28D9),
        )
}

/**
 * Applies an [accent] color override to this [RikkaColors] instance.
 *
 * This replaces [RikkaColors.primary], [RikkaColors.onPrimary],
 * and [RikkaColors.ring] with the values from the given [RikkaAccentColor],
 * while keeping all other tokens unchanged.
 *
 * ### Example
 * ```
 *
 * val lightBlue = RikkaPalettes.SlateLight.withAccent(RikkaAccent.Blue)
 *
 *
 * val darkViolet = RikkaPalettes.SlateDark.withAccent(RikkaAccentDark.Violet)
 * ```
 *
 * @param accent The accent color to apply.
 * @return A new [RikkaColors] with the accent's primary tokens applied.
 */
fun RikkaColors.withAccent(accent: RikkaAccentColor): RikkaColors =
    copy(
        primary = accent.primary,
        onPrimary = accent.onPrimary,
        ring = accent.ring,
        primaryHover = accent.primaryHover,
        primaryPressed = accent.primaryPressed,
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
enum class RikkaPalette(
    val label: String,
) {
    Zinc("Zinc"),
    Slate("Slate"),
    Stone("Stone"),
    Gray("Gray"),
    Neutral("Neutral"),
    ;

    /** Resolves to a [RikkaColors] for the given dark-mode flag. */
    fun resolve(isDark: Boolean): RikkaColors =
        when (this) {
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
enum class RikkaAccentPreset(
    val label: String,
) {
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
        get() =
            when (this) {
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
    fun applyTo(
        base: RikkaColors,
        isDark: Boolean,
    ): RikkaColors {
        if (this == Default) return base
        val accent =
            if (isDark) {
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
