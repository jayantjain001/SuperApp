package com.dev.superapp.controller;

import com.dev.superapp.service.ExceptionHandlerTestService;
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
