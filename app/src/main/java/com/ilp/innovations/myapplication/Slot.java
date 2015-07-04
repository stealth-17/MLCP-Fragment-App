package com.ilp.innovations.myapplication;


public class Slot {

    private int bookId;
    private String empId;
    private String regId;
    private String slot;
    private int flag;
    private boolean isChecked;

    public Slot() {
    }

    public Slot(int bookId, String regId, String slot) {
        this.bookId = bookId;
        this.regId = regId;
        this.slot = slot;
        this.isChecked = false;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}
