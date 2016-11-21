package com.mom.app.retail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nikhilg on 11/9/2016.
 */

public class Get_MasterServiceProviderType {
    Map<String, List<String>> map = new HashMap<String, List<String>>();
    List<String> ServiceList  = new ArrayList<String>();

    public void storeGetMasterServiceProviderTypeAll(String psKey ,Map<String, List<String>> getMasterDetail)
    {
        map = getMasterDetail;
    }

    public List<String> getGetMasterServiceProviderTypeAll(String branchName) {


        return map.get(branchName);

    }

    public String getServiceProviderTypeID(String ServiceProviderTypeName) {

        return map.get(ServiceProviderTypeName).get(1);

    }

    public String getServiceProviderTypeName(String ServiceProviderTypeName)
    {
        return map.get(ServiceProviderTypeName).get(0);
    }





    public void storeGetMasterServiceProviderTypeAll(String psKey ,List<String> branchListDetail){
        ServiceList = branchListDetail;
    }

    public List<String> getGetMasterServiceProviderTypeAll() {
        return ServiceList;

    }

}
