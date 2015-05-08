package com.pwhitman.neonpasswordsafe;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class PasswordFragment extends Fragment {

    public static final String EXTRA_PASSWORD_ID = "com.pwhitman.neonpasswordsafe.password_id";
    private static final String TAG = "PasswordFragment";

    private static final int REQUEST_DATE = 0;

    private Password mPassword;
    private EditText mTitleField;
    private EditText mUsernameField;
    private EditText mPasswordField;
    private EditText mNotes;
    private Button mGeneratePassBtn;
    private Button mDeleteBtn;
    private Button mSaveBtn;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PasswordFragment.
     */

    public static PasswordFragment newInstance(UUID passwordID) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_PASSWORD_ID, passwordID);
        fragment.setArguments(args);
        return fragment;
    }

    public PasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID passId = (UUID)getArguments().getSerializable(EXTRA_PASSWORD_ID);
        mPassword = PasswordStation.get(getActivity()).getPassword(passId);
        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(NavUtils.getParentActivityIntent(getActivity()) != null){
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_password, container, false);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
            if(NavUtils.getParentActivityIntent(getActivity()) != null){
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText)v.findViewById(R.id.password_title);
        mTitleField.setText(mPassword.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUsernameField = (EditText)v.findViewById(R.id.password_username);
        mUsernameField.setText(mPassword.getUsername());
        mUsernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setUsername(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        mPasswordField = (EditText)v.findViewById(R.id.password_pass);
        if(mPassword.getPass() == null || mPassword.getPass().trim().isEmpty()){
            mPasswordField.setText("");
        }else{
            mPasswordField.setText(mPassword.getmPassUtil().decryptString(mPassword.getPass()));
        }

        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setPass(s.toString());
                //Log.i(TAG, "onTextChanged PasswordField: " + mPassword.getPass());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGeneratePassBtn = (Button)v.findViewById(R.id.password_generate_btn);
        mGeneratePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.alert_change_password_title);
                builder.setMessage(R.string.alert_change_password_message);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newRandom = PasswordUtility.generateRandomString();
                        mPassword.setPass(newRandom);
                        mPasswordField.setText(mPassword.getmPassUtil().decryptString(mPassword.getPass()));

                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing and go back
                    }
                });
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        mGeneratePassBtn.setEnabled(true);


        mNotes = (EditText)v.findViewById(R.id.password_notes);
        mNotes.setText(mPassword.getNotes());
        mNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setNotes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDeleteBtn = (Button)v.findViewById(R.id.password_delete_btn);
        mDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PasswordStation.get(getActivity()).deletePassword(mPassword);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.alert_delete_title);
                builder.setMessage(R.string.alert_delete_message);
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PasswordStation.get(getActivity()).deletePassword(mPassword);
                        Intent intent = new Intent(getActivity(), PasswordListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Do nothing and go back
                    }
                });
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
        mDeleteBtn.setEnabled(true);

        mSaveBtn = (Button)v.findViewById(R.id.password_save_btn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PasswordListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra(EXTRA_PASSWORD_ID, 0);
                startActivity(intent);
            }
        });
        mSaveBtn.setEnabled(true);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        PasswordStation.get(getActivity()).savePasswords();
    }
}
