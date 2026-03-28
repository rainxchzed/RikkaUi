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
    ColorScheme.kt        — 5 base palettes (Zinc/Slate/Stone/Gray/Neutral) x Light/Dark = 10 schemes
                            + 7 accent colors (Red/Rose/Orange/Green/Blue/Yellow/Violet) x Light/Dark
                            + withAccent() extension + RikkaAccentColor data class
    RikkaTypography.kt    — 9-level type scale (h1-h4, p, lead, large, small, muted)
    RikkaSpacing.kt       — 7-level spacing scale (xs=4dp through xxxl=48dp, 4dp base grid)
    RikkaShapes.kt        — 5-level shape scale (sm/md/lg/xl/full) from base radius
    RikkaMotion.kt        — Animation token system (springs, tweens, durations, press scales)
    RikkaStyle.kt         — Bundled style presets (RikkaStyle + RikkaStylePresets: Default/Nova/Vega/Aurora/Nebula)
    RikkaFontFamily.kt    — Font wrapper with rememberRikkaFontFamily() composable
    RikkaTheme.kt         — CompositionLocalProvider + RikkaTheme object + convenience style overload
  ui/
    text/Text.kt          — BasicText wrapper with TextVariant enum, heading accessibility
    button/Button.kt      — 6 variants (Default/Secondary/Destructive/Outline/Ghost/Link), 4 sizes (Default/Sm/Lg/Icon), 3 animations
    card/Card.kt          — 3 variants + CardHeader/CardContent/CardFooter, semantic grouping
    badge/Badge.kt        — 4 variants (Default/Secondary/Destructive/Outline), text + content overloads
    separator/Separator.kt — Horizontal/Vertical, decorative (clearAndSetSemantics)
    input/Input.kt        — BasicTextField wrapper, animated focus border, placeholder, accessibility
    toggle/Toggle.kt      — Spring-animated thumb, 2 sizes, thumb uses primaryForeground when checked
    checkbox/Checkbox.kt   — Animated checkmark, label renders visually (not just a11y)
    radio/RadioButton.kt   — Radio selection control
    textarea/Textarea.kt   — Multi-line text input
    label/Label.kt         — Form label with disabled state
    skeleton/Skeleton.kt   — Pulsing loading placeholder
    spinner/Spinner.kt     — Rotating loading indicator (3 sizes)
    alert/Alert.kt         — Alert + AlertTitle + AlertDescription (Default/Destructive variants)
                             Destructive uses semi-transparent red bg/border + destructive text color
    avatar/Avatar.kt       — Fallback initials (3 sizes: Sm/Default/Lg)
    kbd/Kbd.kt             — Keyboard shortcut indicator
    progress/Progress.kt   — Animated progress bar
    slider/Slider.kt       — Draggable range input with pointerInput
    accordion/Accordion.kt — Expandable sections with chevron icon
    tabs/Tabs.kt           — TabList + Tab + TabContent
    togglegroup/ToggleGroup.kt — Grouped toggle buttons (Default/Outline)
    table/Table.kt         — Table + TableHeader + TableRow + TableCell + TableHeaderCell
    dialog/Dialog.kt       — Dialog + DialogHeader + DialogFooter (Popup-based)
    sheet/Sheet.kt         — Sheet + SheetHeader/Content/Footer (4 sides)
    select/Select.kt       — Dropdown select with chevron/check icons
    breadcrumb/Breadcrumb.kt — Navigation breadcrumbs with auto-separators
    pagination/Pagination.kt — Smart page navigation with icons
    scrollarea/ScrollArea.kt — Custom scrollbar (vertical + horizontal)
    popover/Popover.kt     — Click-triggered popup
    dropdown/DropdownMenu.kt — Action menu with items/separators/labels
    hovercard/HoverCard.kt — Hover-triggered popup with delay
    tooltip/Tooltip.kt     — Tooltip on hover
    icon/Icon.kt           — Foundation-only icon composable (ImageVector + ColorFilter.tint)
    icon/RikkaIcons.kt     — 30 Lucide-style icons as lazy ImageVector definitions
    indication/RikkaIndication.kt — IndicationNodeFactory using Modifier.Node API (draw phase only)
```

### Showcase Website Structure

```
composeApp/src/webMain/kotlin/zed/rainxch/rikkaui/
  App.kt                  — Slim entry point: main(), state, ComposeViewport + RikkaTheme wiring
  theme/
    ThemeConfig.kt        — resolvePalette(), resolveAccent(), accentPreviewColor()
    StylePresets.kt       — stylePresetNames, StylePreset data class, resolveStyle()
  showcase/
    ShowcaseApp.kt        — Root layout composable (page flow orchestration)
    ExamplesGrid.kt       — Responsive grid layouts (Compact/Medium/Expanded)
    HeroSection.kt        — Landing hero with title, description, CTA buttons
    ThemeSection.kt       — Interactive style/palette/accent/dark-mode switcher
    FooterSection.kt      — Footer with tagline
    SectionHeader.kt      — Reusable section header
    WindowSizeClass.kt    — Breakpoint utility (Compact/Medium/Expanded)
    examples/             — 10 realistic example cards in 3-column mosaic grid:
      MusicPlayerExample.kt       — Col1: Music player (Progress, Slider, Toggle, Badge)
      WeatherDashboardExample.kt  — Col1: Weather dashboard (Table, Badge, Progress)
      TaskBoardExample.kt         — Col2: Sprint tasks (Checkbox, Badge variants, Avatar)
      FileExplorerExample.kt      — Col2: File browser (Icon, Input, Button)
      QuickNoteExample.kt         — Col2: Note-taking (Textarea, ToggleGroup, Kbd)
      SystemStatusExample.kt      — Col2: Service monitor (Spinner, Badge, Progress)
      UserProfileExample.kt       — Col3: Profile card (Avatar, Toggle, Button)
      ApiKeyManagerExample.kt     — Col3: API key manager (Kbd, Alert, Icon)
      FeedbackFormExample.kt      — Col3: Star rating (interactive stars, Textarea, Checkbox)
      ActivityFeedExample.kt      — Col3: Activity feed (Avatar, Pagination)
```

### Website Page Flow (order matters for engagement)

1. **Hero** — Title + tagline + CTA buttons
2. **"Components in Action"** — 3-column mosaic grid of 10 example cards (the impressive stuff first!)
3. **"Make It Yours"** — Theme section (palette/accent/dark-mode switcher)
4. **Footer**

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

### Theme System (Fully Customizable)

Every theme token is customizable at 3 levels: presets, factory functions, or full override.

- **Colors:** 20 semantic tokens in bg/fg pairs. 5 base palettes x light/dark = 10 schemes. 7 accent colors x light/dark = 14 accents. Composable via `withAccent()`.
- **Light palettes:** Zinc uses pure white background. Slate/Stone/Gray/Neutral use slightly tinted backgrounds with darker borders/secondary for visible differentiation.
- **Dark palettes:** Each palette has distinct background tints and border colors.
- **Accent system:** `RikkaAccentColor` overrides primary/primaryForeground/ring. Applied via `base.withAccent(accent)`.
- **Typography:** `rikkaTypography(fontFamily, scale, h1Size, h2Size, ...)` — font, proportional scale factor (0.85=compact, 1.15=large), or individual size overrides. Line heights auto-calculated from font size ratios.
- **Spacing:** `rikkaSpacing(base = 4.dp)` — generates proportional scale (xs=1x, sm=2x, md=3x, lg=4x, xl=6x, xxl=8x, xxxl=12x). Presets: `RikkaSpacingPresets.compact()` (3dp), `.comfortable()` (5dp), `.spacious()` (6dp).
- **Shapes:** `rikkaShapes(radius = 10.dp)` — generates sm/md/lg/xl/full from one base radius. Presets: `RikkaShapesPresets.square()` (0dp), `.sharp()` (4dp), `.rounded()` (16dp), `.pill()` (24dp).
- **Motion:** `RikkaMotion(...)` with all params having defaults. Presets: `RikkaMotionPresets.snappy()` (no bounce, fast), `.playful()` (bouncy, slow), `.minimal()` (subtle, short).
- **Style presets (in `:components` library):** `RikkaStyle` data class bundles shapes + spacing + motion + typeScale. `RikkaStylePresets` object provides named presets usable by any consumer:
  - `default()`: radius 10dp, spacing 4dp, balanced motion, scale 1.0
  - `nova()`: radius 4dp, spacing 3dp, snappy motion, scale 0.9 (sharp, dense)
  - `vega()`: radius 20dp, spacing 5dp, playful motion, scale 1.05 (rounded, bouncy)
  - `aurora()`: radius 14dp, spacing 5dp, default motion, scale 1.1 (spacious, large)
  - `nebula()`: radius 0dp, spacing 3dp, minimal motion, scale 0.85 (square, tight)
  - `fromName("Nova")`: resolve by string name
  - `names`: list of all preset names
- **Convenience `RikkaTheme` overload:** `RikkaTheme(colors, style, typography) { ... }` accepts a `RikkaStyle` directly, extracts shapes/spacing/motion from it.
- **Website demo:** ThemeSection uses `RikkaStylePresets` from the library + palette/accent/dark-mode controls.

## Build & Run

```bash
# Compile components library
./gradlew :components:compileKotlinWasmJs

# Run website dev server
./gradlew :composeApp:wasmJsBrowserDevelopmentRun

# Build distributable (for preview server)
./gradlew :composeApp:wasmJsBrowserDevelopmentExecutableDistribution

# Format code (runs before build via hook)
./gradlew :components:ktlintFormat

# Preview server (after building distributable)
# Uses .claude/launch.json: npx serve on port 3000
```

### Known ktlint Issues

- Generated Compose resource files fail ktlint (not our code, ignore)
- `@Composable` function names (uppercase) trigger "Function name should start with lowercase" (Compose convention, ignore)
- `ColorScheme.kt` triggers "single top level declaration" filename warning (ignore)
- Max line length is 140 chars. Break long strings with `+` concatenation.

## Known Bugs & Issues Fixed (for context)

### Fixed in Recent Sessions
- **Toggle thumb invisible in dark mode (default accent):** `primary` is near-white (0xFFFAFAFA), thumb was hardcoded `Color.White`. Fix: thumb now uses `primaryForeground` when checked.
- **Play button icon invisible:** Default Button uses `primary` bg with `primaryForeground` text, but Text inside used theme `foreground` instead. Fix: explicitly set `color = RikkaTheme.colors.primaryForeground` on text inside Default variant buttons.
- **Alert Destructive text invisible in light mode:** `destructiveForeground` is near-white on white `card` background. Fix: Destructive Alert now uses `destructive.copy(alpha=0.1f)` background, `destructive.copy(alpha=0.3f)` border, and `destructive` color for both title and description text.
- **Light mode palettes indistinguishable:** All 5 light palettes had identical white backgrounds and near-identical token values. Fix: Zinc keeps white bg as baseline, Slate/Stone/Gray/Neutral now use tinted backgrounds and more distinct border/secondary colors.

### Important Gotchas
- **Checkbox `label` renders visually** — Don't add a separate Text composable next to Checkbox with label; it will show the text twice.
- **Compose PathBuilder** uses `curveTo` (not `cubicTo`), has no `addOval`/`addRoundRect`. Use custom `circle()` and `roundRect()` helper extensions in RikkaIcons.kt.
- **Compose Wasm canvas rendering** — `window.scrollBy()` doesn't work for scrolling. The scroll is internal to the Compose canvas. Use WheelEvent dispatch or page reload for screenshot navigation.
- **`Modifier.weight()`** is a `RowScope`/`ColumnScope` extension — don't import `foundation.layout.weight` directly (it's internal).

## Icon System

- 30 Lucide-style icons as lazy `ImageVector` definitions in `RikkaIcons.kt`
- Icons: ChevronRight/Down/Left/Up, Check, X, Plus, Minus, Search, ArrowLeft/Right/Up/Down, Menu, MoreHorizontal/Vertical, Mail, User, Heart, Star, Eye, Copy, Trash, Edit, Download, Upload, Sun, Moon, Settings, Send
- Helper extensions: `strokePath()`, `fillPath()`, `circle()`, `roundRect()`
- Icon font multi-pack system (supporting multiple icon sets like Lucide/Phosphor/Material Symbols with style variants Thin/Light/Regular/Bold/Filled/Duotone) — **deferred to separate project**

## MVP Scope

**CLI is deferred.** MVP = design system + components + registry + website.

Priority order:
1. Website showcasing all components with live examples (DONE - 10 original example cards)
2. More components if needed (33 components currently built)
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

## Font Resources

Located at `composeApp/src/webMain/composeResources/font/`:
- inter_light.ttf, inter_regular.ttf, inter_medium.ttf, inter_semi_bold.ttf, inter_bold.ttf, inter_black.ttf
- Wired via `rememberRikkaFontFamily()` in App.kt main()
