package com.javaphil.passwordvault;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
//        LinearLayout layout = (LinearLayout)v.findViewById(R.id.tutorial_layout);
//        ImageView image = new ImageView(getActivity());
//
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(layout.getWidth(), layout.getHeight());
//        image.setLayoutParams(params);
////        BitmapDrawable bm = BitmapUtils.getScaledDrawable(getActivity(), imageResourceId);
//
//        //image.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(),imageResourceId, params.width, params.height));
//        layout.addView(image);

        return v;
    }

}
