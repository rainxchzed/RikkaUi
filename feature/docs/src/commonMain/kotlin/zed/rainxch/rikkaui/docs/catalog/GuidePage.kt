package zed.rainxch.rikkaui.docs.catalog

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import rikkaui.feature.docs.generated.resources.Res
import rikkaui.feature.docs.generated.resources.guide_cli
import rikkaui.feature.docs.generated.resources.guide_installation
import rikkaui.feature.docs.generated.resources.guide_introduction
import rikkaui.feature.docs.generated.resources.guide_theming
import zed.rainxch.rikkaui.docs.pages.CliDoc
import zed.rainxch.rikkaui.docs.pages.InstallationDoc
import zed.rainxch.rikkaui.docs.pages.IntroductionDoc
import zed.rainxch.rikkaui.docs.pages.ThemingDoc

object GuideIds {
    const val INTRODUCTION = "introduction"
    const val INSTALLATION = "installation"
    const val THEMING = "theming"
    const val CLI = "cli"
}

data class GuidePage(
    val id: String,
    val nameRes: StringResource,
    val content: @Composable () -> Unit,
)

val guidePages =
    listOf(
        GuidePage(
            id = GuideIds.INTRODUCTION,
            nameRes = Res.string.guide_introduction,
            content = { IntroductionDoc() },
        ),
        GuidePage(
            id = GuideIds.INSTALLATION,
            nameRes = Res.string.guide_installation,
            content = { InstallationDoc() },
        ),
        GuidePage(
            id = GuideIds.THEMING,
            nameRes = Res.string.guide_theming,
            content = { ThemingDoc() },
        ),
        GuidePage(
            id = GuideIds.CLI,
            nameRes = Res.string.guide_cli,
            content = { CliDoc() },
        ),
    )

val guidePageIds = guidePages.map { it.id }.toSet()
