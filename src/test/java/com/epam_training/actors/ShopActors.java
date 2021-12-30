package com.epam_training.actors;

public class ShopActors {
    private String name;
    private String lastName;
    private String company;
    private String email;
    private String phone;
    private String country;
    private String address;
    private String addressDetail;
    private String city;
    private String state;
    private String postCode;
    private String paymentWay;

    public ShopActors() {
        name = "Luigi";
        lastName = "Ruiz";
        company = "Epam S4N";
        email = "123@hotmail.com";
        phone = "3001234567";
        country = "Colombia";
        address = "Calle falsa 123";
        addressDetail = "apt 777";
        city = "Bogota D.C";
        state = "Bogota D.C";
        postCode = "11111";
        paymentWay = "Bank";
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getCountry() {
        return country;
    }

    public String getAddress() {
        return address;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPaymentWay() {
        return paymentWay;
    }
}
