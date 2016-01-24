package com.example.hg_mannen.redditimg;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Vector;

/**
 * Created by hg-mannen on 2015-11-30.
 */
public class ScrapeHtml {

    public Vector<String> getContent(final String subreddit, final String _splitter)
    {
        final String splitter = _splitter;
        final Vector<String> content = new Vector<String>();

        final Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                Elements imglinks = null;
                Elements allChildren = null;
                try {
                    Log.d("CONNECTAR TILL: ", "http://www.reddit.com/r/" + subreddit);
                    doc = Jsoup.connect("http://www.reddit.com/r/" + subreddit).get();

                    allChildren = doc.select("div.sitetable");
                    Elements nextpage = allChildren.select(".nav-buttons").select(".nextprev").select("a[rel=nofollow next");
                    String nextload = nextpage.attr("abs:href");
                    imglinks = doc.select("a.title.may-blank ");

                    if (nextload.isEmpty() || imglinks.isEmpty()) {
                        nextload = "EMPTY";
                    }
                    else {
                        String[] nextPages = nextload.split("/");
                        nextload = nextPages[5];
                    }


                    for(org.jsoup.nodes.Element elem : imglinks)
                    {
                        String temp = elem.attr("abs:href");

                        if(temp.matches(".*.gifv"))
                        {
                            temp = temp.substring(0, temp.length()-1);
                            String element = elem.text() + splitter + temp;
                            content.add(element);
                        }
                        else if(temp.matches(".*.(gif|jpg|png)"))
                        {
                            String element = elem.text() + splitter + temp;
                            content.add(element);
                        }

                        else if(temp.matches("http://imgur.*"))
                        {
                            temp = new StringBuilder(temp).insert(7,"i.").toString() + ".jpg";
                            String element = elem.text() + splitter + temp;
                            content.add(element);
                        }
                        else if(temp.matches("http://i.imgur.*"))
                        {
                            String element = elem.text() + splitter + elem.attr("abs:href");
                            content.add(element);
                        }
                    }
                    content.add(nextload);

                } catch (IOException e) {
                    content.add("EMPTY");
                    e.printStackTrace();
                }
            }

        });
        t2.start();
        while(t2.isAlive())
        {
        }
        return content;
    }

}
