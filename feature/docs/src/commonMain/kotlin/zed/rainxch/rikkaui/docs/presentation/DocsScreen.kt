package zed.rainxch.rikkaui.docs.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import zed.rainxch.rikkaui.foundation.modifier.keyboardScrollable
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.docs.catalog.ComponentRegistry
import zed.rainxch.rikkaui.docs.components.CompactSelector
import zed.rainxch.rikkaui.docs.components.DocsSidebar
import zed.rainxch.rikkaui.docs.components.LocalDocNavigation
import zed.rainxch.rikkaui.docs.components.PageContent
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun DocsScreen(
    state: DocsState,
    registry: ComponentRegistry,
    onSelect: (String) -> Unit,
) {
    CompositionLocalProvider(LocalDocNavigation provides onSelect) {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val isWide = maxWidth >= 800.dp

            if (isWide) {
                WideLayout(
                    state = state,
                    registry = registry,
                    onSelect = onSelect,
                )
            } else {
                CompactLayout(
                    state = state,
                    registry = registry,
                    onSelect = onSelect,
                )
            }
        }
    }
}

@Composable
private fun WideLayout(
    state: DocsState,
    registry: ComponentRegistry,
    onSelect: (String) -> Unit,
) {
    val sidebarScroll = rememberScrollState()
    val contentScroll = rememberScrollState()
    val scope = rememberCoroutineScope()
    val sidebarFocus = remember { FocusRequester() }
    val contentFocus = remember { FocusRequester() }

    Row(Modifier.fillMaxSize()) {
        DocsSidebar(
            grouped = registry.groupedByCategory(),
            selectedId = state.selectedId,
            onSelect = onSelect,
            modifier =
                Modifier
                    .width(240.dp)
                    .fillMaxHeight()
                    .keyboardScrollable(sidebarScroll, scope, sidebarFocus)
                    .verticalScroll(sidebarScroll)
                    .padding(
                        start = RikkaTheme.spacing.lg,
                        top = RikkaTheme.spacing.lg,
                        bottom = RikkaTheme.spacing.lg,
                        end = RikkaTheme.spacing.md,
                    ),
        )

        Box(
            Modifier
                .width(1.dp)
                .fillMaxHeight()
                .background(RikkaTheme.colors.border),
        )

        Column(
            modifier =
                Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .keyboardScrollable(contentScroll, scope, contentFocus)
                    .verticalScroll(contentScroll)
                    .padding(RikkaTheme.spacing.xl),
        ) {
            PageContent(
                selectedId = state.selectedId,
                registry = registry,
            )
        }
    }
}

@Composable
private fun CompactLayout(
    state: DocsState,
    registry: ComponentRegistry,
    onSelect: (String) -> Unit,
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .keyboardScrollable(scrollState, scope, focusRequester)
                .verticalScroll(scrollState)
                .padding(RikkaTheme.spacing.md),
    ) {
        CompactSelector(
            entries = registry.entries,
            selectedId = state.selectedId,
            onSelect = onSelect,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.lg))

        PageContent(
            selectedId = state.selectedId,
            registry = registry,
        )
    }
}
