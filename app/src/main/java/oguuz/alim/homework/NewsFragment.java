package oguuz.alim.homework;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alim on 6.5.2018.
 */

public class NewsFragment extends Fragment {

    private ArrayList<String> news;
    private ArrayList<String> news_url;
    private ListView news_list;
    private org.jsoup.nodes.Document doc;
    private Handler handler;
    private View mMainView;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView= inflater.inflate(R.layout.news_fragment, container, false);
        news_list=mMainView.findViewById(R.id.news_list);
        news=new ArrayList<>();
        news_url=new ArrayList<>();
        handler=new Handler();
        getNews();
        return mMainView;
    }

    private void getNews() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                    Element element= doc.select("div.cnContent").first();
                    Elements rows=element.select("div.cncItem");

                    for(int i=0;i<rows.size();i++){
                        Element row=rows.get(i);
                        Elements sub_row=row.select("a");

                        news.add(sub_row.get(0).text());
                        String content_new=row.select("a").attr("href");
                        news_url.add(content_new);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, news);
                            news_list.setAdapter(adapter);
                            news_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String content_url = "http://www.ybu.edu.tr/muhendislik/bilgisayar/"+news_url.get(position).toString();
                                    Uri uri = Uri.parse(content_url);
                                    Intent open_news = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(open_news);






                                }
                            });


                        }
                    });
                } catch (IOException e) {
                    Log.d("HATA", e.toString());
                }
            }






        }).start();
    }
}
