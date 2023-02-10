package com.example.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

//@Embeddable로 표시하면 이클래스가 DB에 별도로 테이블로 존재하지 않음을 알리는 것.
//Embedded과 함께 사용
@Embeddable
@Getter
@NoArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;



    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
