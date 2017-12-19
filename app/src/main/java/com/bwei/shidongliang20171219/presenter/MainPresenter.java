package com.bwei.shidongliang20171219.presenter;

import com.bwei.shidongliang20171219.bean.Bean;
import com.bwei.shidongliang20171219.model.MainModelImpl;
import com.bwei.shidongliang20171219.model.ModelCallBack;
import com.bwei.shidongliang20171219.view.IMainView;

/**
 * 此类的作用：
 *
 * @author: forever
 * @date: 2017/12/19 10:00
 */
public class MainPresenter {
    private IMainView iView ;
    private MainModelImpl mainModel ;
    public MainPresenter(IMainView view){
        this.iView = view;
        this.mainModel = new MainModelImpl();

    }


    public void get(){

        mainModel.getData(new ModelCallBack() {
            @Override
            public void onSuccess(Bean bean) {

                if(iView != null){
                    iView.onSuccess(bean);
                }
            }

            @Override
            public void onFailure(Exception e) {
                if(iView != null){
                    iView.onFailure(e);
                }
            }
        });
    }
}
