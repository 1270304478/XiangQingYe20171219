package com.bwei.shidongliang20171219.model;

import com.bwei.shidongliang20171219.bean.Bean;

/**
 * 此类的作用：
 *
 * @author: forever
 * @date: 2017/12/19 9:41
 */
public interface ModelCallBack {
    public void onSuccess(Bean bean);
    public void onFailure(Exception e);
}
