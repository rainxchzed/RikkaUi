package zed.rainxch.rikkaui.components.ui.tabs

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── TabList ───────────────────────────────────────────────

/**
 * Horizontal container for [Tab] triggers.
 *
 * Renders a muted-background pill that holds tab items side by side,
 * matching shadcn/ui's TabsList.
 *
 * Usage:
 * ```
 * TabList {
 *     Tab(selected = index == 0, onClick = { index = 0 }, text = "Account")
 *     Tab(selected = index == 1, onClick = { index = 1 }, text = "Password")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content Tab triggers — typically [Tab] composables.
 */
@Composable
fun TabList(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val shape = RikkaTheme.shapes.md

    Row(
        modifier =
            modifier
                .background(RikkaTheme.colors.muted, shape)
                .clip(shape)
                .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        content()
    }
}

// ─── Tab ───────────────────────────────────────────────────

/**
 * Individual tab trigger inside a [TabList].
 *
 * Animates between selected and unselected states with smooth color
 * transitions driven by theme motion tokens.
 *
 * Usage:
 * ```
 * var selectedIndex by remember { mutableStateOf(0) }
 *
 * TabList {
 *     Tab(
 *         selected = selectedIndex == 0,
 *         onClick = { selectedIndex = 0 },
 *         text = "Overview",
 *     )
 *     Tab(
 *         selected = selectedIndex == 1,
 *         onClick = { selectedIndex = 1 },
 *         text = "Analytics",
 *     )
 * }
 * ```
 *
 * @param selected Whether this tab is currently active.
 * @param onClick Called when the tab is clicked.
 * @param text Label text displayed in the tab.
 * @param modifier Modifier for layout and decoration.
 */
@Composable
fun Tab(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val colors = RikkaTheme.colors
    val motion = RikkaTheme.motion
    val shape = RikkaTheme.shapes.sm

    // ─── Animated colors (from theme motion tokens) ──────
    val backgroundColor by animateColorAsState(
        targetValue =
            if (selected) colors.background else colors.muted,
        animationSpec = tween(motion.durationDefault),
    )

    val textColor by animateColorAsState(
        targetValue =
            if (selected) colors.foreground else colors.mutedForeground,
        animationSpec = tween(motion.durationDefault),
    )

    // ─── Shadow modifier for selected state ──────────────
    val shadowModifier =
        if (selected) {
            Modifier.shadow(1.dp, shape)
        } else {
            Modifier
        }

    Box(
        modifier =
            modifier
                .then(shadowModifier)
                .background(backgroundColor, shape)
                .clip(shape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Tab,
                    onClick = onClick,
                )
                .padding(
                    horizontal = RikkaTheme.spacing.md,
                    vertical = RikkaTheme.spacing.sm,
                )
                .semantics {
                    contentDescription = text
                },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            variant = TextVariant.Small,
            color = textColor,
        )
    }
}

// ─── TabContent ────────────────────────────────────────────

/**
 * Content panel displayed below a [TabList].
 *
 * A simple wrapper that adds top padding to separate the tab content
 * from the triggers.
 *
 * Usage:
 * ```
 * TabList { ... }
 *
 * TabContent {
 *     Text("Content for the selected tab.")
 * }
 * ```
 *
 * @param modifier Modifier for layout and decoration.
 * @param content The tab panel content.
 */
@Composable
fun TabContent(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .padding(top = RikkaTheme.spacing.md),
    ) {
        content()
    }
}
