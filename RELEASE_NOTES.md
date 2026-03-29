# RikkaUI v0.1.0 — First Light

The first public release of **RikkaUI** — a shadcn/ui-inspired component library and design system for **Compose Multiplatform**.

Beautiful defaults. No Material3. You own the code.

---

## Highlights

- **40+ production-ready components** built entirely on Compose Foundation — zero Material3 dependency
- **Full theme system** with 5 base palettes, 7 accent colors, light/dark modes, and 5 style presets
- **Multiplatform support** — Android, Desktop (JVM), iOS, and WebAssembly (WasmJs)
- **Accessibility-first** — every interactive component ships with roles, content descriptions, and semantic annotations

## Components

**Inputs & Forms**
Button, IconButton, Input, Textarea, Checkbox, RadioButton, Toggle, ToggleGroup, Select, Slider, Label

**Data Display**
Text, Badge, Avatar, Table, List, Progress, Kbd, Skeleton, Spinner, Icon (30 Lucide-style icons)

**Layout & Navigation**
Card, Separator, Tabs, Accordion, Breadcrumb, Pagination, ScrollArea

**Feedback**
Alert, Dialog, Sheet, Tooltip, Popover, HoverCard, DropdownMenu

## Theme System

| Token | What you get |
|-------|-------------|
| **Colors** | 20 semantic tokens, 5 palettes (Zinc/Slate/Stone/Gray/Neutral), 7 accents (Red/Rose/Orange/Green/Blue/Yellow/Violet) |
| **Typography** | 9-level type scale (h1-h4, p, lead, large, small, muted) with custom font support |
| **Spacing** | 7-level scale from a single base value (default 4dp) |
| **Shapes** | 5-level radius scale (sm/md/lg/xl/full) from one base radius |
| **Motion** | Spring and tween animation tokens with presets (Snappy/Playful/Minimal) |
| **Style Presets** | Default, Nova, Vega, Aurora, Nebula — one-line personality for your app |

## Installation

```kotlin
// build.gradle.kts
dependencies {
    implementation("dev.rikkaui:components:0.1.0")
}
```

Works out of the box for **native Android** projects — no KMP configuration needed.

For Compose Multiplatform projects, add the dependency to your `commonMain` source set.

## Platform Targets

| Platform | Artifact |
|----------|----------|
| Android | `components-android` |
| Desktop (JVM) | `components-desktop` |
| iOS arm64 | `components-iosarm64` |
| iOS Simulator | `components-iossimulatorarm64` |
| iOS x64 | `components-iosx64` |
| WasmJs | `components-wasm-js` |

## Links

- Website, Live Demo and Documentation — [rikkaui.dev](https://rikkaui.dev)
- Discord — Coming soon

---

*RikkaUI — composing elements into harmony.*
