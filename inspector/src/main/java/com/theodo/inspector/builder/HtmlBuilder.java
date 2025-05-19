package com.theodo.inspector.builder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.text.StringEscapeUtils;

import com.theodo.inspector.SpringAccessInspector.Editor;
import com.theodo.inspector.analyzer.AnnotationDto;
import com.theodo.inspector.utils.EmbeddedAssets;

public class HtmlBuilder {

    public static void generateHtmlTable(List<AnnotationDto> annotations, String htmlOutputFile, Editor editor) {
        // Generate the HTML table
        StringBuilder htmlTable = new StringBuilder();

        htmlTable.append("\n<head>\n<style>\ntable {\nborder-collapse: collapse;\nwidth:100%;\n}\n")
            .append("th, td {\npadding: 8px;\ntext-align: left;\nborder-bottom: 1px solid #ddd;\n}\n")
            .append("th {\nbackground-color: #f2f2f2;\nposition: relative;\n}\n")
            .append("th .filter-container {\nmargin-top: 5px;\ndisplay: flex;\nalign-items: center;\n}\n")
            .append("th .header-title {\nfont-size: 18px;\nfont-weight: bold;\ncursor: pointer;\n}\n")
            .append("th .filter-input {\nwidth: 85%;\n}\n")
            .append("th .sort-icon {\nfont-size: 20px;\nmargin-left: 5px;\n}\n")
            .append("th .clear-button {\nmargin-left: 5px;\ncursor: pointer;\nfont-size: 20px;\n}\n")
            .append("</style>\n<script>\n")
            .append(EmbeddedAssets.SORT_TABLE_JS).append(EmbeddedAssets.FILTER_TABLE_JS)
            .append("\n</script>\n</head>\n<body>\n<table>\n<tr>\n")
            .append("<th><div class=\"header-title\" onclick=\"sortTable(0)\">Endpoint<span class=\"sort-icon\">↕</span></div>")
            .append("<div class=\"filter-container\">")
            .append("<input class=\"filter-input\" onkeyup=\"filterTable()\" placeholder=\"Filter...\">")
            .append("<span class=\"clear-button\" onclick=\"clearFilter(this.previousElementSibling)\" style=\"display:none\">⤫</span>")
            .append("</div></th>\n")
            .append("<th><div class=\"header-title\" onclick=\"sortTable(1)\">Method<span class=\"sort-icon\">↕</span></div>")
            .append("<div class=\"filter-container\">")
            .append("<input class=\"filter-input\" onkeyup=\"filterTable()\" placeholder=\"Filter...\">")
            .append("<span class=\"clear-button\" onclick=\"clearFilter(this.previousElementSibling)\" style=\"display:none\">⤫</span>")
            .append("</div></th>\n")
            .append("<th><div class=\"header-title\" onclick=\"sortTable(2)\">PreAuthorize<span class=\"sort-icon\">↕</span></div>")
            .append("<div class=\"filter-container\">")
            .append("<input class=\"filter-input\" onkeyup=\"filterTable()\" placeholder=\"Filter...\">")
            .append("<span class=\"clear-button\" onclick=\"clearFilter(this.previousElementSibling)\" style=\"display:none\">⤫</span>")
            .append("</div></th>\n</tr>\n");

        // Iterate over the list and generate each row of the table
        for (AnnotationDto annotation : annotations) {
            addTableRow(htmlTable, annotation, editor);
        }
        // Close the HTML table and body
        htmlTable.append("</table>\n</body>\n</html>");

        // Write the HTML table to a file
        try (PrintWriter writer = new PrintWriter(htmlOutputFile)) {
            writer.println(htmlTable);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void addTableRow(StringBuilder htmlTable, AnnotationDto annotation, Editor editor) {
        String url;
        boolean openInNewTab = false;
        switch (editor) {
            case VSCODE:
                url = annotation.url().buildVscodeUrl();
                break;
            case INTELLIJ:
                url = annotation.url().buildIntellijUrl();
                break;
            case NONE:
            default:
                url = annotation.url().buildUrl();
                openInNewTab = true;
                break;
        }
        htmlTable.append("<tr>\n<td><a href='").append(StringEscapeUtils.escapeHtml4(url)).append("'")
                .append(openInNewTab ? "target='_blank'" : "").append(">")
                .append(StringEscapeUtils.escapeHtml4(annotation.endpoint())).append("</a></td>\n").append("<td>")
                .append(StringEscapeUtils.escapeHtml4(annotation.method().replace("Mapping", "").toUpperCase()))
                .append("</td>\n").append("<td>").append(StringEscapeUtils.escapeHtml4(annotation.preAuthorize()))
                .append("</td>\n</tr>\n");
    }
}
