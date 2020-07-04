package io.alauda;

import io.alauda.config.Config;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.yaml.snakeyaml.Yaml;

import java.io.*;


public class Option {
    CommandLine cl;
    private String fallbackConfigPath = "src/main/resources/config.yaml";

    public String init(String[] args) {
        Options opts = new Options();
//        opts.addOption("h", false, "");
//        opts.addOption("source", true, "");
        opts.addOption("config", true, "path to yaml config file");
        BasicParser parser = new BasicParser();

        try {
            cl = parser.parse(opts, args);
        } catch (ParseException e) {
            System.err.printf("parse cli args error:%s\n", e.getStackTrace());
            System.exit(1);
        }
        if (cl.getOptions().length == 0) {
            System.err.println("config yaml file not given, will use default settings.");
            return fallbackConfigPath;
        } else {
            if (!cl.hasOption("config")) {
                System.err.println("config yaml file not given, will use default settings.");
                return fallbackConfigPath;
            } else {
                return cl.getOptionValue("config");
            }
        }
    }

    public Config parse(String[] args) {
        String pathToConfig = init(args);
        Yaml yaml = new Yaml();
        InputStream in = null;
        try {
            in = new FileInputStream(pathToConfig);
        } catch (FileNotFoundException e) {
            System.err.printf("config file not found: %s", pathToConfig);
            System.exit(1);
        }
        Config config = yaml.loadAs(in, Config.class);

        return config;
    }

}



