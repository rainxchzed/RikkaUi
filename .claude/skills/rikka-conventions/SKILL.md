---
name: rikka-conventions
description: RikkaUI design system conventions. Use when writing or modifying any component, theme token, or showcase code.
user-invocable: false
---

# RikkaUI Conventions (Auto-Applied)

## Absolute Rules

1. **Foundation only** — NEVER import Material3. Use `BasicText`, `clickable`, `InteractionSource`.
2. **Enum-based config** — Variants, sizes, animations = enums. NEVER booleans or strings.
3. **Theme tokens always** — `RikkaTheme.colors`, `.typography`, `.spacing`, `.shapes`, `.motion`. NEVER hardcode.
4. **Motion tokens** — All springs/tweens reference `RikkaTheme.motion`. NEVER hardcode `spring()` or `tween()`.
5. **Localization** — All UI strings use `stringResource(Res.string.*)`. NEVER hardcode English strings.
6. **Accessibility** — Every interactive component: `Role`, `contentDescription` via `label`, `disabled()` semantics.
7. **`@Immutable`** on all theme data classes. `staticCompositionLocalOf` for theme tokens.
8. **KDoc** on every public API with usage examples.
9. **Parameter order**: required → `modifier: Modifier = Modifier` → optional → trailing `content` lambda.
10. **Max line length**: 140 chars.

## Module Boundaries

- `:components` — The library. Zero app-specific code. Zero Material3.
- `:composeApp` — The showcase website. Depends on `:components`. WasmJs only.

## Style System

- `RikkaStylePreset` enum (Default/Nova/Vega/Aurora/Nebula) bundles shapes+spacing+motion+typeScale
- Custom styles via `RikkaStyle` data class
- `RikkaTheme(preset = RikkaStylePreset.Nova)` convenience overload
