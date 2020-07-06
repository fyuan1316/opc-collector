package io.alauda;

import io.alauda.config.Config;
import org.apache.commons.cli.*;
import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class Option {
    CommandLine cl;
    private String fallbackConfigPath = "src/main/resources/config.yaml";
    String pathToConfig;
    boolean isCustom;

    public InputStream init(String[] args) throws FileNotFoundException {
        Options opts = new Options();
//        opts.addOption("h", false, "");
//        opts.addOption("source", true, "");
        opts.addOption("config", true, "path to yaml config file");
        BasicParser parser = new BasicParser();
        HelpFormatter hf = new HelpFormatter();
        hf.setWidth(110);
        try {
            cl = parser.parse(opts, args);
            if (cl.hasOption('h')) {
                // 打印使用帮助
                hf.printHelp("opc-client", opts, true);
            }
        } catch (ParseException e) {
            System.err.printf("parse cli args error:%s\n", e.getStackTrace());
            System.exit(1);
        }
        InputStream in = null;
        if (cl.getOptions().length == 0 || !cl.hasOption("config")) {
            System.err.println("config yaml file not given, will use default settings.");
            pathToConfig = fallbackConfigPath;
            in = FileUtil.getResource(pathToConfig, true);
            isCustom = false;
        } else {
            pathToConfig = cl.getOptionValue("config");
            in = FileUtil.getResource(pathToConfig, false);
            isCustom = true;
        }
        return in;
    }

    public Config parse(String[] args) {
        InputStream in = null;

        try {
            in = init(args);
        } catch (FileNotFoundException e) {
            System.err.printf("config file not found: %s", pathToConfig);
            System.exit(1);
        }

        Yaml yaml = new Yaml();
        Config config = yaml.loadAs(in, Config.class);
        config.setCustom(isCustom);
        if (null == config.getRun()) {
            System.err.printf("run settings not found in config file: %s\n", pathToConfig);
            System.exit(1);
        }

        return config;
    }

}



