package com.ai.entity;

public class Vendors {
    private Integer vendorId;

    private String vendorName;

    private String vendorEname;

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName == null ? null : vendorName.trim();
    }

    public String getVendorEname() {
        return vendorEname;
    }

    public void setVendorEname(String vendorEname) {
        this.vendorEname = vendorEname == null ? null : vendorEname.trim();
    }
}