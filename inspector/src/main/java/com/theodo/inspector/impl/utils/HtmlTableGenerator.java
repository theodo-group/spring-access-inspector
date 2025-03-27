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
                .append("th {\nbackground-color: #f2f2f2;\ncursor: pointer;\nposition: relative;\n}\n")
                .append("th .sort-icon {\nfont-size: 16px;\nmargin-left: 5px;\n}\n")
                .append("</style>\n<script>\n")
                .append("function sortTable(n) {\n")
                .append("  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;\n")
                .append("  table = document.querySelector('table');\n")
                .append("  switching = true;\n")
                .append("  dir = 'asc';\n")
                .append("  var headers = table.querySelectorAll('th');\n")
                .append("  headers.forEach(header => header.querySelector('.sort-icon').textContent = '');\n")
                .append("  while (switching) {\n")
                .append("    switching = false;\n")
                .append("    rows = table.rows;\n")
                .append("    for (i = 1; i < (rows.length - 1); i++) {\n")
                .append("      shouldSwitch = false;\n")
                .append("      x = rows[i].getElementsByTagName('TD')[n];\n")
                .append("      y = rows[i + 1].getElementsByTagName('TD')[n];\n")
                .append("      if (dir == 'asc') {\n")
                .append("        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {\n")
                .append("          shouldSwitch = true;\n")
                .append("          break;\n")
                .append("        }\n")
                .append("      } else if (dir == 'desc') {\n")
                .append("        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {\n")
                .append("          shouldSwitch = true;\n")
                .append("          break;\n")
                .append("        }\n")
                .append("      }\n")
                .append("    }\n")
                .append("    if (shouldSwitch) {\n")
                .append("      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);\n")
                .append("      switching = true;\n")
                .append("      switchcount++;\n")
                .append("    } else {\n")
                .append("      if (switchcount == 0 && dir == 'asc') {\n")
                .append("        dir = 'desc';\n")
                .append("        switching = true;\n")
                .append("      }\n")
                .append("    }\n")
                .append("  }\n")
                .append("  headers[n].querySelector('.sort-icon').textContent = dir === 'asc' ? '▼' : '▲';\n")
                .append("}\n")
                .append("</script>\n</head>\n<body>\n")
                .append("<table>\n<tr>\n")
                .append("<th onclick=\"sortTable(0)\">Endpoint<span class=\"sort-icon\">▼</span></th>\n")
                .append("<th onclick=\"sortTable(1)\">Method<span class=\"sort-icon\"></span></th>\n")
                .append("<th onclick=\"sortTable(2)\">PreAuthorize<span class=\"sort-icon\"></span></th>\n</tr>\n");

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
