package zed.rainxch.rikkaui.creator.fonts

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.preloadFont
import rikkaui.feature.creator.generated.resources.*

@OptIn(ExperimentalResourceApi::class)
@Composable
fun preloadAllCreatorFonts(): Boolean {
    // ── Inter ──
    val interL =
        preloadFont(
            Res.font.inter_light,
            FontWeight.Light,
        ).value
    val interR =
        preloadFont(
            Res.font.inter_regular,
            FontWeight.Normal,
        ).value
    val interM =
        preloadFont(
            Res.font.inter_medium,
            FontWeight.Medium,
        ).value
    val interSb =
        preloadFont(
            Res.font.inter_semi_bold,
            FontWeight.SemiBold,
        ).value
    val interB =
        preloadFont(
            Res.font.inter_bold,
            FontWeight.Bold,
        ).value
    val interBl =
        preloadFont(
            Res.font.inter_black,
            FontWeight.ExtraBold,
        ).value

    // ── DM Sans ──
    val dmL =
        preloadFont(
            Res.font.dm_sans_light,
            FontWeight.Light,
        ).value
    val dmR =
        preloadFont(
            Res.font.dm_sans_regular,
            FontWeight.Normal,
        ).value
    val dmM =
        preloadFont(
            Res.font.dm_sans_medium,
            FontWeight.Medium,
        ).value
    val dmSb =
        preloadFont(
            Res.font.dm_sans_semi_bold,
            FontWeight.SemiBold,
        ).value
    val dmB =
        preloadFont(
            Res.font.dm_sans_bold,
            FontWeight.Bold,
        ).value
    val dmBl =
        preloadFont(
            Res.font.dm_sans_black,
            FontWeight.ExtraBold,
        ).value

    // ── Lato ──
    val latoL =
        preloadFont(
            Res.font.lato_light,
            FontWeight.Light,
        ).value
    val latoR =
        preloadFont(
            Res.font.lato_regular,
            FontWeight.Normal,
        ).value
    val latoB =
        preloadFont(
            Res.font.lato_bold,
            FontWeight.Bold,
        ).value
    val latoBl =
        preloadFont(
            Res.font.lato_black,
            FontWeight.ExtraBold,
        ).value

    // ── Montserrat ──
    val montL =
        preloadFont(
            Res.font.montserrat_light,
            FontWeight.Light,
        ).value
    val montR =
        preloadFont(
            Res.font.montserrat_regular,
            FontWeight.Normal,
        ).value
    val montM =
        preloadFont(
            Res.font.montserrat_medium,
            FontWeight.Medium,
        ).value
    val montSb =
        preloadFont(
            Res.font.montserrat_semi_bold,
            FontWeight.SemiBold,
        ).value
    val montB =
        preloadFont(
            Res.font.montserrat_bold,
            FontWeight.Bold,
        ).value
    val montBl =
        preloadFont(
            Res.font.montserrat_black,
            FontWeight.ExtraBold,
        ).value

    // ── Nunito ──
    val nunL =
        preloadFont(
            Res.font.nunito_light,
            FontWeight.Light,
        ).value
    val nunR =
        preloadFont(
            Res.font.nunito_regular,
            FontWeight.Normal,
        ).value
    val nunM =
        preloadFont(
            Res.font.nunito_medium,
            FontWeight.Medium,
        ).value
    val nunSb =
        preloadFont(
            Res.font.nunito_semi_bold,
            FontWeight.SemiBold,
        ).value
    val nunB =
        preloadFont(
            Res.font.nunito_bold,
            FontWeight.Bold,
        ).value
    val nunBl =
        preloadFont(
            Res.font.nunito_black,
            FontWeight.ExtraBold,
        ).value

    // ── Open Sans ──
    val osL =
        preloadFont(
            Res.font.open_sans_light,
            FontWeight.Light,
        ).value
    val osR =
        preloadFont(
            Res.font.open_sans_regular,
            FontWeight.Normal,
        ).value
    val osM =
        preloadFont(
            Res.font.open_sans_medium,
            FontWeight.Medium,
        ).value
    val osSb =
        preloadFont(
            Res.font.open_sans_semi_bold,
            FontWeight.SemiBold,
        ).value
    val osB =
        preloadFont(
            Res.font.open_sans_bold,
            FontWeight.Bold,
        ).value

    // ── Plus Jakarta Sans ──
    val pjL =
        preloadFont(
            Res.font.plus_jakarta_sans_light,
            FontWeight.Light,
        ).value
    val pjR =
        preloadFont(
            Res.font.plus_jakarta_sans_regular,
            FontWeight.Normal,
        ).value
    val pjM =
        preloadFont(
            Res.font.plus_jakarta_sans_medium,
            FontWeight.Medium,
        ).value
    val pjSb =
        preloadFont(
            Res.font.plus_jakarta_sans_semi_bold,
            FontWeight.SemiBold,
        ).value
    val pjB =
        preloadFont(
            Res.font.plus_jakarta_sans_bold,
            FontWeight.Bold,
        ).value

    // ── Poppins ──
    val popL =
        preloadFont(
            Res.font.poppins_light,
            FontWeight.Light,
        ).value
    val popR =
        preloadFont(
            Res.font.poppins_regular,
            FontWeight.Normal,
        ).value
    val popM =
        preloadFont(
            Res.font.poppins_medium,
            FontWeight.Medium,
        ).value
    val popB =
        preloadFont(
            Res.font.poppins_bold,
            FontWeight.Bold,
        ).value
    val popBl =
        preloadFont(
            Res.font.poppins_black,
            FontWeight.ExtraBold,
        ).value

    // ── Raleway ──
    val ralL =
        preloadFont(
            Res.font.raleway_light,
            FontWeight.Light,
        ).value
    val ralR =
        preloadFont(
            Res.font.raleway_regular,
            FontWeight.Normal,
        ).value
    val ralM =
        preloadFont(
            Res.font.raleway_medium,
            FontWeight.Medium,
        ).value
    val ralSb =
        preloadFont(
            Res.font.raleway_semi_bold,
            FontWeight.SemiBold,
        ).value
    val ralB =
        preloadFont(
            Res.font.raleway_bold,
            FontWeight.Bold,
        ).value
    val ralBl =
        preloadFont(
            Res.font.raleway_black,
            FontWeight.ExtraBold,
        ).value

    // ── Source Sans 3 ──
    val ssL =
        preloadFont(
            Res.font.source_sans3_light,
            FontWeight.Light,
        ).value
    val ssM =
        preloadFont(
            Res.font.source_sans3_medium,
            FontWeight.Medium,
        ).value
    val ssSb =
        preloadFont(
            Res.font.source_sans3_semi_bold,
            FontWeight.SemiBold,
        ).value
    val ssB =
        preloadFont(
            Res.font.source_sans3_bold,
            FontWeight.Bold,
        ).value
    val ssBl =
        preloadFont(
            Res.font.source_sans3_black,
            FontWeight.ExtraBold,
        ).value

    // ── Work Sans ──
    val wsL =
        preloadFont(
            Res.font.work_sans_light,
            FontWeight.Light,
        ).value
    val wsR =
        preloadFont(
            Res.font.work_sans_regular,
            FontWeight.Normal,
        ).value
    val wsM =
        preloadFont(
            Res.font.work_sans_medium,
            FontWeight.Medium,
        ).value
    val wsSb =
        preloadFont(
            Res.font.work_sans_semi_bold,
            FontWeight.SemiBold,
        ).value
    val wsB =
        preloadFont(
            Res.font.work_sans_bold,
            FontWeight.Bold,
        ).value
    val wsBl =
        preloadFont(
            Res.font.work_sans_black,
            FontWeight.ExtraBold,
        ).value

    @Suppress("ComplexCondition")
    return interL != null &&
        interR != null &&
        interM != null &&
        interSb != null &&
        interB != null &&
        interBl != null &&
        dmL != null &&
        dmR != null &&
        dmM != null &&
        dmSb != null &&
        dmB != null &&
        dmBl != null &&
        latoL != null &&
        latoR != null &&
        latoB != null &&
        latoBl != null &&
        montL != null &&
        montR != null &&
        montM != null &&
        montSb != null &&
        montB != null &&
        montBl != null &&
        nunL != null &&
        nunR != null &&
        nunM != null &&
        nunSb != null &&
        nunB != null &&
        nunBl != null &&
        osL != null &&
        osR != null &&
        osM != null &&
        osSb != null &&
        osB != null &&
        pjL != null &&
        pjR != null &&
        pjM != null &&
        pjSb != null &&
        pjB != null &&
        popL != null &&
        popR != null &&
        popM != null &&
        popB != null &&
        popBl != null &&
        ralL != null &&
        ralR != null &&
        ralM != null &&
        ralSb != null &&
        ralB != null &&
        ralBl != null &&
        ssL != null &&
        ssM != null &&
        ssSb != null &&
        ssB != null &&
        ssBl != null &&
        wsL != null &&
        wsR != null &&
        wsM != null &&
        wsSb != null &&
        wsB != null &&
        wsBl != null
}
