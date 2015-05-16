package com.pwhitman.passwordvault;

import android.support.v4.app.Fragment;

import java.util.UUID;

/**
 * Created by Philip on 5/7/2015.
 */
public class PasswordActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        UUID passwordId = (UUID)getIntent().getSerializableExtra(PasswordFragment.EXTRA_PASSWORD_ID);
        return PasswordFragment.newInstance(passwordId);
    }
}
