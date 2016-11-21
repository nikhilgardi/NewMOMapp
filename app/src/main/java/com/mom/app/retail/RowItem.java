package com.mom.app.retail;

/**
 * Created by nikhilg on 7/15/2016.
 */
public class RowItem {
    private String planMRP;
    private String planDescription;
    private String planValidity;

    public RowItem(String planMRP, String planDescription, String planValidity) {
        this.planMRP = planMRP;
        this.planDescription = planDescription;
        this.planValidity = planValidity;
    }
    public String getplanMRP() {
        return planMRP;
    }
    public void setplanMRP(String planMRP) {
        this.planMRP = planMRP;
    }
    public String getDesc() {
        return planDescription;
    }
    public void setDesc(String planDescription) {
        this.planDescription = planDescription;
    }
    public String getValidity() {
        return planValidity;
    }
    public void setValidity(String planValidity) {
        this.planValidity = planValidity;
    }
    @Override
    public String toString() {
        return planValidity + "\n" + planDescription;
    }
}
