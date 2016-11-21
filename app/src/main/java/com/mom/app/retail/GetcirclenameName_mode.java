package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GetcirclenameName_mode {
    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    List<String> branchList  = new ArrayList<String>();


    public void storeCircleList(String psKey ,HashMap<String, Integer> branchListDetail){
        branchListMap = branchListDetail;
    }

    public Integer getCircleList(String branchName) {

        return branchListMap.get(branchName);

    }

    public void storeBranchListDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getBranchListDetail() {
        return branchList;

    }
}
