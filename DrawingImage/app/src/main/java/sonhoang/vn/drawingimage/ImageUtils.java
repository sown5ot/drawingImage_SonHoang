package sonhoang.vn.drawingimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Son Hoang on 9/27/2017.
 */

public class ImageUtils {
    private static final String TAG = ImageUtils.class.toString();

    public static void saveImage(Bitmap bitmap, Context context){
        String root = Environment.getExternalStorageDirectory().toString();
        File myFolder = new File(root + "/drawingImages");
        myFolder.mkdirs();

        String imageName = Calendar.getInstance().getTime().toString() + ".png";
        Log.d(TAG, "saveImage: " + imageName);

        File imageFile = new File(myFolder, imageName);

        try {
            FileOutputStream fout = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout);
            fout.flush();
            fout.close();

            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();

            MediaScannerConnection.scanFile(context,
                    new String[]{imageFile.getAbsolutePath()},
                    null,
                    null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
