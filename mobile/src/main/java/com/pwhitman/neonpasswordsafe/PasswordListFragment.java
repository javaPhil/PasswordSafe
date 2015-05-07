package com.pwhitman.neonpasswordsafe;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
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
        getActivity().setTitle("Passwords");
        mPasswords = PasswordStation.get(getActivity()).getPasswords();

        PasswordAdapter adapter = new PasswordAdapter(mPasswords);
        setListAdapter(adapter);

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
        Intent i = new Intent(getActivity(), PasswordPagerActivity.class); //TODO This is next
        i.putExtra(PasswordFragment.EXTRA_PASSWORD_ID, p.getId());
        startActivity(i);
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
