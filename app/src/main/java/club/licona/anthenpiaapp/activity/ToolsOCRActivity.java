package club.licona.anthenpiaapp.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.jakewharton.rxbinding3.view.RxView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.GetBaiduAccessTokenVO;
import club.licona.anthenpiaapp.entity.vo.GetImageLiteralVO;
import club.licona.anthenpiaapp.presenter.GetBaiduAccessTokenPresenter;
import club.licona.anthenpiaapp.presenter.GetImageLiteralPresenter;
import club.licona.anthenpiaapp.util.BitmapToBase64;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

public class ToolsOCRActivity extends Activity {
    private GetBaiduAccessTokenPresenter getBaiduAccessTokenPresenter;
    private  String accessToken;
    private GetImageLiteralPresenter getImageLiteralPresenter;

    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;

    /**
     * 调用相机返回的图片文件
     */
    private File tempFile;
    /**
     * 最终图片
     */
    private Bitmap bitmap;

    private Unbinder unbinder;
    @BindView(R.id.rl_bar)
    RelativeLayout rlBar;
    @BindView(R.id.iv_ocr_picture)
    ImageView ivOCRPicture;
    @BindView(R.id.btn_ocr_take_photo)
    Button btnOCRTakePhoto;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.btn_copy)
    Button btnCopy;

    private TextView tvBack;
    private TextView tvTitle;

    private CompositeDisposable compositeDisposable;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools_ocr);

        this.unbinder = ButterKnife.bind(this);
        this.getBaiduAccessTokenPresenter = new GetBaiduAccessTokenPresenter();
        this.getImageLiteralPresenter = new GetImageLiteralPresenter();
        init();
    }

    private void init() {
        rlBar.setBackgroundColor(getColor(R.color.lightcoral));
        tvBack= rlBar.findViewById(R.id.tv_back);
        tvTitle = rlBar.findViewById(R.id.tv_title);
        tvTitle.setText("图像文字识别");
    }

    public void onResume() {
        super.onResume();
        compositeDisposable = new CompositeDisposable();
        subscribeRlBar();
        subscribeGetAccessToken();
    }

    private void subscribeRlBar() {
        Disposable disposable =  RxView.clicks(tvBack).subscribe(unit -> finish());
        compositeDisposable.add(disposable);
    }

    private void subscribeGetAccessToken() {
        /**
         * 获取BaiduAccessToken
         */
        Disposable disposable = getBaiduAccessTokenPresenter.getBaiduAccessToken(Observable.just(new GetBaiduAccessTokenVO("client_credentials",
                "Msu265UvzGzKGUDhSo6p33qf", "DsFZ3N5SHwhpmPrX6gM6LXo4hDLu9IDB")))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        baiduAccessTokenResponseVO -> accessToken = baiduAccessTokenResponseVO.getAccessToken(),
                        error -> Log.e("ToolsOCRActivity#init", error.getMessage(), error)
                );
        compositeDisposable.add(disposable);
    }

    @OnClick(R.id.btn_ocr_select_image)
    void selectImage() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, ALBUM_REQUEST_CODE);
    }

    @OnClick(R.id.btn_ocr_take_photo)
    void takePhoto() {
        tempFile = new File(Environment.getExternalStorageDirectory().getPath(),
                System.currentTimeMillis() + ".jpg");
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /**
         * 判断版本
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(ToolsOCRActivity.this, getPackageName() + ".fileprovider",
                    tempFile);
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            Log.e("getPicFromCamera", uri.toString());
        } else {
            openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }

        startActivityForResult(openCameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    cropPhoto(uri);
                }
                break;
            case CAMERA_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    /**
                     * 用相机返回的照片去调用剪裁也需要对Uri进行处理
                     */
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Uri contentUri = FileProvider.getUriForFile(ToolsOCRActivity.this, getPackageName() + ".fileprovider", tempFile);
                        cropPhoto(contentUri);
                    } else {
                        cropPhoto(Uri.fromFile(tempFile));
                    }
                }
                break;
            case CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(intent);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ivOCRPicture.setImageBitmap(bitmap);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Toast.makeText(this, result.getError().toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 裁剪图片
     */
    private void cropPhoto(Uri uri) {
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(this);
    }

    @OnClick(R.id.btn_begin_ocr)
    void beginOCR() {
        getImageLiteralPresenter.getImageLiteral(Observable.just(new GetImageLiteralVO(accessToken, BitmapToBase64.bitmapToBase64(bitmap))))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        imageLiteralResponseVO -> {
                            tvResult.setText("");
                            List list =  imageLiteralResponseVO.getWordsResult();
                            if(!list.isEmpty()) {
                                for (int i = 0; i < imageLiteralResponseVO.getWordsResultNum(); i++) {
                                    String[] strings;
                                    strings = list.get(i).toString().split("'");
                                    tvResult.append(strings[1] + "\n");
                                }
                            } else {
                                Toast.makeText(this, "对不起，没有检测到图片中的文字", Toast.LENGTH_SHORT).show();
                            }
                        },
                        error -> {
                            Log.e("ToolsOCRActivity#beginOCR", error.getMessage(), error);
                        }
                );
    }

    @OnClick(R.id.btn_copy)
    void copy() {
        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("OCRText", tvResult.getText().toString());
        cm.setPrimaryClip(clipData);
        Toast.makeText(this, "内容已复制到剪切板", Toast.LENGTH_SHORT).show();
    }

    public void onPause() {
        super.onPause();
        compositeDisposable.dispose();
    }

    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
