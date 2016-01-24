package com.example.hg_mannen.redditimg;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

/**
 * Created by hg-mannen on 2015-12-04.
 */
public class WebScroller extends Fragment{

    WebView img;
    TextView text;
    Integer imgIndex=0;
    String subreddit = "blackpeopletwitter";
    String firsthtml= "<!DOCTYPE html><html><body><img src=\"";
    String secondhtml= "\" width=\"100%\" height=\"100%\"></body></html>";
    Vector<String> imgs = new Vector<>();
    ScrapeHtml scraper = new ScrapeHtml();
    String splitter = "HaraldG";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.webscroll, container, false);

        subreddit = getArguments().getString("subreddit");
        text = (TextView) view.findViewById(R.id.textView2);
        img = (WebView) view.findViewById(R.id.webView);
        imgs = scraper.getContent(subreddit,splitter);
        if(imgs.lastElement() == "EMPTY")
        {
            Toast.makeText(getActivity(), "Nothing to see there...", Toast.LENGTH_LONG).show();
            getFragmentManager().beginTransaction().replace(R.id.mainframe, new MainFragment()).commit();
            return view;
        }

        //Laddar touchanimation!
        RelativeLayout scrollview = (RelativeLayout) view.findViewById(R.id.topwebscroll);
        final SwipeTraceView stv = new SwipeTraceView(getActivity());
        stv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        scrollview.addView(stv);


        //Ladda bild och titel:
        img.setBackgroundColor(Color.TRANSPARENT);
        String[] parts = imgs.elementAt(imgIndex).split(splitter);
        text.setText(parts[0]);
        img.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        String data = firsthtml + parts[1] + secondhtml;
        img.loadData(data, "text/html", "utf-8");


        stv.setOnTouchListener(new SwipeListener(getActivity()) {

            public void onSwipeRight() {
                //LADDA FÖREGÅENDE BILD:
                imgIndex--;
                if (imgIndex < 0) {
                    pastIndex();
                } else {
                    rightAnimation(view, 400);
                }
            }

            public void onSwipeLeft() {
                //LADDA NÄSTA BILD:

                imgIndex++;

                if (imgIndex > imgs.size() - 2) {
                    futureIndex(imgs.lastElement());
                }
                leftAnimation(view, 400);
            }

            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP)
                {
                    stv.killDraw();
                }
                else{
                    stv.pressDraw(event.getX(), event.getY());
                }
                return gestureDetector.onTouchEvent(event);
            }

        });

        return view;
    }

    public void pastIndex()
    {
        imgIndex = 0;
        Toast t = new Toast(getActivity());
        t.makeText(getActivity(),"There is no past, only future.",Toast.LENGTH_LONG).show();
    }

    public void futureIndex(String togo)
    {
        imgIndex = 0;
        imgs = scraper.getContent(subreddit + togo, splitter);
        Toast t = new Toast(getActivity());
        t.makeText(getActivity(), "Now entering the future.", Toast.LENGTH_LONG).show();
    }
    public void rightAnimation(final View view1, final int durat)
    {
        final float center = view1.getX();
        //.x(1280)
        //.rotationY(90)
        view1.animate().alpha(0f).setDuration(durat).withEndAction(new Runnable() {
            @Override
            public void run() {
                //view1.setX(-1280);
                //view1.setRotationY(-90);
                String[] parts = imgs.elementAt(imgIndex).split(splitter);
                text.setText(parts[0]);
                img.loadData(firsthtml + parts[1] + secondhtml, "text/html", "utf-8");
                view1.animate().setDuration(durat*2).alpha(1);
                //view1.animate().setDuration(durat).rotationY(0).x(center);
            }
        });
    }    public void leftAnimation(final View view1, final int durat)
    {
        final float center = view1.getX();
        //.x(-1280).rotationY(-90)
        view1.animate().alpha(0f).setDuration(durat).withEndAction(new Runnable() {
            @Override
            public void run() {
                //view1.setX(1280);
                //view1.setRotationY(90);
                String[] parts = imgs.elementAt(imgIndex).split(splitter);
                text.setText(parts[0]);
                img.loadData(firsthtml + parts[1] + secondhtml, "text/html", "utf-8");
                view1.animate().setDuration(durat*2).alpha(1);
                //view1.animate().setDuration(durat).rotationY(0).x(center);
            }
        });
    }

}
