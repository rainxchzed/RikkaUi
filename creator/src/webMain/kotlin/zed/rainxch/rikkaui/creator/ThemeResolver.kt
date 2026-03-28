package zed.rainxch.rikkaui.creator

import androidx.compose.ui.graphics.Color
import zed.rainxch.rikkaui.components.theme.RikkaAccent
import zed.rainxch.rikkaui.components.theme.RikkaAccentDark
import zed.rainxch.rikkaui.components.theme.RikkaColors
import zed.rainxch.rikkaui.components.theme.RikkaPalettes
import zed.rainxch.rikkaui.components.theme.withAccent

/** Available palette names. */
internal val paletteNames = listOf(
    "Zinc", "Slate", "Stone", "Gray", "Neutral",
)

/** Available accent names (including "Default" for no accent). */
internal val accentNames = listOf(
    "Default", "Blue", "Green", "Orange",
    "Red", "Rose", "Violet", "Yellow",
)

/** Resolves a [RikkaColors] palette by name and dark-mode flag. */
internal fun resolvePalette(
    name: String,
    isDark: Boolean,
): RikkaColors =
    when (name) {
        "Neutral" ->
            if (isDark) RikkaPalettes.NeutralDark else RikkaPalettes.NeutralLight
        "Slate" ->
            if (isDark) RikkaPalettes.SlateDark else RikkaPalettes.SlateLight
        "Stone" ->
            if (isDark) RikkaPalettes.StoneDark else RikkaPalettes.StoneLight
        "Gray" ->
            if (isDark) RikkaPalettes.GrayDark else RikkaPalettes.GrayLight
        else ->
            if (isDark) RikkaPalettes.ZincDark else RikkaPalettes.ZincLight
    }

/** Applies an accent color overlay to a base palette. */
internal fun resolveAccent(
    base: RikkaColors,
    accentName: String,
    isDark: Boolean,
): RikkaColors {
    if (accentName == "Default") return base
    val accent =
        if (isDark) {
            when (accentName) {
                "Red" -> RikkaAccentDark.Red
                "Rose" -> RikkaAccentDark.Rose
                "Orange" -> RikkaAccentDark.Orange
                "Green" -> RikkaAccentDark.Green
                "Blue" -> RikkaAccentDark.Blue
                "Yellow" -> RikkaAccentDark.Yellow
                "Violet" -> RikkaAccentDark.Violet
                else -> return base
            }
        } else {
            when (accentName) {
                "Red" -> RikkaAccent.Red
                "Rose" -> RikkaAccent.Rose
                "Orange" -> RikkaAccent.Orange
                "Green" -> RikkaAccent.Green
                "Blue" -> RikkaAccent.Blue
                "Yellow" -> RikkaAccent.Yellow
                "Violet" -> RikkaAccent.Violet
                else -> return base
            }
        }
    return base.withAccent(accent)
}

/**
 * Returns a preview swatch color for an accent name,
 * or `null` for "Default" (no swatch needed).
 */
internal fun accentPreviewColor(name: String): Color? =
    when (name) {
        "Red" -> Color(0xFFDC2626)
        "Rose" -> Color(0xFFE11D48)
        "Orange" -> Color(0xFFF97316)
        "Green" -> Color(0xFF16A34A)
        "Blue" -> Color(0xFF2563EB)
        "Yellow" -> Color(0xFFFACC15)
        "Violet" -> Color(0xFF7C3AED)
        else -> null
    }
