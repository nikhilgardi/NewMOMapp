package com.mom.app.retail;

import android.app.Dialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class ValidateCustomDialogue implements View.OnClickListener {
    private final Dialog dialog;
    private final Spinner spinnerGetIFSCBanks;
    private final Spinner spinnerGetIFSCStates;
    private final Spinner spinnerGetIFSCCity;
    private final Spinner spinnerGetIFSCBranch;
    private TextView tvexceptionIFSCCODE;
    private EditText et_ifsc_code;
    public String str_IFSCCode;

    public ValidateCustomDialogue(Dialog dialog,Spinner spinnerGetIFSCBanks,Spinner spinnerGetIFSCStates,Spinner spinnerGetIFSCCity,Spinner spinnerGetIFSCBranch,String str_IFSCCode)
    {
        this.dialog=dialog;
        this.spinnerGetIFSCBanks=spinnerGetIFSCBanks;
        this.spinnerGetIFSCStates=spinnerGetIFSCStates;
        this.spinnerGetIFSCCity=spinnerGetIFSCCity;
        this.spinnerGetIFSCBranch=spinnerGetIFSCBranch;
        this.str_IFSCCode=str_IFSCCode;
    }
    @Override
    public void onClick(View v)
    {
        tvexceptionIFSCCODE=(TextView)dialog.findViewById(R.id.tvexceptionIFSCCODE);
        et_ifsc_code = (EditText) v.findViewById(R.id.et_ifsc_code);
        if(spinnerGetIFSCBanks.getSelectedItem().toString().equals("Select Bank"))
        {
            tvexceptionIFSCCODE.setText("Select Bank");
        }
        else if(spinnerGetIFSCStates.getSelectedItem().toString().equals("Select State"))
        {
            tvexceptionIFSCCODE.setText("Select State");
        }
        else if(spinnerGetIFSCCity.getSelectedItem().toString().equals("Select City"))
        {
            tvexceptionIFSCCODE.setText("Select City");
        }
        else if(spinnerGetIFSCBranch.getSelectedItem().toString().equals("Select Branch"))
        {
            tvexceptionIFSCCODE.setText("Select Branch");
        }
        else
        {
            tvexceptionIFSCCODE.setText("");
            dialog.dismiss();
            //et_ifsc_code.setText(str_IFSCCode);
            //et_ifsc_code.setEnabled(false);

        }
    }
}
