package com.techm.diway.counterapp.components;

import com.techm.diway.counterapp.error.ErrorCodes;
import com.techm.diway.counterapp.error.exceptions.AppRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CounterAppResourceLoader {

    private final Logger logger = LoggerFactory.getLogger(CounterAppResourceLoader.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean(name = "dataResource")
    public Resource getResource() {
        return resourceLoader.getResource("classpath:data.txt");
    }

    @Bean(name = "rawResourceString")
    public String loadResource(@Autowired Resource dataResource) {
        try {
            InputStream inputStream = dataResource.getInputStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(inputStream);
            String data = new String(bytes, StandardCharsets.UTF_8);
            return data;

        } catch (IOException ex) {
            String errorMsg = "loadResource: failed to convert dataResource";
            logger.error(errorMsg + ", cause: {}, message: {}, class: {}", ex.getCause(), ex.getMessage(), ex.getClass());
            throw new AppRuntimeException(errorMsg, ErrorCodes.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Bean(name = "wordFrequencyCache")
    public Map<String, Integer> wordFrequencyCacheMap(@Autowired String rawResourceString) {
        Map<String, Integer> wordFrequencyCacheMap = Arrays.stream(rawResourceString.split(",|\\s+|\\."))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(val -> 1)));
        if(wordFrequencyCacheMap.containsKey(""))
            wordFrequencyCacheMap.remove("");
        return wordFrequencyCacheMap;
    }
}
