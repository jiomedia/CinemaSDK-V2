package com.jio.media.library.player.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.jio.media.library.player.MediaPlayerHelper;
import com.jio.media.library.player.MediaPlayerListener;
import com.jio.media.library.player.R;
import com.jio.media.library.player.databinding.ActivityPlayerBinding;
import com.jio.media.library.player.dialog.MediaAudioSubtitleDialog;
import com.jio.media.library.player.model.metaMore.MetaMore;
import com.jio.media.library.player.utils.Logger;
import com.jio.media.library.player.utils.MediaUtils;
import com.jio.media.library.player.viewmodel.MediaViewModel;

public class PlayerViewActivity extends AppCompatActivity implements MediaPlayerListener {
    private ActivityPlayerBinding binding;
    private MediaPlayerHelper mediaPlayerHelper;
    private String urlDownload, urlRedirect;
    private MetaMore metaMore;
    private BottomSheetBehavior sheetBehavior;

    private LinearLayout.LayoutParams videoParams;
    private LinearLayout.LayoutParams infoParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_player);
        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet);
        metaMore = new MetaMore();
        MediaViewModel mediaViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);

        videoParams = (LinearLayout.LayoutParams) binding.videoView.getLayoutParams();
        infoParams = (LinearLayout.LayoutParams) binding.infoContainer.getLayoutParams();
        videoParams.width = (int) (getScreenWidth(this) * 0.35);
        videoParams.height = (int) (getScreenHeight(this) * 0.096);

        infoParams.height = (int) (getScreenHeight(this) * 0.096);


        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getExtras() != null) {
                metaMore = (MetaMore) getIntent().getSerializableExtra("metaMore");
                String contentId = getIntent().getStringExtra("contentId");
                String uniqueId = getIntent().getStringExtra("uniqueId");
                urlDownload = getIntent().getStringExtra("urlDownload");
                urlRedirect = getIntent().getStringExtra("urlRedirect");
                if (metaMore != null) {
                    setPlayerView(savedInstanceState);
                    mediaViewModel.getMataMoreDetails(uniqueId, contentId);
                    binding.videoView.setUseController(false);
                    updateVideoName(false);
                }
                if (isAppInstalled(PlayerViewActivity.this, "com.jio.media.ondemand")) {
                    binding.btnJioCinemaDownload.setText("Watch Now");
                } else {
                    binding.btnJioCinemaDownload.setText("Download Now");
                }
                //MediaUtils.showImage(binding.bannerImage, videoInformation.getBannerImage());

            }
        }

        mediaViewModel.getMetaMoreLiveData().observe(this, metaMore -> {
            binding.videoTitle.setText(metaMore.getName());
            binding.videoSubTitle.setText(metaMore.getSubtitle());
            binding.videoDescription.setText(metaMore.getDescription());
            binding.videoMetadataTitle.setText(metaMore.getName());
            MediaUtils.showImage(binding.bannerImage, metaMore.getBanner());

        });

        binding.btnJioCinemaDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (urlDownload != null && urlRedirect != null) {
                        if (isAppInstalled(PlayerViewActivity.this, "com.jio.media.ondemand")) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlRedirect)));
                        } else {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlDownload)));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.btnMediaClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerViewActivity.this.finish();
            }
        });

        binding.bottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                int orientation = getResources().getConfiguration().orientation;
                if (orientation == Configuration.ORIENTATION_LANDSCAPE && newState == BottomSheetBehavior.STATE_DRAGGING) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    return;
                }

                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    binding.videoView.setUseController(true);
                    if (mediaPlayerHelper != null && mediaPlayerHelper.isVideoMuted()) {
                        mediaPlayerHelper.updateVolume();
                    }
                } else {
                    binding.videoView.setUseController(false);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Logger.d("slideOffset: " + slideOffset);
                if (slideOffset > 0.3) {
                    videoParams.width = getScreenWidth(PlayerViewActivity.this);
                    videoParams.height = (int) (getScreenHeight(PlayerViewActivity.this) * 0.35);
                    binding.videoView.setLayoutParams(videoParams);
                } else {
                    videoParams.width = (int) (getScreenWidth(PlayerViewActivity.this) * 0.35);
                    videoParams.height = (int) (getScreenHeight(PlayerViewActivity.this) * 0.096);
                    binding.videoView.setLayoutParams(videoParams);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mediaPlayerHelper != null && mediaPlayerHelper.isFullMode()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }

    public boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((PlayerViewActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((PlayerViewActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Logger.d("orientation: ORIENTATION_LANDSCAPE");
            orientationLandscape();
            updateVideoName(true);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            orientationPortrait();
            updateVideoName(false);
            Logger.d("orientation: ORIENTATION_PORTRAIT");
        }
    }

    private void updateVideoName(boolean isVisble) {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.updateVideoName(isVisble);
        }
    }

    private void setPlayerView(@Nullable Bundle savedInstanceState) {
        mediaPlayerHelper = new MediaPlayerHelper.Builder(this, binding.videoView)
                .setVideoUrls(metaMore.getUrl())
                .setRepeatModeOn(true)
                .setAutoPlayOn(true)
                .addSavedInstanceState(savedInstanceState)
                .setFullScreenBtnVisible()
                .setMuteBtnVisible()
                .setVideoName(metaMore.getName())
                .addMuteButton(true, true)
                .setUiControllersVisibility(true)
                .setExoPlayerEventsListener(this)
                .createAndPrepare();
    }

    private void orientationPortrait() {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.setFullMode(false);
            videoParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            videoParams.height = (int) (getScreenHeight(PlayerViewActivity.this) * 0.35);
            binding.videoView.setLayoutParams(videoParams);
        }
    }

    private void orientationLandscape() {
        if (mediaPlayerHelper != null) {
            mediaPlayerHelper.setFullMode(true);
            videoParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            videoParams.height = getScreenHeight(PlayerViewActivity.this);
            binding.videoView.setLayoutParams(videoParams);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        mediaPlayerHelper.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        mediaPlayerHelper.onActivityStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mediaPlayerHelper.onActivityResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayerHelper.onActivityPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayerHelper.onActivityStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayerHelper.onActivityDestroy();
    }

    @Override
    public void onLoadingStatusChanged(boolean isLoading, long bufferedPosition, int bufferedPercentage) {

    }

    @Override
    public void onPlayerPlaying(int currentWindowIndex) {

    }

    @Override
    public void onPlayerPaused(int currentWindowIndex) {

    }

    @Override
    public void onPlayerBuffering(int currentWindowIndex) {

    }

    @Override
    public void onPlayerStateEnded(int currentWindowIndex) {

    }

    @Override
    public void onPlayerStateIdle(int currentWindowIndex) {

    }

    @Override
    public void onPlayerError(int code, String errorString) {

    }

    @Override
    public void createExoPlayerCalled(boolean isToPrepare) {

    }

    @Override
    public void releaseExoPlayerCalled() {

    }

    @Override
    public void onVideoResumeDataLoaded(int window, long position, boolean isResumeWhenReady) {

    }

    @Override
    public void onTracksChanged(int currentWindowIndex, int nextWindowIndex, boolean isPlayBackStateReady) {

    }

    @Override
    public void onMuteStateChanged(boolean isMuted) {

    }

    @Override
    public void onVideoTapped() {
        Logger.d("onVideoTapped");
    }

    @Override
    public boolean onPlayBtnTap() {
        return false;
    }

    @Override
    public boolean onPauseBtnTap() {
        return false;
    }

    @Override
    public void onFullScreenBtnTap() {
        if (mediaPlayerHelper.isFullMode()) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        }
    }

    @Override
    public void onVideoBackBtnTap() {
        onBackPressed();
    }
}
