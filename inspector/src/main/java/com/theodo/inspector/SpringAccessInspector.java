package com.theodo.inspector;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

import com.theodo.inspector.cli.InspectorCommand;
import com.theodo.inspector.impl.PreAuthorizeAnnotationProcessing;
import com.theodo.inspector.impl.ast.ASTReader;
import com.theodo.inspector.impl.utils.AnnotationDto;
import com.theodo.inspector.impl.utils.HtmlTableGenerator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;
import spoon.reflect.CtModel;


@Slf4j
public class SpringAccessInspector extends InspectorCommand {
    public enum Editor {
        VSCODE,
        INTELLIJ,
        NONE
    }

    @Getter
    public Editor editor;

    public void setProjectDirectory(String projectDirectory) {
        this.projectDirectory = projectDirectory;
    }

    public static void main(String[] args) {
        Configurator.setLevel("com.theodo.tools", Level.INFO);

        SpringAccessInspector inspector = new SpringAccessInspector();
        int exitCode = new CommandLine(inspector).execute(args);
        log.info("Process ended with exit code: {}", exitCode);
    }

    public List<AnnotationDto> analyzer() throws IOException {
        try (Stream<File> walk = findPoms(projectDirectory)) { // For all maven projects found in directory
            List<AnnotationDto> annotations = new ArrayList<>();
            walk.forEach(pomFile -> {
                CtModel astModel = ASTReader.readAst(pomFile); // Analyze JAVA AST
                List<AnnotationDto> temporaryAnnotation = PreAuthorizeAnnotationProcessing
                        .visitAllAnnotations(astModel);
                annotations.addAll(temporaryAnnotation);

            });
            return annotations;
        }
    }

    @Override
    public Integer call() throws Exception {
        List<AnnotationDto> annotations = analyzer();
        HtmlTableGenerator.generateHtmlTable(annotations, this.htmlOutputFile, this.editor);
        return 0;
    }

    public static Stream<File> findPoms(String basePath) throws IOException {
        // noinspection resource
        return Files.walk(Paths.get(basePath))
                .filter(path -> path.getFileName().toString().contains("pom.xml"))
                .map(Path::toFile);
    }
}
