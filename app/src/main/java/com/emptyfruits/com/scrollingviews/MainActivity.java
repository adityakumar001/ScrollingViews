package com.emptyfruits.com.scrollingviews;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.emptyfruits.com.scrollingviews.databinding.ActivityMainBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    ActivityMainBinding mainBinding;
    public static final String URL =
            "https://en.wikipedia.org/wiki/Android_(operating_system)";
    Human[] population = new Human[8];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setPopulation();
        mainBinding.myList
                .setAdapter(new MyAdapter
                        (MainActivity.this, R.layout.my_list_item, population));
        new Thread() {
            public void run() {
                try {
                    Document document = Jsoup.connect(URL).get();
                    MainActivity.this.runOnUiThread(() -> {
                        mainBinding.htmlView
                                .setText(document.body().text().substring(0, 4000));
                    });
                } catch (IOException ie) {
                    Log.d(TAG, "onCreate: ");
                }
            }
        }.start();


    }

    private void setPopulation() {
        Random random = new Random();
        for (int i = 0; i < population.length; i++) {
            population[i] = new Human("abcd" + random.nextInt(100),
                    String.valueOf(random.nextInt(100)),
                    "abc@abc" + random.nextInt(100));
        }
    }

}
