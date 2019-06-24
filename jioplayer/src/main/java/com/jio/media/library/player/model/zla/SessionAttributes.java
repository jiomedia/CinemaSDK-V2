
package com.jio.media.library.player.model.zla;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SessionAttributes
{
    @SerializedName("user")
    @Expose
    private User user;

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }
}
