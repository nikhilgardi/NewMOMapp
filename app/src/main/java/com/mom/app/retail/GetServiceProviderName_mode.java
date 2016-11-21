package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GetServiceProviderName_mode {
    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    List<String> branchList  = new ArrayList<String>();


    public void storeServiceProviderList(String psKey ,HashMap<String, Integer> branchListDetail){
        branchListMap = branchListDetail;
    }

    public Integer getServiceProviderList(String branchName) {

        return branchListMap.get(branchName);

    }

    public void storeBranchListDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getBranchListDetail() {
        return branchList;

    }
}
