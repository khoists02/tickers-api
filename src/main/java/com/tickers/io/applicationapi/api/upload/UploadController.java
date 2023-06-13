package com.tickers.io.applicationapi.api.upload;

import com.tickers.io.applicationapi.enums.TypeEnum;
import com.tickers.io.applicationapi.exceptions.BadRequestException;
import com.tickers.io.applicationapi.services.UploadService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UploadService uploadService;

    @PostMapping("/{ticker}/{type}")
    public String uploadCSVStocksDataFile(
            @PathVariable("ticker") String ticker,
            @PathVariable("type") String type,
            @RequestBody @Valid MultipartFile file) {

        try {
            if (!file.isEmpty()) {
                byte[] bytes = file.getBytes();
                String completeData = new String(bytes);
                String[] rows = completeData.split("\n");
                String json = csvToJson(List.of(rows));
                uploadService.storeTickerData(json, ticker, TypeEnum.valueOf(type));
                return "Upload completed";
            }
            throw new BadRequestException("file_empty");
        } catch (Exception e) {
            logger.info("{}", e.getMessage());
            throw new BadRequestException("upload_or_store_json_fail");
        }
    }

    public static String csvToJson(List<String> csv){

        //remove empty lines
        //this will affect permanently the list.
        //be careful if you want to use this list after executing this method
//        csv.removeIf(e -> e.trim().isEmpty());

        //csv is empty or have declared only columns
        if(csv.size() <= 1){
            return "[]";
        }

        //get first line = columns names
        String[] columns = csv.get(0).split(",");

        //get all rows
        StringBuilder json = new StringBuilder("[\n");
        List<String> csvMapping = csv.subList(1, csv.size());
        csvMapping
                .stream().map(e -> e.split(",", 6))
                .filter(e -> e.length == columns.length) //values size should match with columns size
                .forEach(row -> {

                    json.append("\t{\n");

                    for(int i = 0; i < columns.length; i++){
                        json.append("\t\t\"")
                                .append(columns[i])
                                .append("\" : \"")
                                .append(row[i].replace("\"", ""))
                                .append("\",\n"); //comma-1
                    }

                    //replace comma-1 with \n
                    json.replace(json.lastIndexOf(","), json.length(), "\n");

                    json.append("\t},"); //comma-2

                });

        //remove comma-2
        json.replace(json.lastIndexOf(","), json.length(), "");

        json.append("\n]");

        return json.toString();
    }
}
