interface AnalysisResult {
    contentType: 'TEXT' | 'HTML' | 'CSS';
    lineNumber: number;
    tags?: Record<string, number>;
    cssTargets?: Record<string, number>;
}

class ContentAnalyzer {
    content: string;
    result: AnalysisResult;

    constructor(content: string) {
        this.content = content;
        this.result = { contentType: 'TEXT', lineNumber: this.getLineCount() };
    }

    analyzeContent(): AnalysisResult {
        if (this.isHTML()) {
            this.result.contentType = 'HTML';
            this.result.tags = this.getHTMLTags();
        } else if (this.isCSS()) {
            this.result.contentType = 'CSS';
            this.result.cssTargets = this.getCSSSelectors();
        }

        return this.result;
    }

    getLineCount(): number {
        return this.content.split('\n').length;
    }

    isHTML(): boolean {
        return /<[^!][\s\S]*?>/i.test(this.content);
    }

    getHTMLTags(): Record<string, number> {
        const tags: Record<string, number> = {};
        const tagRegex = /<\s*([^\s>\/]+)/g;
        let match;

        while ((match = tagRegex.exec(this.content)) !== null) {
            const tagName = match[1].toLowerCase();
            tags[tagName] = (tags[tagName] || 0) + 1;
        }

        return tags;
    }

    isCSS(): boolean {
        return /[^{}]+\s*{[^{}]+}/.test(this.content);
    }

    getCSSSelectors(): Record<string, number> {
        const selectors: Record<string, number> = {};
        const selectorRegex = /([^{}]+)\s*{[^{}]+}/g;
        let match;

        while ((match = selectorRegex.exec(this.content)) !== null) {
            const selectorName = match[1].trim();
            selectors[selectorName] = (selectors[selectorName] || 0) + 1;
        }

        return selectors;
    }
}

function analyzeContent(content: string): AnalysisResult {
    const analyzer = new ContentAnalyzer(content);
    return analyzer.analyzeContent();
}

console.log(analyzeContent("this is a test\nSeems to work"));
console.log(analyzeContent("body{blabla} a{color:#fff} a{ padding:0}"));
console.log(analyzeContent("<html><div></div><div></div></html>"));