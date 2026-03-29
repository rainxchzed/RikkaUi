package zed.rainxch.rikkaui.components.ui.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize

// ─── Size ────────────────────────────────────────────────────

/**
 * Icon button size variants.
 *
 * Controls the overall button dimensions and the icon size inside.
 *
 * - [Default] — 36 x 36 dp button, 20 dp icon. Standard toolbar size.
 * - [Sm] — 28 x 28 dp button, 16 dp icon. Compact/dense layouts.
 * - [Lg] — 44 x 44 dp button, 24 dp icon. Prominent actions.
 */
enum class IconButtonSize {
    Default,
    Sm,
    Lg,
}

// ─── Component ───────────────────────────────────────────────

/**
 * Icon-only button for the RikkaUi design system.
 *
 * A convenience wrapper around [Button] with [ButtonSize.Icon] that
 * takes an [ImageVector] directly — no content lambda needed.
 *
 * The icon is automatically tinted to match the button variant's
 * foreground color.
 *
 * Usage:
 * ```
 * IconButton(
 *     icon = RikkaIcons.Plus,
 *     contentDescription = "Add item",
 *     onClick = { addItem() },
 * )
 *
 * // Destructive
 * IconButton(
 *     icon = RikkaIcons.Trash,
 *     contentDescription = "Delete",
 *     onClick = { delete() },
 *     variant = ButtonVariant.Destructive,
 * )
 *
 * // Ghost, small
 * IconButton(
 *     icon = RikkaIcons.X,
 *     contentDescription = "Close",
 *     onClick = { close() },
 *     variant = ButtonVariant.Ghost,
 *     size = IconButtonSize.Sm,
 * )
 * ```
 *
 * @param icon The vector graphic to display.
 * @param contentDescription Accessibility label for screen readers.
 * @param onClick Called when the button is clicked.
 * @param modifier Modifier for layout and decoration.
 * @param variant Visual variant — controls colors and border.
 *   Defaults to [ButtonVariant.Ghost].
 * @param size Icon button size variant.
 * @param animation Press animation style.
 * @param enabled Whether the button is interactive.
 * @param loading When true, shows a [Spinner][zed.rainxch.rikkaui.components.ui.spinner.Spinner]
 *   and disables interaction.
 */
@Composable
fun IconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Ghost,
    size: IconButtonSize = IconButtonSize.Default,
    animation: ButtonAnimation = ButtonAnimation.Scale,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val buttonSize =
        when (size) {
            IconButtonSize.Sm -> ButtonSize.Sm
            IconButtonSize.Default -> ButtonSize.Icon
            IconButtonSize.Lg -> ButtonSize.Lg
        }

    val iconSize =
        when (size) {
            IconButtonSize.Sm -> IconSize.Sm
            IconButtonSize.Default -> IconSize.Default
            IconButtonSize.Lg -> IconSize.Lg
        }

    Button(
        onClick = onClick,
        modifier = modifier,
        variant = variant,
        size = buttonSize,
        animation = animation,
        enabled = enabled,
        loading = loading,
        label = contentDescription,
    ) { foreground ->
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = foreground,
            size = iconSize,
        )
    }
}
