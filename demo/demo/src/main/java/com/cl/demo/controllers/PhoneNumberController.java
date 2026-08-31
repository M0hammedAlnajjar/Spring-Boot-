package com.cl.demo.controllers;
import com.cl.demo.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.responseobjects.PhoneNumberCreateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("phoneNumber")

public class PhoneNumberController {

        @Autowired
        private PhoneNumberService phoneNumberService;


    @PostMapping("/add")
    public PhoneNumberCreateResponse addPhoneNumber(
            @RequestBody PhoneNumberCreateRequest request
    ) {

        PhoneNumber phoneNumber =
                phoneNumberService.addPhoneNumber(request);

        return PhoneNumberCreateResponse.convert(
                phoneNumber
        );
    }
    @GetMapping("/getById")
    public PhoneNumberCreateResponse getPhoneNumberById(
            @RequestParam String uuid
    ) {

        PhoneNumber phoneNumber =
                phoneNumberService.getPhoneNumberById(uuid);

        return PhoneNumberCreateResponse.convert(
                phoneNumber
        );
    }
}
