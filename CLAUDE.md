# RikkaUi — Project Context

## What This Is

**RikkaUi** is a shadcn/ui-inspired component library + design system for Compose Multiplatform.
Name means "snowflake" (六花) or "composing elements into harmony" (立花).
Tagline: "Share UI via Compose Multiplatform UI framework"

**The gap:** No one combines (1) styled components (2) copy-paste ownership (3) registry system (4) CMP support. We fill it.

## Architecture

### Modules

- `:components` — The component library. **No Material3 dependency.** Built on `compose.foundation` only. WasmJs target.
- `:composeApp` — The showcase website. WasmJs (browser). Depends on `:components`.

### Package Structure

```
components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/
  theme/
    RikkaColors.kt       — 20 semantic color tokens (@Immutable, staticCompositionLocalOf)
    ColorScheme.kt        — 6 pre-built palettes: Neutral/Zinc/Rose x Light/Dark (RikkaPalettes object)
    RikkaTypography.kt    — 9-level type scale (h1-h4, p, lead, large, small, muted)
    RikkaSpacing.kt       — 7-level spacing scale (xs=4dp through xxxl=48dp, 4dp base grid)
    RikkaShapes.kt        — 5-level shape scale (sm/md/lg/xl/full) from base radius
    RikkaMotion.kt        — Animation token system (springs, tweens, durations, press scales)
    RikkaFontFamily.kt    — Font wrapper with rememberRikkaFontFamily() composable
    RikkaTheme.kt         — CompositionLocalProvider + RikkaTheme object with @ReadOnlyComposable getters
  ui/
    text/Text.kt          — BasicText wrapper with TextVariant enum, heading accessibility
    button/Button.kt      — 6 variants, 4 sizes, 3 animations (spring physics), accessibility
    card/Card.kt          — 3 variants + CardHeader/CardContent/CardFooter, semantic grouping
    badge/Badge.kt        — 4 variants, text + content overloads
    separator/Separator.kt — Horizontal/Vertical, decorative (clearAndSetSemantics)
    input/Input.kt        — BasicTextField wrapper, animated focus border, placeholder, accessibility
    toggle/Toggle.kt      — Spring-animated thumb, 2 sizes, Role.Switch + stateDescription
    indication/RikkaIndication.kt — IndicationNodeFactory using Modifier.Node API (draw phase only)
```

### Design Patterns (FOLLOW THESE)

1. **Enum-based configuration** — Use enums for variants/sizes/animations, not booleans
2. **Private resolution functions** — `resolveColors()`, `resolveSizeValues()` etc. as @Composable private funs
3. **Theme token injection** — Always use `RikkaTheme.colors`, `.typography`, `.spacing`, `.shapes`, `.motion`
4. **Motion tokens** — All animations reference `RikkaTheme.motion` (never hardcode spring/tween params)
5. **Modifier composition** — Build modifiers with `.then()` chains
6. **State tracking** — `MutableInteractionSource` for hover/press/focus
7. **Composable overloads** — Provide both generic (content lambda) and convenience (string text) versions
8. **Accessibility** — Every interactive component MUST have: Role, contentDescription via `label` param, disabled() semantics. Headings get `heading()`. Decorative elements get `clearAndSetSemantics {}`
9. **No Material3** — Use BasicText (not Text), clickable with Role (not Button), compose.foundation only
10. **KDoc on everything** — With usage examples in code blocks

### Performance Rules

- `@Immutable` on all theme data classes
- `staticCompositionLocalOf` for theme tokens (zero read-tracking overhead)
- `graphicsLayer {}` lambda for animations (skips composition + layout phases)
- `Modifier.Node` API for custom modifiers (never `composed {}`)
- Spring animations as default for interactions (handles interruptions)
- Lambda-based `Modifier.offset {}` to defer reads to placement step

### Theme System

- **Colors:** 20 semantic tokens in bg/fg pairs. 3 palettes x light/dark = 6 schemes.
- **Typography:** 9 styles wired to RikkaFontFamily. Change font once, updates everywhere.
- **Spacing:** 4dp base grid. xs(4) sm(8) md(12) lg(16) xl(24) xxl(32) xxxl(48).
- **Shapes:** Generated from base radius (default 10dp). sm/md/lg/xl/full.
- **Motion:** Springs (default/bouncy/snap), tweens (fast/default/slow/enter), duration ints, press scale targets.

## Build & Run

```bash
# Compile components library
./gradlew :components:compileKotlinWasmJs

# Run website dev server
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Format code (runs before build via hook)
./gradlew :components:ktlintFormat

# Full build
./gradlew :composeApp:wasmJsBrowserDevelopmentExecutableDistribution
```

### Known ktlint Issues

- Generated Compose resource files fail ktlint (not our code, ignore)
- `@Composable` function names (uppercase) trigger "Function name should start with lowercase" (Compose convention, ignore)
- `ColorScheme.kt` triggers "single top level declaration" filename warning (ignore)

## MVP Scope

**CLI is deferred.** MVP = design system + components + registry + website.

Priority order:
1. Website showcasing all components with live examples
2. More components: Checkbox, RadioButton, Textarea, Tooltip, Dialog, Avatar, Tabs
3. Registry system: JSON manifests describing components + dependencies
4. Community: Post on X/LinkedIn, Discord, collect weekly feedback

## Key Technical Decisions

- **Foundation only, no Material3** — Compose Foundation provides BasicText, clickable, InteractionSource. We build everything else.
- **staticCompositionLocalOf** for theme — tokens rarely change, avoid tracking overhead.
- **Spring physics default** — Handles interruptions gracefully, feels native across platforms.
- **RikkaIndication over per-component feedback** — Single IndicationNodeFactory for design-system-wide hover/press/focus.
- **Experimental Styles API** — Future migration target when Foundation 1.11+ stabilizes. Currently we use manual InteractionSource + animateColorAsState.
- **Wasm/Web target** — Canvas-based (no SEO). Target dashboards/apps, not content sites. Hover states critical.

## Who Is This For

Developers starting NEW Compose Multiplatform projects who want:
- Beautiful default styling without Material3 constraints
- Copy-paste components they own (no dependency lock-in)
- shadcn-style registry for community component sharing
