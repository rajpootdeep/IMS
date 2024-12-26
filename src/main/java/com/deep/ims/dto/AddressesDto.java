package com.deep.ims.dto;

public class AddressesDto {
   private  String address;
    private String country;
    private String state;
    private String city;
    private String pinCode;

    public AddressesDto(String address, String country, String state, String city, String pinCode) {
        this.address = address;
        this.country = country;
        this.state = state;
        this.city = city;
        this.pinCode = pinCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}
