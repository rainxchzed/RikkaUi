# RikkaUi — Project Context

## What This Is

**RikkaUi** is a shadcn/ui-inspired component library + design system for Compose Multiplatform.
Name means "snowflake" (六花) or "composing elements into harmony" (立花).
Tagline: "Share UI via Compose Multiplatform UI framework"

**The gap:** No one combines (1) styled components (2) copy-paste ownership (3) registry system (4) CMP support. We fill it.

## Architecture

### Modules

- `:foundation` — Theme tokens and design system foundation. Multiplatform: Android, Desktop (JVM), iOS, WasmJs.
- `:components` — The component library (40+ components). **No Material3 dependency.** Built on `compose.foundation` only. Multiplatform: Android, Desktop (JVM), iOS, WasmJs. Depends on `:foundation`.
- `:composeApp` — The showcase website. WasmJs (browser). Depends on `:components`.
- `:feature:docs` — Documentation pages for all components. Depends on `:components`.
- `:feature:creator` — Design system creator/configurator page.

### Package Structure

```
foundation/src/commonMain/kotlin/zed/rainxch/rikkaui/foundation/
    RikkaColors.kt       — 31 semantic color tokens (@Immutable, staticCompositionLocalOf)
                            + LocalContentColor for implicit foreground propagation
                            + on* naming pattern for content colors (onBackground, onSurface, onPrimary, etc.)
                            + Hover/press tokens (primaryHover, primaryPressed, etc.) default to Color.Unspecified
                            + Tinted container tokens (primaryTinted, destructiveTinted)
    ColorScheme.kt        — 5 base palettes (Zinc/Slate/Stone/Gray/Neutral) x Light/Dark = 10 schemes
                            + 7 accent colors (Red/Rose/Orange/Green/Blue/Yellow/Violet) x Light/Dark
                            + withAccent() extension + RikkaAccentColor data class (onPrimary, primaryHover, primaryPressed)
                            + RikkaPalette enum + RikkaAccentPreset enum with resolve()/applyTo()
                            + All palettes include hover/press values following Tailwind color scale
    RikkaTypography.kt    — 9-level type scale (h1-h4, p, lead, large, small, muted)
    RikkaSpacing.kt       — 7-level spacing scale (xs=4dp through xxxl=48dp, 4dp base grid)
    RikkaShapes.kt        — 5-level shape scale (sm/md/lg/xl/full) from base radius
    RikkaMotion.kt        — Animation token system (springs, tweens, durations, press scales)
    RikkaStyle.kt         — RikkaStyle data class + RikkaStylePreset enum (Default/Nova/Vega/Aurora/Nebula)
    RikkaFontFamily.kt    — Font wrapper with rememberRikkaFontFamily() composable
    RikkaTheme.kt         — 4 RikkaTheme overloads + RikkaTheme object (see Theme System section)

components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui/
    text/Text.kt          — BasicText wrapper with TextVariant enum, heading accessibility
                             Color resolution: explicit → LocalContentColor → variantColor()
    button/Button.kt      — 6 variants (Default/Secondary/Destructive/Outline/Ghost/Link), 4 sizes (Default/Sm/Lg/Icon), 3 animations
                             Provides LocalContentColor to children. Content lambda: @Composable () -> Unit
    button/IconButton.kt  — Convenience wrapper over Button with ButtonSize.Icon. 3 sizes (Sm/Default/Lg). Defaults to Ghost variant.
    card/Card.kt          — 3 variants (Default/Ghost/Elevated) + CardHeader/CardContent/CardFooter
                             Elevated uses subtle border (alpha 0.5) for dark mode visibility
    badge/Badge.kt        — 4 variants (Default/Secondary/Destructive/Outline), text + content overloads
    separator/Separator.kt — Horizontal/Vertical, decorative (clearAndSetSemantics)
    input/Input.kt        — BasicTextField wrapper, animated focus border, placeholder, accessibility
    toggle/Toggle.kt      — Spring-animated thumb, 2 sizes, thumb uses onPrimary when checked
    checkbox/Checkbox.kt   — Animated checkmark, label renders visually (not just a11y)
    radio/RadioButton.kt   — Radio selection control
    textarea/Textarea.kt   — Multi-line text input
    label/Label.kt         — Form label with disabled state
    skeleton/Skeleton.kt   — Pulsing loading placeholder
    spinner/Spinner.kt     — Rotating loading indicator (3 sizes), defaults to LocalContentColor
    alert/Alert.kt         — Alert + AlertTitle + AlertDescription (Default/Destructive variants)
                             Destructive uses semi-transparent red bg/border + destructive text color
    avatar/Avatar.kt       — Fallback initials (3 sizes: Sm/Default/Lg)
    kbd/Kbd.kt             — Keyboard shortcut indicator
    progress/Progress.kt   — Animated progress bar
    slider/Slider.kt       — Draggable range input with pointerInput
    accordion/Accordion.kt — Expandable sections with chevron icon
    tabs/Tabs.kt           — TabList + Tab + TabContent with AnimatedContent transitions
    togglegroup/ToggleGroup.kt — Grouped toggle buttons (Default/Outline)
    table/Table.kt         — Table + TableHeader + TableRow + TableCell + TableHeaderCell
    dialog/Dialog.kt       — Dialog + DialogHeader + DialogFooter (Popup-based)
    sheet/Sheet.kt         — Sheet + SheetHeader/Content/Footer (4 sides)
    select/Select.kt       — Dropdown select with chevron/check icons
    breadcrumb/Breadcrumb.kt — Navigation breadcrumbs with auto-separators
    pagination/Pagination.kt — Smart page navigation with icons, provides LocalContentColor
    scrollarea/ScrollArea.kt — Custom scrollbar (vertical + horizontal)
    popover/Popover.kt     — Click-triggered popup
    dropdown/DropdownMenu.kt — Action menu with items/separators/labels
    contextmenu/ContextMenu.kt — Right-click triggered menu
    alertdialog/AlertDialog.kt — Confirmation dialog with actions
    hovercard/HoverCard.kt — Hover-triggered popup with delay
    tooltip/Tooltip.kt     — Tooltip on hover
    toast/Toast.kt         — ToastHost + LocalToastHostState, rendered via Scaffold slot (not Popup)
    scaffold/Scaffold.kt   — SubcomposeLayout with topBar + content + toastHost slots
    collapsible/Collapsible.kt — Animated expand/collapse container
    navigationbar/NavigationBar.kt — Bottom navigation bar
    topappbar/TopAppBar.kt — Top app bar with navigation + actions
    list/List.kt           — RikkaList with ListVariant (Unordered/Ordered/None), string items + DSL (ListScope)
    icon/Icon.kt           — Foundation-only icon composable (ImageVector + ColorFilter.tint), defaults to LocalContentColor
    icon/RikkaIcons.kt     — 30 Lucide-style icons as lazy ImageVector definitions
```

### Showcase Website Structure

```
composeApp/src/webMain/kotlin/zed/rainxch/rikkaui/
  App.kt                  — Entry point: main(), ComposeViewport, RikkaTheme wiring, bindToBrowserNavigation()
                            Uses Scaffold with topBar + toastHost slots. Provides LocalToastHostState.
  navigation/
    Routes.kt             — 5 serializable routes: HomeRoute, CreatorRoute, DocsRoute, ComponentsRoute, ComponentDetailRoute
  shell/
    TopNavBar.kt          — Top navigation bar with route links + dark mode toggle
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
    examples/             — 10 realistic example cards in 3-column mosaic grid
```

### Navigation (Browser URL Sync)

Uses `androidx.navigation.bindToBrowserNavigation()` (ExperimentalBrowserHistoryApi) for hash-based URL routing:
- `rikkaui.dev#home` → Home
- `rikkaui.dev#docs` → Docs
- `rikkaui.dev#create` → Creator
- `rikkaui.dev#docs/components/{componentId}` → Component detail

Routes use `@SerialName` for clean URL paths. Browser back/forward and page refresh work.

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
11. **LocalContentColor propagation** — Button (and other containers) provide `LocalContentColor` via `CompositionLocalProvider`. Content lambda is `@Composable () -> Unit`. Text, Icon, and Spinner auto-resolve color: explicit → `LocalContentColor` → theme default. Never pass foreground color as a lambda parameter.

### Performance Rules

- `@Immutable` on all theme data classes
- `staticCompositionLocalOf` for theme tokens (zero read-tracking overhead)
- `graphicsLayer {}` lambda for animations (skips composition + layout phases)
- `Modifier.Node` API for custom modifiers (never `composed {}`)
- Spring animations as default for interactions (handles interruptions)
- Lambda-based `Modifier.offset {}` to defer reads to placement step

### Theme System (Fully Customizable)

Every theme token is customizable at 3 levels: presets, factory functions, or full override.

- **Colors:** 31 semantic tokens with `on*` naming pattern (background/onBackground, surface/onSurface, primary/onPrimary, etc.). 5 base palettes x light/dark = 10 schemes. 7 accent colors x light/dark = 14 accents. Composable via `withAccent()`. Includes hover/press tokens (primaryHover, primaryPressed, etc.) and tinted containers (primaryTinted, destructiveTinted).
- **Light palettes:** Zinc uses pure white background. Slate/Stone/Gray/Neutral use slightly tinted backgrounds with darker borders/secondary for visible differentiation.
- **Dark palettes:** Each palette has distinct background tints and border colors.
- **Accent system:** `RikkaAccentColor` overrides primary/onPrimary/ring/primaryHover/primaryPressed. Applied via `base.withAccent(accent)`.
- **Typography:** `rikkaTypography(fontFamily, scale, h1Size, h2Size, ...)` — font, proportional scale factor (0.85=compact, 1.15=large), or individual size overrides. Line heights auto-calculated from font size ratios.
- **Spacing:** `rikkaSpacing(base = 4.dp)` — generates proportional scale (xs=1x, sm=2x, md=3x, lg=4x, xl=6x, xxl=8x, xxxl=12x). Presets: `RikkaSpacingPresets.compact()` (3dp), `.comfortable()` (5dp), `.spacious()` (6dp).
- **Shapes:** `rikkaShapes(radius = 10.dp)` — generates sm/md/lg/xl/full from one base radius. Presets: `RikkaShapesPresets.square()` (0dp), `.sharp()` (4dp), `.rounded()` (16dp), `.pill()` (24dp).
- **Motion:** `RikkaMotion(...)` with all params having defaults. Presets: `RikkaMotionPresets.snappy()` (no bounce, fast), `.playful()` (bouncy, slow), `.minimal()` (subtle, short).
- **Style presets (in `:components` library):** `RikkaStylePreset` enum with type-safe entries. Each entry has a `.style` property returning a `RikkaStyle` data class (shapes + spacing + motion + typeScale). Shortcut properties: `.shapes`, `.spacing`, `.motion`, `.typeScale`, `.label`.
  - `RikkaStylePreset.Default`: radius 10dp, spacing 4dp, balanced motion, scale 1.0
  - `RikkaStylePreset.Nova`: radius 4dp, spacing 3dp, snappy motion, scale 0.9 (sharp, dense)
  - `RikkaStylePreset.Vega`: radius 20dp, spacing 5dp, playful motion, scale 1.05 (rounded, bouncy)
  - `RikkaStylePreset.Aurora`: radius 14dp, spacing 5dp, default motion, scale 1.1 (spacious, large)
  - `RikkaStylePreset.Nebula`: radius 0dp, spacing 3dp, minimal motion, scale 0.85 (square, tight)
  - Iterate with `RikkaStylePreset.entries`
- **`RikkaTheme` overloads (4 total):**
  1. `RikkaTheme(colors, typography, spacing, shapes, motion) { }` — full manual control, all params have defaults
  2. `RikkaTheme(colors, style: RikkaStyle, typography) { }` — `style` has NO default (avoids overload ambiguity)
  3. `RikkaTheme(colors, preset: RikkaStylePreset, typography) { }` — `preset` has NO default
  4. `RikkaTheme(palette: RikkaPalette, accent, isDark, preset) { }` — `palette` has NO default, all-in-one overload
  - **Important:** Distinguishing params (`style`, `preset`, `palette`) intentionally have no defaults. This prevents `RikkaTheme { }` from matching multiple overloads. `RikkaTheme { }` always resolves to overload #1.
- **Website demo:** ThemeSection iterates `RikkaStylePreset.entries` for type-safe style switching.

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

# Generate registry JSON manifests
./gradlew generateRegistryJson

# Build CLI shadow JAR + copy to web resources for Vercel
./gradlew :cli:shadowJar
cp cli/build/libs/rikkaui.jar composeApp/src/webMain/resources/rikkaui.jar

# Preview server (after building distributable)
# Uses .claude/launch.json: npx serve on port 3000

# Local Maven publish test (skip signing for local)
./gradlew publishToMavenLocal --no-configuration-cache -PRELEASE_SIGNING_ENABLED=false

# Full Maven Central publish (CI only)
./gradlew publishAndReleaseToMavenCentral --no-configuration-cache
```

### ktlint Configuration

- **`.editorconfig`** at project root configures ktlint rules
- `ktlint_function_naming_ignore_when_annotated_with = Composable` — allows uppercase @Composable function names
- `ktlint_standard_filename = disabled` — ignores filename warnings (e.g., ColorScheme.kt)
- `[**/build/generated/**] ktlint = disabled` — skips generated code
- Both `:components` and `:foundation` have `ktlint { ignoreFailures = true }` — warns but never fails builds
- Max line length is 140 chars. Break long strings with `+` concatenation.

## Maven Central Publishing

### Coordinates

- **GroupId:** `dev.rikkaui`
- **ArtifactId:** `foundation`, `components` (from module names)
- **Version:** Defined in `gradle.properties` as `VERSION_NAME`

### Published Artifacts (per module)

| Platform | Artifact suffix |
|----------|----------------|
| Kotlin Metadata | (base name) |
| Android | `-android` |
| Desktop (JVM) | `-desktop` |
| iOS arm64 | `-iosarm64` |
| iOS Simulator arm64 | `-iossimulatorarm64` |
| iOS x64 | `-iosx64` |
| WasmJs | `-wasm-js` |

### Usage by Consumers

```kotlin
// For ANY platform (Android, Desktop, iOS, Web) — Gradle resolves the right artifact
dependencies {
    implementation("dev.rikkaui:components:0.1.0")
}
```

Native Android developers need NO KMP configuration — just add the dependency.

### Publishing Stack

- **Plugin:** `com.vanniktech.maven.publish` v0.30.0
- **Sonatype:** Central Portal (`SONATYPE_HOST=CENTRAL_PORTAL`)
- **Signing:** `useInMemoryPgpKeys()` with GPG key decoded from base64 in CI
- **CI:** GitHub Actions on `macos-latest` (needed for iOS targets), triggered on GitHub Release

### gradle.properties (Publishing Config)

```properties
GROUP=dev.rikkaui
VERSION_NAME=0.1.0
SONATYPE_HOST=CENTRAL_PORTAL
RELEASE_SIGNING_ENABLED=true
POM_URL=https://github.com/rainxchzed/RikkaUi
POM_LICENSE_NAME=The Apache Software License, Version 2.0
```

### Library Module Build Config (both foundation and components)

```kotlin
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.gradle.ktlint)
    alias(libs.plugins.vanniktechMavenPublish)
}

android {
    namespace = "dev.rikkaui.<module>"
    compileSdk = 35
    defaultConfig { minSdk = 24 }
}

kotlin {
    androidTarget { publishLibraryVariants("release") }
    jvm("desktop")
    iosX64(); iosArm64(); iosSimulatorArm64()
    wasmJs { browser() }
    applyDefaultHierarchyTemplate()
}

signing {
    useInMemoryPgpKeys(
        findProperty("signingInMemoryKeyId") as String?,
        findProperty("signingInMemoryKey") as String?,
        findProperty("signingInMemoryKeyPassword") as String?,
    )
}

ktlint { ignoreFailures = true }
```

### GitHub Actions Workflow (`.github/workflows/publish.yml`)

- Triggered on `release` event (type: `released`)
- Runs on `macos-latest`
- **Critical:** Must decode base64 GPG key before passing to Gradle:
  ```yaml
  - name: Decode GPG key
    run: |
      echo "${{ secrets.GPG_KEY_CONTENTS }}" | base64 --decode > /tmp/secring.asc
      { echo 'GPG_KEY<<EOF'; cat /tmp/secring.asc; echo 'EOF'; } >> "$GITHUB_ENV"
  ```
- Passes decoded key as `ORG_GRADLE_PROJECT_signingInMemoryKey: ${{ env.GPG_KEY }}`

### GitHub Secrets Required

| Secret | Value |
|--------|-------|
| `MAVEN_CENTRAL_USERNAME` | Sonatype Central Portal token username |
| `MAVEN_CENTRAL_PASSWORD` | Sonatype Central Portal token password |
| `SIGNING_KEY_ID` | Last 8 chars of GPG key ID (e.g., `7601DC0B`) |
| `SIGNING_PASSWORD` | GPG key passphrase |
| `GPG_KEY_CONTENTS` | Base64-encoded ASCII-armored GPG private key (`gpg --export-secret-keys --armor KEY_ID \| base64`) |

### Sonatype Setup

- Namespace `dev.rikkaui` verified via DNS TXT record on `rikkaui.dev`
- GPG public key uploaded to `keyserver.ubuntu.com`

## Deployment (Vercel)

- **Build command:** `chmod +x build.sh && ./build.sh`
- **Output dir:** `composeApp/build/dist/wasmJs/productionExecutable`
- **`build.sh`:** Installs `libatomic` (required by Node.js v25 on Amazon Linux 2023), then runs Gradle
- Kotlin 2.3.0 downloads Node.js v25 internally; `version.set()` on `NodeJsEnvSpec` is ignored
- **Static assets served via Vercel** from `composeApp/src/webMain/resources/`:
  - `rikkaui.dev/install.sh` — CLI install script
  - `rikkaui.dev/rikkaui.jar` — CLI shadow JAR (not in git, copied during build)
  - `rikkaui.dev/r/` — Registry JSON manifests (auto-generated)

## Known Bugs & Issues Fixed (for context)

### Fixed in Recent Sessions
- **Toggle thumb invisible in dark mode (default accent):** `primary` is near-white (0xFFFAFAFA), thumb was hardcoded `Color.White`. Fix: thumb now uses `onPrimary` when checked.
- **Button/Icon/Text color mismatch:** Children inside Button used theme `onBackground` instead of button's resolved foreground. Fix: replaced foreground lambda with `LocalContentColor` propagation. Text/Icon/Spinner now auto-resolve: explicit → LocalContentColor → theme default.
- **RikkaTheme overload ambiguity:** `RikkaTheme { }` matched 4 overloads (all had defaults). Fix: distinguishing params (`style`, `preset`, `palette`) now have NO defaults.
- **Toast relative positioning:** Toast used Popup which positions relative to parent (not viewport) in CMP WasmJs. Fix: Toast integrated into Scaffold as a slot via SubcomposeLayout, rendered last = always on top. Access via `LocalToastHostState`.
- **Card Elevated indistinguishable from Ghost in dark mode:** Shadow invisible on dark backgrounds. Fix: Elevated variant now uses `border = colors.border.copy(alpha = 0.5f)`.
- **Alert Destructive text invisible in light mode:** `destructiveForeground` is near-white on white `card` background. Fix: Destructive Alert now uses `destructive.copy(alpha=0.1f)` background, `destructive.copy(alpha=0.3f)` border, and `destructive` color for both title and description text.
- **Light mode palettes indistinguishable:** All 5 light palettes had identical white backgrounds and near-identical token values. Fix: Zinc keeps white bg as baseline, Slate/Stone/Gray/Neutral now use tinted backgrounds and more distinct border/secondary colors.
- **Spinner cropped in Button loading state:** drawArc stroke extends beyond Canvas bounds. Fix: added `padding(stroke/2)` inset.
- **Tabs text overlapping:** DemoBox is a Box (not Column), so TabList and TabContent stacked. Fix: wrapped in Column.
- **Button loading hides content:** Users want to see both spinner AND text while loading. Fix: loading shows both Spinner and content.
- **CodeBlock copy not working:** Ctrl+C doesn't work in Compose WasmJs canvas. Fix: added explicit Copy button using `LocalClipboardManager` + `AnnotatedString`.

### Important Gotchas
- **Checkbox `label` renders visually** — Don't add a separate Text composable next to Checkbox with label; it will show the text twice.
- **Compose PathBuilder** uses `curveTo` (not `cubicTo`), has no `addOval`/`addRoundRect`. Use custom `circle()` and `roundRect()` helper extensions in RikkaIcons.kt.
- **Compose Wasm canvas rendering** — `window.scrollBy()` doesn't work for scrolling. The scroll is internal to the Compose canvas. Use WheelEvent dispatch or page reload for screenshot navigation.
- **Compose WasmJs Popup positions relative to parent** — No CSS `position: fixed` equivalent. Don't use Popup for viewport-level overlays (toasts, snackbars). Use Scaffold slots or SubcomposeLayout instead.
- **Compose WasmJs clipboard** — `SelectionContainer` + Ctrl+C doesn't copy text reliably. Use `LocalClipboardManager.setText(AnnotatedString(...))` with an explicit Copy button.
- **`Modifier.weight()`** is a `RowScope`/`ColumnScope` extension — don't import `foundation.layout.weight` directly (it's internal).
- **DemoBox in docs uses `Box`** — Children stack (overlap), not flow. Wrap multiple elements in `Column` or `Row`.
- **WasmJs has no `dynamic` type** — Unlike Kotlin/JS. Use typed APIs (e.g., `(Event) -> Unit` not `(dynamic) -> Unit`).
- **CMP `SavedState` is not Android `Bundle`** — No `getString()` method. Use `toRoute<T>()` on `NavBackStackEntry` instead.
- **Kotlin 2.3.0 `kotlinOptions.jvmTarget`** — Deprecated and unresolved. Remove it entirely; AGP handles JVM target via `compileOptions`.
- **Vanniktech v0.30.0 auto-reads `GROUP`/`VERSION_NAME`** from `gradle.properties`. Don't call `coordinates()` manually — it throws because groupId is already final.
- **GPG key for CI must be base64-decoded** before passing to `useInMemoryPgpKeys()`. The secret stores base64, workflow must decode it.
- **macOS `base64` has no `-w` flag** — It doesn't wrap by default (unlike Linux). Use `base64 -w 0` on Linux only.

## Icon System

- 30 Lucide-style icons as lazy `ImageVector` definitions in `RikkaIcons.kt`
- Icons: ChevronRight/Down/Left/Up, Check, X, Plus, Minus, Search, ArrowLeft/Right/Up/Down, Menu, MoreHorizontal/Vertical, Mail, User, Heart, Star, Eye, Copy, Trash, Edit, Download, Upload, Sun, Moon, Settings, Send
- Helper extensions: `strokePath()`, `fillPath()`, `circle()`, `roundRect()`
- Icon font multi-pack system — **deferred to separate project**

## MVP Scope

MVP = design system + components + registry + CLI + website.

Status:
1. Website showcasing all components with live examples (DONE - 10 original example cards)
2. Documentation for all components (DONE - integrated into website via `:feature:docs`, including CLI docs)
3. Maven Central publishing (DONE - v0.1.0 on `dev.rikkaui`)
4. Registry system: JSON manifests with auto-dependency detection (DONE - `GenerateRegistryJsonTask` in buildSrc)
5. CLI: `rikkaui init/add/list` commands (DONE - self-hosted on Vercel via `rikkaui.dev/install.sh`)
6. Community: Post on X/LinkedIn, Discord, collect weekly feedback

## CLI

- **Module:** `:cli` — Kotlin/JVM shadow JAR
- **Install:** `curl -fsSL https://rikkaui.dev/install.sh | bash`
- **Commands:** `rikkaui init`, `rikkaui add <components...>`, `rikkaui list`
- **Self-hosted on Vercel** — install.sh and rikkaui.jar served as static files from `composeApp/src/webMain/resources/`
- **Independent of GitHub Releases** — no GitHub API calls in install script
- **Docs:** CLI documentation page at `feature/docs/.../pages/CliDoc.kt`, accessible via website

## Registry System

- **`GenerateRegistryJsonTask`** in `buildSrc/` — Gradle task that generates JSON manifests for all components
- **Auto-dependency detection** — parses Kotlin source files for import statements, resolves inter-component dependencies
- **Output:** `composeApp/src/webMain/resources/r/` — one JSON file per component (e.g., `button.json`, `card.json`)
- **Cleans stale files** — deletes `r/` directory before regenerating
- **Fields:** name, description, category, dependencies, files, registryDependencies

## Key Technical Decisions

- **Foundation only, no Material3** — Compose Foundation provides BasicText, clickable, InteractionSource. We build everything else.
- **staticCompositionLocalOf** for theme — tokens rarely change, avoid tracking overhead.
- **Spring physics default** — Handles interruptions gracefully, feels native across platforms.
- **LocalContentColor over foreground lambda** — Implicit color propagation via `CompositionLocalProvider`. Children auto-inherit foreground color. No need to pass colors through lambdas.
- **Experimental Styles API** — Future migration target when Foundation 1.11+ stabilizes. Currently we use manual InteractionSource + animateColorAsState.
- **Wasm/Web target** — Canvas-based (no SEO). Target dashboards/apps, not content sites. Hover states critical.
- **bindToBrowserNavigation()** — Official Compose Navigation API for browser hash routing. Replaces custom HashRouter.
- **Vanniktech maven-publish** — Handles POM generation, signing, Sonatype upload for all KMP targets.

## Who Is This For

Developers starting NEW Compose Multiplatform projects who want:
- Beautiful default styling without Material3 constraints
- Copy-paste components they own (no dependency lock-in)
- shadcn-style registry for community component sharing

## Specialized Agents (`.claude/agents/`)

Four agents handle different aspects of RikkaUI development. **Auto-invoke them during conversation based on the task — the user should never have to ask for a specific agent.**

| Agent | Model | When to use |
|---|---|---|
| `rikka-researcher` | Opus | Studying other design systems, comparing approaches, extracting patterns |
| `rikka-component-writer` | Opus | Writing or improving components, quality passes |
| `rikka-doc-writer` | Sonnet | Creating/updating doc pages, bulk doc migrations |
| `rikka-auditor` | Opus | Reviewing components for quality, accessibility, correctness |

**Workflow pattern:** Researcher → Component Writer → Doc Writer → Auditor. Spawn them as subagents in parallel where possible.

## Font Resources

Located at `composeApp/src/webMain/composeResources/font/`:
- inter_light.ttf, inter_regular.ttf, inter_medium.ttf, inter_semi_bold.ttf, inter_bold.ttf, inter_black.ttf
- Wired via `rememberRikkaFontFamily()` in App.kt main()
