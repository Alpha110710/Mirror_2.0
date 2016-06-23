package com.example.dllo.mirror_20.login;

/**
 * Created by dllo on 16/6/23.
 */
public class LoginBean {

    /**
     * result : 1
     * msg :
     * data : {"token":"a71f64abd27baa582961be53c58d9e40","uid":"540"}
     */

    private String result;
    private String msg;
    /**
     * token : a71f64abd27baa582961be53c58d9e40
     * uid : 540
     */

    private DataBean data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String token;
        private String uid;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }
    }
}
