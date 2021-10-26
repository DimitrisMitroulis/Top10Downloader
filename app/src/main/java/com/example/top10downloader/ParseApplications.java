package com.example.top10downloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class ParseApplications {
//class to parse thought the rss feed of top 10 applications
    private static final String TAG = "Parse Applications";
    private ArrayList <FeedEntry> applications;

    public ParseApplications() {
        //initialize ArrayList
        this.applications = new ArrayList<>();
    }

    public ArrayList<FeedEntry> getApplications() {
        return applications;
    }

    public boolean parse(String xmlData){
        boolean Status = true;
        FeedEntry currentRecord;   //for every new entry we create a new feedEntry obj
        boolean inEntry = false;
        String textValue = "";

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            while(xpp.getEventType() != XmlPullParser.END_DOCUMENT){
                xpp.nextToken();

            }
        } catch(Exception e){
            Status =false;
            e.printStackTrace();
        }


        return Status;

    }
}
