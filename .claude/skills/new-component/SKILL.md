---
name: new-component
description: Scaffold a new RikkaUI component following all project conventions
disable-model-invocation: true
allowed-tools: Read, Write, Edit, Glob, Grep, Bash(./gradlew :components:*)
---

# Create New RikkaUI Component

Scaffold `$ARGUMENTS` in the `:components` library.

## Current components for reference
!`ls components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui/`

## Theme tokens available
!`grep "val " components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/theme/RikkaColors.kt | head -20`

## Steps

1. **Create file** at:
   `components/src/commonMain/kotlin/zed/rainxch/rikkaui/components/ui/{lowercase}/{ Name}.kt`

2. **Follow conventions** (see [conventions.md](conventions.md) for full details):
   - Package: `zed.rainxch.rikkaui.components.ui.{lowercase}`
   - Foundation only — `BasicText`, `clickable`, NO Material3
   - Enum-based variants/sizes (not booleans, not strings)
   - Private `resolve*()` functions for colors/sizes
   - All tokens via `RikkaTheme.colors`, `.typography`, `.spacing`, `.shapes`, `.motion`
   - Both overloads: generic (content lambda) + convenience (string text)
   - Accessibility: `Role`, `label` param, `disabled()` semantics
   - `@Immutable` on data classes
   - Parameter order: required → `modifier` → optional → trailing `content`
   - KDoc with usage examples
   - Max 140 char lines

3. **Compile:**
   ```bash
   ./gradlew :components:compileKotlinWasmJs
   ```

4. **Update CLAUDE.md** package structure with the new entry.

5. **Ask user** if they want a showcase example for the website.
