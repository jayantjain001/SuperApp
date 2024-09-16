package com.dev.superapp.controller.rest_controller_advice;

import com.dev.superapp.service.exception_handler.ExceptionHandlerTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RequestMapping("/rest-exceptions-handler")
@RestController
@RequiredArgsConstructor
public class RestExceptionHandlerTestController {

    private final ExceptionHandlerTestService exceptionHandlerTestService;

    @GetMapping("")
    public Serializable fetchData() {
        exceptionHandlerTestService.func();
        return "request processed!";
    }


}
