package zed.rainxch.rikkaui.components.ui.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.theme.RikkaTheme
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant

// ─── Size ───────────────────────────────────────────────────

/**
 * Avatar size variants.
 *
 * - [Sm] — 32dp. Compact contexts like lists or inline mentions.
 * - [Default] — 40dp. Standard usage in headers and profiles.
 * - [Lg] — 48dp. Prominent placement like profile pages.
 */
enum class AvatarSize {
    Sm,
    Default,
    Lg,
}

// ─── Component ──────────────────────────────────────────────

/**
 * Avatar component for the RikkaUi design system.
 *
 * A circular container displaying user initials as a fallback.
 * Replaces Material3's avatar patterns with Rikka theme tokens.
 *
 * Usage:
 * ```
 * Avatar(fallback = "JD")
 * Avatar(fallback = "A", size = AvatarSize.Sm)
 * Avatar(fallback = "RX", size = AvatarSize.Lg)
 * ```
 *
 * @param fallback 1-2 character initials shown as the avatar content.
 * @param modifier Modifier for layout and decoration.
 * @param size Size variant — controls the avatar diameter and text scale.
 */
@Composable
fun Avatar(
    fallback: String,
    modifier: Modifier = Modifier,
    size: AvatarSize = AvatarSize.Default,
) {
    val resolved = resolveSizeValues(size)
    val shape = RikkaTheme.shapes.full

    Box(
        modifier =
            modifier
                .semantics {
                    contentDescription = fallback
                }
                .size(resolved.diameter)
                .background(RikkaTheme.colors.muted, shape)
                .clip(shape),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = fallback,
            variant = resolved.textVariant,
            color = RikkaTheme.colors.mutedForeground,
        )
    }
}

// ─── Internal: Size Resolution ──────────────────────────────

private data class AvatarSizeValues(
    val diameter: Dp,
    val textVariant: TextVariant,
)

private fun resolveSizeValues(size: AvatarSize): AvatarSizeValues =
    when (size) {
        AvatarSize.Sm -> {
            AvatarSizeValues(
                diameter = 32.dp,
                textVariant = TextVariant.Small,
            )
        }

        AvatarSize.Default -> {
            AvatarSizeValues(
                diameter = 40.dp,
                textVariant = TextVariant.P,
            )
        }

        AvatarSize.Lg -> {
            AvatarSizeValues(
                diameter = 48.dp,
                textVariant = TextVariant.Large,
            )
        }
    }
