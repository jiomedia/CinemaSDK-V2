package com.jio.media.library.player.token;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class TokenController extends TokenGenerator
{
    public static final String TAG = "VIJAY.K.ARORA";
    private static final TokenController ourInstance = new TokenController();

    private String ssoToken,secureRandomKey;
    private String sid;
    private String tokenId;
    private int expireTime = 900;
    private boolean hasEncryption;

    public static TokenController getInstance()
    {
        return ourInstance;
    }

    public String getEncryptedUrl(String url)
    {
        return getUrl(url, ssoToken, tokenId, expireTime);
    }

    public String getEncryptedUrlWithHashedToken(String url)
    {
        return getUrlWithHashedToken(url, ssoToken, tokenId, expireTime);
    }

    public String getEncryptedUrlWithSecureKey(String url)
    {
        return getUrl(url, secureRandomKey, tokenId, expireTime);
    }

    public Map<String, Object> getEncryptionHeader()
    {
        return super.getEncryptionHeader(ssoToken, tokenId, expireTime);
    }

    public void setSid(String id)
    {
        this.sid = id;
        setToken(sid);
    }

    public void setSsoToken(String ssoToken)
    {
        this.ssoToken = ssoToken;
    }

    public void setSecureRandomToken(String secureRandomKey)
    {
        this.secureRandomKey = secureRandomKey;
    }

    public String getEncryption(String secret, String path, Long expire) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        return encryption(secret, path, expire);
    }

    public void setTokenId(String tokenId)
    {
        this.tokenId = tokenId;
    }

    public void setEncryption(boolean hasEncryption)
    {
        this.hasEncryption = hasEncryption;
    }

    public boolean hasEncryption()
    {
        return hasEncryption;
    }

    public void setExpireTime(int cdnUrlExpiry)
    {
        this.expireTime = cdnUrlExpiry;
    }
}
