package com.qiufg.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.qiufg.Constants;
import com.qiufg.R;
import com.qiufg.activity.CaptureAct;
import com.qiufg.fragment.base.BasePageFragment;
import com.qiufg.util.Toast;
import com.qiufg.zxing.EncodingHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static com.qiufg.R.id.QrCode;

/**
 * QrScanFr
 * 1、 生成二维码：Bitmap mBitmap = EncodingHandler.createQRCode("www.baidu.com", 300);//300表示宽高
 * 2、 扫描二维码：Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
 * startActivityForResult(intent, REQUEST_CODE);
 * 3、 扫描结果回调，重写方法onActivityResult：
 * /@Override
 * protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * if (resultCode == RESULT_OK) { //RESULT_OK = -1
 * Bundle bundle = data.getExtras();
 * String scanResult = bundle.getString("result");
 * Toast.makeText(MainActivity.this, scanResult, Toast.LENGTH_LONG).show();
 * }
 * }
 * }
 * 4、 CameraManager getFramingRect()方法,定义了扫描的区域，可以自己修改。
 * 5、 ViewfinderView ZXing扫码窗口的绘制。
 * 6、 private void drawTextInfo(Canvas canvas, Rect frame) 修改文本绘制的位置
 * 7、 private void drawLaserScanner(Canvas canvas, Rect frame)
 * 修改扫描线的样式。注意若使用paint.setShader(Shader shader) 方法，一定要在绘制完成后调用paint.setShader(null)。以免绘制信息出错。
 * <p>
 * 8、 CameraConfigurationManager 修改横竖屏、处理变形效果的核心类。
 * 9、 DecodeHandler.decode ZXing解码的核心类
 * 10、CaptureActivityHandler 当DecodeHandler.decode完成解码后，系统会向CaptureActivityHandler发消息。如果编码成功则调用CaptureActivity.handleDecode方法对扫描到的结果进行分类处理。
 *
 * @author 丘凤光
 */
public class QrScanFr extends BasePageFragment {

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CODE = 0;
    @BindView(R.id.qrCodeText)
    TextView mQrCodeText;
    @BindView(R.id.text)
    EditText mText;
    @BindView(QrCode)
    ImageView mQrCode;

    public static QrScanFr newInstance() {
        return new QrScanFr();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.f_scan_qrcode, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @OnClick(R.id.openQrCodeScan)
    public void openQrCodeScan() {
        //打开二维码扫描界面
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                    CAMERA_REQUEST_CODE);

        } else {
            Intent intent = new Intent(getActivity(), CaptureAct.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    @OnClick(R.id.CreateQrCode)
    public void createQrCode() {
        mSubscription = Observable.just(mText.getText().toString().trim())
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        try {
                            return EncodingHandler.createQRCode(s, 500);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mObserver);
    }

    Observer<Bitmap> mObserver = new Observer<Bitmap>() {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            Toast.show(getContext(), "文本信息不能为空！");
        }

        @Override
        public void onNext(Bitmap bitmap) {
            if (bitmap != null) {
                mQrCode.setImageBitmap(bitmap);
                Toast.show(getContext(), "二维码生成成功！");
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == REQUEST_CODE && resultCode == Constants.RESULT_CODE_QR_SCAN) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            mQrCodeText.setText(scanResult);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE && permissions.length > 0 && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(getActivity(), CaptureAct.class);
                startActivityForResult(intent, REQUEST_CODE);

            } else {
                Toast.show(getContext(), "需要开启权限才能拨打电话!");
            }
        }
    }

    @Override
    public void fetchData() {
    }
}
