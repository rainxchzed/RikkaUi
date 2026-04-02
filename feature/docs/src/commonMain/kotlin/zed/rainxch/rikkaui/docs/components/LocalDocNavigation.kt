package zed.rainxch.rikkaui.docs.components

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Provides page navigation within the docs section.
 *
 * [ComponentFamily] reads this to navigate to related components
 * without threading callbacks through every doc page composable.
 *
 * Provided by [DocsScreen] wrapping the content area.
 */
val LocalDocNavigation =
    staticCompositionLocalOf<(String) -> Unit> { {} }
