package maleev.konstantin.f1newsrssreader;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.ArrayList;
import java.util.List;

import maleev.konstantin.f1newsrssreader.Adapters.SharedData;

/**
 * Created by Simplevolk on 27.08.2016.
 */

public class RssLoader {

    public List<RssItem> getRssItems() {
        return RssItems;
    }

    public   List<RssItem> RssItems =new ArrayList<>();
    private  Context _context;
    private  Activity _activity;
    private  SwipeRefreshLayout _swipeRefresh;
    private int _loadCount=0;
    public RssLoader(Context context, Activity activity, SwipeRefreshLayout swipeRefreshLayout)
    {
        _context=context;
        _activity=activity;
        _swipeRefresh=swipeRefreshLayout;
    }

    public RssLoader(Context context, Activity activity)
    {
        _context=context;
        _activity=activity;
    }

public ArrayList<String> loadFeedsArr()
{
    final ArrayList<String> arrayList=new ArrayList<String>();

    //you can also pass multiple urls
    String[] urlArr = {"http://www.f1news.ru/export/news.xml"};
    new RssReader(_context)
            .showDialog(false)
            .urls(urlArr)
            .parse(new OnRssLoadListener() {
                @Override
                public void onSuccess(List<RssItem> rssItems) {

                    Log.i("RSS","Success!");


                    if(rssItems.size()!=0)
                    {
                        arrayList.clear();
                        RssItems.clear();
                    }

                    Toast.makeText(_context, rssItems.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                    for (RssItem item:rssItems
                            ) {
                        RssItems.add(item);
                        arrayList.add(item.getTitle());
                        Log.i("Item:",item.getTitle());
                    }


                }

                @Override
                public void onFailure(String message) {
                    Toast.makeText(_context, "Error: "+message, Toast.LENGTH_SHORT).show();
                   ArrayList<String> arrayList= loadFeedsArr();

                    if(_loadCount>=5)
                        return;
                }
            });

    while (arrayList.isEmpty())
    {

    }

    return arrayList;
}

    //load feeds
    public void loadFeeds() {
        //you can also pass multiple urls
        String[] urlArr = {"http://www.f1news.ru/export/news.xml"};
     new RssReader(_context)
                .showDialog(false)
                .urls(urlArr)
                .parse(new OnRssLoadListener() {
                    @Override
                    public void onSuccess(List<RssItem> rssItems) {

                        Log.i("RSS","Success!");



                        ArrayList<String> titles=new ArrayList<>();
                        if(rssItems.size()!=0)
                        {
                            titles.clear();
                         //   RssItems.clear();
                        }

                        Toast.makeText(_context, rssItems.get(0).getTitle(), Toast.LENGTH_SHORT).show();

                        for (RssItem item:rssItems
                                ) {
                            RssItems.add(item);
                            titles.add(item.getTitle()+"\n"+item.getPubDate());
                            Log.i("Item:",item.getTitle());

                        }

//tvRssElement
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(_context,R.layout.rsse_lement,titles);
                        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(_context,R.layout.support_simple_spinner_dropdown_item,titles);
                        ListView listNewNews= (ListView) _activity.findViewById(R.id.listViewNews);

                        SharedData.adapter=adapter;

                        listNewNews.setAdapter(SharedData.adapter);


                        if(_swipeRefresh!=null) {
                            _swipeRefresh.setRefreshing(false);

                        }

                    }

                    @Override
                    public void onFailure(String message) {
                        Toast.makeText(_context, "Error: "+message, Toast.LENGTH_SHORT).show();
                        loadFeeds();

                        if(_loadCount>=5)
                            return;
                    }
                });

        //return _rssItems;
    }
}
