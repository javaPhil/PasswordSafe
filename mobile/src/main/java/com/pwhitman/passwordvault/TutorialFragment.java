package com.pwhitman.passwordvault;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Philip on 5/18/2015.
 */
public class TutorialFragment extends Fragment {

    public static final String ARG_PAGE = "page";
    private int imageResourceId;


    public TutorialFragment(){

    }
    public static TutorialFragment newInstance(int i){
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PAGE, i);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageResourceId = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tutorial, container, false);
        LinearLayout layout = (LinearLayout)v.findViewById(R.id.tutorial_layout);
        ImageView image = new ImageView(getActivity());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(layout.getWidth(), layout.getHeight());
        image.setLayoutParams(params);
//        BitmapDrawable bm = BitmapUtils.getScaledDrawable(getActivity(), imageResourceId);

        image.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(),imageResourceId, image.getWidth(), image.getHeight()));


        layout.addView(image);

        return v;
    }

    @Override
    public void onStop() {
        super.onStop();
//        BitmapUtils.cleanImageView();
    }



}
