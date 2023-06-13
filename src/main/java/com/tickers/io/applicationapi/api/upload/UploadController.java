package com.tickers.io.applicationapi.api.upload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upload")
public class UploadController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
}
