package com.example.organs_donation;

import java.io.Serializable;

public class Donor implements Serializable{
    private String name;
    private String age;
    private String bloodGroup;
    private String phone;
    private String health;
    private String organToDonate;
    private String address;
    private String hospitalName;
    private String id;

    // Default constructor (required by Firebase)
    public Donor() {
        // Default constructor required for calls to DataSnapshot.getValue(Donor.class)
    }

    public Donor(String name, String age, String bloodGroup, String phone, String health, String address, String hospitalName, String organToDonate) {
        this.name = name;
        this.age = age;
        this.bloodGroup = bloodGroup;
        this.phone = phone;
        this.health = health;
        this.organToDonate = organToDonate;
        this.address = address;
        this.hospitalName= hospitalName;
        this.id= id;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getOrganToDonate() {
        return organToDonate;
    }

    public void setOrganToDonate(String organToDonate) {
        this.organToDonate = organToDonate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
