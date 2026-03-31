package zed.rainxch.rikkaui.creator.download

fun downloadDesignSystemZip(
    zipName: String,
    themeCode: String,
    readmeContent: String,
    fontId: String,
    fontWeights: List<String>,
) {
    val weightsCsv = fontWeights.joinToString(",")
    callDownloadRikkaZip(
        zipName,
        themeCode,
        readmeContent,
        fontId,
        weightsCsv,
    )
}

@OptIn(ExperimentalWasmJsInterop::class)
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
