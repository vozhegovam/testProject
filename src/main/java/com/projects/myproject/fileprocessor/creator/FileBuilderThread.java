package com.projects.myproject.fileprocessor.creator;

import com.projects.myproject.fileprocessor.FileCommonTools;
import org.apache.tomcat.util.buf.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FileBuilderThread implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(FileBuilderThread.class);
    private Integer fileName;
    private Random random;

    public FileBuilderThread(Integer fileName) {
        this.fileName = fileName;
        this.random = new Random();
    }

    @Override
    public void run() {
        generateFile();
    }

    /**
     * The command creates a new file (size ~1gb)
     * Data of the file - array of random numbers from 0 to 9,223,372,036,854,775,807 separated by comma
     */
    private void generateFile() {
        Integer size = 0;
        File file = createFile(fileName);
        while (size < 1024) {
            file = updateFile(file);
            size = FileCommonTools.getFileSizeMegaBytes(file);
        }
        log.info("File {} was created. Size: {}", file, size);
    }

    /**
     * The command creates a new empty txt file in required folder
     * @param fileName - required name for a new file
     */
    private File createFile(Integer fileName) {
        log.info("fileName: {}", fileName);
        File newFile = new File(FileCommonTools.PATH_TO_FOLDER + "\\" + fileName + FileCommonTools.FILE_FORMAT);
        log.info("currentFile: {}", newFile.getAbsolutePath());
        try {
            Boolean isCreated = newFile.createNewFile();
            log.info("File with name = {} was created : {}", fileName, isCreated);
        } catch (IOException e) {
            throw new RuntimeException("Impossible to create a new File with name " + fileName, e);
        }
        return newFile;
    }

    /**
     * The command updated file by random numbers (100000 new values) from 0 to 9,223,372,036,854,775,807 separated by comma
     */
    private File updateFile(File file) {
        List<String> numbers = null;
        String data = "";
        numbers = random.longs(100000, 0, Long.MAX_VALUE)
                .boxed().map(String::valueOf).collect(Collectors.toList());
        data = StringUtils.join(numbers, FileCommonTools.TEXT_SEPARATOR.charAt(0));
        try (FileWriter writer = new FileWriter(file.getAbsoluteFile(), true)) {
            if (FileCommonTools.getFileSizeMegaBytes(file) != 0) {
                writer.append(",");
            }
            writer.write(data);
            writer.flush();
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("File " + fileName + " was not found in folder " + FileCommonTools.PATH_TO_FOLDER, ex);
        } catch (IOException e) {
            throw new RuntimeException("IOException :", e);
        }
        return file;
    }
}
