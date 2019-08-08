package com.qiufg.activity;

import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.qiufg.R;
import com.qiufg.hotfix.BugFixManager;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.qiufg.R.id.result;

public class HotfixAct extends AppCompatActivity {

    @BindView(result)
    TextView mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_hotfix);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.calculate)
    public void calculate() {
        mResult.setText(String.valueOf(divide()));
    }

    public int divide() {
        int args1 = 12;
        int args2 = 0;
        return args1 / args2;
    }


    @OnClick(R.id.fix)
    public void fix() {
        BugFixManager bugFixManager = new BugFixManager(this);
        File okDexFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        bugFixManager.fix(okDexFile);
    }
}
