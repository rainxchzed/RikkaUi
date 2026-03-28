package zed.rainxch.rikkaui.components.ui.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathBuilder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

// ─── Icon Library ───────────────────────────────────────────

/**
 * RikkaUi icon set — Lucide-based [ImageVector] definitions.
 *
 * Every icon is `lazy`-initialised so unused icons incur zero cost.
 * All stroke-based icons share Lucide defaults: 24x24 viewBox,
 * stroke-width 2, round caps and joins.
 *
 * Usage:
 * ```
 * Icon(
 *     imageVector = RikkaIcons.Search,
 *     contentDescription = "Search",
 * )
 *
 * Icon(
 *     imageVector = RikkaIcons.Moon,
 *     contentDescription = "Toggle dark mode",
 *     tint = RikkaTheme.colors.mutedForeground,
 * )
 * ```
 */
object RikkaIcons {
    // ── Shared builder helpers ──────────────────────────────

    private inline fun lucideIcon(
        name: String,
        crossinline block: ImageVector.Builder.() -> Unit,
    ): ImageVector =
        ImageVector
            .Builder(
                name = name,
                defaultWidth = 24.dp,
                defaultHeight = 24.dp,
                viewportWidth = 24f,
                viewportHeight = 24f,
            ).apply(block)
            .build()

    // ── Chevrons ────────────────────────────────────────────

    /** Right-pointing chevron. */
    val ChevronRight: ImageVector by lazy {
        lucideIcon("ChevronRight") {
            strokePath {
                moveTo(9f, 18f)
                lineTo(15f, 12f)
                lineTo(9f, 6f)
            }
        }
    }

    /** Downward-pointing chevron. */
    val ChevronDown: ImageVector by lazy {
        lucideIcon("ChevronDown") {
            strokePath {
                moveTo(6f, 9f)
                lineTo(12f, 15f)
                lineTo(18f, 9f)
            }
        }
    }

    /** Left-pointing chevron. */
    val ChevronLeft: ImageVector by lazy {
        lucideIcon("ChevronLeft") {
            strokePath {
                moveTo(15f, 18f)
                lineTo(9f, 12f)
                lineTo(15f, 6f)
            }
        }
    }

    /** Upward-pointing chevron. */
    val ChevronUp: ImageVector by lazy {
        lucideIcon("ChevronUp") {
            strokePath {
                moveTo(18f, 15f)
                lineTo(12f, 9f)
                lineTo(6f, 15f)
            }
        }
    }

    // ── Actions ─────────────────────────────────────────────

    /** Checkmark / tick. */
    val Check: ImageVector by lazy {
        lucideIcon("Check") {
            strokePath {
                moveTo(20f, 6f)
                lineTo(9f, 17f)
                lineTo(4f, 12f)
            }
        }
    }

    /** X / close. */
    val X: ImageVector by lazy {
        lucideIcon("X") {
            strokePath {
                moveTo(18f, 6f)
                lineTo(6f, 18f)
            }
            strokePath {
                moveTo(6f, 6f)
                lineTo(18f, 18f)
            }
        }
    }

    /** Plus sign. */
    val Plus: ImageVector by lazy {
        lucideIcon("Plus") {
            strokePath {
                moveTo(12f, 5f)
                lineTo(12f, 19f)
            }
            strokePath {
                moveTo(5f, 12f)
                lineTo(19f, 12f)
            }
        }
    }

    /** Minus sign / dash. */
    val Minus: ImageVector by lazy {
        lucideIcon("Minus") {
            strokePath {
                moveTo(5f, 12f)
                lineTo(19f, 12f)
            }
        }
    }

    // ── Search ──────────────────────────────────────────────

    /** Magnifying glass. */
    val Search: ImageVector by lazy {
        lucideIcon("Search") {
            strokePath {
                circle(11f, 11f, 8f)
            }
            strokePath {
                moveTo(21f, 21f)
                lineTo(16.65f, 16.65f)
            }
        }
    }

    // ── Arrows ──────────────────────────────────────────────

    /** Left arrow. */
    val ArrowLeft: ImageVector by lazy {
        lucideIcon("ArrowLeft") {
            strokePath {
                moveTo(19f, 12f)
                lineTo(5f, 12f)
            }
            strokePath {
                moveTo(12f, 19f)
                lineTo(5f, 12f)
                lineTo(12f, 5f)
            }
        }
    }

    /** Right arrow. */
    val ArrowRight: ImageVector by lazy {
        lucideIcon("ArrowRight") {
            strokePath {
                moveTo(5f, 12f)
                lineTo(19f, 12f)
            }
            strokePath {
                moveTo(12f, 5f)
                lineTo(19f, 12f)
                lineTo(12f, 19f)
            }
        }
    }

    /** Up arrow. */
    val ArrowUp: ImageVector by lazy {
        lucideIcon("ArrowUp") {
            strokePath {
                moveTo(12f, 19f)
                lineTo(12f, 5f)
            }
            strokePath {
                moveTo(5f, 12f)
                lineTo(12f, 5f)
                lineTo(19f, 12f)
            }
        }
    }

    /** Down arrow. */
    val ArrowDown: ImageVector by lazy {
        lucideIcon("ArrowDown") {
            strokePath {
                moveTo(12f, 5f)
                lineTo(12f, 19f)
            }
            strokePath {
                moveTo(19f, 12f)
                lineTo(12f, 19f)
                lineTo(5f, 12f)
            }
        }
    }

    // ── Menu / More ─────────────────────────────────────────

    /** Hamburger menu — three horizontal lines. */
    val Menu: ImageVector by lazy {
        lucideIcon("Menu") {
            strokePath {
                moveTo(3f, 4f)
                lineTo(21f, 4f)
            }
            strokePath {
                moveTo(3f, 12f)
                lineTo(21f, 12f)
            }
            strokePath {
                moveTo(3f, 20f)
                lineTo(21f, 20f)
            }
        }
    }

    /** Three horizontal dots. */
    val MoreHorizontal: ImageVector by lazy {
        lucideIcon("MoreHorizontal") {
            fillPath {
                circle(5f, 12f, 1f)
                circle(12f, 12f, 1f)
                circle(19f, 12f, 1f)
            }
        }
    }

    /** Three vertical dots. */
    val MoreVertical: ImageVector by lazy {
        lucideIcon("MoreVertical") {
            fillPath {
                circle(12f, 5f, 1f)
                circle(12f, 12f, 1f)
                circle(12f, 19f, 1f)
            }
        }
    }

    // ── Communication ───────────────────────────────────────

    /** Envelope / mail. */
    val Mail: ImageVector by lazy {
        lucideIcon("Mail") {
            strokePath {
                roundedRect(2f, 4f, 20f, 16f, 2f)
            }
            strokePath {
                moveTo(22f, 7f)
                lineTo(12f, 13f)
                lineTo(2f, 7f)
            }
        }
    }

    /** Send / paper plane. */
    val Send: ImageVector by lazy {
        lucideIcon("Send") {
            strokePath {
                moveTo(22f, 2f)
                lineTo(11f, 13f)
            }
            strokePath {
                moveTo(22f, 2f)
                lineTo(15f, 22f)
                lineTo(11f, 13f)
                lineTo(2f, 9f)
                lineTo(22f, 2f)
                close()
            }
        }
    }

    // ── People ──────────────────────────────────────────────

    /** User / person silhouette. */
    val User: ImageVector by lazy {
        lucideIcon("User") {
            strokePath {
                circle(12f, 8f, 5f)
            }
            strokePath {
                moveTo(20f, 21f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4f, 21f)
            }
        }
    }

    // ── Feedback / Social ───────────────────────────────────

    /** Heart shape. */
    val Heart: ImageVector by lazy {
        lucideIcon("Heart") {
            strokePath {
                moveTo(19f, 14f)
                curveTo(20.49f, 12.54f, 22f, 10.79f, 22f, 8.5f)
                arcTo(5.5f, 5.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16.5f, 3f)
                curveTo(14.74f, 3f, 13.5f, 3.5f, 12f, 5f)
                curveTo(10.5f, 3.5f, 9.26f, 3f, 7.5f, 3f)
                arcTo(5.5f, 5.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2f, 8.5f)
                curveTo(2f, 10.8f, 3.5f, 12.55f, 5f, 14f)
                lineTo(12f, 21f)
                close()
            }
        }
    }

    /** Five-pointed star. */
    val Star: ImageVector by lazy {
        lucideIcon("Star") {
            strokePath {
                moveTo(12f, 2f)
                lineTo(15.09f, 8.26f)
                lineTo(22f, 9.27f)
                lineTo(17f, 14.14f)
                lineTo(18.18f, 21.02f)
                lineTo(12f, 17.77f)
                lineTo(5.82f, 21.02f)
                lineTo(7f, 14.14f)
                lineTo(2f, 9.27f)
                lineTo(8.91f, 8.26f)
                close()
            }
        }
    }

    // ── Visibility ──────────────────────────────────────────

    /** Eye / visibility. */
    val Eye: ImageVector by lazy {
        lucideIcon("Eye") {
            strokePath {
                moveTo(2f, 12f)
                curveTo(2f, 12f, 5f, 5f, 12f, 5f)
                curveTo(19f, 5f, 22f, 12f, 22f, 12f)
                curveTo(22f, 12f, 19f, 19f, 12f, 19f)
                curveTo(5f, 19f, 2f, 12f, 2f, 12f)
                close()
            }
            strokePath {
                circle(12f, 12f, 3f)
            }
        }
    }

    // ── Editing ─────────────────────────────────────────────

    /** Copy / duplicate — two overlapping rectangles. */
    val Copy: ImageVector by lazy {
        lucideIcon("Copy") {
            strokePath {
                roundedRect(9f, 9f, 11f, 11f, 2f)
            }
            strokePath {
                moveTo(5f, 15f)
                lineTo(4f, 15f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2f, 13f)
                lineTo(2f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4f, 2f)
                lineTo(13f, 2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15f, 4f)
                lineTo(15f, 5f)
            }
        }
    }

    /** Trash / delete. */
    val Trash: ImageVector by lazy {
        lucideIcon("Trash") {
            strokePath {
                moveTo(3f, 6f)
                lineTo(5f, 6f)
                lineTo(21f, 6f)
            }
            strokePath {
                moveTo(19f, 6f)
                lineTo(19f, 20f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 17f, 22f)
                lineTo(7f, 22f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5f, 20f)
                lineTo(5f, 6f)
            }
            strokePath {
                moveTo(8f, 6f)
                lineTo(8f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10f, 2f)
                lineTo(14f, 2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 16f, 4f)
                lineTo(16f, 6f)
            }
        }
    }

    /** Pencil / edit. */
    val Edit: ImageVector by lazy {
        lucideIcon("Edit") {
            strokePath {
                moveTo(17f, 3f)
                arcTo(
                    2.85f,
                    2.83f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    21f,
                    7f,
                )
                lineTo(7.5f, 20.5f)
                lineTo(2f, 22f)
                lineTo(3.5f, 16.5f)
                close()
            }
        }
    }

    // ── File transfer ───────────────────────────────────────

    /** Download arrow with tray. */
    val Download: ImageVector by lazy {
        lucideIcon("Download") {
            strokePath {
                moveTo(21f, 15f)
                lineTo(21f, 19f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19f, 21f)
                lineTo(5f, 21f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 19f)
                lineTo(3f, 15f)
            }
            strokePath {
                moveTo(7f, 10f)
                lineTo(12f, 15f)
                lineTo(17f, 10f)
            }
            strokePath {
                moveTo(12f, 15f)
                lineTo(12f, 3f)
            }
        }
    }

    /** Upload arrow with tray. */
    val Upload: ImageVector by lazy {
        lucideIcon("Upload") {
            strokePath {
                moveTo(21f, 15f)
                lineTo(21f, 19f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19f, 21f)
                lineTo(5f, 21f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 3f, 19f)
                lineTo(3f, 15f)
            }
            strokePath {
                moveTo(17f, 8f)
                lineTo(12f, 3f)
                lineTo(7f, 8f)
            }
            strokePath {
                moveTo(12f, 3f)
                lineTo(12f, 15f)
            }
        }
    }

    // ── Theme ───────────────────────────────────────────────

    /** Sun — circle with radiating lines. */
    val Sun: ImageVector by lazy {
        lucideIcon("Sun") {
            strokePath { circle(12f, 12f, 5f) }
            strokePath {
                moveTo(12f, 1f)
                lineTo(12f, 3f)
            }
            strokePath {
                moveTo(12f, 21f)
                lineTo(12f, 23f)
            }
            strokePath {
                moveTo(4.22f, 4.22f)
                lineTo(5.64f, 5.64f)
            }
            strokePath {
                moveTo(18.36f, 18.36f)
                lineTo(19.78f, 19.78f)
            }
            strokePath {
                moveTo(1f, 12f)
                lineTo(3f, 12f)
            }
            strokePath {
                moveTo(21f, 12f)
                lineTo(23f, 12f)
            }
            strokePath {
                moveTo(4.22f, 19.78f)
                lineTo(5.64f, 18.36f)
            }
            strokePath {
                moveTo(18.36f, 5.64f)
                lineTo(19.78f, 4.22f)
            }
        }
    }

    /** Crescent moon. */
    val Moon: ImageVector by lazy {
        lucideIcon("Moon") {
            strokePath {
                moveTo(12f, 3f)
                arcTo(6f, 6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 21f, 12f)
                arcTo(9f, 9f, 0f, isMoreThanHalf = true, isPositiveArc = true, 12f, 3f)
                close()
            }
        }
    }

    // ── Settings ────────────────────────────────────────────

    /** Gear / settings cog. */
    val Settings: ImageVector by lazy {
        lucideIcon("Settings") {
            strokePath { circle(12f, 12f, 3f) }
            strokePath {
                moveTo(12.22f, 2f)
                lineTo(11.78f, 2f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.78f, 4f)
                lineTo(9.78f, 4.18f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.78f, 5.91f)
                lineTo(8.35f, 6.16f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 6.35f, 6.16f)
                lineTo(6.2f, 6.08f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.47f, 6.81f)
                lineTo(3.25f, 7.19f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.98f, 9.92f)
                lineTo(4.13f, 10.02f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5.13f, 11.74f)
                lineTo(5.13f, 12.25f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.13f, 13.99f)
                lineTo(3.98f, 14.08f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.25f, 16.81f)
                lineTo(3.47f, 17.19f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 6.2f, 17.92f)
                lineTo(6.35f, 17.84f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8.35f, 17.84f)
                lineTo(8.78f, 18.09f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 9.78f, 19.82f)
                lineTo(9.78f, 20f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 11.78f, 22f)
                lineTo(12.22f, 22f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.22f, 20f)
                lineTo(14.22f, 19.82f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.22f, 18.09f)
                lineTo(15.65f, 17.84f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 17.65f, 17.84f)
                lineTo(17.8f, 17.92f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 20.53f, 17.19f)
                lineTo(20.75f, 16.81f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 20.02f, 14.08f)
                lineTo(19.87f, 13.99f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 18.87f, 12.25f)
                lineTo(18.87f, 11.74f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 19.87f, 10.02f)
                lineTo(20.02f, 9.92f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 20.75f, 7.19f)
                lineTo(20.53f, 6.81f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 17.8f, 6.08f)
                lineTo(17.65f, 6.16f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 15.65f, 6.16f)
                lineTo(15.22f, 5.91f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = true, 14.22f, 4.18f)
                lineTo(14.22f, 4f)
                arcTo(2f, 2f, 0f, isMoreThanHalf = false, isPositiveArc = false, 12.22f, 2f)
                close()
            }
        }
    }
}

// ── Private extension helpers ───────────────────────────────

/**
 * Adds a stroke-only path with Lucide defaults:
 * stroke-width 2, round cap, round join.
 */
private inline fun ImageVector.Builder.strokePath(crossinline block: PathBuilder.() -> Unit) {
    path(
        stroke = SolidColor(Color.Black),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
        pathBuilder = block,
    )
}

/**
 * Adds a fill-only path (for solid dots like MoreHorizontal / MoreVertical).
 */
private inline fun ImageVector.Builder.fillPath(crossinline block: PathBuilder.() -> Unit) {
    path(
        fill = SolidColor(Color.Black),
        pathBuilder = block,
    )
}

/**
 * Draws a circle at ([cx], [cy]) with radius [r] using two arcs.
 */
private fun PathBuilder.circle(
    cx: Float,
    cy: Float,
    r: Float,
) {
    moveTo(cx + r, cy)
    arcTo(r, r, 0f, isMoreThanHalf = true, isPositiveArc = true, cx - r, cy)
    arcTo(r, r, 0f, isMoreThanHalf = true, isPositiveArc = true, cx + r, cy)
    close()
}

/**
 * Draws a rounded rectangle at ([x], [y]) with size [w] x [h] and corner radius [r].
 */
private fun PathBuilder.roundedRect(
    x: Float,
    y: Float,
    w: Float,
    h: Float,
    r: Float,
) {
    val right = x + w
    val bottom = y + h
    moveTo(x + r, y)
    lineTo(right - r, y)
    arcTo(r, r, 0f, isMoreThanHalf = false, isPositiveArc = true, right, y + r)
    lineTo(right, bottom - r)
    arcTo(r, r, 0f, isMoreThanHalf = false, isPositiveArc = true, right - r, bottom)
    lineTo(x + r, bottom)
    arcTo(r, r, 0f, isMoreThanHalf = false, isPositiveArc = true, x, bottom - r)
    lineTo(x, y + r)
    arcTo(r, r, 0f, isMoreThanHalf = false, isPositiveArc = true, x + r, y)
    close()
}
