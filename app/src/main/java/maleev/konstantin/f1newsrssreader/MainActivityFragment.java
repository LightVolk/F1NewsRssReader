package maleev.konstantin.f1newsrssreader;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.crazyhitty.chdev.ks.rssmanager.OnRssLoadListener;
import com.crazyhitty.chdev.ks.rssmanager.RssItem;
import com.crazyhitty.chdev.ks.rssmanager.RssReader;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {




    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);

        final RssLoader loader=new RssLoader(getContext(),getActivity());
        loader.loadFeeds();

        ListView listView= (ListView) view.findViewById(R.id.listViewNews);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                if(loader.getRssItems().isEmpty())
                {
                    return;
                }

                RssItem item=loader.getRssItems().get(i);
                if(item==null)
                    return;

                //start new activity with text of news
                Intent launchBrowser = new Intent(getActivity(), PageActivity.class);
                String goUrl=item.getLink();
                launchBrowser.putExtra("url", goUrl);

                startActivity(launchBrowser);
            }
        });



//ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        //      android.R.layout.simple_list_item_1, names);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.rsse_lement,names);



        return view;
    }



}
