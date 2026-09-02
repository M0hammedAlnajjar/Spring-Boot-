package com.cl.demo.responseobjects;

import com.cl.demo.entities.PhoneNumber;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

        response.setPhoneNumberId(
                phoneNumberObj.getId().toString()
        );

        response.setCountryCode(
                phoneNumberObj.getCountryCode()
        );

        response.setPhoneNumber(
                phoneNumberObj.getPhoneNumber()
        );

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
