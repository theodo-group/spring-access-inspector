package com.theodo.inspector.impl.utils;

public class AnnotationDto {
    FileUrlDto url;
    String endpoint;
    String method;
    String preAuthorize;

    public AnnotationDto(FileUrlDto url, String endpoint, String method, String preAuthorize) {
        this.url = url;
        this.endpoint = endpoint;
        this.method = method;
        this.preAuthorize = preAuthorize;
    }

    public String endpoint() {
        return endpoint;
    }

    public String method() {
        return method;
    }

    public String preAuthorize() {
        return preAuthorize;
    }
}