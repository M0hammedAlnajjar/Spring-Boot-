package com.cl.demo.responseobjects;

import com.cl.demo.entities.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PersonCreateResponse {
    String personId;
    String fullName;
    String userName;
    String email;
    String phoneNumber;

    public static PersonCreateResponse convert(Person person) {
        PersonCreateResponse response = new PersonCreateResponse();
        response.setPersonId(person.getId().toString());
        response.setFullName(person.getName());
        response.setUserName(person.getUserName().getActiveUserName());
        response.setEmail(person.getEmail());
        //response.setPhoneNumber(person.getPhoneNumber().toString());
        return response;
    }

    public static List<PersonCreateResponse> convert(List<Person> personList) {
        List<PersonCreateResponse> responseList = new ArrayList<>();
        for (Person p : personList) {
            responseList.add(convert(p));
        }
        return responseList;
    }
}


