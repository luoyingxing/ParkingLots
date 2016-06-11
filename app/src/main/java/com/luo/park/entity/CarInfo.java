package com.luo.park.entity;

import java.io.Serializable;

/**
 * CarInfo
 * <p/>
 * Created by luoyingxing on 16/6/10.
 */
public class CarInfo implements Serializable {
    //    ID，车牌号码，姓名，性别，车辆类型，联系电话，卡类型，卡起效时间，卡结束时间，押金，卡费用
    private String id;
    private String carNumber;
    private String name;
    private String sex;
    private String carType;
    private String phone;
    private String cardType;
    private String cardStartTime;
    private String cardEndTime;
    private float deposit;
    private float charge;

    public CarInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardStartTime() {
        return cardStartTime;
    }

    public void setCardStartTime(String cardStartTime) {
        this.cardStartTime = cardStartTime;
    }

    public String getCardEndTime() {
        return cardEndTime;
    }

    public void setCardEndTime(String cardEndTime) {
        this.cardEndTime = cardEndTime;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getCharge() {
        return charge;
    }

    public void setCharge(float charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "id=" + id +
                ", carNumber='" + carNumber + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", carType='" + carType + '\'' +
                ", phone='" + phone + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardStartTime='" + cardStartTime + '\'' +
                ", cardEndTime='" + cardEndTime + '\'' +
                ", deposit=" + deposit +
                ", charge=" + charge +
                '}';
    }
}
