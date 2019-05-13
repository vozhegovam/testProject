package com.projects.myproject.fileprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileCommonTools {

    private static final Logger log = LoggerFactory.getLogger(FileCommonTools.class);

    public static final String PATH_TO_FOLDER = "C:\\files";
    public static final String TEXT_SEPARATOR = ",";
    public static final String FILE_FORMAT = ".txt";

    /**
     * The command returns all files in a directory
     *
     * @return  - array of files
     */
    public static File[] getAllFiles(){
        File folder = new File(PATH_TO_FOLDER);
        if(!folder.exists() && !folder.mkdir()){
            throw new RuntimeException("Failed to create directory C:\\files");
        }
        log.info("folder : " + folder.getAbsolutePath());
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null || listOfFiles.length == 0) {
            return null;
        }
        return listOfFiles;
    }

    /**
     * Get next available name for a new file. Current number of files in a directory + 1
     *
     * @return  - number of files in a directory + 1
     */
    public static Integer generateNewFileName() {
        File[] listOfFiles = getAllFiles();
        if (listOfFiles != null && listOfFiles.length != 0) {
            return listOfFiles.length + 1;
        }
        return 1;
    }

    /**
     * The command calculates size of input file
     *
     * @param file - file
     * @return  - size of current file in MegaBytes
     */
    public static int getFileSizeMegaBytes(File file) {
        return (int) file.length() / (1024 * 1024);
    }
}
