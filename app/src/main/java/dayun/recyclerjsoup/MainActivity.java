package dayun.recyclerjsoup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    RecyclerViewAdapter mAdapter;
    ArrayList<String> imageArray = new ArrayList<String>();
    ArrayList<String> hrefArray = new ArrayList<String>();
    private String htmlPageUrl = "http://www.gettyimagesgallery.com/collections/archive/slim-aarons.aspx/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.main_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();
                Elements links = doc.select("[src]");

                for (Element src : links) {
                    if (src.tagName().equals("img")) {
                        hrefArray.add(src.attr("abs:src"));
                    }
                }
                for (int i = 0; i < hrefArray.size(); i++) {
                    String str = hrefArray.get(i);
                    if (str.startsWith("https://"))
                        str = str.replace("https://", "http://");
                    if (str != null)
                        imageArray.add(str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mAdapter = new RecyclerViewAdapter(imageArray);
            mRecyclerView.setAdapter(mAdapter);

            Toast.makeText(MainActivity.this, "이미지 출력", Toast.LENGTH_LONG).show();


        }
    }
}