/**
 * Preloads all font files into the browser cache so font switching
 * in the Creator is instant. Called once from Kotlin when the
 * Creator page is first composed.
 */
window.preloadCreatorFonts = function() {
    var base = "composeResources/rikkaui.feature.creator.generated.resources/font/";
    var fonts = [
        "inter", "dm_sans", "lato", "montserrat", "nunito",
        "open_sans", "plus_jakarta_sans", "poppins",
        "raleway", "source_sans3", "work_sans"
    ];
    var weights = [
        "light", "regular", "medium", "semi_bold", "bold", "black"
    ];
    fonts.forEach(function(fontId) {
        weights.forEach(function(weight) {
            var url = base + fontId + "_" + weight + ".ttf";
            fetch(url).catch(function() {});
        });
    });
};

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
