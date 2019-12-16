package com.lx.lxyd.mvp.list;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.lx.lxyd.R;
import com.lx.lxyd.bean.colMBean;
import com.lx.lxyd.bean.hisMBean;
import com.lx.lxyd.bean.infoData;

import cn.jzvd.JZMediaManager;
import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class PlayerActivity extends AppCompatActivity {
    private colMBean colBean = new colMBean();
    private hisMBean hisBean = new hisMBean();
    private infoData mcolBean = new infoData();
    JZVideoPlayerStandard jzVideoPlayerStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initData();
        Intent i = getIntent();
        jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        if (i.getStringExtra("flag").equals("1")) {
            colBean = i.getParcelableExtra("info");
            jzVideoPlayerStandard.setUp(colBean.getAudio_url(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    colBean.getTitle());
        } else if (i.getStringExtra("flag").equals("2")) {
            hisBean = i.getParcelableExtra("info");
            jzVideoPlayerStandard.setUp(hisBean.getAudio_url(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    hisBean.getTitle());
        } else {
            mcolBean = i.getParcelableExtra("info");
            jzVideoPlayerStandard.setUp(mcolBean.getAudio_url(),
                    JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                    mcolBean.getTitle());
        }
        jzVideoPlayerStandard.startVideo();
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    private void initData() {
        Window window = this.getWindow();
        ViewGroup mContentView = (ViewGroup) this.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
        }
        int statusBarHeight = 10;
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (mChildView != null && mChildView.getLayoutParams() != null && mChildView.getLayoutParams().height == statusBarHeight) {
            mContentView.removeView(mChildView);
            mChildView = mContentView.getChildAt(0);
        }
        if (mChildView != null) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mChildView.getLayoutParams();
            if (lp != null && lp.topMargin >= statusBarHeight) {
                lp.topMargin -= statusBarHeight;
                mChildView.setLayoutParams(lp);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {

            case KeyEvent.KEYCODE_ENTER:     //确定键enter
            case KeyEvent.KEYCODE_DPAD_CENTER:
                    jzVideoPlayerStandard.startVideo();
                break;

            case KeyEvent.KEYCODE_BACK:    //返回键
                JZVideoPlayer.releaseAllVideos();
                finish();
                return true;   //这里由于break会退出，所以我们自己要处理掉 不返回上一层

            case KeyEvent.KEYCODE_SETTINGS: //设置键

                break;

            case KeyEvent.KEYCODE_DPAD_DOWN:   //向下键

                /*    实际开发中有时候会触发两次，所以要判断一下按下时触发 ，松开按键时不触发
                 *    exp:KeyEvent.ACTION_UP
                 */
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                }

                break;

            case KeyEvent.KEYCODE_DPAD_UP:   //向上键

                break;

            case KeyEvent.KEYCODE_0:   //数字键0

                break;

            case KeyEvent.KEYCODE_DPAD_LEFT: //向左键
                //跳转制定位置播放
                JZMediaManager.seekTo(jzVideoPlayerStandard.getCurrentPositionWhenPlaying()+5000);

                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:  //向右键
                JZMediaManager.seekTo(jzVideoPlayerStandard.getCurrentPositionWhenPlaying()-5000);
                break;

            case KeyEvent.KEYCODE_INFO:    //info键

                break;

            case KeyEvent.KEYCODE_PAGE_DOWN:     //向上翻页键
            case KeyEvent.KEYCODE_MEDIA_NEXT:

                break;


            case KeyEvent.KEYCODE_PAGE_UP:     //向下翻页键
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:

                break;

            case KeyEvent.KEYCODE_VOLUME_UP:   //调大声音键

                break;

            case KeyEvent.KEYCODE_VOLUME_DOWN: //降低声音键

                break;
            case KeyEvent.KEYCODE_VOLUME_MUTE: //禁用声音
                break;

            default:
                break;
        }

        return super.onKeyDown(keyCode, event);

    }
    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
