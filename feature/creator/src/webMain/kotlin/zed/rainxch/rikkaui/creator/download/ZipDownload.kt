package zed.rainxch.rikkaui.creator.download

/**
 * Triggers a browser download of the generated design system as a ZIP.
 *
 * Calls `window.downloadRikkaZip()` defined in `zipHelper.js`,
 * which uses JSZip (loaded via CDN) for client-side ZIP creation.
 *
 * The ZIP includes:
 * - `AppTheme.kt` — generated theme composable
 * - `README.md` — setup instructions
 * - `font/` — ALL weight files for the selected font
 *
 * @param zipName Filename for the download.
 * @param themeCode Contents of `AppTheme.kt`.
 * @param readmeContent Contents of `README.md`.
 * @param fontId Font identifier (resource file prefix).
 * @param fontWeights List of weight suffixes to include in the ZIP
 *     (e.g., ["light", "regular", "medium", "semi_bold", "bold", "black"]).
 */
fun downloadDesignSystemZip(
    zipName: String,
    themeCode: String,
    readmeContent: String,
    fontId: String,
    fontWeights: List<String>,
) {
    val weightsCsv = fontWeights.joinToString(",")
    callDownloadRikkaZip(
        zipName, themeCode, readmeContent, fontId, weightsCsv,
    )
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun(
    """(zipName, themeCode, readmeContent, fontId, weightsCsv) => {
    window.downloadRikkaZip(
        zipName, themeCode, readmeContent, fontId, weightsCsv
    );
}""",
)
private external fun callDownloadRikkaZip(
    zipName: String,
    themeCode: String,
    readmeContent: String,
    fontId: String,
    weightsCsv: String,
)
