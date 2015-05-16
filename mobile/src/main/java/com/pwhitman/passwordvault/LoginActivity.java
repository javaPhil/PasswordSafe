package com.pwhitman.passwordvault;

import android.support.v4.app.Fragment;

/**
 * Created by Philip on 5/13/2015.
 */
public class LoginActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
