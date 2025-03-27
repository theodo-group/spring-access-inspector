package com.theodo.inspector.impl.utils;

public class UriNormalizer {
    // Make all URI looks like "/something/other/{param}/other2/{param}
    public static String normalizeUri(String methodContext) {
        if (methodContext == null) return null;

        // ensure URI starts with "/"
        if (!methodContext.startsWith("/")) {
            methodContext = "/" + methodContext;
        }

        // remove trailing "/"
        if(methodContext.endsWith("/")){
            methodContext = methodContext.substring(0,methodContext.length() - 1);
        }

        return methodContext;
    }
}
