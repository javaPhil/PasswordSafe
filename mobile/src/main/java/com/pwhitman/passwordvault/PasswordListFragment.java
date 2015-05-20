package com.pwhitman.passwordvault;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.melnykov.fab.FloatingActionButton;
import com.pwhitman.passwordvault.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Philip on 1/4/2015.
 */
public class PasswordListFragment extends ListFragment {

    private ArrayList<Password> mPasswords;
    private static final String TAG = "PasswordListFragment";
    private boolean sortByDate;
    private boolean sortByDateReverse;
    private boolean sortByAlpha;
    private boolean sortByAlphaReverse;
    private SharedPreferences mPrefs;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.password_list_title);
        mPasswords = PasswordStation.get(getActivity()).getPasswords();


        mPrefs = getActivity().getSharedPreferences(LoginUtility.PREFERENCES, Context.MODE_PRIVATE);
        sortByDate = mPrefs.getBoolean(LoginUtility.PREF_SORT_DATE, true);
        sortByAlpha = mPrefs.getBoolean(LoginUtility.PREF_SORT_ALPHA, false);
        sortByDateReverse = mPrefs.getBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false);
        sortByAlphaReverse = mPrefs.getBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false);

        //Getting user preference on sorting
        if(sortByDate && sortByDateReverse){
            Collections.sort(mPasswords, new Comparator<Password>() {
                @Override
                public int compare(Password p1, Password p2) {
                    if (p1.getCreationDate() == null || p2.getCreationDate() == null) {
                        return 0;
                    } else {
                        return p2.getCreationDate().compareTo(p1.getCreationDate());
                    }
                }
            });
//            mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false).commit();
        }else if(sortByDate && !sortByDateReverse) {
            Collections.sort(mPasswords, new Comparator<Password>() {
                @Override
                public int compare(Password p1, Password p2) {
                    if (p1.getCreationDate() == null || p2.getCreationDate() == null) {
                        return 0;
                    } else {
                        return p1.getCreationDate().compareTo(p2.getCreationDate());
                    }

                }
            });
//            mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, true).commit();
        }else if(sortByAlpha && sortByAlphaReverse){
            Collections.sort(mPasswords, new Comparator<Password>() {
                @Override
                public int compare(Password p1, Password p2) {
                    if (p1.getTitle() == null || p2.getTitle() == null) {
                        return 0;
                    } else {
                        return p2.getTitle().compareTo(p1.getTitle());
                    }

                }
            });
//            mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false).commit();
        }else if(sortByAlpha && !sortByAlphaReverse) {
            Collections.sort(mPasswords, new Comparator<Password>() {
                @Override
                public int compare(Password p1, Password p2) {
                    if (p1.getTitle() == null || p2.getTitle() == null) {
                        return 0;
                    } else {
                        return p1.getTitle().compareTo(p2.getTitle());
                    }

                }
            });
//            mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, true).commit();
        }

        PasswordAdapter adapter = new PasswordAdapter(mPasswords);
        setListAdapter(adapter);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    public void updateUI(){
        ((PasswordAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_password_list, container, false);
        ListView listView = (ListView)v.findViewById(android.R.id.list);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password pass = new Password();
                PasswordStation.get(getActivity()).addPassword(pass);
                Intent i = new Intent(getActivity(), PasswordPagerActivity.class);
                i.putExtra(PasswordFragment.EXTRA_PASSWORD_ID, pass.getId());
                startActivityForResult(i, 0);
            }
        });
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB){
            //Use floating context menus on Froyo and Gingerbread
            registerForContextMenu(listView);
        }else{
            //Use contextual action bar on Honeycomb and higher
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                    //Required, but not used in this implementation
                }

                @Override
                public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                    MenuInflater inflater = actionMode.getMenuInflater();
                    inflater.inflate(R.menu.password_list_item_context, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                    return false;
                    //Required but not used for this implementation
                }

                @Override
                public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                    switch(menuItem.getItemId()){
                        case R.id.menu_item_delete_password:
                        {
                            PasswordAdapter adapter = (PasswordAdapter) getListAdapter();
                            PasswordStation passStation = PasswordStation.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                if (getListView().isItemChecked(i)) {
                                    passStation.deletePassword(adapter.getItem(i));
                                }
                            }
                            actionMode.finish();
                            adapter.notifyDataSetChanged();
                        }
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode actionMode) {
                    //Required but not used for this implementation
                }
            });
        }

        return v;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.password_list_item_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = info.position;
        PasswordAdapter adapter = (PasswordAdapter)getListAdapter();
        Password pass = adapter.getItem(position);
        switch(item.getItemId()){
            case R.id.menu_item_delete_password:
                PasswordStation.get(getActivity()).deletePassword(pass);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Password p = ((PasswordAdapter)getListAdapter()).getItem(position);
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
//            case R.id.menu_item_new_password:
//                Password pass = new Password();
//                PasswordStation.get(getActivity()).addPassword(pass);
//                Intent i = new Intent(getActivity(), PasswordPagerActivity.class);
//                i.putExtra(PasswordFragment.EXTRA_PASSWORD_ID, pass.getId());
//                startActivityForResult(i, 0);
//                return true;
            case R.id.sub_menu_sort_date:
            {
                //Getting booleans for sorting
                sortByDate = mPrefs.getBoolean(LoginUtility.PREF_SORT_DATE, false);
                sortByDateReverse = mPrefs.getBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false);;

                PasswordAdapter adapter = (PasswordAdapter) getListAdapter();
                PasswordStation passStation = PasswordStation.get(getActivity());

                if(sortByDate && !sortByDateReverse){
                    Collections.sort(passStation.getPasswords(), new Comparator<Password>() {
                        @Override
                        public int compare(Password p1, Password p2) {
                            if (p1.getCreationDate() == null || p2.getCreationDate() == null) {
                                return 0;
                            } else {
                                return p2.getCreationDate().compareTo(p1.getCreationDate());
                            }
                        }
                    });
                    mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, true).commit();
                }else {
                    Collections.sort(passStation.getPasswords(), new Comparator<Password>() {
                        @Override
                        public int compare(Password p1, Password p2) {
                            if (p1.getCreationDate() == null || p2.getCreationDate() == null) {
                                return 0;
                            } else {
                                return p1.getCreationDate().compareTo(p2.getCreationDate());
                            }

                        }
                    });
                    mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false).commit();
                }
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE, true).commit();
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA, false).commit();
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false).commit();
                adapter.notifyDataSetChanged();
            }
            return true;
            case R.id.sub_menu_sort_alpha:
            {

                //Getting booleans for sorting
                PasswordAdapter adapter = (PasswordAdapter) getListAdapter();
                PasswordStation passStation = PasswordStation.get(getActivity());

                sortByAlpha = mPrefs.getBoolean(LoginUtility.PREF_SORT_ALPHA, false);
                sortByAlphaReverse = mPrefs.getBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false);;

                if(sortByAlpha && !sortByAlphaReverse){
                    Collections.sort(passStation.getPasswords(), new Comparator<Password>() {
                        @Override
                        public int compare(Password p1, Password p2) {
                            if (p1.getTitle() == null || p2.getTitle() == null) {
                                return 0;
                            } else {
                                return p2.getTitle().compareTo(p1.getTitle());
                            }

                        }
                    });
                    mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, true).commit();
                }else {
                    Collections.sort(passStation.getPasswords(), new Comparator<Password>() {
                        @Override
                        public int compare(Password p1, Password p2) {
                            if (p1.getTitle() == null || p2.getTitle() == null) {
                                return 0;
                            } else {
                                return p1.getTitle().compareTo(p2.getTitle());
                            }

                        }
                    });
                    mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA_REVERSE, false).commit();
                }
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE, false).commit();
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_DATE_REVERSE, false).commit();
                mPrefs.edit().putBoolean(LoginUtility.PREF_SORT_ALPHA, true).commit();
                adapter.notifyDataSetChanged();
            }
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
            SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
            String newDate = format.format(p.getCreationDate());
            dateTextView.setText(newDate);

            return convertView;
        }
    }
}
