package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Gift_ServiceProviderID {

    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    List<String> branchList  = new ArrayList<String>();


    public void storeGiftList(String psKey ,Map<String, List<String>> branchListDetail){
        map = branchListDetail;
    }

    public List<String> getGiftList(String branchName) {


        return map.get(branchName);

    }
    public String getgetserviceProviderName(String branchName) {

        return map.get(branchName).get(0);

    }
    public String getServiceProviderID(String branchName) {


        return map.get(branchName).get(1);

    }


    public void storeGiftListDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getGiftListDetail() {
        return branchList;

    }
}
