package zed.rainxch.rikkaui.creator.fonts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import rikkaui.feature.creator.generated.resources.*
import zed.rainxch.rikkaui.foundation.RikkaFontFamily
import zed.rainxch.rikkaui.foundation.rememberRikkaFontFamily

/**
 * Describes a font family available in the Creator.
 *
 * @param displayName Human-readable name shown in the picker.
 * @param id Internal identifier matching the resource file prefix.
 * @param weights List of weight suffixes available for ZIP download.
 */
data class FontEntry(
    val displayName: String,
    val id: String,
    val weights: List<String>,
)

/** Standard 6-weight set used by most fonts. */
private val FULL_WEIGHTS =
    listOf(
        "light",
        "regular",
        "medium",
        "semi_bold",
        "bold",
        "black",
    )

/** All available font families for the Design System Creator. */
val availableFonts: List<FontEntry> =
    listOf(
        FontEntry("Inter", "inter", FULL_WEIGHTS),
        FontEntry("DM Sans", "dm_sans", FULL_WEIGHTS),
        FontEntry(
            "Lato",
            "lato",
            listOf("light", "regular", "bold", "black"),
        ),
        FontEntry("Montserrat", "montserrat", FULL_WEIGHTS),
        FontEntry("Nunito", "nunito", FULL_WEIGHTS),
        FontEntry(
            "Open Sans",
            "open_sans",
            listOf(
                "light",
                "regular",
                "medium",
                "semi_bold",
                "bold",
            ),
        ),
        FontEntry(
            "Plus Jakarta Sans",
            "plus_jakarta_sans",
            listOf(
                "light",
                "regular",
                "medium",
                "semi_bold",
                "bold",
            ),
        ),
        FontEntry(
            "Poppins",
            "poppins",
            listOf(
                "light",
                "regular",
                "medium",
                "bold",
                "black",
            ),
        ),
        FontEntry("Raleway", "raleway", FULL_WEIGHTS),
        FontEntry(
            "Source Sans 3",
            "source_sans3",
            listOf(
                "light",
                "medium",
                "semi_bold",
                "bold",
                "black",
            ),
        ),
        FontEntry("Work Sans", "work_sans", FULL_WEIGHTS),
    )

/**
 * Resolves a [RikkaFontFamily] from a font [id] for the
 * live preview. Uses all available weights per font.
 */
@Composable
fun resolvePreviewFontFamily(id: String): RikkaFontFamily {
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

    val family =
        when (id) {
            "dm_sans" -> {
                FontFamily(
                    Font(Res.font.dm_sans_light, FontWeight.Light),
                    Font(Res.font.dm_sans_regular, FontWeight.Normal),
                    Font(Res.font.dm_sans_medium, FontWeight.Medium),
                    Font(Res.font.dm_sans_semi_bold, FontWeight.SemiBold),
                    Font(Res.font.dm_sans_bold, FontWeight.Bold),
                    Font(Res.font.dm_sans_black, FontWeight.ExtraBold),
                )
            }

            "lato" -> {
                FontFamily(
                    Font(Res.font.lato_light, FontWeight.Light),
                    Font(Res.font.lato_regular, FontWeight.Normal),
                    Font(Res.font.lato_regular, FontWeight.Medium),
                    Font(Res.font.lato_bold, FontWeight.SemiBold),
                    Font(Res.font.lato_bold, FontWeight.Bold),
                    Font(Res.font.lato_black, FontWeight.ExtraBold),
                )
            }

            "montserrat" -> {
                FontFamily(
                    Font(Res.font.montserrat_light, FontWeight.Light),
                    Font(Res.font.montserrat_regular, FontWeight.Normal),
                    Font(Res.font.montserrat_medium, FontWeight.Medium),
                    Font(
                        Res.font.montserrat_semi_bold,
                        FontWeight.SemiBold,
                    ),
                    Font(Res.font.montserrat_bold, FontWeight.Bold),
                    Font(Res.font.montserrat_black, FontWeight.ExtraBold),
                )
            }

            "nunito" -> {
                FontFamily(
                    Font(Res.font.nunito_light, FontWeight.Light),
                    Font(Res.font.nunito_regular, FontWeight.Normal),
                    Font(Res.font.nunito_medium, FontWeight.Medium),
                    Font(Res.font.nunito_semi_bold, FontWeight.SemiBold),
                    Font(Res.font.nunito_bold, FontWeight.Bold),
                    Font(Res.font.nunito_black, FontWeight.ExtraBold),
                )
            }

            "open_sans" -> {
                FontFamily(
                    Font(Res.font.open_sans_light, FontWeight.Light),
                    Font(Res.font.open_sans_regular, FontWeight.Normal),
                    Font(Res.font.open_sans_medium, FontWeight.Medium),
                    Font(
                        Res.font.open_sans_semi_bold,
                        FontWeight.SemiBold,
                    ),
                    Font(Res.font.open_sans_bold, FontWeight.Bold),
                )
            }

            "plus_jakarta_sans" -> {
                FontFamily(
                    Font(
                        Res.font.plus_jakarta_sans_light,
                        FontWeight.Light,
                    ),
                    Font(
                        Res.font.plus_jakarta_sans_regular,
                        FontWeight.Normal,
                    ),
                    Font(
                        Res.font.plus_jakarta_sans_medium,
                        FontWeight.Medium,
                    ),
                    Font(
                        Res.font.plus_jakarta_sans_semi_bold,
                        FontWeight.SemiBold,
                    ),
                    Font(
                        Res.font.plus_jakarta_sans_bold,
                        FontWeight.Bold,
                    ),
                )
            }

            "poppins" -> {
                FontFamily(
                    Font(Res.font.poppins_light, FontWeight.Light),
                    Font(Res.font.poppins_regular, FontWeight.Normal),
                    Font(Res.font.poppins_medium, FontWeight.Medium),
                    Font(Res.font.poppins_bold, FontWeight.Bold),
                    Font(Res.font.poppins_black, FontWeight.ExtraBold),
                )
            }

            "raleway" -> {
                FontFamily(
                    Font(Res.font.raleway_light, FontWeight.Light),
                    Font(Res.font.raleway_regular, FontWeight.Normal),
                    Font(Res.font.raleway_medium, FontWeight.Medium),
                    Font(Res.font.raleway_semi_bold, FontWeight.SemiBold),
                    Font(Res.font.raleway_bold, FontWeight.Bold),
                    Font(Res.font.raleway_black, FontWeight.ExtraBold),
                )
            }

            "source_sans3" -> {
                FontFamily(
                    Font(Res.font.source_sans3_light, FontWeight.Light),
                    Font(Res.font.source_sans3_light, FontWeight.Normal),
                    Font(Res.font.source_sans3_medium, FontWeight.Medium),
                    Font(
                        Res.font.source_sans3_semi_bold,
                        FontWeight.SemiBold,
                    ),
                    Font(Res.font.source_sans3_bold, FontWeight.Bold),
                    Font(Res.font.source_sans3_black, FontWeight.ExtraBold),
                )
            }

            "work_sans" -> {
                FontFamily(
                    Font(Res.font.work_sans_light, FontWeight.Light),
                    Font(Res.font.work_sans_regular, FontWeight.Normal),
                    Font(Res.font.work_sans_medium, FontWeight.Medium),
                    Font(
                        Res.font.work_sans_semi_bold,
                        FontWeight.SemiBold,
                    ),
                    Font(Res.font.work_sans_bold, FontWeight.Bold),
                    Font(Res.font.work_sans_black, FontWeight.ExtraBold),
                )
            }

            else -> {
                FontFamily(
                    Font(Res.font.inter_regular, FontWeight.Normal),
                    Font(Res.font.inter_bold, FontWeight.Bold),
                )
            }
        }
    return remember(id) { RikkaFontFamily(fontFamily = family) }
}
