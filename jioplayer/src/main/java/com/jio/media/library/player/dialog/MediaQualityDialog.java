package com.jio.media.library.player.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.jio.media.library.player.R;
import com.jio.media.library.player.utils.CustomRadioGroup;
import com.jio.media.library.player.utils.FontUtil;
import com.jio.media.library.player.utils.MediaQuailty;
import com.jio.media.library.player.utils.MediaQualityTitle;

import java.lang.ref.WeakReference;

public class MediaQualityDialog extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private CustomRadioGroup _radioGroupQuality;
    protected boolean mCancelable = true;
    private MediaQuailty[] _mediaQuality;
    private Button _btnOK, _btnCancel;
    private WeakReference<IMenuQualitySelectedCallback> menuQualityCallBack;
    private CheckBox _rememberMeCheckBox;
    private String _defaultQualitySelected;
    private MediaQualityDialog mediaQualityDialog;
    private TextView txtSubTitle;
    private boolean _isRememberMySettingChecked;
    private MediaQualityTitle[] mediaQualityTitles;
    private CustomRadioGroup customRadioGroup;

    public MediaQualityDialog(Context context, IMenuQualitySelectedCallback menuQualitySelectedCallback, String defaultQualitySelected, boolean isRememberMySettingChecked) {
        super(context);
        menuQualityCallBack = new WeakReference<>(menuQualitySelectedCallback);
        _mediaQuality = MediaQuailty.values();
        _defaultQualitySelected = defaultQualitySelected;
        mediaQualityTitles = MediaQualityTitle.values();
        _isRememberMySettingChecked = isRememberMySettingChecked;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setDialogAttributes();
        _radioGroupQuality = findViewById(R.id.qualityRadioGroup);
        _rememberMeCheckBox = findViewById(R.id.rememberMeBox);
        _btnOK = findViewById(R.id.btnOk);
        _btnCancel = findViewById(R.id.btnCancel);
        _btnCancel.setOnClickListener(this);
        _btnOK.setOnClickListener(this);
        ((TextView) findViewById(R.id.dialogTitleTxt)).setTypeface(FontUtil.getFontInstance().getHelveticaNeue65Medium(getContext()));
        _rememberMeCheckBox.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
        inflateRadioGroups();
        setRememberMySetting();
    }


    private void setRememberMySetting() {
        if (_isRememberMySettingChecked) {
            _rememberMeCheckBox.setChecked(true);
        } else {
            _rememberMeCheckBox.setChecked(false);
        }
    }

    private void setDialogAttributes() {
        this.setContentView(R.layout.menu_quality_dialog);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        params.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(params);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.bitratewidth);
        this.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.transparent)));
    }

    private void inflateRadioGroups() {

        LinearLayout rootView;
        RadioGroup.LayoutParams radioGroupParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        radioGroupParams.setMargins(0, 20, 0, 0);

        for (int i = 0; i < _mediaQuality.length; i++) {
            rootView = (LinearLayout) getLayoutInflater().inflate(R.layout.bitrate_radio_selection, null);
            RadioButton radioButton = (RadioButton) rootView.getChildAt(0);
            radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));

            radioButton.setText(_mediaQuality[i].getQuality());
            radioButton.setId(i);

            txtSubTitle = (TextView) rootView.getChildAt(1);
            txtSubTitle.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
            txtSubTitle.setText(mediaQualityTitles[i].getQualityTitle());

            if (i == MediaQuailty.getMediaQualityCode(_defaultQualitySelected)) {
                radioButton.setChecked(true);
                radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
                _radioGroupQuality.check(i);
            }
            _radioGroupQuality.addView(rootView);
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCancel) {
            menuQualityCallBack.get().onQualityCancelClick();
            dismiss();
        } else if (view.getId() == R.id.btnOk) {
            int selectedId = _radioGroupQuality.getCheckedRadioButtonId();
            menuQualityCallBack.get().onQualitySelected(_mediaQuality[selectedId], _rememberMeCheckBox.isChecked());
            cancel();
            dismiss();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        for (int radio = 0; radio < _radioGroupQuality.getChildCount(); radio++) {
            if (_radioGroupQuality.getChildAt(radio) instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) _radioGroupQuality.getChildAt(radio);
                radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
            }
        }
        RadioButton rb = radioGroup.findViewById(_radioGroupQuality.getCheckedRadioButtonId());
        rb.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public interface IMenuQualitySelectedCallback {
        void onQualityCancelClick();

        void onQualitySelected(MediaQuailty mediaQuality, boolean toRemember);
    }
}

