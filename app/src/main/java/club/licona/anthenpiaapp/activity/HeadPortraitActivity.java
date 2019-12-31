package club.licona.anthenpiaapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tencent.mmkv.MMKV;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import club.licona.anthenpiaapp.R;
import club.licona.anthenpiaapp.entity.vo.HeadPortraitSaveVO;
import club.licona.anthenpiaapp.presenter.HeadPortraitPresenter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.theartofdev.edmodo.cropper.CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE;

/**
 * @author licona
 */
public class HeadPortraitActivity extends Activity {
    private HeadPortraitPresenter headPortraitPresenter;

    private static final int ALBUM_REQUEST_CODE = 1;
    private static final int CAMERA_REQUEST_CODE = 2;
    private static final int CROP_REQUEST_CODE = 3;
    /**
     * 调用相机返回的图片文件
     */
    private File tempFile;
    /**
     * 最终图片
     */
    private Bitmap bitmap;

    private Unbinder unbinder;

    @BindView(R.id.iv_header)
    ImageView ivHeader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header);

        this.unbinder = ButterKnife.bind(this);
        this.headPortraitPresenter = new HeadPortraitPresenter();

        init();
    }

    private void init() {
        ivHeader.setBackground(getDrawable(R.drawable.bg_default));
    }

    @OnClick(R.id.btn_select_image)
    void selectImage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HeadPortraitActivity.this);
        builder.setTitle("添加图片");
        String[] items = {"选择本地图片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, (dialog, which) -> {
            switch (which) {
                case 0:
                    Intent openAlbumIntent = new Intent(Intent.ACTION_PICK);
                    openAlbumIntent.setType("image/*");
                    startActivityForResult(openAlbumIntent, ALBUM_REQUEST_CODE);
                    break;
                case 1:
                    tempFile = new File(Environment.getExternalStorageDirectory().getPath(),
                            System.currentTimeMillis() + ".jpg");
                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    /**
                     * 判断版本
                     */
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        openCameraIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        Uri uri = FileProvider.getUriForFile(HeadPortraitActivity.this, getPackageName() + ".fileprovider",
                                tempFile);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        Log.e("getPicFromCamera", uri.toString());
                    } else {
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                    }
                    startActivityForResult(openCameraIntent, CAMERA_REQUEST_CODE);
                    break;
                default:
                    break;
            }
        });
        builder.show();
    }

    /**
     * @return Observable 上传用户头像
     */
    private Observable<HeadPortraitSaveVO> getUploadHeader() {
        MMKV mmkv = MMKV.defaultMMKV();
        String token = mmkv.decodeString("token");
        if(bitmap == null) {
            BitmapDrawable d = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_default);
            bitmap = d.getBitmap();
        }

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fileName = simpleDateFormat.format(date).replace(":", "-").replace(" ", "-")+ ".png";
        String path = getFilesDir() + File.separator + fileName;
        try{
            OutputStream os = new FileOutputStream(path);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            os.close();
        }catch(Exception e){
            Log.e("TAG", "", e);
        }

        File file = new File(path);

        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("header", file.getName(), requestBody);
        return Observable.just(new HeadPortraitSaveVO(token, body));
    }

    @OnClick(R.id.btn_change_header)
    void changeHeader() {
        headPortraitPresenter.saveHeadPortrait(getUploadHeader())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseVO -> {
                            if(responseVO.getCode() == 0) {
                                Toast.makeText(this, "上传成功", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(HeadPortraitActivity.this, MainActivity.class);
                                intent.putExtra("selectPage", "我的");
                                startActivity(intent);
                            } else {
                                Toast.makeText(this, "上传失败", Toast.LENGTH_LONG).show();
                            }
                        },
                        error -> {
                            Log.e("HeadPortraitActivity#changeHeader", error.getMessage(), error);
                        }
                );
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
                        Uri contentUri = FileProvider.getUriForFile(HeadPortraitActivity.this, getPackageName() + ".fileprovider", tempFile);
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
                    ivHeader.setImageBitmap(bitmap);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
