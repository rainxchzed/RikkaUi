package zed.rainxch.rikkaui.components.ui.button

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.IconSize

// ─── Size ────────────────────────────────────────────────────

/** Icon button size variants. */
enum class IconButtonSize {
    /** 36x36 dp button, 20 dp icon. */
    Default,

    /** 28x28 dp button, 16 dp icon. */
    Sm,

    /** 44x44 dp button, 24 dp icon. */
    Lg,
}

// ─── Component ───────────────────────────────────────────────

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
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            size = iconSize,
        )
    }
}
