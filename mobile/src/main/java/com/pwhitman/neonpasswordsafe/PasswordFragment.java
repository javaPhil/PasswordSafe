package com.pwhitman.neonpasswordsafe;


import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.UUID;

public class PasswordFragment extends Fragment {

    public static final String EXTRA_PASSWORD_ID = "com.pwhitman.neonpasswordsafe.password_id";

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
        if (getArguments() != null) {
            UUID passwordID = (UUID)getArguments().getSerializable(EXTRA_PASSWORD_ID);
            mPassword = PasswordStation.get(getActivity()).getPassword(passwordID);
        }
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
        mPasswordField.setText(mPassword.getPass());
        mPasswordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setPass(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mGeneratePassBtn = (Button)v.findViewById(R.id.password_generate_btn);
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
        mDeleteBtn.setEnabled(true);

        mSaveBtn = (Button)v.findViewById(R.id.password_save_btn);
        mSaveBtn.setEnabled(true);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        PasswordStation.get(getActivity()).savePasswords();
    }
}
