package me.airomad.projects_builder_cli;

import org.apache.commons.cli.*;

/**
 * Created by Airomad on 02.06.2017.
 *
 */
public class Launcher {

    public static void main(String[] args) {

        Options options = new Options();
        Option option = new Option("b", "build", true, "build");
        option.setArgs(2);          // 1 - kit, 2 - Project Name
        options.addOption(option);

        CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = cmdLinePosixParser.parse(options, args);
            if (commandLine.hasOption("b")) {
                String[] arguments = commandLine.getOptionValues("b");
                GradleFileBuilder.newBuilder()
                        .setKit(arguments[0])
                        .setProjectName(arguments[1])
                        .build();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
