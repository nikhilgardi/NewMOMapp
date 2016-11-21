package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by nikhilg on 1/30/2016.
 */
public class Payment_Request_payment_mode {

    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    List<String> branchList  = new ArrayList<String>();


    public void storeBranchList(String psKey ,HashMap<String, Integer> branchListDetail){
        branchListMap = branchListDetail;
    }

    public Integer getBranchList(String branchName) {

        return branchListMap.get(branchName);

    }

    public void storeBranchListDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getBranchListDetail() {
        return branchList;

    }
}
