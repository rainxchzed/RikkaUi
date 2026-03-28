package zed.rainxch.rikkaui.showcase

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class WindowSizeClass {
    Compact,
    Medium,
    Expanded,
    ;

    companion object {
        fun fromWidth(width: Dp): WindowSizeClass =
            when {
                width < 600.dp -> Compact
                width < 900.dp -> Medium
                else -> Expanded
            }
    }
}
