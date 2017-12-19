package com.bwei.shidongliang20171219;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwei.shidongliang20171219.adapter.ShopAdapter;
import com.bwei.shidongliang20171219.bean.Bean;
import com.bwei.shidongliang20171219.presenter.MainPresenter;
import com.bwei.shidongliang20171219.view.IMainView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TwoActivity extends Activity implements IMainView {
    @BindView(R.id.third_recyclerview)
    RecyclerView thirdRecyclerview;
    @BindView(R.id.third_allselect)
    CheckBox thirdAllselect;
    @BindView(R.id.third_totalprice)
    TextView thirdTotalprice;
    @BindView(R.id.third_totalnum)
    TextView thirdTotalnum;
    @BindView(R.id.third_submit)
    TextView thirdSubmit;
    @BindView(R.id.third_pay_linear)
    LinearLayout thirdPayLinear;
    private MainPresenter presenter;
    private ShopAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);
        presenter.get();
        adapter = new ShopAdapter(this);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        thirdRecyclerview.setLayoutManager(manager);
        thirdRecyclerview.setAdapter(adapter);


        adapter.setListener(new ShopAdapter.UpdateUiListener(){
            @Override
            public void setTotal(String total, String num,boolean allCheck) {

                thirdAllselect.setChecked(allCheck);
                thirdTotalnum.setText(num);
                thirdTotalprice.setText(total);
            }
        });
    }

    @Override
    public void onSuccess(Bean bean) {
        adapter.add(bean);

    }
    @Override
    public void onFailure(Exception e) {

    }
    @OnClick(R.id.third_allselect)
    public void onViewClicked() {

        adapter.selectAll(thirdAllselect.isChecked());
    }

}
