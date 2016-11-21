package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetDetailsGiftCardDenominationsID {

    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    List<String> branchList  = new ArrayList<String>();


    public void storeDenominationsList(String psKey ,Map<String, List<String>> branchListDetail){
        map = branchListDetail;
    }

    public List<String> getDenominationsList(String DenominationsName) {

        return map.get(DenominationsName);

    }
    public String getDenominations(String DenominationsName) {


        return map.get(DenominationsName).get(0);

    }
    public String getDenominationsNumber(String DenominationsName) {

        return map.get(DenominationsName).get(1);

    }


    public void storeDenominationsDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getDenominationsDetail() {
        return branchList;

    }
}
