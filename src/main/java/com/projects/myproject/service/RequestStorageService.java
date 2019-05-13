package com.projects.myproject.service;


import com.projects.myproject.fileprocessor.creator.FileBuilderRunner;
import com.projects.myproject.fileprocessor.scanner.FileScannerRunner;
import com.projects.myproject.model.RequestStorage;
import com.projects.myproject.repository.RequestStorageRepository;
import localhost._8080.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestStorageService {

    private static final Logger log = LoggerFactory.getLogger(RequestStorageService.class);

    private RequestStorageRepository storageRepository;
    private FileScannerRunner fileScannerRunner;
    private FileBuilderRunner fileBuilderRunner;

    @Autowired
    public RequestStorageService(RequestStorageRepository storageRepository, FileScannerRunner fileScannerRunner, FileBuilderRunner fileBuilderRunner) {
        this.storageRepository = storageRepository;
        this.fileScannerRunner = fileScannerRunner;
        this.fileBuilderRunner = fileBuilderRunner;
    }

    /**
     * Search for the required value in all files in the directory and save result of the search to database
     *
     * @param   number - required value for search
     * @return  Result object with
     *      1) status of the operation
     *      2) list of files that contain required value
     *      3) error description (if error exists)
     */
    public Result getFileNamesByNumber(Long number) {
        Result result = fileScannerRunner.getResultByNumber(number);
        log.info("result : {}", result);
        result.setFileNames(result.getFileNames());
        RequestStorage requestStorage = saveRequestResult(new RequestStorage(result.getCode(), number, result.getFileNames(), result.getError()));
        log.info("New Request Storage was added : {}", requestStorage);
        return result;
    }

    /**
     * Create required number of files
     *
     * @param   numberOfFiles - number of files
     */
    public void createNewFiles(Integer numberOfFiles) {
        fileBuilderRunner.generateFiles(numberOfFiles);
    }

    private RequestStorage saveRequestResult(RequestStorage requestStorage) {
        return storageRepository.save(requestStorage);
    }

}

