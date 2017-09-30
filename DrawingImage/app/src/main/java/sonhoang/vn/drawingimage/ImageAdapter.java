package sonhoang.vn.drawingimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Son Hoang on 9/30/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private List<String> imagePaths = new ArrayList<>();
    private Context context;

    public ImageAdapter(List<String> imagePaths, Context context) {
        this.imagePaths = imagePaths;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imagePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);

        //set Size
        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                500));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setPadding(5, 5, 5, 5);
        imageView.setCropToPadding(true);

        //set Data
        Bitmap bitmap = BitmapFactory.decodeFile(imagePaths.get(position));
        imageView.setImageBitmap(bitmap);

        return imageView;
    }
}
