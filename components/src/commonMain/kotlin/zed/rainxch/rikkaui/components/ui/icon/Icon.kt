package zed.rainxch.rikkaui.components.ui.icon

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.foundation.LocalContentColor
import zed.rainxch.rikkaui.foundation.RikkaTheme

// ─── Size ───────────────────────────────────────────────────

/** Icon size variants. */
enum class IconSize(
    val dp: Dp,
) {
    Xs(12.dp),
    Sm(16.dp),
    Default(20.dp),
    Lg(24.dp),
    Xl(32.dp),
}

// ─── Component ──────────────────────────────────────────────

@Composable
fun Icon(
    imageVector: ImageVector,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    size: IconSize? = null,
    spin: Boolean = false,
) {
    val resolvedTint =
        when {
            tint != Color.Unspecified -> tint
            LocalContentColor.current != Color.Unspecified -> LocalContentColor.current
            else -> RikkaTheme.colors.foreground
        }
    val painter = rememberVectorPainter(imageVector)

    val semanticsModifier =
        if (contentDescription == null) {
            modifier.clearAndSetSemantics {}
        } else {
            modifier
        }

    val sizeModifier =
        if (size != null) {
            Modifier.size(size.dp)
        } else {
            Modifier.size(
                imageVector.defaultWidth,
                imageVector.defaultHeight,
            )
        }

    val spinModifier =
        if (spin) {
            val motion = RikkaTheme.motion
            val infiniteTransition = rememberInfiniteTransition()
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec =
                    infiniteRepeatable(
                        animation =
                            tween(
                                durationMillis = motion.durationSpin,
                                easing = LinearEasing,
                            ),
                    ),
            )
            Modifier.graphicsLayer { rotationZ = rotation }
        } else {
            Modifier
        }

    Box(
        modifier =
            semanticsModifier
                .then(sizeModifier)
                .then(spinModifier),
    ) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(resolvedTint),
        )
    }
}
