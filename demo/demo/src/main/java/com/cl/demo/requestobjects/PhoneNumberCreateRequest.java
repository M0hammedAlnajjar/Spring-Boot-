package com.cl.demo.requestobjects;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PhoneNumberCreateRequest {
    private String CountryCode;
    private long phoneNumber;
}
