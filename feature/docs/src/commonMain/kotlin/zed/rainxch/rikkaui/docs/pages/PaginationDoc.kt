package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import rikkaui.feature.docs.generated.resources.*
import rikkaui.feature.docs.generated.resources.Res
import zed.rainxch.rikkaui.components.ui.pagination.Pagination
import zed.rainxch.rikkaui.components.ui.pagination.PaginationAnimation
import zed.rainxch.rikkaui.components.ui.pagination.PaginationSize
import zed.rainxch.rikkaui.docs.components.CodeBlock
import zed.rainxch.rikkaui.docs.components.ComponentPageHeader
import zed.rainxch.rikkaui.docs.components.DemoBox
import zed.rainxch.rikkaui.docs.components.DocSection
import zed.rainxch.rikkaui.docs.components.PropInfo
import zed.rainxch.rikkaui.docs.components.PropsTable
import zed.rainxch.rikkaui.docs.components.VariantSelector
import zed.rainxch.rikkaui.foundation.RikkaTheme

/**
 * Documentation page for the Pagination component.
 *
 * Demonstrates page navigation with animation variants,
 * button sizes, and automatic ellipsis calculation.
 */
@Composable
fun PaginationDoc() {
    ComponentPageHeader(
        name = stringResource(Res.string.component_pagination_name),
        description = stringResource(Res.string.pagination_page_desc),
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection(stringResource(Res.string.section_animations)) {
        var selectedAnim by remember { mutableStateOf("Scale") }
        var currentPage by remember { mutableStateOf(1) }

        VariantSelector(
            options = listOf("Scale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation =
            when (selectedAnim) {
                "Fade" -> PaginationAnimation.Fade
                "None" -> PaginationAnimation.None
                else -> PaginationAnimation.Scale
            }

        DemoBox {
            Pagination(
                currentPage = currentPage,
                totalPages = 20,
                onPageChange = { currentPage = it },
                animation = animation,
            )
        }
    }

    // ─── Button Sizes ───────────────────────────────────────
    DocSection(stringResource(Res.string.section_sizes)) {
        var selectedSize by remember { mutableStateOf("Default") }
        var currentPage by remember { mutableStateOf(1) }

        VariantSelector(
            options = listOf("Small", "Default", "Large"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val buttonSize =
            when (selectedSize) {
                "Small" -> PaginationSize.Small
                "Large" -> PaginationSize.Large
                else -> PaginationSize.Default
            }

        DemoBox {
            Pagination(
                currentPage = currentPage,
                totalPages = 10,
                onPageChange = { currentPage = it },
                buttonSize = buttonSize,
            )
        }
    }

    // ─── Few Pages ──────────────────────────────────────────
    DocSection(stringResource(Res.string.pagination_section_few_pages)) {
        var currentPage by remember { mutableStateOf(1) }

        DemoBox {
            Pagination(
                currentPage = currentPage,
                totalPages = 5,
                onPageChange = { currentPage = it },
            )
        }
    }

    // ─── Usage ──────────────────────────────────────────────
    DocSection(stringResource(Res.string.section_usage)) {
        CodeBlock(
            """
var currentPage by remember { mutableStateOf(1) }

Pagination(
    currentPage = currentPage,
    totalPages = 20,
    onPageChange = { currentPage = it },
    animation = PaginationAnimation.Scale,
    buttonSize = PaginationSize.Default,
)
            """.trimIndent(),
        )
    }

    // ─── API Reference ──────────────────────────────────────
    DocSection(stringResource(Res.string.section_api_reference)) {
        PropsTable(
            listOf(
                PropInfo(
                    "currentPage",
                    "Int",
                    "required",
                    stringResource(Res.string.pagination_prop_current_page_desc),
                ),
                PropInfo(
                    "totalPages",
                    "Int",
                    "required",
                    stringResource(Res.string.pagination_prop_total_pages_desc),
                ),
                PropInfo(
                    "onPageChange",
                    "(Int) -> Unit",
                    "required",
                    stringResource(Res.string.pagination_prop_on_page_change_desc),
                ),
                PropInfo(
                    "modifier",
                    "Modifier",
                    "Modifier",
                    stringResource(Res.string.pagination_prop_modifier_desc),
                ),
                PropInfo(
                    "maxVisiblePages",
                    "Int",
                    "5",
                    stringResource(Res.string.pagination_prop_max_visible_desc),
                ),
                PropInfo(
                    "animation",
                    "PaginationAnimation",
                    "Scale",
                    stringResource(Res.string.pagination_prop_animation_desc),
                ),
                PropInfo(
                    "buttonSize",
                    "PaginationSize",
                    "Default",
                    stringResource(Res.string.pagination_prop_button_size_desc),
                ),
                PropInfo(
                    "previousContent",
                    "((Color) -> Unit)?",
                    "null",
                    stringResource(Res.string.pagination_prop_previous_desc),
                ),
                PropInfo(
                    "nextContent",
                    "((Color) -> Unit)?",
                    "null",
                    stringResource(Res.string.pagination_prop_next_desc),
                ),
            ),
        )
    }
}
