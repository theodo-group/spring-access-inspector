package com.theodo.inspector.impl.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class HtmlTableGenerator {

    public static void generateHtmlTable(List<AnnotationDto> annotations, String htmlOutputFile) {
        // Generate the HTML table
        StringBuilder htmlTable = new StringBuilder();
        htmlTable.append("\n<head>\n<style>\n")
                .append("table {\nborder-collapse: collapse;\nwidth:100%;\n}\n")
                .append("th, td {\npadding: 8px;\ntext-align: left;\nborder-bottom: 1px solid #ddd;\n}\n")
                .append("th {\nbackground-color: #f2f2f2;\n}\n")
                .append("</style>\n</head>\n<body>\n")
                .append("<table>\n<tr>\n<th>Endpoint</th>\n<th>Method</th>\n<th>PreAuthorize</th>\n</tr>\n");

        // Iterate over the list and generate each row of the table
        for (AnnotationDto annotation : annotations) {
            htmlTable.append("<tr>\n<td>").append(annotation.endpoint()).append("</td>\n")
                    .append("<td>").append(annotation.method().replace("Mapping", "")).append("</td>\n")
                    .append("<td>").append(annotation.preAuthorize()).append("</td>\n</tr>\n");
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
}
