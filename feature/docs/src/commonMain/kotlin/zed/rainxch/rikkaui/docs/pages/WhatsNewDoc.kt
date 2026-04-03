package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.list.ListVariant
import zed.rainxch.rikkaui.components.ui.list.RikkaList
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * "What's New in 0.3.0" release notes documentation page.
 *
 * Summarises the major changes since 0.2.0 for developers
 * upgrading or evaluating RikkaUI.
 */
@Composable
fun WhatsNewDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.whats_new_title),
        description = stringResource(Res.string.whats_new_desc),
    )

    // ─── Accessibility Overhaul ────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_a11y)) {
        Text(
            text = stringResource(Res.string.whats_new_a11y_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items =
                listOf(
                    stringResource(Res.string.whats_new_a11y_item_roles),
                    stringResource(Res.string.whats_new_a11y_item_collection),
                    stringResource(Res.string.whats_new_a11y_item_keyboard),
                    stringResource(Res.string.whats_new_a11y_item_live_region),
                    stringResource(Res.string.whats_new_a11y_item_touch),
                ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Distinct Color Palettes ───────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_palettes)) {
        Text(
            text = stringResource(Res.string.whats_new_palettes_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        Row(
            horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        ) {
            Badge(text = "Zinc", variant = BadgeVariant.Secondary)
            Badge(text = "Slate", variant = BadgeVariant.Secondary)
            Badge(text = "Stone", variant = BadgeVariant.Secondary)
            Badge(text = "Gray", variant = BadgeVariant.Secondary)
            Badge(text = "Neutral", variant = BadgeVariant.Secondary)
        }
    }

    // ─── Spacing & Layout Fixes ────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_spacing)) {
        Text(
            text = stringResource(Res.string.whats_new_spacing_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items =
                listOf(
                    stringResource(Res.string.whats_new_spacing_item_card),
                    stringResource(Res.string.whats_new_spacing_item_form),
                    stringResource(Res.string.whats_new_spacing_item_popup),
                    stringResource(Res.string.whats_new_spacing_item_menu),
                ),
            variant = ListVariant.Unordered,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        CodeBlock(
            """
// Popover now disables minTouchTarget for dense content
CompositionLocalProvider(
    LocalMinTouchTarget provides 0.dp,
) {
    // Buttons render at natural size here
}
            """.trimIndent(),
        )
    }

    // ─── New Components ────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_components)) {
        Text(
            text = stringResource(Res.string.whats_new_components_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        CodeBlock(
            """
Scaffold(
    fab = {
        Fab(
            onClick = { /* action */ },
            icon = { Icon(RikkaIcons.Plus, "Add") },
        )
    },
    fabPosition = FabPosition.End,
) { padding ->
    // content
}
            """.trimIndent(),
        )
    }

    // ─── Text & Typography Fixes ───────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_text)) {
        Text(
            text = stringResource(Res.string.whats_new_text_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items =
                listOf(
                    stringResource(Res.string.whats_new_text_item_merge),
                    stringResource(Res.string.whats_new_text_item_kbd),
                    stringResource(Res.string.whats_new_text_item_branding),
                ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Documentation ─────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_docs)) {
        Text(
            text = stringResource(Res.string.whats_new_docs_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items =
                listOf(
                    stringResource(Res.string.whats_new_docs_item_tabs),
                    stringResource(Res.string.whats_new_docs_item_preview),
                    stringResource(Res.string.whats_new_docs_item_search),
                    stringResource(Res.string.whats_new_docs_item_why),
                ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Example Cards Polish ──────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_examples)) {
        Text(
            text = stringResource(Res.string.whats_new_examples_body),
            variant = TextVariant.P,
        )

        Spacer(Modifier.height(RikkaTheme.spacing.sm))

        RikkaList(
            items =
                listOf(
                    stringResource(Res.string.whats_new_examples_item_home),
                    stringResource(Res.string.whats_new_examples_item_creator),
                ),
            variant = ListVariant.Unordered,
        )
    }

    // ─── Migration ─────────────────────────────────────────
    DocSection(stringResource(Res.string.whats_new_section_migration)) {
        Text(
            text = stringResource(Res.string.whats_new_migration_body),
            variant = TextVariant.P,
        )
    }
}
