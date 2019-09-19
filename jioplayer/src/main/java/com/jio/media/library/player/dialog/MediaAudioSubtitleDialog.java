package com.jio.media.library.player.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jio.media.library.player.MediaPlayerHelper;
import com.jio.media.library.player.R;
import com.jio.media.library.player.utils.FontUtil;
import com.jio.media.library.player.utils.Logger;

import java.util.ArrayList;
import java.util.List;


public class MediaAudioSubtitleDialog extends Dialog implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup _radioGroupAudio, _radioGroupSubtitle;
    private int rbAudioValue, rbSubtitleValue;
    private Button _btnOK, _btnCancel;
    private MediaAudioSubtitleDialog.IMenuAudioSelectedCallBack _menuAudioSelectedCallBack;
    private int _rendererIndex;
    private RadioButton defaultView;
    private RadioButton radioButton;
    private boolean _defaultSelected = false;
    private String _defaultSubtitle;
    private MediaSubtitle[] _mediaSubtitles;
    RadioGroup.LayoutParams radioGroupParams;
    private boolean _srtOnOff;
    private String defaultLanguage;
    private ArrayList<String> audioList;

    public MediaAudioSubtitleDialog(Context context, IMenuAudioSelectedCallBack menuAudioSelectedCallBack/*, int rendererIndex, String subtitleName, boolean srtOnOff, ArrayList<String> langaugeList, String language, int audioValue, int subtitleValue*/) {
        super(context);
        _menuAudioSelectedCallBack = menuAudioSelectedCallBack;
/*        _rendererIndex = rendererIndex;
        _defaultSubtitle = subtitleName;
        _srtOnOff = srtOnOff;
        audioList = langaugeList;
        defaultLanguage = language;
        rbAudioValue = audioValue;
        rbSubtitleValue = subtitleValue;*/
        _mediaSubtitles = MediaSubtitle.values();

        if (_srtOnOff) {
            _defaultSubtitle = MediaSubtitle.English.getSubtitle();
        } else {
            _defaultSubtitle = MediaSubtitle.Off.getSubtitle();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setDialogAttributes();
        _radioGroupAudio = (RadioGroup) findViewById(R.id.audioRadioGroup);
        _radioGroupSubtitle = (RadioGroup) findViewById(R.id.subtitleRadioGroup);
        _btnOK = (Button) findViewById(R.id.btnOk);
        _btnCancel = (Button) findViewById(R.id.btnCancel);
        _btnCancel.setOnClickListener(this);
        _btnOK.setOnClickListener(this);

        ((TextView) findViewById(R.id.dialogTitleTxtAudio)).setTypeface(FontUtil.getFontInstance().getHelveticaNeue65Medium(getContext()));
        ((TextView) findViewById(R.id.dialogTitleTxtSubtitle)).setTypeface(FontUtil.getFontInstance().getHelveticaNeue65Medium(getContext()));

        inflateSubtitleRadioGroups();
        inflateAudioRadioGroup();
        _radioGroupAudio.setOnCheckedChangeListener(this);
        _radioGroupSubtitle.setOnCheckedChangeListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        pauseMedia();
    }

    private void pauseMedia() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 100);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        int radioGroupId = radioGroup.getId();
        if (radioGroupId == R.id.audioRadioGroup) {
            for (int radio = 0; radio < _radioGroupAudio.getChildCount(); radio++) {
                if (_radioGroupAudio.getChildAt(radio) instanceof RadioButton) {
                    if (defaultView != null && i == defaultView.getId()) {
                        // _override = null;
                        _defaultSelected = true;
                        _menuAudioSelectedCallBack.onDefaultSelected();
                    } else {
                        _defaultSelected = false;
                    }
                    RadioButton radioButton = (RadioButton) _radioGroupAudio.getChildAt(radio);
                    radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
                }
            }
            RadioButton rb = (RadioButton) radioGroup.findViewById(_radioGroupAudio.getCheckedRadioButtonId());
            rb.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
            rb.setChecked(true);
            if (!_defaultSelected) {
            }
        } else if (radioGroupId == R.id.subtitleRadioGroup) {
            for (int radio = 0; radio < _radioGroupSubtitle.getChildCount(); radio++) {
                if (_radioGroupSubtitle.getChildAt(radio) instanceof RadioButton) {
                    RadioButton radioButton = (RadioButton) _radioGroupSubtitle.getChildAt(radio);
                }
            }
            RadioButton rb = (RadioButton) radioGroup.findViewById(_radioGroupSubtitle.getCheckedRadioButtonId());
        }
    }

    private void setDialogAttributes() {
        this.setContentView(R.layout.menu_audio_subtitle_dialog);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.alpha = 1.0f;
        params.dimAmount = 0.5f;
        params.gravity = Gravity.CENTER;
        this.getWindow().setAttributes(params);
        this.setCancelable(true);
        this.setCanceledOnTouchOutside(true);
        int width = getContext().getResources().getDimensionPixelSize(R.dimen.audioSubtitleDialogWidth);
        this.getWindow().setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), android.R.color.transparent)));
    }

    private void inflateSubtitleRadioGroups() {
        radioGroupParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        radioGroupParams.setMargins(0, (int) getContext().getResources().getDimension(R.dimen.multiaudioRadioButtonSpace), 0, 0);

        // int subtitle = -1;
        //radioButton.setLayoutParams(radioGroupParams);
        Logger.d("subtitle count - " + MediaSubtitle.values().length);
        if (!_srtOnOff) {
            radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.birate_selection_radio_view, null);
            radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
            radioButton.setText(MediaSubtitle.getMediaSubtitleString(0));
            radioButton.setId(0);
            radioButton.setChecked(true);
            radioButton.setLayoutParams(radioGroupParams);
            _radioGroupSubtitle.removeView(radioButton);
            _radioGroupSubtitle.addView(radioButton);

        } else {
            for (int i = 0; i <= 1; i++) {
                radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.birate_selection_radio_view, null);
                radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
                radioButton.setText(MediaSubtitle.getMediaSubtitleString(i));
                radioButton.setId(i);
                radioButton.setLayoutParams(radioGroupParams);
                if (i == rbSubtitleValue) {
                    radioButton.setChecked(true);
                } else {
                    radioButton.setChecked(false);
                }
                _radioGroupSubtitle.removeView(radioButton);
                _radioGroupSubtitle.addView(radioButton);

            }

            /*if(rbSubtitleValue != -1){
                Log.d("subtitleCondition" , "Value = " + rbSubtitleValue);
                _radioGroupSubtitle.check(rbSubtitleValue);
            }*/
        }
    }


    @SuppressLint("ResourceType")
    private void inflateAudioRadioGroup() {
        try {
            List<String> languages = audioList;
            String lang = defaultLanguage;
            RadioGroup.LayoutParams radioGroupParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            radioGroupParams.setMargins(0, (int) getContext().getResources().getDimension(R.dimen.multiaudioRadioButtonSpace), 0, 0);

            if (lang != null && languages.size() == 0) {
                RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.birate_selection_radio_view, null);
                radioButton.setChecked(true);
                radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
                radioButton.setText(lang);
                radioButton.setTag(lang);
                radioButton.setId(0);
                radioButton.setLayoutParams(radioGroupParams);
                _radioGroupAudio.addView(radioButton);
            }
            for (int i = 0; i < languages.size(); i++) {
                RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.birate_selection_radio_view, null);
                radioButton.setTypeface(FontUtil.getFontInstance().getHelveticaNeue55Roman(getContext()));
                radioButton.setText(languages.get(i));
                radioButton.setId(i);
                radioButton.setTag(languages.get(i));
                radioButton.setLayoutParams(radioGroupParams);
                if (radioButton.getId() == 0 || radioButton.getId() == rbAudioValue) {
                    radioButton.setChecked(true);
                }
                _radioGroupAudio.addView(radioButton);
            }
//            if(rbAudioValue != -1){
//                Log.d("audioCondition" , "Value = " + rbAudioValue);
//                _radioGroupAudio.check(rbAudioValue);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnCancel) {
            _menuAudioSelectedCallBack.onAudioCancelClick();
            dismiss();
        } else if (view.getId() == R.id.btnOk) {
            rbAudioValue = _radioGroupAudio.getCheckedRadioButtonId();
            rbSubtitleValue = _radioGroupSubtitle.getCheckedRadioButtonId();
            RadioButton rb = (RadioButton) _radioGroupAudio.findViewById(_radioGroupAudio.getCheckedRadioButtonId());
            if (rb != null) {
                _menuAudioSelectedCallBack.onAudioSelected(rb);
            }
            int selectedId = _radioGroupSubtitle.getCheckedRadioButtonId();
            _menuAudioSelectedCallBack.onSubtitleSelected(_mediaSubtitles[selectedId], rbSubtitleValue);
            dismiss();
        }
    }

    public interface IMenuAudioSelectedCallBack {
        void onAudioCancelClick();

        void onAudioSelected(View view);

        void updateView(RadioButton[] view);

        void onDefaultSelected();

        void onSubtitleSelected(MediaSubtitle mediaSubtitle, int rbSubtitleValue);
    }

    @Override
    public void onDetachedFromWindow() {
        Log.v("onDetachedFromWindow", "onDetachedFromWindow");
        super.onDetachedFromWindow();
        Log.v("onDetachedFromWindow", "onDetachedFromWindow1");

    }

    public enum MediaSubtitle {
        Off(0, "Off"),
        English(1, "English");

        String subtitle;
        int subtitleCode;

        MediaSubtitle(int subtitlevalue, String subtitleName) {
            subtitleCode = subtitlevalue;
            subtitle = subtitleName;
        }

        public static MediaSubtitle getMediaSubtitle(String subtitle) {
            if (subtitle == null) {
                return MediaSubtitle.English;
            }
            for (MediaSubtitle mediaSubtitle : MediaSubtitle.values()) {
                if (mediaSubtitle.getSubtitle().equalsIgnoreCase(subtitle)) {
                    return mediaSubtitle;
                }
            }
            return MediaSubtitle.English;
        }

        public static int getMediaSubtitleCode(String subtitleSelected) {
            if (subtitleSelected == null) {
                return MediaSubtitle.getMediaSubtitleCode(subtitleSelected);
            }
            for (MediaSubtitle mediaSubtitle : MediaSubtitle.values()) {
                if (mediaSubtitle.getSubtitle().equalsIgnoreCase(subtitleSelected)) {
                    return mediaSubtitle.getSubtitleCode();
                }
            }
            return MediaSubtitle.English.getSubtitleCode();
        }

        public static String getMediaSubtitleString(int subtitleSelected) {
            for (MediaSubtitle mediaSubtitle : MediaSubtitle.values()) {
                if (mediaSubtitle.getSubtitleCode() == subtitleSelected) {
                    return mediaSubtitle.getSubtitle();
                }
            }
            return MediaSubtitle.English.getSubtitle();
        }

        public String getSubtitle() {
            return subtitle;
        }

        public int getSubtitleCode() {
            return subtitleCode;
        }
    }
}