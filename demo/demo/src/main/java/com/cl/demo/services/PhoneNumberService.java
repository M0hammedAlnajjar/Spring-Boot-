package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class PhoneNumberService {
    public PhoneNumber addPhoneNumber(
            PhoneNumberCreateRequest request
    ) {

        if (request == null
                || request.getPhoneNumber() == null) {

            return new PhoneNumber();
        }

        PhoneNumber phoneNumber =
                new PhoneNumber();

        phoneNumber.setId(
                UUID.randomUUID()
        );

        phoneNumber.setIsActive(
                Boolean.TRUE
        );

        phoneNumber.setCreatedDate(
                new Date()
        );

        phoneNumber.setCountryCode(
                request.getCountryCode()
        );

        phoneNumber.setPhoneNumber(
                request.getPhoneNumber()
        );

        DemoApplication.PhoneNumber_List.add(
                phoneNumber
        );

        return phoneNumber;
    }

}