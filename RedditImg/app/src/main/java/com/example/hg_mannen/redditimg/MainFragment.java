package com.example.hg_mannen.redditimg;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by hg-mannen on 2015-12-01.
 */
public class MainFragment extends Fragment{
    String subreddit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.main_fragment,container,false);

        Button button = (Button)view.findViewById(R.id.gogo);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText temp =  (EditText)view.findViewById(R.id.subreddit);
                subreddit = temp.getText().toString();

                WebScroller scroll = new WebScroller();
                Bundle b = new Bundle();
                b.putString("subreddit",subreddit);
                scroll.setArguments(b);
                Log.d("click", subreddit);
                //FragmentTransaction ft = getFragmentManager().beginTransaction();
                //ft.setCustomAnimations(R.anim.inleft, R.anim.outleft);
                getFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mainframe,scroll).commit();
                //ft.replace(R.id.mainframe,scroll).commit();

            }
        });

        return view;
    }
}
