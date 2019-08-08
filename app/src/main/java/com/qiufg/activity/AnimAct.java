package com.qiufg.activity;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.mcxtzhang.pathanimlib.PathAnimView;
import com.mcxtzhang.pathanimlib.res.StoreHousePath;
import com.mcxtzhang.pathanimlib.utils.PathParserUtils;
import com.mcxtzhang.pathanimlib.utils.SvgPathParser;
import com.qiufg.R;
import com.qiufg.view.RoundImageView;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description @linke {https://github.com/mcxtzhang/PathAnimView}
 * Author Qiufg
 * Date 2017/3/16
 */
public class AnimAct extends AppCompatActivity {

    @BindView(R.id.font)
    PathAnimView mFont;
    @BindView(R.id.girl)
    PathAnimView mGirl;
    @BindView(R.id.round)
    RoundImageView mRound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_anim);
        ButterKnife.bind(this);

        initAnim();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFont.startAnim();
        mGirl.startAnim();
        mRound.startAnimation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFont.stopAnim();
        mGirl.clearAnim();
        mGirl.clearAnim();
        mRound.stopAnimation();
    }

    private void initAnim() {
        mFont.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("you are welcome", 1.0f, 10)))//不能有汉字，不能有标点符号
                .setColorFg(Color.RED)
                .setColorBg(Color.GREEN)
                .setAnimTime(5000)
                .setAnimInfinite(true);

        //动态设置 从StringArray里取
//        storeView2.setSourcePath(PathParserUtils.getPathFromStringArray(this, R.array.storehouse, 2));

        String qianbihua = getString(R.string.qianbihua);
        SvgPathParser svgPathParser = new SvgPathParser();
        try {
            Path path = svgPathParser.parsePath(qianbihua);
            mGirl.setSourcePath(path)
                    .setColorFg(Color.RED)
                    .setColorBg(Color.TRANSPARENT);
            mGirl.setScaleX(1.2f);
            mGirl.setScaleY(1.2f);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mGirl.getPathAnimHelper().setAnimTime(5000);

        mRound.setDrawPoint(true);
    }
}
