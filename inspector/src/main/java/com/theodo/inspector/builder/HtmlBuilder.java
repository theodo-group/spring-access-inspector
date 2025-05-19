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
        StringBuilder htmlTable = new StringBuilder();

        htmlTable.append("\n<head>\n");
        htmlTable.append(generateStyles());
        htmlTable.append(generateScripts());
        htmlTable.append("</head>\n<body>\n<table>\n<tr>\n");
        htmlTable.append(generateTableHeader("Endpoint", 0, "43%"));
        htmlTable.append(generateTableHeader("Method", 1, "14%"));
        htmlTable.append(generateTableHeader("PreAuthorize", 2, "43%"));
        htmlTable.append("</tr>\n");

        // Iterate over the list and generate each row of the table
        for (AnnotationDto annotation : annotations) {
            addTableRow(htmlTable, annotation, editor);
        }

        htmlTable.append("</table>\n</body>\n</html>");

        // Write the HTML table to a file
        try (PrintWriter writer = new PrintWriter(htmlOutputFile)) {
            writer.println(htmlTable);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static String generateStyles()
    {
        return """
            <style>
            table {
                border-collapse: collapse;
                width: 100%;
            }
            th, td {
                padding: 8px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }
            th {
                background-color: #f2f2f2;
                position: relative;
            }
            th .filter-container {
                margin-top: 5px;
                display: flex;
                align-items: center;
            }
            th .header-title {
                font-size: 18px;
                font-weight: bold;
                cursor: pointer;
            }
            th .filter-input {
                width: 85%;
            }
            th .sort-icon {
                font-size: 20px;
                margin-left: 5px;
            }
            th .clear-button {
                margin-left: 5px;
                cursor: pointer;
                font-size: 16px;
            }
            </style>
            """;
    }

    private static String generateScripts() {
        // On build, js files in "inspector/src/main/resources" will be stringified and embedded
        // in "inspector/target/generated-sources/embedded/com/theodo/inspector/utils/EmbeddedAssets.java"
        return """
            <script>
            """ + EmbeddedAssets.SORT_TABLE_JS + EmbeddedAssets.FILTER_TABLE_JS + """
            </script>
            """;
    }

    private static String generateTableHeader(String title, int columnIndex, String width) {
        return "<th style=\"width: " + width + ";\">" +
                "<div class=\"header-title\" onclick=\"sortTable(" + columnIndex + ")\">" + title +
                "<span class=\"sort-icon\">↕</span></div>" +
                "<div class=\"filter-container\">" +
                "<input class=\"filter-input\" onkeyup=\"filterTable()\" placeholder=\"Filter...\">" +
                "<span class=\"clear-button\" onclick=\"clearFilter(this.previousElementSibling)\" style=\"display:none\">⤫</span>" +
                "</div>" +
                "</th>";
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
