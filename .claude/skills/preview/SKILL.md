---
name: preview
description: Build the distributable and launch a local preview server to see the website
trigger: when user says "preview", "show me", "let me see", "run the website", "start the server"
---

# Preview Skill

Build and preview the RikkaUI showcase website.

## Steps

1. **Build the distributable:**
   ```bash
   ./gradlew :composeApp:wasmJsBrowserDevelopmentExecutableDistribution
   ```
   Wait for BUILD SUCCESSFUL (timeout: 180s).

2. **Start the preview server** using the launch configuration:
   - The project has a `launch.json` config named `rikka-preview`
   - It serves `composeApp/build/dist/wasmJs/developmentExecutable` on port 3000
   - Use the Claude Preview MCP: `preview_start` with url `http://localhost:3000`

3. **Take a screenshot** after the page loads to show the user what it looks like.

4. **If the user asks to scroll or interact**, use preview MCP tools (click, screenshot, etc.)

## Notes
- The website is a Compose Wasm canvas app — CSS selectors don't work, `window.scrollBy()` doesn't scroll the Compose canvas
- To navigate, use keyboard events or page reload
- The preview server must be stopped before rebuilding
