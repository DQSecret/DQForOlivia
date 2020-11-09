package com.example.dqforolivia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.ims.ImsRcsManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    boolean ctl = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three);

        // 进行include 定位view 需要下一层级，进行寻找
//        View tv_1 = findViewById(R.id.top_10);
//        TextView tv_2 = tv_1.findViewById(R.id.subtop_01);
//
//        tv_2.setText(R.string.oliviaTest4);

        /* Find button add click event listener */
        /* When click button change text info to olivia dynamic text olivia love DQ*/
        Button btn_01 = findViewById(R.id.btn_01);
//        btn_01.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                TextView text_default = (TextView) findViewById(R.id.default_id);
//                text_default.setText("Olivia lover DQ");
//
//            }
//        });


        /* 外部进行定义点击事件 */
        btn_01.setOnClickListener(this);

//        View three =  LayoutInflater.from(this).inflate(R.layout.activity_three,null);

        findViewById(R.id.btn_02).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_01:
                this.clickText();
//                TextView text_default = (TextView) findViewById(R.id.default_id);
//                text_default.setText("Olivia lover DQ 222");
                break;
            case R.id.btn_02:
                this.clickText02();
        }


    }

    private void clickText() {
        this.ctl = !this.ctl;
        TextView text_default = findViewById(R.id.default_id);

        if (this.ctl) {
            text_default.setText("This is button 01 first click");
        } else {
            text_default.setText("This is button 02 second click");
        }


    }

    private void clickText02() {

        LinearLayoutCompat root = findViewById(R.id.root_container);
        /* 添加一个新的view */
        TextView new_tv = new TextView(this);
        new_tv.setText("Add new text");
        new_tv.setTextSize(20);

//        new_tv.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_purple));

//        new_tv.setBackgroundColor(ContextCompat.getColor(this, android.R.color.holo_blue_bright));
        /* 设置边框 */
        new_tv.setBackgroundResource(R.drawable.person);
//        BitmapFactory.decodeFile()
//        new_tv.setBackground();
        /* 定义layout param */
        LinearLayoutCompat.LayoutParams lp = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        new_tv.setLayoutParams(lp);
        new_tv.getLayoutParams().width = 300;
        // add view
        root.addView(new_tv);
    }
}