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
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alim on 6.5.2018.
 */

public class AnnouncementsFragment extends Fragment {
    private View mMainView;
    private ArrayList<String> announce;
    private ArrayList<String> announce_url;
    private ListView annonce_list;
    private org.jsoup.nodes.Document doc;
    private Handler handler;

    public AnnouncementsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView= inflater.inflate(R.layout.announcements_fragment, container, false);
        annonce_list=mMainView.findViewById(R.id.announce_list);
        announce=new ArrayList<>();
        announce_url=new ArrayList<>();
        handler=new Handler();
        getAnnouncementList();
        return mMainView;


    }

    private void getAnnouncementList() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doc = Jsoup.connect("http://www.ybu.edu.tr/muhendislik/bilgisayar/").get();
                    Element element= doc.select("div.caContent").first();
                    Elements rows=element.select("div.cncItem");

                    for(int i=0;i<rows.size();i++){
                        Element row=rows.get(i);
                        Elements sub_row=row.select("a");

                        announce.add(sub_row.get(0).text());
                        String content_announce=row.select("a").attr("href");
                        announce_url.add(content_announce);
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            ArrayAdapter adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, announce);
                            annonce_list.setAdapter(adapter);
                            annonce_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                    String content_url = "http://www.ybu.edu.tr/muhendislik/bilgisayar/"+announce_url.get(position).toString();
                                    Uri uri = Uri.parse(content_url);
                                    Intent open_announce = new Intent(Intent.ACTION_VIEW, uri);
                                    startActivity(open_announce);





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
