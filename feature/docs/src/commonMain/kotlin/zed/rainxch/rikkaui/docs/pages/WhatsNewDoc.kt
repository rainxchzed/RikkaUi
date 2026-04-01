package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.list.ListVariant
import zed.rainxch.rikkaui.components.ui.list.RikkaList
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * "What's New in 0.2.0" release notes documentation page.
 *
 * Summarises the major changes since 0.1.0 for developers
 * upgrading or evaluating RikkaUI.
 */
@Composable
fun WhatsNewDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.whats_new_title),
        description = stringResource(Res.string.whats_new_desc),
    )

    // ─── Color System Overhaul ──────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_colors)) {
        Text(
            text = stringResource(Res.string.whats_new_colors_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items = listOf(
                stringResource(Res.string.whats_new_colors_item_token_count),
                stringResource(Res.string.whats_new_colors_item_on_naming),
                stringResource(Res.string.whats_new_colors_item_hover_press),
                stringResource(Res.string.whats_new_colors_item_tinted),
                stringResource(Res.string.whats_new_colors_item_tailwind),
            ),
            variant = ListVariant.Unordered,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        CodeBlock(
            """
// Before (0.1.0)
RikkaTheme.colors.foreground
RikkaTheme.colors.card
RikkaTheme.colors.primaryForeground

// After (0.2.0)
RikkaTheme.colors.onBackground
RikkaTheme.colors.surface
RikkaTheme.colors.onPrimary
            """.trimIndent(),
        )
    }

    // ─── LocalContentColor ──────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_content_color)) {
        Text(
            text = stringResource(Res.string.whats_new_content_color_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        CodeBlock(
            """
Button(onClick = {}) {
    // Icon and Text auto-inherit the button's
    // foreground via LocalContentColor
    Icon(RikkaIcons.Send, "Send")
    Text("Submit")
}
            """.trimIndent(),
        )
    }

    // ─── Theme Foundation ───────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_foundation)) {
        Text(
            text = stringResource(Res.string.whats_new_foundation_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items = listOf(
                stringResource(Res.string.whats_new_foundation_item_motion),
                stringResource(Res.string.whats_new_foundation_item_elevation),
                stringResource(Res.string.whats_new_foundation_item_shapes),
                stringResource(Res.string.whats_new_foundation_item_typography),
                stringResource(Res.string.whats_new_foundation_item_touch),
                stringResource(Res.string.whats_new_foundation_item_focus_ring),
            ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Component Color Systems ────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_component_colors)) {
        Text(
            text = stringResource(Res.string.whats_new_component_colors_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        Row(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Badge(text = "Button", variant = BadgeVariant.Secondary)
            Badge(text = "Card", variant = BadgeVariant.Secondary)
            Badge(text = "Input", variant = BadgeVariant.Secondary)
            Badge(text = "Toggle", variant = BadgeVariant.Secondary)
        }

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        CodeBlock(
            """
Button(
    onClick = {},
    colors = ButtonDefaults.colors(
        ButtonVariant.Default,
    ),
)
            """.trimIndent(),
        )
    }

    // ─── Accessibility ──────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_a11y)) {
        Text(
            text = stringResource(Res.string.whats_new_a11y_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items = listOf(
                stringResource(Res.string.whats_new_a11y_item_roles),
                stringResource(Res.string.whats_new_a11y_item_touch),
                stringResource(Res.string.whats_new_a11y_item_staggered),
            ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── CLI Improvements ───────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_cli)) {
        Text(
            text = stringResource(Res.string.whats_new_cli_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items = listOf(
                stringResource(Res.string.whats_new_cli_item_browser),
                stringResource(Res.string.whats_new_cli_item_detection),
                stringResource(Res.string.whats_new_cli_item_tests),
            ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Website ────────────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_website)) {
        Text(
            text = stringResource(Res.string.whats_new_website_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items = listOf(
                stringResource(Res.string.whats_new_website_item_hash_routing),
                stringResource(Res.string.whats_new_website_item_catalog),
                stringResource(Res.string.whats_new_website_item_source_viewer),
                stringResource(Res.string.whats_new_website_item_creator),
                stringResource(Res.string.whats_new_website_item_responsive),
            ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Migration Guide ────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_migration)) {
        Text(
            text = stringResource(Res.string.whats_new_migration_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        MigrationTable()
    }
}

@Composable
private fun MigrationTable() {
    val renames = listOf(
        "foreground" to "onBackground",
        "card" to "surface",
        "cardForeground" to "onSurface",
        "primaryForeground" to "onPrimary",
        "secondaryForeground" to "onSecondary",
        "mutedForeground" to "onMuted",
        "destructiveForeground" to "onDestructive",
        "accentForeground" to "onPrimary",
        "popover" to "surface",
        "popoverForeground" to "onSurface",
        "input" to "border",
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                RikkaTheme.colors.surface,
                RikkaTheme.shapes.md,
            )
            .padding(RikkaTheme.spacing.md),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(Res.string.whats_new_migration_old),
                variant = TextVariant.Small,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = stringResource(Res.string.whats_new_migration_new),
                variant = TextVariant.Small,
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(Modifier.height(RikkaTheme.spacing.xs))
        Separator()
        Spacer(Modifier.height(RikkaTheme.spacing.xs))

        renames.forEachIndexed { index, (old, new) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = old,
                    variant = TextVariant.Muted,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = new,
                    variant = TextVariant.P,
                    modifier = Modifier.weight(1f),
                )
            }
            if (index < renames.lastIndex) {
                Spacer(Modifier.height(RikkaTheme.spacing.xs))
            }
        }
    }
}
