package com.example.nubank.model;

import java.util.Objects;

public class Invoice {

    private String month;

    public Invoice(String month, int year, double amount, int status) {
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.status = status;
    }

    private int year;
    private double amount;
    private int status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return year == invoice.year && Double.compare(invoice.amount, amount) == 0 && status == invoice.status && Objects.equals(month, invoice.month);
    }

    @Override
    public int hashCode() {
        return Objects.hash(month, year, amount, status);
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
