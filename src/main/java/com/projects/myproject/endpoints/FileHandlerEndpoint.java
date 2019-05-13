package com.projects.myproject.endpoints;

import com.projects.myproject.service.RequestStorageService;
import localhost._8080.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class FileHandlerEndpoint {

    private static final Logger log = LoggerFactory.getLogger(FileHandlerEndpoint.class);
    private static final String NAMESPACE_URI = "http://localhost:8080/";

    private RequestStorageService requestStorageService;

    @Autowired
    public FileHandlerEndpoint(RequestStorageService requestStorageService) {
        this.requestStorageService = requestStorageService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getRequestStorageRequest")
    @ResponsePayload
    public GetRequestStorageResponse getRequestStorage(@RequestPayload GetRequestStorageRequest request) {
        log.info("request : {}", request);
        GetRequestStorageResponse response = new GetRequestStorageResponse();
        Result result = requestStorageService.getFileNamesByNumber(request.getNumber());
        response.setRequestStorage(result);
        log.info("response : {}", response);

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createNewFilesRequest")
    @ResponsePayload
    public CreateNewFilesResponse generateNewFiles(@RequestPayload CreateNewFilesRequest request) {
        log.info("request : {}", request);
        CreateNewFilesResponse response = new CreateNewFilesResponse();
        Integer fileCount = request.getNumberOfFiles();
        log.info("fileCount : {}", fileCount);
        requestStorageService.createNewFiles(fileCount);
        if(fileCount == 1){
            response.setMessage(fileCount + " new file was created");
        } else {
            response.setMessage(fileCount + " new files were created");
        }
        return response;
    }
}
