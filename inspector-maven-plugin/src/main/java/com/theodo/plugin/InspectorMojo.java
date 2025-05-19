package com.theodo.plugin;

import com.theodo.inspector.SpringAccessInspector;
import com.theodo.inspector.SpringAccessInspector.Editor;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "inspect", defaultPhase = LifecyclePhase.PRE_SITE)
public class InspectorMojo extends AbstractMojo {
    @Parameter(defaultValue = "")
    String projectBaseDir;
    @Parameter(defaultValue = "access_control")
    String outputFileName;
    @Parameter(defaultValue = "none")
    String editor;

    private Editor buildEditor() {
        switch (editor.toLowerCase()) {
            case "intellij":
                return SpringAccessInspector.Editor.INTELLIJ;
            case "vscode":
                return SpringAccessInspector.Editor.VSCODE;
            case "none":
                return SpringAccessInspector.Editor.NONE;
            default:
                getLog().warn("Unknown editor type. Defaulting to NONE.");
                return SpringAccessInspector.Editor.NONE;
        }
    }

    private String buildHtmlOutputFile() {
        return "./" + outputFileName + ".html";
    }

    private String buildProjectDirectory() {
        return projectBaseDir == null ? System.getProperty("user.dir") : projectBaseDir;
    }

    @Override
    public void execute() throws MojoExecutionException {
        try {
            SpringAccessInspector inspector =
                    new SpringAccessInspector(buildProjectDirectory(), buildHtmlOutputFile(), buildEditor());
            inspector.call();
        } catch (Exception e) {
            throw new MojoExecutionException(
                    String.format("Exception occurred while inspecting project %s", projectBaseDir), e);
        }
    }
}
