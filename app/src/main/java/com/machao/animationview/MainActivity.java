package com.machao.animationview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.machao.animationview.animation.CloudView;
import com.machao.animationview.animation.VerticalMenuView;

public class MainActivity extends AppCompatActivity {

    private CloudView cloudView;
    private VerticalMenuView menuView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cloudView = findViewById(R.id.cloudView);
        menuView = findViewById(R.id.menuView);
        cloudView.setIcon(R.mipmap.ic_avater_five, R.mipmap.ic_avater_four, R.mipmap.ic_avater_six, R.mipmap.ic_avater_one,
                R.mipmap.ic_avater_three, R.mipmap.ic_avater_two, R.mipmap.ic_avater_three, R.mipmap.ic_avater_two, R.mipmap.ic_avater_five, R.mipmap.ic_avater_four);
        menuView.setText("测试文本1", "测试文本2", "测试文本3", "测试文本4", "测试文本5", "测试文本6", "测试文本7");
        menuView.setOnItemClickListener(new VerticalMenuView.onItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
            }
        });
        cloudView.setOnItemClickListener(new CloudView.onItemClickListener() {
            @Override
            public void itemClick(int position) {
                Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startAnima(View view) {
        menuView.startAnimation();
        cloudView.startAnimation();
    }
}
