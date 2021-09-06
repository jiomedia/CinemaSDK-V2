package com.jio.media.sdk.cinema;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jio.media.library.player.model.information.VideoInformation;
import com.jio.media.library.player.utils.Logger;
import com.jio.media.library.player.view.PlayerViewActivity;
import com.jio.media.library.player.viewmodel.MediaViewModel;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaViewModel mediaViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
        mediaViewModel.startMediaPlayer("{\"deviceInfo\":{\"consumptionDeviceName\":\"Mytablet\",\"jToken\":\"f1a7af76450b52dcbd77a83f5fa30116.2d66a2dcc8295111a775753e24d8aad8dae0312c6ccc6a50fb35a3c38cd6e7afb13f005832a6caf691b6b88811d6fbcfe0169f72cf7c69697f249c6b28aa03eac48461011b6b21b979699458dbb82354fa492e0f3920a630801752fc3a49aa059207a864dbae6c34fc1374664150f2f88066e982387c63262e04bcbe53f55bef1b66cdd8fc7bc911639fcd4b3ee16ff461955c5408a12683f273d62c913bcd8a1aa0f7c1ab425a13601fb6295bb3297c18751a9cb25580d2a242706195b80e7febf8ac16af37df9f2f43d040f80c387a1d59f61c033ccd236f7b1b5c7eb582ebe808a2027f659b8f25705b806caad7e86cbdb8d234f45607913b823d999f3b254647f3c3c16a748e611d1c4d7b6c6c356b1ab4da7450887c776267d5fb679341ea97081696f61525940d47455bc678bf98d37a8097829a9f976c050a6fe21ad5c725284ba10be4b14d14d0051d1ff6159c34a9a8f00b4d2f0f9d3195c68b260e\",\"info\":{\"androidId\":\"6c1aeec6741e29a8\",\"mac\":\"02:00:00:00:00:00\",\"type\":\"android\",\"imsi\":\"\"}},\"ssoToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWUiOiI0YmRiNzZiYS1lYjc2LTRlMTEtODhjZi1mZmQzMGY2ZjUwYmMiLCJ1c2VyVHlwZSI6IlJJTHBlcnNvbiIsImF1dGhMZXZlbCI6IjMwIiwiZGV2aWNlSWQiOiI4ZDc0YTcxYTdmZWY5ZWZmYWZhNmMwZTY0MDk3ZjdlNTg2OWYxNTNkYmM3ODlhZDJkNGE4YTQwNGJhODAxZDUwNzY2OTU4OWNkYjUzNmI4OTZkY2YzMDY0ZmM2M2IwYWVmOGIyODQyN2I2MjBjNTdiMTY4Njk5NzU3NDdhOTk1NSIsImp0aSI6ImU2Y2U5MDQxLTU2ZDItNGVjMi05NjE4LWJlNGY1ODAwZWNkZCIsImlhdCI6MTYzMDY1NTYyOX0.ZKCWsMZzL6Qp376MIcHSiaig97xTyNqr08Hi9DI2fck\",\"subscriberId\":\"9335729617\",\"uniqueId\":\"4bdb76ba-eb76-4e11-88cf-ffd30f6f50bc\",\"uId\":\"hussainchachuliya2\",\"name\":\"Hussain Chachuliya\",\"loginType\":3,\"ssoExpired\":false}");

        mediaViewModel.getInformationLiveData().observe(this, videoInformation -> {
            Intent intent = new Intent(MainActivity.this, PlayerViewActivity.class);
            intent.putExtra("videoInformation", videoInformation);
            startActivity(intent);
        });

        Button btnCLick = findViewById(R.id.btnCLick);
        btnCLick.setOnClickListener(view -> Logger.d("btnClicked"));
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }
}