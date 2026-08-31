package com.cl.demo.services;

import com.cl.demo.DemoApplication;
import com.cl.demo.entities.Person;
import com.cl.demo.entities.PhoneNumber;
import com.cl.demo.entities.UserName;
import com.cl.demo.requestobjects.PersonCreateRequest;
import com.cl.demo.requestobjects.PersonUpdateRequest;
import com.cl.demo.requestobjects.PhoneNumberCreateRequest;
import com.cl.demo.utils.HelperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PersonService {

    public static final String PERSON_USERNAME_OR_EMAIL_ALREADY_TAKEN =
            "Given username or email is already taken";

    public static final String PERSON_SAVED =
            "Person saved";

    @Autowired
    private PhoneNumberService phoneNumberService;


    // =========================
    // ADD PERSON
    // =========================

    public Map<String, String> addPerson(
            PersonCreateRequest requestObj
    ) {

        Map<String, String> response =
                new HashMap<>();

        Person person =
                new Person();

        if (!verifyUserNameAndEmail(
                requestObj.getPersonUserName(),
                requestObj.getPersonEmail()
        )) {

            response.put(
                    "error",
                    PERSON_USERNAME_OR_EMAIL_ALREADY_TAKEN
            );

            return response;
        }

        // BaseClass information
        person.setId(
                UUID.randomUUID()
        );

        person.setIsActive(
                Boolean.TRUE
        );

        person.setCreatedDate(
                new Date()
        );


        // =========================
        // USERNAME
        // =========================

        UserName userName =
                new UserName();

        userName.setActiveUserName(
                requestObj.getPersonUserName()
        );

        person.setUserName(
                userName
        );


        // =========================
        // PERSON INFORMATION
        // =========================

        person.setName(
                getFullName(requestObj)
        );

        person.setEmail(
                requestObj.getPersonEmail()
        );


        // =========================
        // PHONE NUMBER
        // =========================

        PhoneNumberCreateRequest phoneNumberRequest =
                new PhoneNumberCreateRequest();

        phoneNumberRequest.setCountryCode(
                requestObj.getPersonCountryCode()
        );

        phoneNumberRequest.setPhoneNumber(
                requestObj.getPersonPhoneNumber()
        );

        PhoneNumber phoneNumber =
                phoneNumberService.addPhoneNumber(
                        phoneNumberRequest
                );

        person.setPhoneNumber(
                phoneNumber
        );


        // =========================
        // SAVE PERSON
        // =========================

        Boolean result =
                DemoApplication.Person_List.add(
                        person
                );

        if (result) {

            response.put(
                    "response",
                    PERSON_SAVED
            );
        }

        return response;
    }


    // =========================
    // GET PERSON BY ID
    // =========================

    public Person getPersonById(String uuid) {

        for (Person person
                : DemoApplication.Person_List) {

            if (person.getId() != null
                    && person.getId()
                    .toString()
                    .equals(uuid)
                    && Boolean.TRUE.equals(
                    person.getIsActive()
            )) {

                return person;
            }
        }

        return new Person();
    }


    // =========================
    // UPDATE PERSON
    // =========================

    public Person updatePerson(
            PersonUpdateRequest updateObj
    ) {

        Person person =
                getPersonById(
                        updateObj.getUuid()
                );

        if (person == null
                || person.getId() == null
                || !Boolean.TRUE.equals(
                person.getIsActive()
        )) {

            return person;
        }

        DemoApplication.Person_List.remove(
                person
        );

        person.setUserName(
                getUserNameByCompare(
                        person.getUserName(),
                        updateObj
                )
        );

        person.setEmail(
                HelperUtils.compare(
                        person.getEmail(),
                        updateObj.getEmailToUpdate()
                )
        );

        person.setUpdatedDate(
                new Date()
        );

        DemoApplication.Person_List.add(
                person
        );

        return person;
    }


    // =========================
    // GET ALL PERSONS
    // =========================

    public List<Person> getAllPersons() {

        List<Person> resultList =
                new ArrayList<>();

        for (Person person
                : DemoApplication.Person_List) {

            if (Boolean.TRUE.equals(
                    person.getIsActive()
            )) {

                resultList.add(
                        person
                );
            }
        }

        return resultList;
    }


    // =========================
    // VERIFY USERNAME AND EMAIL
    // =========================

    public Boolean verifyUserNameAndEmail(
            String userName,
            String email
    ) {

        if (!DemoApplication.emails.add(email)
                || !DemoApplication.userNames.add(userName)) {

            return false;
        }

        return true;
    }


    // =========================
    // GET FULL NAME
    // =========================

    public String getFullName(
            PersonCreateRequest request
    ) {

        return request.getPersonFirstName()
                + " "
                + request.getPersonMiddleName()
                + " "
                + request.getPersonLastName();
    }


    // =========================
    // UPDATE USERNAME
    // =========================

    private UserName getUserNameByCompare(
            UserName currentUserNameObj,
            PersonUpdateRequest updateRequest
    ) {

        String userNameToUpdate =
                HelperUtils.compare(
                        currentUserNameObj.getActiveUserName(),
                        updateRequest.getUserNameToUpdate()
                );

        if (DemoApplication.userNames.add(
                userNameToUpdate
        )) {

            List<String> userNameHistory =
                    currentUserNameObj.getPrevUserNames();

            if (userNameHistory == null) {

                userNameHistory =
                        new ArrayList<>();
            }

            userNameHistory.add(
                    currentUserNameObj.getActiveUserName()
            );

            currentUserNameObj.setPrevUserNames(
                    userNameHistory
            );

            currentUserNameObj.setActiveUserName(
                    userNameToUpdate
            );
        }

        return currentUserNameObj;
    }


    // =========================
    // DELETE PERSON
    // =========================

    public Boolean deleteById(String uuid) {

        Person person =
                getPersonById(uuid);

        if (person == null
                || person.getId() == null
                || !Boolean.TRUE.equals(
                person.getIsActive()
        )) {

            return false;
        }

        person.setIsActive(
                Boolean.FALSE
        );

        person.setUpdatedDate(
                new Date()
        );

        return true;
    }
}