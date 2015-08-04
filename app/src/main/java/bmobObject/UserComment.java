package bmobObject;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2015/7/23 0023.
 */
public class UserComment extends BmobObject {
    private String shopname;
    private String username;
    private String content;
    private Boolean state;//true代表好评，false代表差评

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
