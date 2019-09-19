package com.jio.media.library.player.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import java.util.Hashtable;

/**
 * An implementation for applying CustomFonts dynamically to the TextViews.
 */
public class FontUtil extends Application
{
    private static FontUtil mFontInstance;
    private static Hashtable<String, Typeface> fontCache = new Hashtable<>();

    public static final FontUtil getFontInstance()
    {
        if (mFontInstance == null)
        {
            mFontInstance = new FontUtil();
        }

        return mFontInstance;
    }

    public static Typeface get(String name, Context context)
    {
        Typeface tf = fontCache.get(name);
        if (tf == null)
        {
            try
            {
                tf = Typeface.createFromAsset(context.getAssets(), name);
            }
            catch (Exception e)
            {
                return null;
            }
            fontCache.put(name, tf);
        }
        return tf;
    }

    @Override
    public void onCreate()
    {

        super.onCreate();

        // mInstance = this;

        getFontInstance();

    }

    public void setTypeFace(Context context, View... views)
    {
        //for (int i = 0; i < views.length; i++)
        for (View view : views)
        {
            if (view != null)
            {
                ((TextView) view).setTypeface(getRobotoRegular(context));
            }
        }
    }

    public Typeface getRobotoThin(Context context)
    {

        return FontUtil.get("roboto_thin.ttf", context);
    }

    public Typeface getRobotoRegular(Context context)
    {

        return FontUtil.get("roboto_regular.ttf", context);
    }

    public Typeface getRobotoMedium(Context context)
    {

        return FontUtil.get("roboto_medium.ttf", context);
    }

    public Typeface getRobotoLight(Context context)
    {

        return FontUtil.get("roboto_light.ttf", context);
    }

    public Typeface getRobotoBold(Context context)
    {

        return FontUtil.get("roboto_bold.ttf", context);
    }

    public Typeface getRobotoLightItalic(Context context)
    {

        return FontUtil.get("roboto_lightitalic.ttf", context);
    }

    public Typeface getHelveticaNeue75Bold(Context context)
    {

        return FontUtil.get("helvetica_neue_75_bold.ttf", context);
    }

    public Typeface getHelveticaNeue65Medium(Context context)
    {

        return FontUtil.get("helvetica_neue_65_medium.ttf", context);
    }

    public Typeface getHelveticaNeue55Roman(Context context)
    {

        return FontUtil.get("helvetica_neue_55_roman.ttf", context);
    }

    public Typeface getHelveticaNeue45Light(Context context)
    {

        return FontUtil.get("helvetica_neue_45_light.ttf", context);
    }

    public Typeface getIconTextFont(Context context)
    {

        return FontUtil.get("icomoon.ttf", context);
    }


}
