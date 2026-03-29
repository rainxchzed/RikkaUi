<div align="center">
<br/>

<img src="https://img.shields.io/badge/六花-RikkaUI-white?style=for-the-badge&labelColor=18181b&color=fafafa" height="40"/>

<br/>
<br/>

# RikkaUI

**A shadcn/ui-inspired component library & design system for Compose Multiplatform**

*Composing elements into harmony*

<br/>

<p>
  <img alt="Kotlin" src="https://img.shields.io/badge/Kotlin-Multiplatform-a503fc?logo=kotlin&logoColor=white&style=for-the-badge"/>
  <img alt="Compose Multiplatform" src="https://img.shields.io/static/v1?style=for-the-badge&message=Compose+Multiplatform&color=4285F4&logo=Jetpack+Compose&logoColor=FFFFFF&label="/>
  <img alt="No Material3" src="https://img.shields.io/badge/No%20Material3-Foundation%20Only-18181b?style=for-the-badge"/>
</p>

<p>
  <img alt="Maven Central" src="https://img.shields.io/maven-central/v/dev.rikkaui/components?style=for-the-badge&logo=apachemaven&logoColor=white&label=Maven%20Central&color=a503fc"/>
  <img alt="License" src="https://img.shields.io/github/license/rainxchzed/RikkaUi?style=for-the-badge&color=4285F4"/>
  <a href="https://github.com/rainxchzed/RikkaUi/stargazers">
    <img alt="Stars" src="https://img.shields.io/github/stars/rainxchzed/RikkaUi?style=for-the-badge&color=ffff00&labelColor=a1a116"/>
  </a>
</p>

<br/>

<a href="https://www.rikkaui.dev"><strong>Live Demo & Docs</strong></a> &nbsp;&bull;&nbsp; <a href="#-quick-start"><strong>Quick Start</strong></a> &nbsp;&bull;&nbsp; <a href="#-theme-system"><strong>Theming</strong></a>

<br/>
<br/>

</div>

---

## Why RikkaUI?

**40+ production-ready components** for Android, iOS, Desktop, and Web — built entirely on Compose Foundation with **zero Material3 dependency**.

- Beautiful defaults, fully customizable theme system
- 5 palettes, 7 accent colors, 5 style presets — one line changes your app's personality
- Copy-paste ownership: no dependency lock-in
- Accessibility-first with spring physics animations

<br/>

## Quick Start

```kotlin
dependencies {
    implementation("dev.rikkaui:components:0.1.0")
}
```

> Works out of the box for **native Android** projects — no KMP setup needed.
> For Compose Multiplatform, add to your `commonMain` source set.

```kotlin
RikkaTheme(
    palette = RikkaPalette.Zinc,
    isDark = true,
) {
    Button(text = "Get Started", onClick = { })
}
```

<br/>

## Theme System

One line changes your entire app's personality:

```kotlin
RikkaTheme(preset = RikkaStylePreset.Default)  // Balanced
RikkaTheme(preset = RikkaStylePreset.Nova)     // Sharp & dense
RikkaTheme(preset = RikkaStylePreset.Vega)     // Rounded & bouncy
RikkaTheme(preset = RikkaStylePreset.Aurora)   // Spacious & large
RikkaTheme(preset = RikkaStylePreset.Nebula)   // Square & tight
```

5 base palettes x 7 accent colors x light/dark = endless combinations.

Explore all options interactively at [rikkaui.dev](https://www.rikkaui.dev).

<br/>

## Platform Support

| Platform | Status |
|----------|--------|
| Android | Stable |
| Desktop (JVM) | Stable |
| iOS | Stable |
| Web (WasmJs) | Stable |

<br/>

## Links

| | |
|---|---|
| Website, Docs & Live Demo | [rikkaui.dev](https://www.rikkaui.dev) |
| Maven Central | [dev.rikkaui](https://central.sonatype.com/namespace/dev.rikkaui) |

<br/>

---

## License

```
Copyright 2026 rainxchzed

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this project except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0
```

<div align="center">
<br/>

*RikkaUI (六花) — composing elements into harmony.*

</div>
