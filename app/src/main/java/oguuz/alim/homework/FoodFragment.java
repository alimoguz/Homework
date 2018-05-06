package oguuz.alim.homework;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;

import java.io.IOException;

/**
 * Created by Alim on 6.5.2018.
 */

public class FoodFragment extends Fragment {
    private View mMainView;
    private TextView mFoodList;
    private String result="";
    private org.jsoup.nodes.Document doc;
    private Handler handler;


    public FoodFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mMainView= inflater.inflate(R.layout.food_fragment, container, false);
        mFoodList= mMainView.findViewById(R.id.food_list);
        handler= new Handler();
        getFoodList();


        return mMainView;
    }

    private void getFoodList() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    doc = Jsoup.connect("http://ybu.edu.tr/sks/").get();
                    Element table= doc.select("table").get(0);
                    Elements rows= table.select("tr");

                    for(int i=1;i<rows.size();i++){
                        Element row=rows.get(i);
                        Elements td= row.select("td");
                        result=result+"\n"+td.get(0).text();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            MainActivity.mProgressDialog.dismiss();
                            mFoodList.setText(result);

                        }
                    });
                } catch (IOException e) {
                    Log.d("HATA", e.toString());
                }
            }






        }).start();

    }

    @Override
    public void onStop() {
        super.onStop();
        result="";
    }
}
