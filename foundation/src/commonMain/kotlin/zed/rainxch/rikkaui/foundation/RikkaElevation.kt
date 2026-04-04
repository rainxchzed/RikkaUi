package zed.rainxch.rikkaui.foundation

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * RikkaElevation defines the shadow elevation scale for the design system.
 *
 * Four levels from flat to prominent, used by cards, dialogs, sheets,
 * popovers, and FABs.
 *
 * ### Customization
 * ```
 * RikkaTheme(
 *     elevation = RikkaElevation(
 *         none = 0.dp,
 *         low = 1.dp,
 *         medium = 3.dp,
 *         high = 6.dp,
 *     ),
 * ) { ... }
 * ```
 */
@Immutable
public data class RikkaElevation(
    /** No shadow. Flat on surface. */
    val none: Dp = 0.dp,
    /** Subtle shadow — cards, low emphasis. */
    val low: Dp = 2.dp,
    /** Medium shadow — dropdowns, popovers. */
    val medium: Dp = 4.dp,
    /** Prominent shadow — dialogs, sheets, FABs. */
    val high: Dp = 8.dp,
)

public val LocalRikkaElevation: androidx.compose.runtime.ProvidableCompositionLocal<RikkaElevation> =
    staticCompositionLocalOf { RikkaElevation() }
