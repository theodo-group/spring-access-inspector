package com.theodo.tools.metamodel;

import com.theodo.inspector.SpringAccessInspector;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "inspect", defaultPhase = LifecyclePhase.PRE_SITE)
public class InspectorMojo extends AbstractMojo {
    @Parameter(defaultValue = "${project.basedir}", required = true)
    String projectBaseDir;
    @Parameter(defaultValue = "./access_control.html")
    String htmlOutputFile;
    @Parameter(defaultValue = "none")
    String editor;

    public void setEditor(SpringAccessInspector inspector) throws MojoExecutionException {
        switch (editor.toLowerCase()) {
            case "intellij":
                inspector.editor = SpringAccessInspector.Editor.INTELLIJ;
                break;
            case "vscode":
                inspector.editor = SpringAccessInspector.Editor.VSCODE;
                break;
            case "none":
                inspector.editor = SpringAccessInspector.Editor.NONE;
                break;
            default:
                getLog().warn("Unknown editor type. Defaulting to NONE.");
                inspector.editor = SpringAccessInspector.Editor.NONE;
                break;
        }
    }

    @Override
    public void execute() throws MojoExecutionException {
        try {
            SpringAccessInspector inspector = new SpringAccessInspector();
            inspector.projectDirectory = projectBaseDir;
            inspector.htmlOutputFile = htmlOutputFile;
            setEditor(inspector);

            inspector.call();
        } catch (Exception e) {
            throw new MojoExecutionException(String.format("Exception occurred while inspecting project %s", projectBaseDir), e);
        }

    }
}
