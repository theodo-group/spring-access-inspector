package com.theodo.inspector.impl.utils;

import spoon.reflect.declaration.CtElement;

public class FileUrlDto {
    String path;
    String line;

    public FileUrlDto(String path, String line) {
        this.path = path; // starts with /Users/...
        this.line = line; // just the number
    }

    public FileUrlDto(CtElement ctElement) {
        this.path = ctElement.getPosition().getFile().getPath(); // starts with /Users/...
        this.line = String.valueOf(ctElement.getPosition().getLine()); // just the number
    }

    public String getPath() {
        return path;
    }
    public String getLine() {
        return line;
    }

    public String getUrl() {
        return "file://" + path + "#" + line;
    }
    public String getVscodeUrl() {
        return "vscode://file" + path + ":" + line;
    }
    public String getIntellijUrl() {
        return "idea://open?file=" + path + "&line=" + line;
    }
}
