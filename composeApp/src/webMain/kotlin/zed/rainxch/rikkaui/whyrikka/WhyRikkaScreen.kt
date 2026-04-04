@file:OptIn(ExperimentalLayoutApi::class)

package zed.rainxch.rikkaui.whyrikka

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import zed.rainxch.rikkaui.foundation.modifier.keyboardScrollable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import zed.rainxch.rikkaui.components.ui.badge.Badge
import zed.rainxch.rikkaui.components.ui.badge.BadgeVariant
import zed.rainxch.rikkaui.components.ui.card.Card
import zed.rainxch.rikkaui.components.ui.card.CardContent
import zed.rainxch.rikkaui.components.ui.card.CardHeader
import zed.rainxch.rikkaui.components.ui.icon.Icon
import zed.rainxch.rikkaui.components.ui.icon.RikkaIcons
import zed.rainxch.rikkaui.components.ui.separator.Separator
import zed.rainxch.rikkaui.components.ui.text.Text
import zed.rainxch.rikkaui.components.ui.text.TextVariant
import zed.rainxch.rikkaui.foundation.RikkaTheme
import zed.rainxch.rikkaui.utils.WindowSizeClass

@Composable
fun WhyRikkaScreen() {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }

    BoxWithConstraints(
        modifier =
            Modifier
                .fillMaxSize()
                .keyboardScrollable(scrollState, scope, focusRequester)
                .verticalScroll(scrollState),
        contentAlignment = Alignment.TopCenter,
    ) {
        val sizeClass = WindowSizeClass.fromWidth(maxWidth)
        val horizontalPadding =
            when (sizeClass) {
                WindowSizeClass.Compact -> RikkaTheme.spacing.md
                WindowSizeClass.Medium -> RikkaTheme.spacing.lg
                WindowSizeClass.Expanded -> RikkaTheme.spacing.lg
            }

        Column(
            modifier =
                Modifier
                    .widthIn(max = 860.dp)
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ── Hero ──
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            Badge(text = "Manifesto", variant = BadgeVariant.Outline)

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "Why RikkaUI",
                variant = TextVariant.H1,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            Text(
                text =
                    "For teams who ship products with a strong visual identity " +
                        "across Android, iOS, and Desktop \u2014 without looking like a Google app.",
                variant = TextVariant.Lead,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            // ── Section 1: Design Philosophy ──
            SectionBadge("Design Philosophy")

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "A design system with convictions",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text =
                    "RikkaUI is not a theme layer on top of Material3. " +
                        "It is built from scratch on Compose Foundation \u2014 " +
                        "every color token, every animation curve, every interaction pattern " +
                        "was chosen deliberately, not inherited.",
                variant = TextVariant.P,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            PhilosophyGrid(sizeClass)

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
            Separator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            // ── Section 2: Why Not Material3 ──
            SectionBadge("The M3 Problem")

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "Material3 is great \u2014 until it isn't",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text =
                    "M3 has dozens of engineers behind it. " +
                        "It is optimized for one thing: making Android apps fast. " +
                        "But if you are building a product with its own identity " +
                        "across multiple platforms, M3 becomes the constraint, not the tool.",
                variant = TextVariant.P,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            PainPointsList()

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
            Separator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            // ── Section 3: What RikkaUI Does Differently ──
            SectionBadge("The RikkaUI Answer")

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "Built different, on purpose",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            DifferentiatorsList()

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
            Separator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            // ── Section 4: Accessibility ──
            SectionBadge("Engineering Depth")

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text = "Accessibility is not an afterthought",
                variant = TextVariant.H2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text =
                    "Every interactive component in RikkaUI has been audited against " +
                        "WCAG 2.2 Level AA. Not partially \u2014 fully. " +
                        "Roles, keyboard navigation, focus trapping, screen reader labels, " +
                        "48dp touch targets, progress semantics. " +
                        "The kind of work M3 does with dozens of engineers \u2014 we do too.",
                variant = TextVariant.P,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xl))

            AccessibilityGrid(sizeClass)

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
            Separator(modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))

            // ── Section 5: Who This Is For ──
            Text(
                text =
                    "RikkaUI is for teams who want to move fast " +
                        "and look like themselves.",
                variant = TextVariant.H3,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.md))

            Text(
                text =
                    "Material3 is for teams who want to move fast " +
                        "and look like Android.",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.sm))

            Text(
                text = "Different jobs. Different tools. Choose yours.",
                variant = TextVariant.Muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
            Spacer(Modifier.height(RikkaTheme.spacing.xxxl))
        }
    }
}

// ── Helpers ──

@Composable
private fun SectionBadge(label: String) {
    Badge(text = label, variant = BadgeVariant.Secondary)
}

@Composable
private fun PhilosophyGrid(sizeClass: WindowSizeClass) {
    val cards =
        listOf(
            PhilosophyItem(
                icon = RikkaIcons.Settings,
                title = "Spring physics over easing curves",
                body =
                    "M3 hardcodes cubic-bezier easing that feels mechanical. " +
                        "RikkaUI defaults to spring animations \u2014 they handle interruptions " +
                        "gracefully and feel alive. Every spring parameter is a theme token " +
                        "you control.",
            ),
            PhilosophyItem(
                icon = RikkaIcons.Eye,
                title = "Semantic tokens over tonal palettes",
                body =
                    "M3's tonal palette system (primary, primaryContainer, onPrimaryContainer) " +
                        "bleeds Google's design language into your product. " +
                        "RikkaUI uses 31 semantic tokens with on* naming \u2014 " +
                        "your palette, your brand, zero M3 residue.",
            ),
            PhilosophyItem(
                icon = RikkaIcons.Copy,
                title = "Copy-paste over package dependency",
                body =
                    "Every M3 update is a potential breaking change to your UI. " +
                        "With RikkaUI, the component is yours the moment you add it. " +
                        "Modify it, extend it, delete half of it. No upstream surprises.",
            ),
            PhilosophyItem(
                icon = RikkaIcons.Star,
                title = "Platform-native over Android-first",
                body =
                    "M3 was designed for phones. On desktop, hover states are wrong. " +
                        "On iOS, scroll physics feel foreign. " +
                        "RikkaUI on compose.foundation is genuinely cross-platform \u2014 " +
                        "hover, density, and motion adapt to where your app runs.",
            ),
        )

    val columns = if (sizeClass == WindowSizeClass.Compact) 1 else 2

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        maxItemsInEachRow = columns,
        modifier = Modifier.fillMaxWidth(),
    ) {
        cards.forEach { item ->
            PhilosophyCard(
                item = item,
                modifier =
                    Modifier.weight(1f),
            )
        }
    }
}

private data class PhilosophyItem(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val title: String,
    val body: String,
)

@Composable
private fun PhilosophyCard(
    item: PhilosophyItem,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        CardHeader {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier =
                        Modifier
                            .size(36.dp)
                            .clip(RikkaTheme.shapes.md)
                            .background(RikkaTheme.colors.primaryTinted)
                            .padding(RikkaTheme.spacing.sm),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = RikkaTheme.colors.primary,
                        modifier = Modifier.size(18.dp),
                    )
                }
                Spacer(Modifier.width(RikkaTheme.spacing.sm))
                Text(
                    text = item.title,
                    variant = TextVariant.Large,
                    style = TextStyle(fontWeight = FontWeight.SemiBold),
                )
            }
        }
        CardContent {
            Text(
                text = item.body,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )
        }
    }
}

@Composable
private fun PainPointsList() {
    val painPoints =
        listOf(
            PainPoint(
                number = "01",
                title = "Branding lock-in",
                body =
                    "M3's tonal palette system bleeds through everything. " +
                        "70%+ of Compose apps look identifiably \"Material\" even after extensive theming. " +
                        "Your product ends up looking like a Google app wearing a costume.",
            ),
            PainPoint(
                number = "02",
                title = "Cross-platform mismatch",
                body =
                    "M3 is Android-first. On Desktop, ripple effects feel wrong. " +
                        "On iOS, the scroll physics and density are foreign. " +
                        "M3 can't be platform-native without enormous custom work \u2014 " +
                        "work that defeats the purpose of using it.",
            ),
            PainPoint(
                number = "03",
                title = "Motion you can't escape",
                body =
                    "M3 hardcodes ripple indicators, state layers, and transition specifications. " +
                        "Want spring-based interactions? You have to fight the framework. " +
                        "Want to remove ripple? Prepare for unexpected visual breakage.",
            ),
            PainPoint(
                number = "04",
                title = "Update roulette",
                body =
                    "Every M3 version bump is a gamble. Token renames, behavior changes, " +
                        "deprecated APIs \u2014 your UI breaks because of someone else's roadmap. " +
                        "You never truly own your components.",
            ),
        )

    Column(
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        modifier = Modifier.fillMaxWidth(),
    ) {
        painPoints.forEach { point ->
            PainPointRow(point)
        }
    }
}

private data class PainPoint(
    val number: String,
    val title: String,
    val body: String,
)

@Composable
private fun PainPointRow(point: PainPoint) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                .clip(RikkaTheme.shapes.md)
                .padding(RikkaTheme.spacing.lg),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
    ) {
        Text(
            text = point.number,
            variant = TextVariant.H3,
            color = RikkaTheme.colors.primary,
        )
        Column {
            Text(
                text = point.title,
                variant = TextVariant.Large,
                style = TextStyle(fontWeight = FontWeight.SemiBold),
            )
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Text(
                text = point.body,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )
        }
    }
}

@Composable
private fun DifferentiatorsList() {
    val items =
        listOf(
            Differentiator(
                title = "Zero Material3 dependency",
                body =
                    "Built entirely on compose.foundation. No M3 tokens, no M3 behaviors, " +
                        "no M3 opinions leaking into your product. Every pixel is intentional.",
            ),
            Differentiator(
                title = "5 palettes \u00d7 7 accents \u00d7 5 style presets",
                body =
                    "175 unique theme combinations out of the box. " +
                        "Or build your own from scratch \u2014 every token is overridable. " +
                        "Your app should look like your brand, not our defaults.",
            ),
            Differentiator(
                title = "Spring physics as a first-class token",
                body =
                    "RikkaMotion gives you spring stiffness, damping, and duration as theme tokens. " +
                        "Switch from snappy to playful with a single preset. " +
                        "No fighting the framework to get the motion you want.",
            ),
            Differentiator(
                title = "Copy-paste ownership via CLI + registry",
                body =
                    "rikkaui add button copies the source into your project. " +
                        "It's your code now. Modify it, extend it, delete half. " +
                        "Auto-resolved dependencies mean no broken imports.",
            ),
            Differentiator(
                title = "True multiplatform",
                body =
                    "Android, iOS, Desktop (JVM), and Web (WasmJs). " +
                        "Same components, same tokens, same behavior. " +
                        "Hover states on desktop, touch targets on mobile, " +
                        "platform-appropriate density everywhere.",
            ),
        )

    Column(
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { item ->
            DifferentiatorRow(item)
        }
    }
}

private data class Differentiator(
    val title: String,
    val body: String,
)

@Composable
private fun DifferentiatorRow(item: Differentiator) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(RikkaTheme.colors.primaryTinted, RikkaTheme.shapes.md)
                .clip(RikkaTheme.shapes.md)
                .padding(RikkaTheme.spacing.lg),
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.sm),
        verticalAlignment = Alignment.Top,
    ) {
        Icon(
            imageVector = RikkaIcons.Check,
            contentDescription = null,
            tint = RikkaTheme.colors.primary,
            modifier = Modifier.size(20.dp),
        )
        Column {
            Text(
                text = item.title,
                variant = TextVariant.Large,
                style = TextStyle(fontWeight = FontWeight.SemiBold),
            )
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Text(
                text = item.body,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )
        }
    }
}

@Composable
private fun AccessibilityGrid(sizeClass: WindowSizeClass) {
    val items =
        listOf(
            A11yItem("Semantic roles", "Every interactive component declares its role \u2014 Button, Checkbox, Switch, Tab, DropdownList."),
            A11yItem("Keyboard navigation", "Focus trapping in dialogs, Escape to close overlays, arrow keys in menus and sliders."),
            A11yItem("Screen reader labels", "contentDescription, stateDescription, paneTitle, heading semantics, liveRegion on alerts."),
            A11yItem(
                "48dp touch targets",
                "minTouchTarget modifier on every interactive element. Expands hit area without changing visuals.",
            ),
            A11yItem(
                "Progress semantics",
                "progressBarRangeInfo on Progress, Spinner, and Slider. Screen readers announce values natively.",
            ),
            A11yItem(
                "Form error states",
                "isError + error() semantics on Input, Textarea, Select. Destructive border on validation failure.",
            ),
        )

    val columns = if (sizeClass == WindowSizeClass.Compact) 1 else 2

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        verticalArrangement = Arrangement.spacedBy(RikkaTheme.spacing.md),
        maxItemsInEachRow = columns,
        modifier = Modifier.fillMaxWidth(),
    ) {
        items.forEach { item ->
            A11yCard(
                item = item,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

private data class A11yItem(
    val title: String,
    val body: String,
)

@Composable
private fun A11yCard(
    item: A11yItem,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .border(1.dp, RikkaTheme.colors.border, RikkaTheme.shapes.md)
                .clip(RikkaTheme.shapes.md)
                .padding(RikkaTheme.spacing.lg),
    ) {
        Column {
            Text(
                text = item.title,
                variant = TextVariant.Large,
                style = TextStyle(fontWeight = FontWeight.SemiBold),
            )
            Spacer(Modifier.height(RikkaTheme.spacing.xs))
            Text(
                text = item.body,
                variant = TextVariant.Small,
                color = RikkaTheme.colors.onMuted,
            )
        }
    }
}
