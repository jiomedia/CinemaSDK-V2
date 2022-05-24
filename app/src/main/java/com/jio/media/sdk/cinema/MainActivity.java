package com.jio.media.sdk.cinema;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.jio.media.library.player.utils.Logger;
import com.jio.media.library.player.view.PlayerViewActivity;
import com.jio.media.library.player.viewmodel.MediaViewModel;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaViewModel mediaViewModel = ViewModelProviders.of(this).get(MediaViewModel.class);
        mediaViewModel.startMediaPlayer(" {\"deviceInfo\":{\"consumptionDeviceName\":\"Mytablet\",\"jToken\":\"193db14786d76c1151754dbb2c06962c.bb52c2feae4ec0561ad0b7672cb6765f28dd6d2116e239296d28f5b931135bb08d42872b5404a56b8e8a53578c87bbaa19728c5c81f35b55152410a52848e0b3c90101a406126bcee1c8f2fabcfe5d20f0e9fc332f85c3f467f6da9385798e932a2d69007188c2e342d79bb5bd0d4d52eb437f925302c5a66a407cc7fe99848dd723b5d10ff1eea9dce7ff43bf09e2cceb9e84ccad52d3ab974ee0c3453bdec40517006e3c6687fab9e051a822da7ec8cb7bb312a533883e21fa5994682bd383a7a4381d2c7b8ad3126e40a15ac5bcbb50f9a0b7e927704aeb713df696e87e2963bd5a645feba9cc59fa20846fd55532ef920261fd189985049a934077ffe42ffa2d45729ca717e9e2db109f47b40a2bd674edb25062c811283ba3a54758c713806f5dd846a1d1ce28bb4255e9bba1da7c457af18b5840e8c1e185aef3e2d922f06ac59bf6aa3b5eff202f76e9e52391949d69a97a5a91c1a0f093b9d3be46b8\",\"info\":{\"androidId\":\"82109e0f9d6f135b\",\"mac\":\"02:00:00:00:00:00\",\"type\":\"android\",\"imsi\":\"\"}},\"ssoToken\":\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWUiOiI2ZTQ3ZGQwMi00NDkxLTRjOTQtYmExNi1mNTJkNGM0NzM3NzciLCJ1c2VyVHlwZSI6IlJJTHBlcnNvbiIsImF1dGhMZXZlbCI6IjMwIiwiZGV2aWNlSWQiOiI4ZDc0YTcxYTdmZWY5ZWZmYWZhNmMwZTY0MDk3ZjdlNTg2OWYxNTNkYmM3ODlhZDJkNGE4YTQwNGJhODAxZDUwNzY2OTU4OWNkYjUzNmI4OTZkY2YzMDY0ZmM2M2IwYWVmOGIyODQyN2I2MjBjNTdiMTY4Njk5NzU3NDdhOTk1NSIsImp0aSI6ImMwM2FjNjdkLTJkODYtNGE0NS1hOWZlLTQ4YzYyZTM3ZGY0OCIsImlhdCI6MTYzMjMwNTkxN30.bt2DZ3B-FYz7GJf2CW7o-aFQUlpkKM1y8qPJ-RjAjUs\",\"subscriberId\":\"9335166293\",\"uniqueId\":\"6e47dd02-4491-4c94-ba16-f52d4c473777\",\"uId\":\"imranansari268\",\"name\":\"Imran Ansari\",\"loginType\":3,\"ssoExpired\":false}");

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