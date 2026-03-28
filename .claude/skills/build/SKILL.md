---
name: build
description: Build and verify the project. Compiles both :components and :composeApp modules, reports errors clearly.
trigger: when user says "build", "compile", "check if it compiles", or after making code changes that need verification
---

# Build Skill

Run the appropriate Gradle build command based on what changed:

## Decision logic

1. **If only `:components` files changed** (anything under `components/src/`):
   ```bash
   ./gradlew :components:compileKotlinWasmJs
   ```

2. **If only `:composeApp` files changed** (anything under `composeApp/src/`):
   ```bash
   ./gradlew :composeApp:compileKotlinWasmJs
   ```

3. **If both changed, or unsure**:
   ```bash
   ./gradlew :components:compileKotlinWasmJs :composeApp:compileKotlinWasmJs
   ```

4. **If user asks for format/lint**:
   ```bash
   ./gradlew :components:ktlintFormat
   ```

## After build

- If BUILD SUCCESSFUL: report success concisely (e.g. "Build passed.")
- If BUILD FAILED: read the error output carefully, identify the exact file and line, fix it, then rebuild
- Ignore known ktlint warnings (generated Compose resources, uppercase @Composable function names, ColorScheme.kt filename warning)
- Timeout: 180 seconds max
