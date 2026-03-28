package zed.rainxch.rikkaui.docs.pages

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import zed.rainxch.rikkaui.components.theme.RikkaTheme
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

/**
 * Documentation page for the Pagination component.
 *
 * Demonstrates page navigation with animation variants,
 * button sizes, and automatic ellipsis calculation.
 */
@Composable
fun PaginationDoc() {
    ComponentPageHeader(
        name = "Pagination",
        description = "A smart page navigation component with "
            + "automatic ellipsis and Previous/Next buttons.",
    )

    // ─── Animation Variants ─────────────────────────────────
    DocSection("Animations") {
        var selectedAnim by remember { mutableStateOf("Scale") }
        var currentPage by remember { mutableStateOf(1) }

        VariantSelector(
            options = listOf("Scale", "Fade", "None"),
            selected = selectedAnim,
            onSelect = { selectedAnim = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val animation = when (selectedAnim) {
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
    DocSection("Sizes") {
        var selectedSize by remember { mutableStateOf("Default") }
        var currentPage by remember { mutableStateOf(1) }

        VariantSelector(
            options = listOf("Small", "Default", "Large"),
            selected = selectedSize,
            onSelect = { selectedSize = it },
        )

        Spacer(Modifier.height(RikkaTheme.spacing.md))

        val buttonSize = when (selectedSize) {
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
    DocSection("Few Pages (No Ellipsis)") {
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
    DocSection("Usage") {
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
    DocSection("API Reference") {
        PropsTable(
            listOf(
                PropInfo(
                    "currentPage", "Int", "required",
                    "The currently active page (1-indexed).",
                ),
                PropInfo(
                    "totalPages", "Int", "required",
                    "Total number of pages.",
                ),
                PropInfo(
                    "onPageChange", "(Int) -> Unit", "required",
                    "Called when a different page is selected.",
                ),
                PropInfo(
                    "modifier", "Modifier", "Modifier",
                    "Modifier for layout and decoration.",
                ),
                PropInfo(
                    "maxVisiblePages", "Int", "5",
                    "Max page buttons visible at once.",
                ),
                PropInfo(
                    "animation", "PaginationAnimation", "Scale",
                    "Active page animation: Scale, Fade, None.",
                ),
                PropInfo(
                    "buttonSize", "PaginationSize", "Default",
                    "Button size: Small (28dp), Default (36dp), Large (44dp).",
                ),
                PropInfo(
                    "previousContent", "((Color) -> Unit)?", "null",
                    "Custom content for the previous button.",
                ),
                PropInfo(
                    "nextContent", "((Color) -> Unit)?", "null",
                    "Custom content for the next button.",
                ),
            ),
        )
    }
}
