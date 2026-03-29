package zed.rainxch.rikkaui.utils

import androidx.compose.runtime.Composable
import rikkaui.composeapp.generated.resources.Res
import rikkaui.composeapp.generated.resources.inter_black
import rikkaui.composeapp.generated.resources.inter_bold
import rikkaui.composeapp.generated.resources.inter_light
import rikkaui.composeapp.generated.resources.inter_medium
import rikkaui.composeapp.generated.resources.inter_regular
import rikkaui.composeapp.generated.resources.inter_semi_bold
import zed.rainxch.rikkaui.foundation.RikkaFontFamily
import zed.rainxch.rikkaui.foundation.rememberRikkaFontFamily

object ThemeUtils {
    @Composable
    fun getFontFamily(): RikkaFontFamily =
        rememberRikkaFontFamily(
            light = Res.font.inter_light,
            regular = Res.font.inter_regular,
            medium = Res.font.inter_medium,
            semiBold = Res.font.inter_semi_bold,
            bold = Res.font.inter_bold,
            extraBold = Res.font.inter_black,
        )
}
