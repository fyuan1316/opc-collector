package io.alauda;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

public class FileUtil {

    public static InputStream getResource(String pathToFile, boolean inJar) throws FileNotFoundException {
        if (inJar) {
            return getResourceInJar(pathToFile);
        } else {
            return getResource(pathToFile);
        }
    }

    private static InputStream getResourceInJar(String pathToFile) throws FileNotFoundException {
        InputStream in = null;
        URL location = Main.class.getProtectionDomain().getCodeSource().getLocation();
        if (location.toString().endsWith(".jar")) {
            //in jar
            if (!pathToFile.startsWith("/")) {
                pathToFile = "/" + pathToFile;
            }
            in = Main.class.getClass().getResourceAsStream(pathToFile);
            System.out.println(in);
        } else {
            // idea
            in = new FileInputStream(pathToFile);
        }
        return in;
    }

    private static InputStream getResource(String pathToFile) throws FileNotFoundException {
        return new FileInputStream(pathToFile);
    }
}
