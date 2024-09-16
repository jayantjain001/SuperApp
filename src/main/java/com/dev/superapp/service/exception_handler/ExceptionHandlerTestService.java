package com.dev.superapp.service.exception_handler;

import org.apache.kafka.common.errors.InvalidRequestException;
import org.springframework.stereotype.Service;

@Service
public class ExceptionHandlerTestService {


    public String func(){
        throw new InvalidRequestException("no resource was found");
    }
}
