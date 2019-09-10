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
        mediaViewModel.startMediaPlayer("{\n" +
                "    \"deviceInfo\": {\n" +
                "        \"consumptionDeviceName\": \"Mytablet\",\n" +
                "        \"jToken\": \"671c8afede1a05fc2917f2abcbd07fdb.9a2ea38cf6b03d6001853e77ab3c5325f44ddea0892327b7747de265cbba73a465e417474933284b548aea6c7a0e10c6977e3d5135d6c53c3d4120a243cd04ef6b21fcc6d9ab4362be4221a993d96096b7a104c3cb9884b8150b6c92c9beea0b6713935867033538aa3b9b4cb75fe745a6ce69adf34638bef2d61729297aff9e689c7412ae042c751f7dc22876027f4c3500ef4670d9dd46fc5a283e0e439ec859a97200eacea8e251388faa9c899748b0bfc3075132c552ab194879db4a550ad3adb23ffe2766899ced91689185a38df32873bbd737ebe2bf0944abeb6afea3685ed573f0ae3283e64dfd5cc1b764c21b8ba477c2654683357bb9ae1cb8cb33e2037d5a7697a7c6f4f517604967b66e72abbcfafc32f6ee83da4f82820faf13abbf37294d6d1ed7bb8fad7cc3ddf68e6cb31fdc663dfdd2cc99910cfcad74e47538ee31b08f086b4523b2a06643900b987c21e1c2563cb212f59816fc74a826\",\n" +
                "        \"info\": {\n" +
                "            \"androidId\": \"488b63f3e19b59d6\",\n" +
                "            \"mac\": \"02:00:00:00:00:00\",\n" +
                "            \"type\": \"android\",\n" +
                "            \"imsi\": \"\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"ssoToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1bmlxdWUiOiJlYTgyZGFiZC1iNTQxLTRmNjItOTdlNC03M2U2ZTYxM2I5MTQiLCJ1c2VyVHlwZSI6IlJJTHBlcnNvbiIsImF1dGhMZXZlbCI6IjEwIiwianRpIjoiMjRjZDgwYmYtODYyMC00MmFlLWE4MzctNmI0N2M3MDBkZGQzIiwiaWF0IjoxNTYwODU0ODk1fQ.9vFM_0d7drLB7MN459YiK0gnjipCFSjrQ-21PjTUe8E\",\n" +
                "    \"subscriberId\": \"9000406895\",\n" +
                "    \"uniqueId\": \"ea82dabd-b541-4f62-97e4-73e6e613b914\",\n" +
                "    \"uId\": \"prerana.barve\",\n" +
                "    \"name\": \"Prerana  Barve\",\n" +
                "    \"loginType\": 2,\n" +
                "    \"ssoExpired\": false\n" +
                "}");

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