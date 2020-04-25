package com.techm.diway.counterapp.web.validators;

import com.techm.diway.counterapp.error.ErrorCodes;
import com.techm.diway.counterapp.error.exceptions.AppRuntimeException;
import com.techm.diway.counterapp.models.SearchRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CounterAppValidator {

    private final Logger logger = LoggerFactory.getLogger(CounterAppValidator.class);

    private final List<Integer> topValuesAllowed = Stream.of(5, 10, 20, 30).collect(Collectors.toList());

    public void validateSearchRequest(final SearchRequest searchRequest) {
        if(searchRequest == null || searchRequest.getSearchText() == null || CollectionUtils.isEmpty(searchRequest.getSearchText())) {
            String errorMsg = "Attribute searchText is required/invalid";
            logger.error(errorMsg);
            throw new AppRuntimeException(errorMsg, ErrorCodes.BAD_REQUEST);
        }
    }

    public void validateTopValue(Integer value) {
        if(!topValuesAllowed.contains(value)) {
            String errorMsg = "Allowed top values are: " + topValuesAllowed;
            logger.error(errorMsg);
            throw new AppRuntimeException(errorMsg, ErrorCodes.BAD_REQUEST);
        }
    }
}
