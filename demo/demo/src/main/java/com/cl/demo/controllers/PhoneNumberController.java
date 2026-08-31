package com.cl.demo.controllers;
import com.cl.demo.services.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("phoneNumber")

public class PhoneNumberController {

        @Autowired
        private PhoneNumberService phoneNumberService;

}
