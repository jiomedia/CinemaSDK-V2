package com.jio.media.library.player.token;

import android.text.TextUtils;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

class TokenGenerator
{
    private String id;

    private long expireTime(int expireTime)
    {
        Date d = new Date();
        return (d.getTime() / 1000) + expireTime;
    }

    void setToken(String token)
    {
        this.id = token;
    }

    private String secret(String token)
    {
        String shared = "";
        for (Character ch : getPassword().toCharArray())
        {
            int pos = (int) ch - 'A' + 1;
            shared += token.charAt(pos - 1);
        }

        return shared;
    }

    String getUrl(String url, String ssoToken, String tokenId, int expireTime)
    {
        if (url.contains("?"))
        {
            url = url.substring(0, url.indexOf('?'));
        }

        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(ssoToken) && !TextUtils.isEmpty(tokenId))
        {
            String secret = secret(tokenId);
            long expires = expireTime(expireTime);

            String key = null;
            try
            {
                key = TokenController.getInstance().getEncryption(secret, ssoToken, expires);
                key = key.replaceAll("=", "").replaceAll(Pattern.quote("+"), "-").replaceAll("/", "_").replaceAll("[\n\r]", "");
            }
            catch (UnsupportedEncodingException | NoSuchAlgorithmException ex)
            {
                ex.printStackTrace();
            }

            return url + "?jct=" + key.trim() + "&pxe=" + expires + "&st=" + ssoToken;
        }

        return null;
    }


    String getUrlWithHashedToken(String url, String ssoToken, String tokenId, int expireTime)
    {
        if (url.contains("?"))
        {
            url = url.substring(0, url.indexOf('?'));
        }

        if (!TextUtils.isEmpty(url) && !TextUtils.isEmpty(ssoToken) && !TextUtils.isEmpty(tokenId))
        {
            String secret = secret(tokenId);
            long expires = expireTime(expireTime);

            String key = null, hashedToken = null;
            try
            {
                hashedToken = TokenController.getInstance().getHashedToken(secret, ssoToken);
                hashedToken = hashedToken.replaceAll("=", "").replaceAll(Pattern.quote("+"), "-").replaceAll("/", "_").replaceAll("[\n\r]", "");
                key = TokenController.getInstance().getEncryption(secret, hashedToken, expires);
                key = key.replaceAll("=", "").replaceAll(Pattern.quote("+"), "-").replaceAll("/", "_").replaceAll("[\n\r]", "");
            }
            catch (UnsupportedEncodingException | NoSuchAlgorithmException ex)
            {
                ex.printStackTrace();
            }

            return url + "?jct=" + key.trim() + "&pxe=" + expires + "&st=" + hashedToken;
        }

        return null;
    }

    String encryption(String secret, String path, Long expire) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        String message = secret + path + expire.toString();
        byte[] bytesOfMessage = message.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bytesOfMessage);
        String encoded = encodeBase64URLSafeString(digest);
        return encoded;
    }

    String getHashedToken(String secret, String ssoToken) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        String message = secret + ssoToken.toString();
        byte[] bytesOfMessage = message.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] digest = md.digest(bytesOfMessage);
        String encoded = encodeBase64URLSafeString(digest);
        return encoded;
    }

    private String encodeBase64URLSafeString(byte[] binaryData)
    {
        return Base64.encodeToString(binaryData, Base64.URL_SAFE);
    }

    private String getPassword()
    {
        return id.replace("Y", "").replace("F", "").replace("G", "");
    }


    private Map<String, Object> _encryptionHeaderProperty;

    protected Map<String, Object> getEncryptionHeader(String ssoToken, String tokenId, int expireTime)
    {
        _encryptionHeaderProperty = new HashMap<>();
        if (!TextUtils.isEmpty(ssoToken) && !TextUtils.isEmpty(tokenId))
        {
            String secret = secret(tokenId);
            long expires = expireTime(expireTime);

            String key = null;
            try
            {
                key = TokenController.getInstance().getEncryption(secret, ssoToken, expires);
                key = key.replaceAll("=", "").replaceAll(Pattern.quote("+"), "-").replaceAll("/", "_").replaceAll("[\n\r]", "");
            }
            catch (UnsupportedEncodingException | NoSuchAlgorithmException ex)
            {
                ex.printStackTrace();
            }
            _encryptionHeaderProperty.put("jct", key.trim());
            _encryptionHeaderProperty.put("pxe", expires);
            _encryptionHeaderProperty.put("st", ssoToken);

            return _encryptionHeaderProperty;
        }
        return null;
    }

}
