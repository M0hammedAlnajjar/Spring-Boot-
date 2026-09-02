package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.requestobjects.PhoneNumberUpdateRequest;
import com.cl.demo.utils.HelperUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class PhoneNumberService {
    public PhoneNumber addPhoneNumber(
            PhoneNumberCreateRequest request
    ) {
        if (request == null
                || request.getCountryCode() == null
                || request.getCountryCode().isBlank()
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
    public PhoneNumber getPhoneNumberById(String uuid) {

        if (uuid == null || uuid.isBlank()) {
            return new PhoneNumber();
        }

        for (PhoneNumber phoneNumber
                : DemoApplication.PhoneNumber_List) {

            if (phoneNumber.getId() != null
                    && phoneNumber.getId()
                    .toString()
                    .equals(uuid)
                    && Boolean.TRUE.equals(
                    phoneNumber.getIsActive()
            )) {

                return phoneNumber;
            }
        }

        return new PhoneNumber();
    }
    public List<PhoneNumber> getAllPhoneNumbers() {

        List<PhoneNumber> activePhoneNumbers =
                new ArrayList<>();

        for (PhoneNumber phoneNumber
                : DemoApplication.PhoneNumber_List) {

            if (Boolean.TRUE.equals(
                    phoneNumber.getIsActive()
            )) {

                activePhoneNumbers.add(
                        phoneNumber
                );
            }
        }

        return activePhoneNumbers;
    }
    public PhoneNumber updatePhoneNumber(
            PhoneNumberUpdateRequest request
    ) {

        if (request == null
                || request.getUuid() == null
                || request.getUuid().isBlank()) {

            return new PhoneNumber();
        }

        PhoneNumber existingPhoneNumber =
                getPhoneNumberById(
                        request.getUuid()
                );

        if (existingPhoneNumber.getId() == null) {
            return existingPhoneNumber;
        }

        existingPhoneNumber.setCountryCode(
                HelperUtils.compare(
                        existingPhoneNumber.getCountryCode(),
                        request.getCountryCodeToUpdate()
                )
        );

        existingPhoneNumber.setPhoneNumber(
                HelperUtils.compare(
                        existingPhoneNumber.getPhoneNumber(),
                        request.getPhoneNumberToUpdate()
                )
        );

        existingPhoneNumber.setUpdatedDate(
                new Date()
        );

        return existingPhoneNumber;
    }
    public Boolean deleteById(String uuid) {

        PhoneNumber phoneNumberToDelete =
                getPhoneNumberById(uuid);

        if (phoneNumberToDelete.getId() == null
                || !Boolean.TRUE.equals(
                phoneNumberToDelete.getIsActive()
        )) {

            return false;
        }

        phoneNumberToDelete.setIsActive(
                Boolean.FALSE
        );

        phoneNumberToDelete.setUpdatedDate(
                new Date()
        );

        return true;
    }

}
