package sonhoang.vn.drawingimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.toString();
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static String MODE_CAMERA = "mode_camera";

    private FloatingActionButton fbAddButton;
    private SubActionButton btCameraNote;
    private SubActionButton btBlankNote;
    private FloatingActionMenu floatingActionMenu;
    private GridView gvImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
        setupUI();
        addListener();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE);
            }
        }
    }

    private void setupUI() {
        fbAddButton = (FloatingActionButton) findViewById(R.id.fb_addbutton);
        SubActionButton.Builder sbBuilder = new SubActionButton.Builder(this);
        btCameraNote = sbBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_add_a_photo_black_24dp)).build();
        btBlankNote = sbBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_brush_black_24dp)).build();
        floatingActionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(btCameraNote)
                .addSubActionView(btBlankNote)
                .attachTo(fbAddButton)
                .build();

        gvImage = (GridView) findViewById(R.id.gv_image);
    }

    private void addListener() {
        btCameraNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                intent.putExtra(MODE_CAMERA, true);
                startActivity(intent);
                floatingActionMenu.close(false);
                Log.d(TAG, "onClick: camera");
            }
        });

        btBlankNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                intent.putExtra(MODE_CAMERA, false);
                startActivity(intent);
                floatingActionMenu.close(false);
            }
        });
    }

    private List<String> getListImagePath(){
        List<String> imagePaths = new ArrayList<>();

        File imageFolder = new File(Environment.getExternalStorageDirectory().toString() + "/drawingImages");
        File[] listFile = imageFolder.listFiles();
        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                String filePath = listFile[i].getAbsolutePath();
                imagePaths.add(filePath);
            }
        }
        return imagePaths;
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageAdapter imageAdapter = new ImageAdapter(getListImagePath(), this);
        gvImage.setAdapter(imageAdapter);
    }
}
