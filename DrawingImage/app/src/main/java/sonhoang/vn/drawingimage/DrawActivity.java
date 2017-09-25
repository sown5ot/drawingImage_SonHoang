package sonhoang.vn.drawingimage;

import android.content.DialogInterface;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = DrawActivity.class.toString();
    private ImageView ivPickColor;
    private ImageView ivSave;
    private RadioGroup radioGroup;

    private int currentColor = 0xFFF21524;
    private int curentSize = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        setupUI();
        addListener();
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
                save();
                break;
            }
        }
    }

    private void save() {

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
                .build()
                .show();
    }
}
