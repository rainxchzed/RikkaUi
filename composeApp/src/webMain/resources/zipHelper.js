/**
 * Creates a ZIP file with the given files and triggers download.
 * Called from Kotlin/Wasm via external declaration.
 */
window.downloadRikkaZip = function(zipName, themeCode, readmeContent, fontId) {
    var zip = new JSZip();
    zip.file("AppTheme.kt", themeCode);
    zip.file("README.md", readmeContent);

    var fontFiles = [
        fontId + "_regular.ttf",
        fontId + "_bold.ttf"
    ];

    var fontPromises = fontFiles.map(function(name) {
        var url = "composeResources/rikkaui.creator.generated.resources/font/" + name;
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
