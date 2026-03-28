/**
 * Creates a ZIP file with the given files and triggers download.
 * Called from Kotlin/Wasm via external declaration.
 *
 * @param {string} zipName - Download filename
 * @param {string} themeCode - AppTheme.kt contents
 * @param {string} readmeContent - README.md contents
 * @param {string} fontId - Font prefix (e.g., "inter")
 * @param {string} weightsCsv - Comma-separated weight suffixes
 *                               (e.g., "light,regular,medium,semi_bold,bold,black")
 */
window.downloadRikkaZip = function(zipName, themeCode, readmeContent, fontId, weightsCsv) {
    var zip = new JSZip();
    zip.file("AppTheme.kt", themeCode);
    zip.file("README.md", readmeContent);

    var weights = weightsCsv.split(",");
    var fontPromises = weights.map(function(weight) {
        var name = fontId + "_" + weight + ".ttf";
        var url = "composeResources/rikkaui.feature.creator.generated.resources/font/" + name;
        return fetch(url)
            .then(function(r) { return r.ok ? r.arrayBuffer() : null; })
            .then(function(buf) { if (buf) zip.file("font/" + name, buf); })
            .catch(function() {});
    });

    Promise.all(fontPromises).then(function() {
        return zip.generateAsync({ type: "blob" });
    }).then(function(blob) {
        var url = URL.createObjectURL(blob);
        var a = document.createElement("a");
        a.href = url;
        a.download = zipName;
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    });
};
