package np.gov.shris.lcd.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import np.gov.shris.lcd.R;


/**
 * Created by shris on 6/9/2017.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    ArrayList<Integer> images;

    private ImageView imageView;

    public ImageAdapter(Context context, ArrayList<Integer> images) {
        this.context = context;
        this.images = images;
    }

    public int getCount() {
        return images == null ? 0 : images.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            // if it's not recycled, initialize some attributes

            convertView = inflater.inflate(R.layout.gallery_item, null);
            imageView = (ImageView) convertView.findViewById(R.id.galleryImage);


        } else {
            //imageView = (ImageView) convertView;
        }
        try {
            Picasso.with(context)
                    .load(images.get(position))
                    .resize(400,400)
                    .centerInside()
                    .into(imageView);
        }catch (OutOfMemoryError e){
            e.printStackTrace();
        }

        return convertView;
    }


}

