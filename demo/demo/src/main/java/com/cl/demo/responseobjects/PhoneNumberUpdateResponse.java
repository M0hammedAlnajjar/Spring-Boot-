package com.cl.demo.responseobjects;

import com.cl.demo.entities.PhoneNumber;

import java.util.ArrayList;
import java.util.List;

public class PhoneNumberUpdateResponse {

    private String phoneNumberId;
    private String countryCode;
    private Long phoneNumber;

    public static PhoneNumberUpdateResponse convert(
            PhoneNumber phoneNumberObj
    ) {

        if (phoneNumberObj == null
                || phoneNumberObj.getId() == null) {

            return new PhoneNumberUpdateResponse();
        }

        PhoneNumberUpdateResponse response =
                new PhoneNumberUpdateResponse();

        response.phoneNumberId =
                phoneNumberObj.getId().toString();

        response.countryCode =
                phoneNumberObj.getCountryCode();

        response.phoneNumber =
                phoneNumberObj.getPhoneNumber();

        return response;
    }

    public static List<PhoneNumberUpdateResponse> convert(
            List<PhoneNumber> phoneNumbers
    ) {

        List<PhoneNumberUpdateResponse> responses =
                new ArrayList<>();

        if (phoneNumbers == null) {
            return responses;
        }

        for (PhoneNumber phoneNumber : phoneNumbers) {

            responses.add(
                    convert(phoneNumber)
            );
        }

        return responses;
    }
}