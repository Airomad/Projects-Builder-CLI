package me.airomad.projects_builder_cli;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Airomad on 02.06.2017.
 *
 */
public class GradleFileBuilder {

    private GradleFileBuilder(GradleFileInternalBuilder builder) {
        File dir = new File(builder.name + "/src/me/airomad/" + builder.name.toLowerCase().replaceAll(" ", "-"));
        if (!dir.exists()) {
            dir.mkdirs();
            File buildFile = new File(builder.name + "/build.gradle");
            File launcherFile = new File(builder.name + "/src/me/airomad/" + builder.name.toLowerCase().replaceAll(" ", "-") + "/Launcher.java");
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(buildFile);
                out.write(getKitContent(builder).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                out = new FileOutputStream(launcherFile);
                out.write(getLauncerClassContent(builder.name).getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static GradleFileInternalBuilder newBuilder() {
        return new GradleFileInternalBuilder();
    }

    static class GradleFileInternalBuilder {
        String kit = null;
        String name = null;

        public GradleFileInternalBuilder setKit(String kit) {
            if (kit != null && (kit.equalsIgnoreCase("kotlin") || kit.equalsIgnoreCase("java")))
                this.kit = kit;
            return this;
        }

        public GradleFileInternalBuilder setProjectName(String name) {
            if (name != null && name.length() > 0)
                this.name = name;
            return this;
        }

        public GradleFileBuilder build() {
            if (name == null) throw new IllegalStateException("Project Name cannot be empty!");
            if (kit == null) throw new IllegalStateException("Kit cannot be empty!");

            return new GradleFileBuilder(this);
        }
    }

    private String getLauncerClassContent(String projectName) {
        return "package me.airomad." + projectName.toLowerCase().replaceAll(" ", "-") + ";\n" +
                "\n" +
                "/**\n" +
                " * Created by Airomad.\n" +
                " */\n" +
                "public class Launcher {\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "    }\n" +
                "    \n" +
                "}\n";
    }

    private String getKitContent(GradleFileInternalBuilder builder) {
        switch (builder.kit) {
            case "java" : return JAVA_BUILD_FILE_TEMPLATE(builder.name);
            default : return JAVA_BUILD_FILE_TEMPLATE(builder.name);
        }
    }

    private String JAVA_BUILD_FILE_TEMPLATE(String projectName) {
        return "buildscript {\n" +
                "  repositories {\n" +
                "    mavenCentral()\n" +
                "  }\n" +
                "}\n" +
                "\n" +
                "apply plugin: 'java'\n" +
                "apply plugin: 'idea'\n" +
                "apply plugin: 'application'\n" +
                "\n" +
                "repositories {\n" +
                "  mavenCentral()\n" +
                "}\n" +
                "\n" +
                "sourceSets {\n" +
                "    main {\n" +
                "        java {\n" +
                "            srcDir 'src'\n" +
                "        }\n" +
                "    }\n" +
                "}\n" +
                "\n" +
                "mainClassName = 'me.airomad." + projectName.toLowerCase().replaceAll(" ", "-") + ".Launcher'\n" +
                "jar {\n" +
                "    baseName = 'opb'\n" +
                "}\n" +
                "\n" +
                "dependencies {\n" +
                "\n" +
                "}\n";
    }
}
