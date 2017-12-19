package com.bwei.shidongliang20171219;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {
    @BindView(R.id.jiaru)
    Button jiaru;
    @BindView(R.id.goumain)
    Button goumain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.jiaru, R.id.goumain})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.jiaru:
                Toast.makeText(this, "加入成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.goumain:
                startActivity(new Intent(MainActivity.this,TwoActivity.class));
                break;
        }
    }
}
