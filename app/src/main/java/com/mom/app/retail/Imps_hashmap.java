package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Imps_hashmap {

    HashMap<String, Integer> branchListMap = new HashMap<String, Integer>();
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    List<String> branchList  = new ArrayList<String>();


    public void storeBranchList(String psKey ,Map<String, List<String>> branchListDetail){
        map = branchListDetail;
    }

    public List<String> getBranchList(String branchName) {


        return map.get(branchName);

    }
    public String getFavoriteName(String branchName) {


        return map.get(branchName).get(0);

    }
    public String getFavoriteMobileNumber(String branchName) {


        return map.get(branchName).get(1);

    }



    public void storeBranchListDetail(String psKey ,List<String> branchListDetail){
        branchList = branchListDetail;
    }

    public List<String> getBranchListDetail() {
        return branchList;

    }
}
