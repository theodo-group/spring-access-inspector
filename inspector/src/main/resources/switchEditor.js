/**
 * Updates the href attributes of all links in the table based on the selected IDE.
 */
function updateLinks() {
    const prefix = document.getElementById('linkPrefix').value;
    const links = document.querySelectorAll('.link');

    links.forEach(link => {
        const href = link.getAttribute('href');
        const originalEditor = href.split('://')[0];
        let originalPath = '';
        let lineNumber = '';
        switch (originalEditor) {
            case 'file':
                originalPath = href.replace('file://', '').split('#')[0];
                lineNumber = href.split('#')[1] || '';
                break;
            case 'vscode':
                originalPath = href.replace('vscode://file', '').split(':')[0];
                lineNumber = href.replace('vscode://file', '').split(':')[1] || '';
                break;
            case 'idea':
                originalPath = href.replace('idea://open?file=', '').split('&line=')[0];
                lineNumber = href.split('&line=')[1] || '';
                break;
            default:
                return;
        }

        let newHref = '';
        switch (prefix) {
            case 'vscode':
                newHref = `vscode://file${originalPath}${lineNumber ? `:${lineNumber}` : ''}`;
                link.removeAttribute('target');
                break;
            case 'intellij':
                newHref = `idea://open?file=${originalPath}${lineNumber ? `&line=${lineNumber}` : ''}`;
                link.removeAttribute('target');
                break;
            case 'none':
            default:
                newHref = `file://${originalPath}${lineNumber ? `#${lineNumber}` : ''}`;
                link.setAttribute('target', '_blank');
                break;
        }

        link.setAttribute('href', newHref);
    });
}
