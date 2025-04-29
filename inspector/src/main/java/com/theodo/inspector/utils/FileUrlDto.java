package com.theodo.inspector.utils;

import spoon.reflect.declaration.CtElement;

/**
 * Represents a file path+line with methods to generate URLs for specific IDEs (e.g., VSCode, IntelliJ) or directly in the file system.
 */
public class FileUrlDto {
    String path; // absolute path starting with /
    String line; // just the line number

    public FileUrlDto(String path, String line) {
        this.path = path;
        this.line = line;
    }

    public FileUrlDto(CtElement ctElement) {
        this.path = ctElement.getPosition().getFile().getPath();
        this.line = String.valueOf(ctElement.getPosition().getLine());
    }

    public String getPath() {
        return path;
    }

    public String getLine() {
        return line;
    }

    public String buildUrl() {
        return "file://" + path + "#" + line;
    }

    public String buildVscodeUrl() {
        return "vscode://file" + path + ":" + line;
    }

    public String buildIntellijUrl() {
        return "idea://open?file=" + path + "&line=" + line;
    }
}
