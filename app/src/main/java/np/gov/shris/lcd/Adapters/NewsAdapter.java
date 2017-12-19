package np.gov.shris.lcd.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import np.gov.shris.lcd.Models.News;
import np.gov.shris.lcd.R;

/**
 * Created by shris on 6/10/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    Context context;
    List<News> newsList;


    public NewsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<News> objects) {
        super(context, resource, objects);

        this.context = context;
        this.newsList = objects;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        final News news = newsList.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_single_item, null);

        final TextView newsLastUpdated = (TextView) view.findViewById(R.id.newsLastUpdated);
        final TextView newsTitle = (TextView) view.findViewById(R.id.newsTitle);


        newsLastUpdated.setText(context.getString(R.string.updated_at) + news.getUpdatedAt());
        newsTitle.setText(news.getNewsTitle());

        return view;
    }

}
