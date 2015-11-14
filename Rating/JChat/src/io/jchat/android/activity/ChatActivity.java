package io.jchat.android.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.ImageContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import com.dcw.framework.jpush.R;
import io.jchat.android.application.JPushDemoApplication;
import io.jchat.android.controller.ChatController;
import io.jchat.android.controller.RecordVoiceBtnController;
import io.jchat.android.tools.BitmapLoader;
import io.jchat.android.view.ChatView;

/*
 * 对话界面
 */
public class ChatActivity extends BaseActivity {

    private static final String TAG = "ChatActivity";

    private ChatView mChatView;
    private ChatController mChatController;
    private MyReceiver mReceiver;
    private String mTargetID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        JMessageClient.registerEventReceiver(this);
        setContentView(R.layout.activity_chat);
        mChatView = (ChatView) findViewById(R.id.chat_view);
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        mChatView.initModule(dm.density, dm.densityDpi);
        mChatController = new ChatController(mChatView, this);
        mChatView.setListeners(mChatController);
        mChatView.setOnTouchListener(mChatController);
        mChatView.setOnSizeChangedListener(mChatController);
        mChatView.setOnKbdStateListener(mChatController);
        initReceiver();

    }

    // 监听耳机插入
    private void initReceiver() {
        mReceiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, filter);
    }

    private class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent data) {
            if (data != null) {
                mTargetID = data.getStringExtra(JPushDemoApplication.TARGET_ID);
                //插入了耳机
                if (data.getAction().equals(Intent.ACTION_HEADSET_PLUG)) {
                    mChatController.getAdapter().setAudioPlayByEarPhone(data.getIntExtra("state", 0));
                }
            }
        }

    }

    /*
    重写BaseActivity的handleMsg()方法，实现刷新消息
     */
    @Override
    public void handleMsg(android.os.Message msg) {
        switch (msg.what) {
            case JPushDemoApplication.REFRESH_GROUP_NAME:
                if (mChatController.getConversation() != null) {
                    int num = msg.getData().getInt("membersCount");
                    String groupName = msg.getData().getString(JPushDemoApplication.GROUP_NAME);
                    mChatView.setChatTitle(groupName, num);
                }
                break;
            case JPushDemoApplication.REFRESH_GROUP_NUM:
                int num = msg.getData().getInt("membersCount");
                mChatView.setChatTitle(ChatActivity.this.getString(R.string.group), num);
                break;
        }
    }

    /**
     * 处理发送图片，刷新界面
     *
     * @param data intent
     */
    private void handleImgRefresh(Intent data) {
        mTargetID = data.getStringExtra(JPushDemoApplication.TARGET_ID);
        long groupID = data.getLongExtra(JPushDemoApplication.GROUP_ID, 0);
        Log.i(TAG, "Refresh Image groupID: " + groupID);
        //判断是否在当前会话中发图片
        if (mTargetID != null) {
            if (mTargetID.equals(mChatController.getTargetID())) {
                mChatController.getAdapter().setSendImg(mTargetID, data.getIntArrayExtra(JPushDemoApplication.MsgIDs));
                mChatView.setToBottom();
            }
        } else if (groupID != 0) {
            if (groupID == mChatController.getGroupID()) {
                mChatController.getAdapter().setSendImg(groupID, data.getIntArrayExtra(JPushDemoApplication.MsgIDs));
                mChatView.setToBottom();
            }
        }


    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    Log.i(TAG, "BACK pressed");
                    if (RecordVoiceBtnController.mIsPressed) {
                        mChatView.dismissRecordDialog();
                        mChatView.releaseRecorder();
                        RecordVoiceBtnController.mIsPressed = false;
                    }
                    if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
                        mChatView.dismissMoreMenu();
                        return false;
                    }else {
                        mChatController.resetUnreadMsg();
                    }

                    break;
                case KeyEvent.KEYCODE_MENU:
                    // 处理自己的逻辑
                    break;
                case KeyEvent.KEYCODE_ESCAPE:
                    Log.i(TAG, "KeyCode: escape");
                    break;
                default:
                    break;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    /**
     * 释放资源
     */
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
        unregisterReceiver(mReceiver);
        mChatController.releaseMediaPlayer();
        mChatView.releaseRecorder();
    }

    @Override
    protected void onPause() {
        RecordVoiceBtnController.mIsPressed = false;
        JMessageClient.exitConversaion();
        Log.i(TAG, "[Life cycle] - onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        mChatController.getAdapter().stopMediaPlayer();
        if (mChatView.getMoreMenu().getVisibility() == View.VISIBLE) {
            mChatView.dismissMoreMenu();
        }
        if (mChatController.getConversation() != null)
            mChatController.getConversation().resetUnreadCount();
        Log.i(TAG, "[Life cycle] - onStop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        if (!RecordVoiceBtnController.mIsPressed)
            mChatView.dismissRecordDialog();
        String targetID = getIntent().getStringExtra(JPushDemoApplication.TARGET_ID);
        boolean isGroup = getIntent().getBooleanExtra(JPushDemoApplication.IS_GROUP, false);
        if (isGroup) {
            try {
                long groupID = getIntent().getLongExtra(JPushDemoApplication.GROUP_ID, 0);
                if (groupID == 0) {
                    JMessageClient.enterGroupConversation(Long.parseLong(targetID));
                } else JMessageClient.enterGroupConversation(groupID);
            } catch (NumberFormatException nfe) {
                nfe.printStackTrace();
            }
        } else if (null != targetID) {
            JMessageClient.enterSingleConversaion(targetID);
        }
        mChatController.getAdapter().initMediaPlayer();
        Log.i(TAG, "[Life cycle] - onResume");
        super.onResume();
    }

    /**
     * 用于处理拍照发送图片返回结果
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_CANCELED) {
            return;
        }
        if (requestCode == JPushDemoApplication.REQUEST_CODE_TAKE_PHOTO) {
            Conversation conv = mChatController.getConversation();
            try {
                String originPath = mChatController.getPhotoPath();
                Bitmap bitmap = BitmapLoader.getBitmapFromFile(originPath, 720, 1280);
                String thumbnailPath = BitmapLoader.saveBitmapToLocal(bitmap);
                File file = new File(thumbnailPath);
                ImageContent content = new ImageContent(file);
                content.setStringExtra("tempPath", thumbnailPath);
                content.setStringExtra("localPath", originPath);
                Message msg = conv.createSendMessage(content);
                Intent intent = new Intent();
                intent.putExtra(JPushDemoApplication.MsgIDs, new int[]{msg.getId()});
                if (conv.getType() == ConversationType.group) {
                    intent.putExtra(JPushDemoApplication.GROUP_ID,
                            ((GroupInfo)conv.getTargetInfo()).getGroupID());
                } else {
                    intent.putExtra(JPushDemoApplication.TARGET_ID,
                            ((UserInfo)conv.getTargetInfo()).getUserName());
                }
                handleImgRefresh(intent);
            } catch (FileNotFoundException e) {
                Log.i(TAG, "create file failed!");
            } catch (NullPointerException e) {
                Log.i(TAG, "onActivityResult unexpected result");
            }
        } else if (resultCode == JPushDemoApplication.RESULT_CODE_SELECT_PICTURE) {
            handleImgRefresh(data);
        } else if (resultCode == JPushDemoApplication.RESULT_CODE_CHAT_DETAIL) {
            if (mChatController.isGroup()) {
                GroupInfo groupInfo = (GroupInfo)mChatController.getConversation().getTargetInfo();
                UserInfo userInfo = groupInfo.getGroupMemberInfo(JMessageClient.getMyInfo().getUserName());
                //如果自己在群聊中，同时显示群人数
                if (userInfo != null){
                    if (TextUtils.isEmpty(data.getStringExtra(JPushDemoApplication.NAME))) {
                        mChatView.setChatTitle(this.getString(R.string.group),
                                data.getIntExtra("currentCount", 0));
                    } else {
                        mChatView.setChatTitle(data.getStringExtra(JPushDemoApplication.NAME),
                                data.getIntExtra("currentCount", 0));
                    }
                }else {
                    if (TextUtils.isEmpty(data.getStringExtra(JPushDemoApplication.NAME))) {
                        mChatView.setChatTitle(this.getString(R.string.group));
                        mChatView.dismissGroupNum();
                    } else {
                        mChatView.setChatTitle(data.getStringExtra(JPushDemoApplication.NAME));
                        mChatView.dismissGroupNum();
                    }
                }

            }else mChatView.setChatTitle(data.getStringExtra(JPushDemoApplication.NAME));
            if (data.getBooleanExtra("deleteMsg", false)){
                mChatController.getAdapter().clearMsgList();
            }
        } else if (resultCode == JPushDemoApplication.RESULT_CODE_FRIEND_INFO) {
            if (!mChatController.isGroup()) {
                String nickname = data.getStringExtra(JPushDemoApplication.NICKNAME);
                if (nickname != null) {
                    mChatView.setChatTitle(nickname);
                }
            }
        }
    }

    public void startChatDetailActivity(boolean isGroup, String targetID, long groupID) {
        Intent intent = new Intent();
        intent.putExtra(JPushDemoApplication.IS_GROUP, isGroup);
        intent.putExtra(JPushDemoApplication.TARGET_ID, targetID);
        intent.putExtra(JPushDemoApplication.GROUP_ID, groupID);
        intent.setClass(this, ChatDetailActivity.class);
        startActivityForResult(intent, JPushDemoApplication.REQUEST_CODE_CHAT_DETAIL);
    }

    public void startPickPictureTotalActivity(Intent intent) {
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, this.getString(R.string.sdcard_not_exist_toast), Toast.LENGTH_SHORT).show();
        } else {
            intent.setClass(this, PickPictureTotalActivity.class);
            startActivityForResult(intent, JPushDemoApplication.REQUEST_CODE_SELECT_PICTURE);
        }
    }

    /**
     * 接收消息类事件
     *
     * @param event 消息事件
     */
    public void onEvent(MessageEvent event) {
        final Message msg = event.getMessage();
        //若为群聊相关事件，如添加、删除群成员
        Log.i(TAG, event.getMessage().toString());
        if (msg.getContentType() == ContentType.eventNotification) {
            long groupID = ((GroupInfo)msg.getTargetInfo()).getGroupID();
            UserInfo myInfo = JMessageClient.getMyInfo();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) msg
                    .getContent()).getEventNotificationType();
            if (type.equals(EventNotificationContent.EventNotificationType.group_member_removed)) {
                //删除群成员事件
                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                //群主删除了当前用户，则隐藏聊天详情按钮
                if (groupID == mChatController.getGroupID()) {
                    if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mChatView.dismissRightBtn();
                                GroupInfo groupInfo = (GroupInfo)mChatController.getConversation()
                                        .getTargetInfo();
                                if (TextUtils.isEmpty(groupInfo.getGroupName())){
                                    mChatView.setChatTitle(ChatActivity.this.getString(R.string.group));
                                }else mChatView.setChatTitle(groupInfo.getGroupName());
                                mChatView.dismissGroupNum();
                            }
                        });
                    }else {
                        refreshGroupNum();
                    }
                }
            } else {
                //添加群成员事件
                List<String> userNames = ((EventNotificationContent) msg.getContent()).getUserNames();
                //群主把当前用户添加到群聊，则显示聊天详情按钮
                if (groupID == mChatController.getGroupID()) {
                    refreshGroupNum();
                    if (userNames.contains(myInfo.getNickname()) || userNames.contains(myInfo.getUserName())) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mChatView.showRightBtn();
                            }
                        });
                    }
                }
            }
        }
        //刷新消息
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //收到消息的类型为单聊
                if (msg.getTargetType().equals(ConversationType.single)) {
                    String targetID = ((UserInfo)msg.getTargetInfo()).getUserName();
                    //判断消息是否在当前会话中
                    if (!mChatController.isGroup() && targetID.equals(mChatController.getTargetID())) {
                        mChatController.getAdapter().addMsgToList(msg);
                    }
                } else {
                    long groupID = ((GroupInfo)msg.getTargetInfo()).getGroupID();
                    if (mChatController.isGroup() && groupID == mChatController.getGroupID()) {
                        mChatController.getAdapter().addMsgToList(msg);
                    }
                }
            }
        });
    }

    private void refreshGroupNum() {
        Conversation conv = JMessageClient.getGroupConversation(mChatController.getGroupID());
        GroupInfo groupInfo = (GroupInfo)conv.getTargetInfo();
        if (!TextUtils.isEmpty(groupInfo.getGroupName())){
            android.os.Message handleMessage = mHandler.obtainMessage();
            handleMessage.what = JPushDemoApplication.REFRESH_GROUP_NAME;
            Bundle bundle = new Bundle();
            bundle.putString(JPushDemoApplication.GROUP_NAME, groupInfo.getGroupName());
            bundle.putInt("membersCount", groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        }else {
            android.os.Message handleMessage = mHandler.obtainMessage();
            handleMessage.what = JPushDemoApplication.REFRESH_GROUP_NUM;
            Bundle bundle = new Bundle();
            bundle.putInt("membersCount", groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        }
    }
}
