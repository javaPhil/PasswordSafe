package com.pwhitman.passwordvault;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.UUID;

public class PasswordFragment extends Fragment {

    public static final String EXTRA_PASSWORD_ID = "com.pwhitman.passwordvault.password_id";
    private static final String TAG = "PasswordFragment";

    private Password mPassword;
    private EditText mTitleField;
    private EditText mUsernameField;
    private EditText mWebsiteField;
    private ImageButton mGoToWebsiteBtn;
    private EditText mPasswordField;
    private EditText mNotes;
    private Button mGeneratePassBtn;
    private Button mSaveBtn;
    private Button mCopyBtn;
    private ImageButton mShowHideBtn;


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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_password, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                if(mPassword.getTitle() == null || mPassword.getTitle().isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.alert_navigate_home_title_empty_title);
                    builder.setMessage(R.string.alert_navigate_home_title_empty_message);
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PasswordStation.get(getActivity()).deletePassword(mPassword);
                            if (NavUtils.getParentActivityIntent(getActivity()) != null) {
                                NavUtils.navigateUpFromSameTask(getActivity());
                            }
                            Toast.makeText(getActivity(), R.string.toast_password_deleted, Toast.LENGTH_LONG).show();
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
                }else {
                    if (NavUtils.getParentActivityIntent(getActivity()) != null) {
                        NavUtils.navigateUpFromSameTask(getActivity());
                    }
                }
                return true;
            case R.id.password_fragment_menu_delete:
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
                        Toast.makeText(getActivity(), R.string.toast_password_deleted, Toast.LENGTH_LONG).show();
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

        mWebsiteField = (EditText)v.findViewById(R.id.password_website_editText);
        mWebsiteField.setText(mPassword.getmWebsite());
        mWebsiteField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPassword.setmWebsite(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mGoToWebsiteBtn = (ImageButton)v.findViewById(R.id.password_website_gotoBtn);
        mGoToWebsiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = mPassword.getmWebsite();
                if(url != null && !url.trim().isEmpty()) {
                    if (!url.startsWith("https://") && !url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    if(Patterns.WEB_URL.matcher(url).matches()){
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }else{
                        Toast.makeText(getActivity(), "The URL is invalid please make sure the website is entered correctly", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Please enter a website and try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        mPasswordField = (EditText)v.findViewById(R.id.password_pass);
        mPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
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
                if(s.toString().trim().isEmpty()) mPassword.setPass("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mShowHideBtn = (ImageButton)v.findViewById(R.id.show_hide_btn);
        mShowHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int inputType = mPasswordField.getInputType();

                if(inputType == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD){
                    mPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else{
                    mPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
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

        mCopyBtn = (Button)v.findViewById(R.id.password_copy_brn);
        mCopyBtn.setEnabled(true);
        mCopyBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @TargetApi(11)
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB){
                    android.text.ClipboardManager clipboard = (android.text.ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(mPassword.getmPassUtil().decryptString(mPassword.getPass()));
                }else{
                    android.content.ClipboardManager clipboard = (android.content.ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                    android.content.ClipData clip = android.content.ClipData.newPlainText("text label", mPassword.getmPassUtil().decryptString(mPassword.getPass()));
                    clipboard.setPrimaryClip(clip);
                }
                Toast.makeText(getActivity(),R.string.toast_password_copied, Toast.LENGTH_LONG).show();

            }
        });

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

        mSaveBtn = (Button)v.findViewById(R.id.password_save_btn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPassword.getTitle() == null || mPassword.getTitle().isEmpty()){
                    mTitleField.requestFocus();
                    mTitleField.setError("Please enter a Title");
                }else {
                    Intent intent = new Intent(getActivity(), PasswordListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.putExtra(EXTRA_PASSWORD_ID, 0);
                    startActivity(intent);
                    Toast.makeText(getActivity(), R.string.toast_password_saved, Toast.LENGTH_LONG).show();
                }
            }
        });
        mSaveBtn.setEnabled(true);
        //Making sure the software keyboard does not pop up on page load
        getActivity().getWindow().setSoftInputMode(EditorInfo.IME_ACTION_DONE);

        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        PasswordStation.get(getActivity()).savePasswords();
    }
}
