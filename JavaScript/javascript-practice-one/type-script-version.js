var ContentAnalyzer = /** @class */ (function () {
    function ContentAnalyzer(content) {
        this.content = content;
        this.result = { contentType: 'TEXT', lineNumber: this.getLineCount() };
    }
    ContentAnalyzer.prototype.analyzeContent = function () {
        if (this.isHTML()) {
            this.result.contentType = 'HTML';
            this.result.tags = this.getHTMLTags();
        }
        else if (this.isCSS()) {
            this.result.contentType = 'CSS';
            this.result.cssTargets = this.getCSSSelectors();
        }
        return this.result;
    };
    ContentAnalyzer.prototype.getLineCount = function () {
        return this.content.split('\n').length;
    };
    ContentAnalyzer.prototype.isHTML = function () {
        return /<[^!][\s\S]*?>/i.test(this.content);
    };
    ContentAnalyzer.prototype.getHTMLTags = function () {
        var tags = {};
        var tagRegex = /<\s*([^\s>\/]+)/g;
        var match;
        while ((match = tagRegex.exec(this.content)) !== null) {
            var tagName = match[1].toLowerCase();
            tags[tagName] = (tags[tagName] || 0) + 1;
        }
        return tags;
    };
    ContentAnalyzer.prototype.isCSS = function () {
        return /[^{}]+\s*{[^{}]+}/.test(this.content);
    };
    ContentAnalyzer.prototype.getCSSSelectors = function () {
        var selectors = {};
        var selectorRegex = /([^{}]+)\s*{[^{}]+}/g;
        var match;
        while ((match = selectorRegex.exec(this.content)) !== null) {
            var selectorName = match[1].trim();
            selectors[selectorName] = (selectors[selectorName] || 0) + 1;
        }
        return selectors;
    };
    return ContentAnalyzer;
}());
function analyzeContent(content) {
    var analyzer = new ContentAnalyzer(content);
    return analyzer.analyzeContent();
}
console.log(analyzeContent("this is a test\nSeems to work"));
console.log(analyzeContent("body{blabla} a{color:#fff} a{ padding:0}"));
console.log(analyzeContent("<html><div></div><div></div></html>"));
