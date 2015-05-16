package com.pwhitman.passwordvault;

import android.support.v4.app.Fragment;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new PasswordListFragment();
    }
}
