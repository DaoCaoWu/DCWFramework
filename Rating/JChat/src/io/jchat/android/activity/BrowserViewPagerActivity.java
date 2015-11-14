package io.jchat.android.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.DownloadCompletionCallback;
import cn.jpush.im.android.api.callback.ProgressUpdateCallback;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import com.dcw.framework.jpush.R;
import io.jchat.android.application.JPushDemoApplication;
import io.jchat.android.tools.BitmapLoader;
import io.jchat.android.tools.HandleResponseCode;
import io.jchat.android.view.ImgBrowserViewPager;
import io.jchat.android.view.photoview.PhotoView;

//用于浏览图片
public class BrowserViewPagerActivity extends BaseActivity {

    private static String TAG = BrowserViewPagerActivity.class.getSimpleName();
    private PhotoView photoView;
    private ImgBrowserViewPager mViewPager;
    private ProgressDialog mProgressDialog;
    //存放所有图片的路径
    private List<String> mPathList = new ArrayList<String>();
    //存放图片消息的ID
    private List<Integer> mMsgIDList = new ArrayList<Integer>();
    private TextView mNumberTv;
    private Button mSendBtn;
    private CheckBox mOriginPictureCb;
    private TextView mTotalSizeTv;
    private CheckBox mPictureSelectedCb;
    private Button mLoadBtn;
    private int mPosition;
    private Conversation mConv;
    private Message mMsg;
    private String mTargetID;
    private boolean mFromChatActivity = true;
    private int mWidth;
    private int mHeight;
    //当前消息数
    private int mStart;
    private int mOffset = 18;
    private Context mContext;
    private boolean mDownloading = false;
    private Long mGroupID;
    private int[] mMsgIDs;
    private final MyHandler myHandler = new MyHandler(this);
    private final static int DOWNLOAD_ORIGIN_IMAGE_SUCCEED = 1;
    private final static int DOWNLOAD_PROGRESS = 2;
    private final static int DOWNLOAD_COMPLETED = 3;
    private final static int DOWNLOAD_ORIGIN_IMAGE_FAILED = 4;
    private final static int SEND_PICTURE = 5;
    private final static int DOWNLOAD_ORIGIN_PROGRESS = 6;
    private final static int DOWNLOAD_ORIGIN_COMPLETED = 7;

    /**
     * 用来存储图片的选中情况
     */
    private SparseBooleanArray mSelectMap = new SparseBooleanArray();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageButton returnBtn;
        RelativeLayout titleBarRl, checkBoxRl;

        mContext = this;
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mWidth = dm.widthPixels;
        mHeight = dm.heightPixels;
        Log.i(TAG, "width height :" + mWidth + mHeight);
        setContentView(R.layout.activity_image_browser);
        mViewPager = (ImgBrowserViewPager) findViewById(R.id.img_browser_viewpager);
        returnBtn = (ImageButton) findViewById(R.id.return_btn);
        mNumberTv = (TextView) findViewById(R.id.number_tv);
        mSendBtn = (Button) findViewById(R.id.pick_picture_send_btn);
        titleBarRl = (RelativeLayout) findViewById(R.id.title_bar_rl);
        checkBoxRl = (RelativeLayout) findViewById(R.id.check_box_rl);
        mOriginPictureCb = (CheckBox) findViewById(R.id.origin_picture_cb);
        mTotalSizeTv = (TextView) findViewById(R.id.total_size_tv);
        mPictureSelectedCb = (CheckBox) findViewById(R.id.picture_selected_cb);
        mLoadBtn = (Button) findViewById(R.id.load_image_btn);

        Intent intent = this.getIntent();
        mGroupID = intent.getLongExtra(JPushDemoApplication.GROUP_ID, 0);
        if (mGroupID != 0){
            mConv = JMessageClient.getGroupConversation(mGroupID);
        }else {
            mTargetID = intent.getStringExtra(JPushDemoApplication.TARGET_ID);
            mConv = JMessageClient.getSingleConversation(mTargetID);
        }
        mStart = intent.getIntExtra("msgCount", 0);
        mPosition = intent.getIntExtra(JPushDemoApplication.POSITION, 0);
        mFromChatActivity = intent.getBooleanExtra("fromChatActivity", true);
        boolean browserAvatar = intent.getBooleanExtra("browserAvatar", false);

        PagerAdapter pagerAdapter = new PagerAdapter() {

            @Override
            public int getCount() {
                return mPathList.size();
            }

            /**
             * 点击某张图片预览时，系统自动调用此方法加载这张图片左右视图（如果有的话）
             */
            @Override
            public View instantiateItem(ViewGroup container, int position) {
                photoView = new PhotoView(mFromChatActivity, container.getContext());
                photoView.setTag(position);
                String path = mPathList.get(position);
                Bitmap bitmap = BitmapLoader.getBitmapFromFile(path, mWidth, mHeight);
                if (bitmap != null)
                    photoView.setImageBitmap(bitmap);
                else photoView.setImageResource(R.drawable.friends_sends_pictures_no);
                container.addView(photoView, LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                return photoView;
            }

            @Override
            public int getItemPosition(Object object) {
                View view = (View) object;
                int currentPage = mViewPager.getCurrentItem();
                if (currentPage == (Integer) view.getTag()) {
                    return POSITION_NONE;
                } else {
                    return POSITION_UNCHANGED;
                }
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

        };
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        returnBtn.setOnClickListener(listener);
        mSendBtn.setOnClickListener(listener);
        mLoadBtn.setOnClickListener(listener);

        // 在聊天界面中点击图片
        if (mFromChatActivity) {
            titleBarRl.setVisibility(View.GONE);
            checkBoxRl.setVisibility(View.GONE);
            if(mViewPager != null && mViewPager.getAdapter() != null){
                mViewPager.getAdapter().notifyDataSetChanged();
            }
            //预览头像
            if (browserAvatar) {
                mPathList.add(intent.getStringExtra("avatarPath"));
                photoView = new PhotoView(mFromChatActivity, this);
                mLoadBtn.setVisibility(View.GONE);
                try {
                    photoView.setImageBitmap(BitmapLoader.getBitmapFromFile(mPathList.get(0), mWidth, mHeight));
                } catch (Exception e) {
                    photoView.setImageResource(R.drawable.friends_sends_pictures_no);
                }
            //预览聊天界面中的图片
            } else {
                initImgPathList();
                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Toast.makeText(this, this.getString(R.string.local_picture_not_found_toast), Toast.LENGTH_SHORT).show();
                }
                mMsg = mConv.getMessage(intent.getIntExtra("msgID", 0));
                photoView = new PhotoView(mFromChatActivity, this);
                int currentItem = mMsgIDList.indexOf(mMsg.getId());
                try {
                    ImageContent ic = (ImageContent) mMsg.getContent();
                    //如果点击的是第一张图片并且图片未下载过，则显示大图
                    if (ic.getLocalPath() == null && mMsgIDList.indexOf(mMsg.getId()) == 0) {
                        downloadImage();
                    }
                    //如果发送方上传了原图
                    if(ic.getBooleanExtra("originalPicture")){
                        mLoadBtn.setVisibility(View.GONE);
                        setLoadBtnText(ic);
                    }
                    photoView.setImageBitmap(BitmapLoader.getBitmapFromFile(mPathList.get(mMsgIDList
                            .indexOf(mMsg.getId())), mWidth, mHeight));
                    mViewPager.setCurrentItem(currentItem);
                } catch (NullPointerException e) {
                    photoView.setImageResource(R.drawable.friends_sends_pictures_no);
                    mViewPager.setCurrentItem(currentItem);
                }finally {
                    if (currentItem == 0){
                        getImgMsg();
                    }
                }
            }
            // 在选择图片时点击预览图片
        } else {
            mPathList = intent.getStringArrayListExtra("pathList");
            int[] pathArray = intent.getIntArrayExtra("pathArray");
            //初始化选中了多少张图片
            for (int i = 0; i < pathArray.length; i++) {
                if (pathArray[i] == 1) {
                    mSelectMap.put(i, true);
                }
            }
            showSelectedNum();
            mLoadBtn.setVisibility(View.GONE);
            mViewPager.setCurrentItem(mPosition);
            mNumberTv.setText(mPosition + 1 + "/" + mPathList.size());
            int currentItem = mViewPager.getCurrentItem();
            checkPictureSelected(currentItem);
            checkOriginPictureSelected();
            //第一张特殊处理
            mPictureSelectedCb.setChecked(mSelectMap.get(currentItem));
            showTotalSize();
        }
    }

    private void setLoadBtnText(ImageContent ic) {
        NumberFormat ddf1 = NumberFormat.getNumberInstance();
        //保留小数点后两位
        ddf1.setMaximumFractionDigits(2);
        double size = ic.getFileSize() / 1048576.0;
        String fileSize = "(" + ddf1.format(size) + "M" + ")";
        mLoadBtn.setText(mContext.getString(R.string.load_origin_image) + fileSize);
    }

    /**
     * 在图片预览中发送图片，点击选择CheckBox时，触发事件
     *
     * @param currentItem 当前图片索引
     */
    private void checkPictureSelected(final int currentItem) {
        mPictureSelectedCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (mSelectMap.size() + 1 <= 9) {
                    if (isChecked)
                        mSelectMap.put(currentItem, true);
                    else mSelectMap.delete(currentItem);
                } else if (isChecked) {
                    Toast.makeText(mContext, mContext.getString(R.string.picture_num_limit_toast), Toast.LENGTH_SHORT).show();
                    mPictureSelectedCb.setChecked(mSelectMap.get(currentItem));
                } else {
                    mSelectMap.delete(currentItem);
                }

                showSelectedNum();
                showTotalSize();
            }
        });

    }

    /**
     * 点击发送原图CheckBox，触发事件
     *
     */
    private void checkOriginPictureSelected() {
        mOriginPictureCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    if (mSelectMap.size() < 1)
                        mPictureSelectedCb.setChecked(true);
                }
            }
        });
    }

    //显示选中的图片总的大小
    private void showTotalSize() {
        if (mSelectMap.size() > 0) {
            List<String> pathList = new ArrayList<String>();
            for (int i=0; i < mSelectMap.size(); i++) {
                pathList.add(mPathList.get(mSelectMap.keyAt(i)));
            }
            String totalSize = BitmapLoader.getPictureSize(pathList);
            mTotalSizeTv.setText(mContext.getString(R.string.origin_picture) + "(" + totalSize + ")");
        } else mTotalSizeTv.setText(mContext.getString(R.string.origin_picture));
    }

    //显示选中了多少张图片
    private void showSelectedNum() {
        if (mSelectMap.size() > 0) {
            mSendBtn.setText(mContext.getString(R.string.send) + "(" + mSelectMap.size() + "/" + "9)");
        } else mSendBtn.setText(mContext.getString(R.string.send));
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        //在滑动的时候更新CheckBox的状态
        @Override
        public void onPageScrolled(final int i, float v, int i2) {
            checkPictureSelected(i);
            checkOriginPictureSelected();
            mPictureSelectedCb.setChecked(mSelectMap.get(i));
        }

        @Override
        public void onPageSelected(final int i) {
            Log.d(TAG, "onPageSelected current position: " + i);
            if (mFromChatActivity) {
                mMsg = mConv.getMessage(mMsgIDList.get(i));
                Log.d(TAG, "onPageSelected Image Message ID: " + mMsg.getId());
                ImageContent ic = (ImageContent) mMsg.getContent();
                //每次选择或滑动图片，如果不存在本地图片则下载，显示大图
                if (ic.getLocalPath() == null) {
//                    mLoadBtn.setVisibility(View.VISIBLE);
                    downloadImage();
                } else if(ic.getBooleanExtra("hasDownloaded") != null && !ic.getBooleanExtra("hasDownloaded")){
                    setLoadBtnText(ic);
                    mLoadBtn.setVisibility(View.GONE);
                }else {
                    mLoadBtn.setVisibility(View.GONE);
                }
                if (i == 0){
                    getImgMsg();
                }
            } else {
                mNumberTv.setText(i + 1 + "/" + mPathList.size());
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    private void getImgMsg() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ImageContent ic;
                final int msgSize = mMsgIDList.size();
                List<Message> msgList = mConv.getMessagesFromNewest(mStart, mOffset);
                mOffset = msgList.size();
                if (mOffset > 0){
                    for (Message msg : msgList){
                        if (msg.getContentType().equals(ContentType.image)){
                            mMsgIDList.add(0, msg.getId());
                            ic = (ImageContent) msg.getContent();
                            if (msg.getDirect().equals(MessageDirect.send)){
                                if (TextUtils.isEmpty(ic.getStringExtra("localPath"))){
                                    if (!TextUtils.isEmpty(ic.getLocalPath())){
                                        mPathList.add(0, ic.getLocalPath());
                                    }else {
                                        mPathList.add(0, ic.getLocalThumbnailPath());
                                    }
                                }else {
                                    mPathList.add(0, ic.getStringExtra("localPath"));
                                }
                            }else if (ic.getLocalPath() != null) {
                                mPathList.add(0, ic.getLocalPath());
                            } else mPathList.add(0, ic.getLocalThumbnailPath());
                        }
                    }
                    mStart += mOffset;
                    if (msgSize == mMsgIDList.size()){
                        getImgMsg();
                    }else {
                        BrowserViewPagerActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPosition = mMsgIDList.size() - msgSize;
                                mViewPager.setCurrentItem(mPosition);
                                mViewPager.getAdapter().notifyDataSetChanged();
                            }
                        });
                    }
                }
            }
        });
        thread.start();
    }

    /**
     * 初始化会话中的所有图片路径
     */
    private void initImgPathList() {
        mMsgIDList = this.getIntent().getIntegerArrayListExtra(JPushDemoApplication.MsgIDs);
        Message msg;
        ImageContent ic;
        for (int msgID : mMsgIDList){
            msg = mConv.getMessage(msgID);
            if (msg.getContentType().equals(ContentType.image)) {
                ic = (ImageContent) msg.getContent();
                if (msg.getDirect().equals(MessageDirect.send)){
                    if (TextUtils.isEmpty(ic.getStringExtra("localPath"))){
                        if (!TextUtils.isEmpty(ic.getLocalPath())){
                            mPathList.add(ic.getLocalPath());
                        }else {
                            mPathList.add(ic.getLocalThumbnailPath());
                        }
                    }else {
                        mPathList.add(ic.getStringExtra("localPath"));
                    }
                }else if (ic.getLocalPath() != null) {
                    mPathList.add(ic.getLocalPath());
                } else mPathList.add(ic.getLocalThumbnailPath());
            }
        }
//        List<Message> msgList = mConv.getAllMessage();
//        Message msg;
//        ImageContent ic;
//        for (int i = 0; i < msgList.size(); i++) {
//            msg = msgList.get(i);
//            if (msg.getContentType().equals(ContentType.image)) {
//                ic = (ImageContent) msg.getContent();
//                if (msg.getDirect().equals(MessageDirect.send)){
//                    if (TextUtils.isEmpty(ic.getStringExtra("localPath"))){
//                        if (!TextUtils.isEmpty(ic.getLocalPath())){
//                            mPathList.add(ic.getLocalPath());
//                        }else {
//                            mPathList.add(ic.getLocalThumbnailPath());
//                        }
//                    }else {
//                        mPathList.add(ic.getStringExtra("localPath"));
//                    }
//                }else if (ic.getLocalPath() != null) {
//                    mPathList.add(ic.getLocalPath());
//                } else mPathList.add(ic.getLocalThumbnailPath());
//                mMsgIDList.add(msg.getId());
//            }
//        }
//        Log.d(TAG, "Image Message List: " + mPathList.toString());
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.return_btn:
                    int pathArray[] = new int[mPathList.size()];
                    for (int i = 0; i < pathArray.length; i++)
                        pathArray[i] = 0;
                    for (int j = 0; j < mSelectMap.size(); j++) {
                        pathArray[mSelectMap.keyAt(j)] = 1;
                    }
                    Intent intent = new Intent();
                    intent.putExtra("pathArray", pathArray);
                    setResult(JPushDemoApplication.RESULT_CODE_SELECT_PICTURE, intent);
                    finish();
                    break;
                case R.id.pick_picture_send_btn:
                    mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.setMessage(mContext.getString(R.string.sending_hint));
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.show();
                    mPosition = mViewPager.getCurrentItem();
//                    pathList.add(mPathList.get(mPosition));

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (mOriginPictureCb.isChecked()) {
                                Log.i(TAG, "发送原图");
                                getOriginPictures(mPosition);
                            } else {
                                Log.i(TAG, "发送缩略图");
                                getThumbnailPictures(mPosition);
                            }
                            myHandler.sendEmptyMessageDelayed(SEND_PICTURE, 1000);
                        }
                    });
                    thread.start();
                    break;
                //点击显示原图按钮，下载原图
                case R.id.load_image_btn:
                    downloadOriginalPicture();
                    break;
            }
        }
    };

    private void downloadOriginalPicture() {
        final ImageContent imgContent = (ImageContent) mMsg.getContent();
        //如果不存在下载进度
        if (!mMsg.isContentDownloadProgressCallbackExists()) {
            mMsg.setOnContentDownloadProgressCallback(new ProgressUpdateCallback() {
                @Override
                public void onProgressUpdate(double progress) {
                    android.os.Message msg = myHandler.obtainMessage();
                    Bundle bundle = new Bundle();
                    if (progress < 1.0) {
                        msg.what = DOWNLOAD_ORIGIN_PROGRESS;
                        bundle.putInt("progress", (int) (progress * 100));
                        msg.setData(bundle);
                        msg.sendToTarget();
                    } else {
                        msg.what = DOWNLOAD_ORIGIN_COMPLETED;
                        msg.sendToTarget();
                    }
                }
            });
            imgContent.downloadOriginImage(mMsg, new DownloadCompletionCallback() {
                @Override
                public void onComplete(int status, String desc, File file) {
                    if(status == 0){
                        imgContent.setBooleanExtra("hasDownloaded", true);
                    }else{
                        imgContent.setBooleanExtra("hasDownloaded", false);
                        android.os.Message msg = myHandler.obtainMessage();
                        msg.what = DOWNLOAD_ORIGIN_IMAGE_FAILED;
                        Bundle bundle = new Bundle();
                        bundle.putInt(JPushDemoApplication.STATUS, status);
                        msg.setData(bundle);
                        msg.sendToTarget();
                    }
                }
            });
        }
    }


    /**
     * 获得选中图片的原图路径
     *
     * @param position 选中的图片位置
     */
    private void getOriginPictures(int position) {
        Bitmap bitmap;
        if (mSelectMap.size() < 1)
            mSelectMap.put(position, true);
        mMsgIDs = new int[mSelectMap.size()];
        for (int i = 0; i < mSelectMap.size(); i++) {
            final int index = i;
            //验证图片大小，若小于720 * 1280则直接发送原图，否则压缩
            if (BitmapLoader.verifyPictureSize(mPathList.get(mSelectMap.keyAt(i)))){
                File file = new File(mPathList.get(mSelectMap.keyAt(i)));
                ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int status, String desc, ImageContent imageContent) {
                        if (status == 0) {
                            imageContent.setBooleanExtra("isOriginalPicture", true);
                            Message msg = mConv.createSendMessage(imageContent);
                            mMsgIDs[index] = msg.getId();
                        } else {
                            Log.d("PickPictureActivity", "create image content failed! status:" + status);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            } else {
                bitmap = BitmapLoader.getBitmapFromFile(mPathList.get(mSelectMap.keyAt(i)), 720, 1280);
                final String tempPath = BitmapLoader.saveBitmapToLocal(bitmap);
                File file = new File(tempPath);
                ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int status, String desc, ImageContent imageContent) {
                        if (status == 0) {
                            imageContent.setStringExtra("tempPath", tempPath);
                            imageContent.setStringExtra("localPath", mPathList.get(mSelectMap.keyAt(index)));
                            imageContent.setBooleanExtra("originalPicture", true);
                            Message msg = mConv.createSendMessage(imageContent);
                            mMsgIDs[index] = msg.getId();
                        } else {
                            Log.d("PickPictureActivity", "create image content failed! status:" + status);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            }
        }
    }

    /**
     * 获得选中图片的缩略图路径
     *
     * @param position 选中的图片位置
     */
    private void getThumbnailPictures(int position) {
        Bitmap bitmap;
        if (mSelectMap.size() < 1)
            mSelectMap.put(position, true);
        mMsgIDs = new int[mSelectMap.size()];
        for (int i = 0; i < mSelectMap.size(); i++) {
            final int index = i;
            //验证图片大小，若小于720 * 1280则直接发送原图，否则压缩
            if (BitmapLoader.verifyPictureSize(mPathList.get(mSelectMap.keyAt(i)))){
                File file = new File(mPathList.get(mSelectMap.keyAt(i)));
                ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int status, String desc, ImageContent imageContent) {
                        if (status == 0) {
                            Message msg = mConv.createSendMessage(imageContent);
                            mMsgIDs[index] = msg.getId();
                        } else {
                            Log.d("PickPictureActivity", "create image content failed! status:" + status);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            } else {
                bitmap = BitmapLoader.getBitmapFromFile(mPathList.get(mSelectMap.keyAt(i)), 720, 1280);
                final String tempPath = BitmapLoader.saveBitmapToLocal(bitmap);
                File file = new File(tempPath);
                ImageContent.createImageContentAsync(file, new ImageContent.CreateImageContentCallback() {
                    @Override
                    public void gotResult(int status, String desc, ImageContent imageContent) {
                        if (status == 0) {
                            imageContent.setStringExtra("tempPath", tempPath);
                            imageContent.setStringExtra("localPath", mPathList.get(mSelectMap.keyAt(index)));
                            Message msg = mConv.createSendMessage(imageContent);
                            mMsgIDs[index] = msg.getId();
                        } else {
                            Log.d("PickPictureActivity", "create image content failed! status:" + status);
                            HandleResponseCode.onHandle(mContext, status, false);
                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (mDownloading) {
            mProgressDialog.dismiss();
            //TODO cancel download image
        }
        int pathArray[] = new int[mPathList.size()];
        for (int i = 0; i < pathArray.length; i++)
            pathArray[i] = 0;
        for (int i = 0; i < mSelectMap.size(); i++) {
            pathArray[mSelectMap.keyAt(i)] = 1;
        }
        Intent intent = new Intent();
        intent.putExtra("pathArray", pathArray);
        setResult(JPushDemoApplication.RESULT_CODE_SELECT_PICTURE, intent);
        super.onBackPressed();
    }

    //每次在聊天界面点击图片或者滑动图片自动下载大图
    private void downloadImage() {
        ImageContent imgContent = (ImageContent) mMsg.getContent();
        if(imgContent.getLocalPath() == null){
            //如果不存在进度条Callback，重新注册
            if (!mMsg.isContentDownloadProgressCallbackExists()) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setMessage(mContext.getString(R.string.downloading_hint));
                mDownloading = true;
                mProgressDialog.show();
                // 显示下载进度条
                mMsg.setOnContentDownloadProgressCallback(new ProgressUpdateCallback() {

                    @Override
                    public void onProgressUpdate(double progress) {
                        android.os.Message msg = myHandler.obtainMessage();
                        Bundle bundle = new Bundle();
                        if (progress < 1.0) {
                            msg.what = DOWNLOAD_PROGRESS;
                            bundle.putInt("progress", (int) (progress * 100));
                            msg.setData(bundle);
                            msg.sendToTarget();
                        } else {
                            msg.what = DOWNLOAD_COMPLETED;
                            msg.sendToTarget();
                        }
                    }
                });
                // msg.setContent(imgContent);
                imgContent.downloadOriginImage(mMsg,
                        new DownloadCompletionCallback() {
                            @Override
                            public void onComplete(int status, String desc, File file) {
                                mDownloading = false;
                                if (status == 0) {
                                    android.os.Message msg = myHandler.obtainMessage();
                                    msg.what = DOWNLOAD_ORIGIN_IMAGE_SUCCEED;
                                    Bundle bundle = new Bundle();
                                    bundle.putString("path", file.getAbsolutePath());
                                    bundle.putInt(JPushDemoApplication.POSITION,
                                            mViewPager.getCurrentItem());
                                    msg.setData(bundle);
                                    msg.sendToTarget();
                                } else {
                                    android.os.Message msg = myHandler.obtainMessage();
                                    msg.what = DOWNLOAD_ORIGIN_IMAGE_FAILED;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(JPushDemoApplication.STATUS, status);
                                    msg.setData(bundle);
                                    msg.sendToTarget();
                                }
                            }
                        });
            }
        }
    }

    private static class MyHandler extends Handler{
        private final WeakReference<BrowserViewPagerActivity> mActivity;

        public MyHandler(BrowserViewPagerActivity activity){
            mActivity = new WeakReference<BrowserViewPagerActivity>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            BrowserViewPagerActivity activity = mActivity.get();
            if(activity != null){
                switch (msg.what) {
                    case DOWNLOAD_ORIGIN_IMAGE_SUCCEED:
                        //更新图片并显示
                        Bundle bundle = msg.getData();
                        activity.mPathList.set(bundle.getInt(JPushDemoApplication.POSITION), bundle.getString("path"));
                        activity.mViewPager.getAdapter().notifyDataSetChanged();
                        activity.mLoadBtn.setVisibility(View.GONE);
                        break;
                    case DOWNLOAD_PROGRESS:
                        activity.mProgressDialog.setProgress(msg.getData().getInt("progress"));
                        break;
                    case DOWNLOAD_COMPLETED:
                        activity.mProgressDialog.dismiss();
                        break;
                    case DOWNLOAD_ORIGIN_IMAGE_FAILED:
                        if(activity.mProgressDialog != null){
                            activity.mProgressDialog.dismiss();
                        }
                        HandleResponseCode.onHandle(activity, msg.getData().getInt(JPushDemoApplication.STATUS), false);
                        break;
                    case SEND_PICTURE:
                        Intent intent = new Intent();
                        intent.putExtra(JPushDemoApplication.TARGET_ID, activity.mTargetID);
                        intent.putExtra(JPushDemoApplication.GROUP_ID, activity.mGroupID);
                        intent.putExtra(JPushDemoApplication.MsgIDs, activity.mMsgIDs);
                        activity.setResult(JPushDemoApplication.RESULT_CODE_BROWSER_PICTURE, intent);
                        activity.finish();
                        break;
                    //显示下载原图进度
                    case DOWNLOAD_ORIGIN_PROGRESS:
                        activity.mLoadBtn.setText(msg.getData().getInt("progress") + "%");
                        break;
                    case DOWNLOAD_ORIGIN_COMPLETED:
                        activity.mLoadBtn.setText(activity.getString(R.string.download_completed_toast));
                        activity.mLoadBtn.setVisibility(View.GONE);
                        break;
                }
            }
        }
    }

}
