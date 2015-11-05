package com.dcw.app.rating.ui.qrcode;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dcw.app.rating.R;
import com.dcw.app.rating.biz.MainActivity;
import com.dcw.app.rating.ui.framework.BaseFragmentWrapper;
import com.dcw.framework.view.annotation.InjectLayout;
import com.dcw.framework.view.annotation.InjectView;
import com.google.zxing.WriterException;

/**
 * <p>Title: ucweb</p>
 * <p/>
 * <p>Description: </p>
 * ......
 * <p>Copyright: Copyright (c) 2015</p>
 * <p/>
 * <p>Company: ucweb.com</p>
 *
 * @author JiaYing.Cheng
 * @version 1.0
 * @email adao12.vip@gmail.com
 * @create 15/11/5
 */
@InjectLayout(R.layout.fragment_qr_code)
public class QRCodeFragment extends BaseFragmentWrapper implements View.OnClickListener {

    @InjectView(value = R.id.btn_add_qrcode, listeners = View.OnClickListener.class)
    Button mBtnAddQrCode;
    @InjectView(R.id.et_qr_string)
    EditText mEtQrStr;
    @InjectView(R.id.iv_qr_image)
    ImageView mIvQrImage;

    @Override
    public Class getHostActivity() {
        return MainActivity.class;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initUI() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        try {
            String contentString = mEtQrStr.getText().toString();
            if (!contentString.equals("")) {
                //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
                Bitmap qrCodeBitmap = EncodingHandler.createQRCode(contentString, 550);
                mIvQrImage.setImageBitmap(qrCodeBitmap);
            }else {
                Toast.makeText(getActivity(), "Text can not be empty", Toast.LENGTH_SHORT).show();
            }

        } catch (WriterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
