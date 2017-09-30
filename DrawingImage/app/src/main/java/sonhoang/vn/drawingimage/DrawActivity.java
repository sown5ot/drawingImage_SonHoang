package sonhoang.vn.drawingimage;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;


import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DrawActivity.class.toString();
    private ImageView ivPickColor;
    private ImageView ivSave;
    private RadioGroup radioGroup;
    private DrawingView drawingView;

    public static int currentColor = 0xFFF21524;
    public static int curentSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        setupUI();

        if (getIntent().getBooleanExtra(MainActivity.MODE_CAMERA, false)){
            openCamera();
            Log.d(TAG, "onCreate: OpenCamera");

        } else {
            addDrawingView();
            Log.d(TAG, "onCreate: DrawingView");
        }

        addListener();
    }

    private void openCamera() {

    }

    private void addDrawingView() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_drawing_view);

        drawingView = new DrawingView(this);
        drawingView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        relativeLayout.addView(drawingView);
    }

    private void addListener() {
        ivPickColor.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_thin: {
                        curentSize = 5;
                        break;
                    }

                    case R.id.rb_medium: {
                        curentSize = 10;
                        break;
                    }

                    case R.id.rb_strong: {
                        curentSize = 15;
                        break;
                    }
                }
                Log.d(TAG, "onCheckedChange: " + curentSize);
            }
        });
    }

    private void setupUI() {
        ivPickColor = (ImageView) findViewById(R.id.iv_view_color);
        ivPickColor.setColorFilter(currentColor);
        ivSave = (ImageView) findViewById(R.id.iv_save);

        radioGroup = (RadioGroup) findViewById(R.id.rg_pen_size);
        radioGroup.check(R.id.rb_medium);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_view_color:{
                pickColor();
                break;
            }
            case R.id.iv_save:{
                saveImage();
                ivSave.setClickable(false);
                this.finish();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void saveImage() {
        drawingView.setDrawingCacheEnabled(true);
        drawingView.buildDrawingCache();
        Bitmap bitmap = drawingView.getDrawingCache();

        Log.d(TAG, "save image: " + bitmap.getWidth());

        ImageUtils.saveImage(bitmap, this);
    }

    private void pickColor() {
        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(currentColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setPositiveButton("OK", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int selectedColor, Integer[] integers) {
                        ivPickColor.setColorFilter(selectedColor);
                        currentColor = selectedColor;
                    }
                })
                .lightnessSliderOnly()
                .build()
                .show();
    }



}
