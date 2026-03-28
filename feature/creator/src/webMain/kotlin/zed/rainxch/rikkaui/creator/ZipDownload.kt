package zed.rainxch.rikkaui.creator

/**
 * Triggers a browser download of the generated design system as a ZIP.
 *
 * Calls `window.downloadRikkaZip()` defined in `zipHelper.js`,
 * which uses JSZip (loaded via CDN) for client-side ZIP creation.
 *
 * @param zipName Filename for the download (e.g., "rikka-theme.zip").
 * @param themeCode Contents of `AppTheme.kt`.
 * @param readmeContent Contents of `README.md`.
 * @param fontId Font identifier used to resolve font resource paths.
 */
fun downloadDesignSystemZip(
    zipName: String,
    themeCode: String,
    readmeContent: String,
    fontId: String,
) {
    callDownloadRikkaZip(zipName, themeCode, readmeContent, fontId)
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun(
    """(zipName, themeCode, readmeContent, fontId) => {
    window.downloadRikkaZip(zipName, themeCode, readmeContent, fontId);
}""",
)
private external fun callDownloadRikkaZip(
    zipName: String,
    themeCode: String,
    readmeContent: String,
    fontId: String,
)
