package com.bwei.shidongliang20171219;

import com.bwei.shidongliang20171219.bean.Bean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 此类的作用：
 *
 * @author: forever
 * @date: 2017/12/19 9:48
 */
public interface IGetDataBase {
    @GET("/product/getCarts")
    Call<Bean> get(@Query("uid") String uid);
}
