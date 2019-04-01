package com.gathratechnal.nandyalcity.property;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.gathratechnal.nandyalcity.R;
import com.gathratechnal.nandyalcity.utils.CommonAsyncTask;
import com.gathratechnal.nandyalcity.utils.CommonOnPostExecuteListener;
import com.gathratechnal.nandyalcity.utils.Preferences;
import com.gathratechnal.nandyalcity.utils.ResponseModel;

import org.json.JSONObject;

/**
 * Created by arunmididoddy on 1/24/2018.
 */

public class AddPropertyFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private EditText name, email, mobile, title, description, type, amount, address;
    private Spinner modeSpinner, typeSpinner;
    private int selectedMode;
    private AppCompatButton add_btn;
    private ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.please_wait));
        }

        mProgressDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View busView = inflater.inflate(R.layout.add_property, container, false);
        modeSpinner = (Spinner) busView.findViewById(R.id.add_p_mode);
        typeSpinner = (Spinner) busView.findViewById(R.id.add_p_type_spinner);
        name = (EditText) busView.findViewById(R.id.add_p_name);
        email = (EditText) busView.findViewById(R.id.add_p_email);
        mobile = (EditText) busView.findViewById(R.id.add_p_mobile);
        add_btn = (AppCompatButton) busView.findViewById(R.id.add_p_btn);
        title = (EditText) busView.findViewById(R.id.add_p_title);
        description = (EditText) busView.findViewById(R.id.add_p_descption);
        address = (EditText) busView.findViewById(R.id.add_p_address);
        amount = (EditText) busView.findViewById(R.id.add_p_amount);
        setSpinnerValues();
        name.setText(Preferences.getInstance().getUserName(getActivity()));
        email.setText(Preferences.getInstance().getUserEmail(getActivity()));
        mobile.setText(Preferences.getInstance().getUserMobile(getActivity()));
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProperty();
            }
        });
        return busView;
    }

    private void setSpinnerValues() {
        modeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.property_mode_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        modeSpinner.setAdapter(adapter);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.property_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);
    }

    private void addProperty() {
        if (title.getText().length() < 1) {
            title.setError("Min 10 characters..");
            Toast.makeText(getActivity(), "Please enter title", Toast.LENGTH_SHORT).show();
        } else if (modeSpinner.getSelectedItemPosition() < 1) {
            Toast.makeText(getActivity(), "Please select Mode", Toast.LENGTH_SHORT).show();
        } else if (typeSpinner.getSelectedItemPosition() < 1) {
            Toast.makeText(getActivity(), "Please select property type", Toast.LENGTH_SHORT).show();
        } else if (name.getText().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter name", Toast.LENGTH_SHORT).show();
        } else if (email.getText().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter email", Toast.LENGTH_SHORT).show();
        } else if (mobile.getText().length() < 1) {
            Toast.makeText(getActivity(), "Enter Valid mobile number", Toast.LENGTH_SHORT).show();
        } else if (description.getText().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Details with atleast 10 characters", Toast.LENGTH_SHORT).show();
        } else if (amount.getText().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
        } else if (address.getText().length() < 1) {
            Toast.makeText(getActivity(), "Please Enter valid Address", Toast.LENGTH_SHORT).show();
        } else {
            makeServiceCall();
        }

    }

    private void makeServiceCall() {
        try {
            showProgressDialog();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nc_code", Preferences.getInstance().getNcCode(getActivity()));
            jsonObject.put("propearty_title", title.getText().toString().trim());
            jsonObject.put("type", typeSpinner.getSelectedItem().toString());
            jsonObject.put("name", name.getText().toString().trim());
            jsonObject.put("email", email.getText().toString().trim());
            jsonObject.put("mode", modeSpinner.getSelectedItem().toString());
            jsonObject.put("mobile", mobile.getText().toString().trim());
            jsonObject.put("details", description.getText().toString().trim());
            jsonObject.put("amount", amount.getText().toString().trim());
            jsonObject.put("address", address.getText().toString().trim());
            jsonObject.put("exp_date", "");

            CommonAsyncTask asyncTask = new CommonAsyncTask();
            asyncTask.listener = new CommonOnPostExecuteListener() {
                @Override
                public void onPostExecuteListener(ResponseModel responseModel, String message) {
                    mProgressDialog.dismiss();
                    if (responseModel.getStatusCode() == 200) {
                        Toast.makeText(getActivity(), responseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        getActivity().finish();
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }
                }
            };
            asyncTask.addProperty(jsonObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        selectedMode = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
