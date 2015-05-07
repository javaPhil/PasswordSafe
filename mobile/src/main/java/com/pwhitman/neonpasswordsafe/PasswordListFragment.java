package com.pwhitman.neonpasswordsafe;


import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordListFragment extends ListFragment {

    private ArrayList<Password> mPasswords;
    private static final String TAG = "PasswordListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.password_list_title);
        mPasswords = PasswordStation.get(getActivity()).getPasswords();

        PasswordAdapter adapter = new PasswordAdapter(mPasswords);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((PasswordAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Password p = ((PasswordAdapter)getListAdapter()).getItem(position);
        //Log.d(TAG, c.getTitle() + " was clicked");

        //Intent i = new Intent(getActivity(), CrimeActivity.class);
        Intent i = new Intent(getActivity(), PasswordPagerActivity.class);
        i.putExtra(PasswordFragment.EXTRA_PASSWORD_ID, p.getId());
        startActivity(i);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_password_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_item_new_password:
                Password pass = new Password();
                PasswordStation.get(getActivity()).addPassword(pass);
                Intent i = new Intent(getActivity(), PasswordPagerActivity.class);
                startActivityForResult(i, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class PasswordAdapter extends ArrayAdapter<Password>{

        public PasswordAdapter(ArrayList<Password> passwords){
            super(getActivity(), 0, passwords);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           if(convertView == null){
               convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_password, null);
           }

            Password p = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id.password_list_item_titleTextView);
            titleTextView.setText(p.getTitle());

            TextView dateTextView = (TextView)convertView.findViewById((R.id.password_list_item_creationDateTextView));
            dateTextView.setText(p.getCreationDate().toString());

            return convertView;
        }
    }
}
