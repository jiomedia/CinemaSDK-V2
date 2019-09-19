package com.jio.media.library.player.utils;

public enum MediaQualityTitle
{
    AutoTitle(0, ""),
    LowTitle(1, "Basic: uses upto 0.17 GB per hour"),
    MediumTitle(2, "Standard: uses upto 0.51 GB per hour"),
    HighTitle(3, "Best: uses upto 1 GB per hour in HD");


    String qualityTitle;
    int qualityTitleCode;

    MediaQualityTitle(int code, String title)
    {
        qualityTitleCode = code;
        qualityTitle = title;
    }

    public static MediaQualityTitle getMediaQuality(String quality)
    {
        if (quality == null)
        {
            return MediaQualityTitle.AutoTitle;
        }
        for (MediaQualityTitle mediaQuality : MediaQualityTitle.values())
        {
            if (mediaQuality.getQualityTitle().equalsIgnoreCase(quality))
            {
                return mediaQuality;
            }
        }
        return MediaQualityTitle.AutoTitle;
    }

    public static int getMediaQualityCode(String qualitySelected)
    {
        if (qualitySelected == null)
        {
            return MediaQualityTitle.AutoTitle.getQualityTitleCode();
        }
        for (MediaQualityTitle mediaQuality : MediaQualityTitle.values())
        {
            if (mediaQuality.getQualityTitle().equalsIgnoreCase(qualitySelected))
            {
                return mediaQuality.getQualityTitleCode();
            }
        }
        return MediaQualityTitle.AutoTitle.getQualityTitleCode();
    }

    public String getQualityTitle()
    {
        return qualityTitle;
    }

    public int getQualityTitleCode()
    {
        return qualityTitleCode;
    }


}
