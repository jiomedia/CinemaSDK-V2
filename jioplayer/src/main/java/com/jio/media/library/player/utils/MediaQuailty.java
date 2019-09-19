package com.jio.media.library.player.utils;

public enum MediaQuailty {
    Auto(0, "Auto"),
    Low(1, "Low"),
    Medium(2, "Medium"),
    High(3, "High");
    String quality;
    int qualityCode;

    MediaQuailty(int qualityCd, String qualityValue) {
        qualityCode = qualityCd;
        quality = qualityValue;
    }

    public static MediaQuailty getMediaQuality(String quality) {
        if (quality == null) {
            return MediaQuailty.Auto;
        }
        for (MediaQuailty mediaQuality : MediaQuailty.values()) {
            if (mediaQuality.getQuality().equalsIgnoreCase(quality)) {
                return mediaQuality;
            }
        }
        return MediaQuailty.Auto;
    }

    public static int getMediaQualityCode(String qualitySelected) {
        if (qualitySelected == null) {
            return MediaQuailty.Auto.getQualityCode();
        }
        for (MediaQuailty mediaQuality : MediaQuailty.values()) {
            if (mediaQuality.getQuality().equalsIgnoreCase(qualitySelected)) {
                return mediaQuality.getQualityCode();
            }
        }
        return MediaQuailty.Auto.getQualityCode();
    }

    public String getQuality() {
        return quality;
    }

    public int getQualityCode() {
        return qualityCode;
    }


}


