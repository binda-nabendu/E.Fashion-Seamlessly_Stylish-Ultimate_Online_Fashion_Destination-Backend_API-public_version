package org.example.ecomarcehandicraftbackend.model;
import jakarta.persistence.*;

import java.time.LocalDate;

public class PaymentInfo {
    @Column(name = "cardholder_name") private String cardholderName;
    @Column (name = "card _number")
    private String cardNumber;
    @Column(name = "expiration _date")
    private LocalDate expirationDate;
    @Column (name = "cvv")
    private String cvv;

    public PaymentInfo() {
    }

    public PaymentInfo(String cardholderName, String cardNumber, LocalDate expirationDate, String cvv) {
        this.cardholderName = cardholderName;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
