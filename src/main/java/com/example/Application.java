package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 3.0 entry point.
 *
 * <p>This application demonstrates XSD-to-Java code generation using
 * {@code org.jvnet.jaxb:jaxb-maven-plugin} with two XJC extensions:
 * <ul>
 *   <li><b>immutable-xjc</b> – removes setters, marks generated fields and
 *       classes {@code final}, and creates builder utility classes.</li>
 *   <li><b>jaxb-annotate-plugin</b> – reads {@code annox:annotate} elements
 *       defined inline in {@code library.xsd} and adds the corresponding Java
 *       annotations (e.g. {@code @NotNull}) to the generated sources.</li>
 * </ul>
 *
 * <p>The generated classes live in
 * {@code target/generated-sources/xjc/com/example/library/} and are
 * automatically compiled and added to the application classpath.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
