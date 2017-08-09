//package com.cainiao.factory;
//
//import android.app.ProgressDialog;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Matrix;
//import android.graphics.RectF;
//import android.net.Uri;
//import android.util.Log;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.cainiao.factory.model.im.CustomizeMessage;
//import com.cainiao.factory.utils.rongyun.FakeServer;
//import com.cainiao.factory.utils.rongyun.HttpUtil;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.List;
//
//import io.rong.imlib.IRongCallback;
//import io.rong.imlib.RongIMClient;
//import io.rong.imlib.location.RealTimeLocationConstant;
//import io.rong.imlib.model.Conversation;
//import io.rong.imlib.model.Message;
//import io.rong.imlib.model.MessageContent;
//import io.rong.message.FileMessage;
//import io.rong.message.ImageMessage;
//import io.rong.message.TextMessage;
//import io.rong.message.VoiceMessage;
//
//import static cn.bmob.v3.Bmob.getCacheDir;
//
///**
// * Created by wuyinlei on 2017/8/4.
// */
//
//public class AppTest {
//
//
//    private final static String TAG = "AppTest";
//
//    private TextView mInstructTV; //"使用指南"文本框
//    private EditText mSenderET; //"发送信息者ID"的编辑框
//    private EditText mReceiverET; //"接收信息者ID"的编辑框
//    private ProgressDialog mProgressDialog; //回调函数onProgress()中，显示进度值的对话框
//
//    private String[] functionOptSet; //标识"IMLib接口功能"的数组
//
//    private boolean mResultShown; //标识"结果"页面，是否处于展示状态
//
//    String token; //通过融云Server API接口，获取的token
//
//    private static String mSenderIdTest; //发送信息者ID
//    private static String mSenderNameTest = "Oliver"; //发送信息者的昵称
//    private static String mPortraitUriTest = "http://static.yingyonghui.com/screenshots/1657/1657011_5.jpg"; //获取发送信息者头像的url
//
//    private static String mReceiverIdTest; //接收信息者ID
//
//    /**
//     * 获取发送信息者ID
//     *
//     * @return 发送信息者ID
//     */
//    public String getSenderIdTest() {
//        if ("".equals(mSenderET.getText().toString().trim())) {
//            setSenderIdTest("12315");
//        } else {
//            setSenderIdTest(mSenderET.getText().toString().trim());
//        }
//        mSenderET.setText(mSenderIdTest);
//        return mSenderIdTest;
//    }
//
//    /**
//     * 获取接收信息者ID
//     *
//     * @return 接收信息者ID
//     */
//    public String getReceiverIdTest() {
//        if ("".equals(mReceiverET.getText().toString().trim())) {
//            setReceiverIdTest("12317");
//        } else {
//            setReceiverIdTest(mReceiverET.getText().toString().trim());
//        }
//        mReceiverET.setText(mReceiverIdTest);
//        return mReceiverIdTest;
//    }
//
//    /**
//     * 设置发送信息者ID
//     */
//    public static void setSenderIdTest(String str_SenderIdTest) {
//        AppTest.mSenderIdTest = str_SenderIdTest;
//    }
//
//    /**
//     * 设置接收信息者ID
//     */
//    public static void setReceiverIdTest(String str_ReceiverIdTest) {
//        AppTest.mReceiverIdTest = str_ReceiverIdTest;
//    }
//
//
//
//    /**
//     * 初始化：进度对话框
//     */
//    private void initProgressDialog() {
////        mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        mProgressDialog.setTitle("进度");
//        mProgressDialog.setProgress(100);
//        mProgressDialog.setIndeterminate(false);
//        mProgressDialog.setCancelable(true);
//    }
//
//
//    /**
//     * 监听RecyclerView中不同item的事件，进而调用IMLib的相关接口
//     */
//    private void start(String string) {
//
//
//        switch ("string") {
//            case "获取Token":
//                getToken();
//                break;
//
//            case "连接融云服务器":
//                connect(token);
//                break;
//
//            case "发送文本消息":
//                TextMessage textMessage = TextMessage.obtain("云中谁寄锦书来");
//                textMessage.setExtra("融云");
//                sendTextMessage(textMessage);
//                break;
//
//            case "发送图片消息":
//                ImageMessage imageMessage = acquireImage();
//                if (imageMessage != null)
//                    sendImageMessage(imageMessage);
//                else
//                    Log.d(TAG, "图片消息不存在");
//                break;
//
//            case "发送语音消息":
//                VoiceMessage voiceMessage = acquireVoice();
//                if (voiceMessage != null)
//                    sendVoiceMessage(voiceMessage);
//                break;
//
//            case "发送文件消息":
//                FileMessage fileMessage = createFile();
//                sendFileMessage(fileMessage);
//                break;
//
//            case "发送自定义消息":
//                CustomizeMessage customizeMessage = CustomizeMessage.obtain(System.currentTimeMillis(), "融云，国内首家专业的即时通讯云服务提供商");
//                sendCustomizeMessage(customizeMessage);
//                break;
//
//            case "插入消息(只在本地存储,不发送)":
//                insertMessage();
//                break;
//
//            case "获取本地未读消息数":
//                getUnreadMessageCount();
//                break;
//
//            case "读取本地存储的历史消息":
//                getLocalMessages();
//                break;
//
//            case "读取远程服务器的消息":
//                getRemoteMessages();
//                break;
//
//            case "获取位置共享信息":
//                getLocationMessage();
//                break;
//
//            case "获取会话(Conversation)信息":
//                getConversation();
//                break;
//
//            case "删除会话(Conversation)信息":
//                deleteConversation();
//                break;
//
//            case "清空消息":
//                clearMessages();
//                break;
//
//            case "断开连接, 继续接收Push消息":
//                RongIMClient.getInstance().disconnect();
//                break;
//
//            case "断开连接, 不再接收Push消息":
//                RongIMClient.getInstance().logout();
//                break;
//
//            default:
//                break;
//        }
//
//    }
//
//    /**
//     * 通过服务器端请求获取token，客户端不提供获取token的接口
//     */
//    private void getToken() {
//        FakeServer.getToken(getSenderIdTest(), mSenderNameTest, mPortraitUriTest, new HttpUtil.OnResponse() {
//            @Override
//            public void onResponse(int code, String body) {
//                if (code == 200) {
//                    JSONObject jsonObj = null;
//                    try {
//                        jsonObj = new JSONObject(body);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    token = jsonObj.optString("token");
//                    Log.i(TAG, "获取的 token 值为:\n" + token + '\n');
//                } else {
//                    Log.i(TAG, "获取 token 失败" + '\n');
//                }
//            }
//        });
//    }
//
//    /**
//     * 连接融云服务器
//     */
//    private void connect(String token) {
//        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
//
//            /**
//             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
//             */
//            @Override
//            public void onTokenIncorrect() {
//                Log.d(TAG, "Token 错误---onTokenIncorrect---" + '\n');
//            }
//
//            /**
//             * 连接融云成功
//             * @param userid 当前 token
//             */
//            @Override
//            public void onSuccess(String userid) {
//                Log.d(TAG, "连接融云成功---onSuccess---用户ID:" + userid + '\n');
//            }
//
//            /**
//             * 连接融云失败
//             * @param errorCode 错误码，可到官网 查看错误码对应的注释
//             */
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.d(TAG, "连接融云失败, 错误码: " + errorCode + '\n');
//            }
//        });
//    }
//
//    /**
//     * 发送文本消息
//     */
//    private void sendTextMessage(MessageContent messageContent) {
//        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                messageContent, null, null, new IRongCallback.ISendMessageCallback() {
//                    @Override
//                    public void onAttached(Message message) {
//                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
//                    }
//
//                    @Override
//                    public void onSuccess(Message message) {
//                        if (message.getContent() instanceof TextMessage) {
//                            Log.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
//                            Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
//                        }
//                    }
//
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 获取图片信息
//     */
//    private ImageMessage acquireImage() {
//        File imageFileSource;
//        imageFileSource = new File(getCacheDir(), "source.jpg");
//        File imageFileThumb = new File(getCacheDir(), "thumb.jpg");
//
//        InputStream inputStream = null;
//        FileOutputStream fosSource = null;
//        FileOutputStream fosThumb = null;
//        BufferedOutputStream bufSource = null;
//        BufferedOutputStream bufThumb = null;
//
//        try {
//            // 读取图片
//            inputStream = getAssets().open("opera.jpg");
////            getAssets().close();
//
//            Bitmap bmpSource = BitmapFactory.decodeStream(inputStream);
//            imageFileSource.createNewFile();
//            fosSource = new FileOutputStream(imageFileSource);
//            bufSource = new BufferedOutputStream(fosSource);
//            // 保存原始图片
//            bmpSource.compress(Bitmap.CompressFormat.JPEG, 100, bufSource);
//
//            // 生成缩略图
//            Matrix matrix = new Matrix();
//            matrix.setRectToRect(new RectF(0, 0, bmpSource.getWidth(), bmpSource.getHeight()),
//                    new RectF(0, 0, 240, 240), Matrix.ScaleToFit.CENTER);
//            Bitmap bmpThumb = Bitmap.createBitmap(bmpSource, 0, 0, bmpSource.getWidth(), bmpSource.getHeight(), matrix, true);
//            imageFileThumb.createNewFile();
//            fosThumb = new FileOutputStream(imageFileThumb);
//            bufThumb = new BufferedOutputStream(fosThumb);
//            // 保存缩略图
//            bmpThumb.compress(Bitmap.CompressFormat.JPEG, 60, bufThumb);
//
//            ImageMessage imageMessage = ImageMessage.obtain(Uri.fromFile(imageFileThumb), Uri.fromFile(imageFileSource));
//            return imageMessage;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        } finally {
//            if (inputStream != null)
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            if (fosSource != null)
//                try {
//                    fosSource.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            if (bufSource != null)
//                try {
//                    bufSource.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            if (fosThumb != null)
//                try {
//                    fosSource.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            if (bufThumb != null)
//                try {
//                    bufThumb.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//        }
//        return null;
//    }
//
//    /**
//     * 发送图片信息
//     */
//    private void sendImageMessage(ImageMessage imgMessage) {
//        RongIMClient.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                imgMessage, null, null, new RongIMClient.SendImageMessageCallback() {
//                    // 保存数据库成功
//                    @Override
//                    public void onAttached(Message message) {
//                        Log.d(TAG, "发送的图片消息已保存至本地数据库中");
//                    }
//
//                    // 发送失败
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//
//                    // 发送成功
//                    @Override
//                    public void onSuccess(Message message) {
//                        if (message.getContent() instanceof ImageMessage) {
//                            Log.d(TAG, "成功发送图片消息, Uri --> " + ((ImageMessage) message.getContent()).getThumUri() + '\n');
////                                Log.d(TAG, "成功发送图片消息: " + ((ImageMessage) message.getContent()).getLocalUri());
////                                Log.d(TAG, "成功发送图片消息: " + ((ImageMessage) message.getContent()).getRemoteUri() + '\n');
//                        }
//                    }
//
//                    // 发送进度
//                    @Override
//                    public void onProgress(Message message, int i) {
//                        if (!mProgressDialog.isShowing())
//                            mProgressDialog.show();
//                        mProgressDialog.setProgress(i);
//                    }
//                });
//    }
//
//    /**
//     * 获取语音消息
//     */
//    private VoiceMessage acquireVoice() {
////        File voiceFile = new File(getCacheDir(), "voice.amr");
////        InputStream inputStream = null;
////        OutputStream outputStream = null;
////        try {
////            // 读取音频文件
////            inputStream = getResources().openRawResource(R.raw.hella);
////            outputStream = new FileOutputStream(voiceFile);
////            byte[] buffer = new byte[1024];
////            int bytesRead;
////            // 写入缓存文件
////            while ((bytesRead = inputStream.read(buffer)) != -1)
////                outputStream.write(buffer, 0, bytesRead);
////
////            VoiceMessage voiceMessage = VoiceMessage.obtain(Uri.fromFile(voiceFile), 15);
////            return voiceMessage;
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////            return null;
////        } catch (IOException e) {
////            e.printStackTrace();
////            return null;
////        } finally {
////            if (inputStream != null)
////                try {
////                    inputStream.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////
////            if (outputStream != null) {
////                try {
////                    outputStream.flush();
////                    outputStream.close();
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
//        return null;
//    }
//
//    /**
//     * 发送语音消息
//     */
//    private void sendVoiceMessage(VoiceMessage voiceMessage) {
//        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                voiceMessage, null, null, new IRongCallback.ISendMessageCallback() {
//
//                    @Override
//                    public void onAttached(Message message) {
//                        Log.d(TAG, "发送的语音消息已保存至本地数据库中");
//                    }
//
//                    @Override
//                    public void onSuccess(Message message) {
//                        Log.d(TAG, "成功发送语音消息,Uri --> " + ((VoiceMessage) message.getContent()).getUri());
//                        Log.d(TAG, "成功发送语音消息,时长: " + ((VoiceMessage) message.getContent()).getDuration() + '\n');
//                    }
//
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 创建并获取外围设备上的一个文件
//     */
//    private FileMessage createFile() {
////        String state = Environment.getExternalStorageState();
////        if (state.equals(Environment.MEDIA_MOUNTED)) {
////            File SDPath = Environment.getExternalStorageDirectory();
////            File file = new File(SDPath, "RongDemoFile.txt");
////            String data = "www.rongcloud.cn/download";
////            FileOutputStream fileOutputStream;
////
////            try {
////                fileOutputStream = new FileOutputStream(file);
////                fileOutputStream.write(data.getBytes());
////                fileOutputStream.close();
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////
////            Uri filePath = Uri.parse("file://" + file.getPath());
////            return FileMessage.obtain(filePath);
////        } else
//        return null;
//    }
//
//    /**
//     * 发送文件消息
//     */
//    private void sendFileMessage(FileMessage fileMessage) {
//
//        if (fileMessage == null) {
//            Log.d(TAG, "待发送的文件消息不存在" + '\n');
//            return;
//        }
//
//        Message message = Message.obtain(getReceiverIdTest(), Conversation.ConversationType.PRIVATE, fileMessage);
//        RongIMClient.getInstance().sendMediaMessage(message, null, null, new IRongCallback.ISendMediaMessageCallback() {
//
//            @Override
//            public void onAttached(Message message) {
//                Log.d(TAG, "发送的文件消息已保存至本地数据库中");
//            }
//
//            @Override
//            public void onSuccess(Message message) {
//                Log.d(TAG, "成功发送文件消息, 本地 Uri --> " + ((FileMessage) message.getContent()).getLocalPath());
//                Log.d(TAG, "服务端 Uri --> " + ((FileMessage) message.getContent()).getFileUrl() + '\n');
//            }
//
//            @Override
//            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//            }
//
//            @Override
//            public void onProgress(Message message, int i) {
//                if (!mProgressDialog.isShowing())
//                    mProgressDialog.show();
//                mProgressDialog.setProgress(i);
//            }
//
//            @Override
//            public void onCanceled(Message message) {
//
//            }
//        });
//    }
//
//
//    /**
//     * 发送自定义消息
//     */
//    public void sendCustomizeMessage(CustomizeMessage customizeMessage) {
//        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                customizeMessage, null, null, new IRongCallback.ISendMessageCallback() {
//                    @Override
//                    public void onAttached(Message message) {
//                        Log.d(TAG, "发送的自定义消息已保存至本地数据库中");
//                    }
//
//                    @Override
//                    public void onSuccess(Message message) {
//                        if (message.getContent() instanceof CustomizeMessage) {
//                            Log.d(TAG, "成功发送自定义消息，它的时间戳: " + ((CustomizeMessage) message.getContent()).getSendTime());
//                            Log.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) message.getContent()).getContent() + '\n');
//                        }
//                    }
//
//                    @Override
//                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 插入消息(只在本地存储,不发送)
//     */
//    private void insertMessage() {
//        TextMessage textMessage = TextMessage.obtain("融云客服为您服务");
//        RongIMClient.getInstance().insertMessage(Conversation.ConversationType.PRIVATE, getReceiverIdTest(), getSenderIdTest(),
//                textMessage, new RongIMClient.ResultCallback<Message>() {
//                    @Override
//                    public void onSuccess(Message message) {
//                        if (message != null)
//                            Log.d(TAG, "成功插入消息: " + ((TextMessage) message.getContent()).getContent() + '\n');
//                        else
//                            Log.d(TAG, "待插入的消息不存在" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "插入消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 获取本地未读消息数
//     */
//    private void getUnreadMessageCount() {
//        RongIMClient.getInstance().getUnreadCount(Conversation.ConversationType.PRIVATE, getReceiverIdTest(), new RongIMClient.ResultCallback<Integer>() {
//            @Override
//            public void onSuccess(Integer integer) {
//                Log.d(TAG, "发送端(本端):" + getSenderIdTest() + " 未读消息数: " + integer + '\n');
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.d(TAG, "获取未读消息数失败，错误码: " + errorCode.getValue() + '\n');
//            }
//        });
//    }
//
//    /**
//     * 获取本地存储的历史消息
//     */
//    private void getLocalMessages() {
//        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.PRIVATE, getReceiverIdTest(), -1, 25,
//                new RongIMClient.ResultCallback<List<Message>>() {
//                    @Override
//                    public void onSuccess(List<Message> messages) {
//                        if (messages != null) {
//                            Log.d(TAG, "本地存储的历史消息个数为 " + messages.size());
//
//                            for (Message message : messages) {
//                                handleMessage(message.getContent());
//                            }
//                        } else
//                            Log.d(TAG, "本地存储的历史消息个数为 0" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "读取本地存储的历史消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 读取远程服务器的消息
//     */
//    private void getRemoteMessages() {
//        RongIMClient.getInstance().getRemoteHistoryMessages(Conversation.ConversationType.PRIVATE, getReceiverIdTest(), 0, 10, new RongIMClient.ResultCallback<List<Message>>() {
//            @Override
//            public void onSuccess(List<Message> messages) {
//                if (messages != null) {
//                    Log.d(TAG, "远端服务器存储的历史消息个数为 " + messages.size());
//
//                    for (Message message : messages) {
//                        handleMessage(message.getContent());
//                    }
//                } else
//                    Log.d(TAG, "远端服务器存储的历史消息个数为 0" + '\n');
//            }
//
//            @Override
//            public void onError(RongIMClient.ErrorCode errorCode) {
//                Log.d(TAG, "读取远端服务器存储的历史消息失败，错误码: " + errorCode.getValue() + '\n');
//            }
//        });
//    }
//
//    /**
//     * 获取位置共享信息
//     */
//    private void getLocationMessage() {
//        RealTimeLocationConstant.RealTimeLocationErrorCode mLocationStatus = RongIMClient.getInstance().startRealTimeLocation(Conversation.ConversationType.PRIVATE, getReceiverIdTest());
//        Log.d(TAG, "是否成功开启位置共享功能: " + mLocationStatus.getMessage() + '\n');
//
//        /**
//         * demo 代码  开发者根据 mLocationStatus 的值，做进一步的处理。
//         */
//    }
//
//    /**
//     * 获取会话(Conversation)信息
//     */
//    private void getConversation() {
//        RongIMClient.getInstance().getConversation(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                new RongIMClient.ResultCallback<Conversation>() {
//                    @Override
//                    public void onSuccess(Conversation conversation) {
//                        if (conversation != null) {
//                            Log.d(TAG, "获取的会话信息:");
//                            Log.d(TAG, "会话类型: " + conversation.getConversationType());
//                            Log.d(TAG, "发送者ID: " + conversation.getSenderUserId());
//                            Log.d(TAG, "接收者ID: " + conversation.getTargetId());
//                            Log.d(TAG, "最新一条消息的ID: " + conversation.getLatestMessageId());
//                            Log.d(TAG, "最新一条消息内容:");
//                            handleMessage(conversation.getLatestMessage());
//                            Log.d(TAG, "消息提醒状态: " + ((conversation.getNotificationStatus().getValue() == 1) ? "接收消息" : "消息免打扰"));
//                            Log.d(TAG, "未读消息数: " + conversation.getUnreadMessageCount() + '\n');
//                        } else
//                            Log.d(TAG, "会话不存在" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "获取会话信息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 删除会话(Conversation)信息
//     */
//    private void deleteConversation() {
//        RongIMClient.getInstance().removeConversation(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                new RongIMClient.ResultCallback<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean aBoolean) {
//                        if (aBoolean)
//                            Log.d(TAG, "删除会话信息成功" + '\n');
//                        else
//                            Log.d(TAG, "删除会话信息失败" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "删除会话信息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//    /**
//     * 清空消息
//     */
//    private void clearMessages() {
//        RongIMClient.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, getReceiverIdTest(),
//                new RongIMClient.ResultCallback<Boolean>() {
//                    @Override
//                    public void onSuccess(Boolean aBoolean) {
//                        if (aBoolean)
//                            Log.d(TAG, "执行清空消息成功" + '\n');
//                        else
//                            Log.d(TAG, "执行清空消息失败" + '\n');
//                    }
//
//                    @Override
//                    public void onError(RongIMClient.ErrorCode errorCode) {
//                        Log.d(TAG, "执行清空消息失败，错误码: " + errorCode.getValue() + '\n');
//                    }
//                });
//    }
//
//
//    /**
//     * 根据不同的messageContent, 在"结果"页面做出相应的显示
//     */
//    private void handleMessage(MessageContent messageContent) {
//        if (messageContent == null)
//            return;
//
//        if (messageContent instanceof TextMessage) {
//            Log.d(TAG, "文本消息: " + ((TextMessage) messageContent).getContent());
//        } else if (messageContent instanceof ImageMessage) {
//            Log.d(TAG, "图片消息, Uri --> " + ((ImageMessage) messageContent).getThumUri());
//        } else if (messageContent instanceof VoiceMessage) {
//            Log.d(TAG, "语音消息, Uri --> " + ((VoiceMessage) messageContent).getUri());
//        } else if (messageContent instanceof FileMessage) {
//            Log.d(TAG, "文件消息, Uri --> " + ((FileMessage) messageContent).getFileUrl());
//        } else if (messageContent instanceof CustomizeMessage) {
//            Log.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) messageContent).getContent());
//        }
//    }
//
//
//}
