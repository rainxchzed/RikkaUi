package zed.rainxch.rikkaui.creator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import zed.rainxch.rikkaui.components.theme.RikkaFontFamily
import zed.rainxch.rikkaui.components.theme.rememberRikkaFontFamily
import rikkaui.creator.generated.resources.Res
import rikkaui.creator.generated.resources.dm_sans_regular
import rikkaui.creator.generated.resources.inter_black
import rikkaui.creator.generated.resources.inter_bold
import rikkaui.creator.generated.resources.inter_light
import rikkaui.creator.generated.resources.inter_medium
import rikkaui.creator.generated.resources.inter_regular
import rikkaui.creator.generated.resources.inter_semi_bold
import rikkaui.creator.generated.resources.lato_bold
import rikkaui.creator.generated.resources.lato_regular
import rikkaui.creator.generated.resources.montserrat_regular
import rikkaui.creator.generated.resources.nunito_regular
import rikkaui.creator.generated.resources.open_sans_regular
import rikkaui.creator.generated.resources.plus_jakarta_sans_regular
import rikkaui.creator.generated.resources.poppins_bold
import rikkaui.creator.generated.resources.poppins_regular
import rikkaui.creator.generated.resources.raleway_regular
import rikkaui.creator.generated.resources.source_sans_3_regular
import rikkaui.creator.generated.resources.work_sans_regular

/**
 * Describes a font family available in the Creator.
 *
 * @param displayName Human-readable name shown in the picker.
 * @param id Internal identifier matching the resource file prefix.
 */
data class FontEntry(
    val displayName: String,
    val id: String,
)

/** All available font families for the Design System Creator. */
val availableFonts: List<FontEntry> = listOf(
    FontEntry("Inter", "inter"),
    FontEntry("DM Sans", "dm_sans"),
    FontEntry("Lato", "lato"),
    FontEntry("Montserrat", "montserrat"),
    FontEntry("Nunito", "nunito"),
    FontEntry("Open Sans", "open_sans"),
    FontEntry("Plus Jakarta Sans", "plus_jakarta_sans"),
    FontEntry("Poppins", "poppins"),
    FontEntry("Raleway", "raleway"),
    FontEntry("Source Sans 3", "source_sans_3"),
    FontEntry("Work Sans", "work_sans"),
)

/**
 * Resolves a [RikkaFontFamily] from a font [id] for the live preview.
 *
 * Inter uses all 6 weights. Other fonts use variable font files
 * (single .ttf containing all weights) for both regular and bold.
 */
@Composable
fun resolvePreviewFontFamily(id: String): RikkaFontFamily {
    // Inter has all 6 weight files
    if (id == "inter") {
        return rememberRikkaFontFamily(
            light = Res.font.inter_light,
            regular = Res.font.inter_regular,
            medium = Res.font.inter_medium,
            semiBold = Res.font.inter_semi_bold,
            bold = Res.font.inter_bold,
            extraBold = Res.font.inter_black,
        )
    }

    // Other fonts: use the available weight files.
    // Variable fonts contain all weights in a single file.
    val family = when (id) {
        "dm_sans" -> FontFamily(
            Font(Res.font.dm_sans_regular, FontWeight.Normal),
            Font(Res.font.dm_sans_regular, FontWeight.Bold),
        )
        "lato" -> FontFamily(
            Font(Res.font.lato_regular, FontWeight.Normal),
            Font(Res.font.lato_bold, FontWeight.Bold),
        )
        "montserrat" -> FontFamily(
            Font(Res.font.montserrat_regular, FontWeight.Normal),
            Font(Res.font.montserrat_regular, FontWeight.Bold),
        )
        "nunito" -> FontFamily(
            Font(Res.font.nunito_regular, FontWeight.Normal),
            Font(Res.font.nunito_regular, FontWeight.Bold),
        )
        "open_sans" -> FontFamily(
            Font(Res.font.open_sans_regular, FontWeight.Normal),
            Font(Res.font.open_sans_regular, FontWeight.Bold),
        )
        "plus_jakarta_sans" -> FontFamily(
            Font(
                Res.font.plus_jakarta_sans_regular,
                FontWeight.Normal,
            ),
            Font(
                Res.font.plus_jakarta_sans_regular,
                FontWeight.Bold,
            ),
        )
        "poppins" -> FontFamily(
            Font(Res.font.poppins_regular, FontWeight.Normal),
            Font(Res.font.poppins_bold, FontWeight.Bold),
        )
        "raleway" -> FontFamily(
            Font(Res.font.raleway_regular, FontWeight.Normal),
            Font(Res.font.raleway_regular, FontWeight.Bold),
        )
        "source_sans_3" -> FontFamily(
            Font(Res.font.source_sans_3_regular, FontWeight.Normal),
            Font(Res.font.source_sans_3_regular, FontWeight.Bold),
        )
        "work_sans" -> FontFamily(
            Font(Res.font.work_sans_regular, FontWeight.Normal),
            Font(Res.font.work_sans_regular, FontWeight.Bold),
        )
        else -> FontFamily(
            Font(Res.font.inter_regular, FontWeight.Normal),
            Font(Res.font.inter_bold, FontWeight.Bold),
        )
    }
    return remember(id) { RikkaFontFamily(fontFamily = family) }
}
