package com.bwei.shidongliang20171219.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bwei.shidongliang20171219.PlusView;
import com.bwei.shidongliang20171219.R;
import com.bwei.shidongliang20171219.bean.Bean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import butterknife.BindView;
import butterknife.ButterKnife;



public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.IViewHolder> {
    private Context context;

    private List<Bean.DataBean.ListBean> list;
    // 存放 商家的id 和 商家名称
    private Map<String, String> map = new HashMap<>();

    public ShopAdapter(Context context) {
        this.context = context;
    }
    /**
     * 添加数据 并更新显示
     *
     * @param bean
     */
    public void add(Bean bean) {
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
        // 遍历商家
        for (Bean.DataBean shop : bean.getData()) {
            map.put(shop.getSellerid(), shop.getSellerName());
            // 遍历商品
            for (int i = 0; i < shop.getList().size(); i++) {
                this.list.add(shop.getList().get(i));
            }
        }
        setFirst(this.list);

        notifyDataSetChanged();
    }

    /**
     * 设置数据源， 控制显示商家
     *
     * @param list
     */
    private void setFirst(List<Bean.DataBean.ListBean> list) {

        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getSellerid() == list.get(i - 1).getSellerid()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                    if(list.get(i).isItemSelected()){
                        list.get(i).setShopSelected(list.get(i).isItemSelected());
                    }

                }
            }

        }

    }
    @Override
    public IViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.adapter_layout, null);
        return new IViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final IViewHolder holder, final int position) {

        // 显示商品图片

        if(list.get(position).getIsFirst() == 1){
            //显示商家
            holder.shopCheckbox.setVisibility(View.VISIBLE);
            holder.tvItemShopcartShopname.setVisibility(View.VISIBLE);
            holder.shopCheckbox.setChecked(list.get(position).isShopSelected());


            holder.tvItemShopcartShopname.setText(map.get(String.valueOf(list.get(position).getSellerid())));
        } else {
            holder.shopCheckbox.setVisibility(View.GONE);
            holder.tvItemShopcartShopname.setVisibility(View.GONE);
        }


        //控制 商品的  checkbox
        holder.itemCheckbox.setChecked(list.get(position).isItemSelected());





        String[] url = list.get(position).getImages().split("\\|");
        ImageLoader.getInstance().displayImage(url[0],holder.itemPic);


        holder.itemName.setText(list.get(position).getTitle());
        holder.itemPrice.setText(list.get(position).getPrice()+"");


        holder.plusViewId.setEditText(list.get(position).getNum());


        // 商家的checkbox
        holder.shopCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list.get(position).setShopSelected(holder.shopCheckbox.isChecked());

                for(int i=0;i<list.size();i++){
                    if(list.get(position).getSellerid() == list.get(i).getSellerid()){
                        list.get(i).setItemSelected(holder.shopCheckbox.isChecked());
                    }
                }

                notifyDataSetChanged();
                sum(list);

            }
        });

        // 商品的checkbox

        holder.itemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                list.get(position).setItemSelected(holder.itemCheckbox.isChecked());


                for(int i=0;i<list.size();i++){
                    for (int j=0;j<list.size();j++){
                        if(list.get(i).getSellerid() == list.get(j).getSellerid() && !list.get(j).isItemSelected()){
                            list.get(i).setShopSelected(false);
                            break;
                        }else {
                            list.get(i).setShopSelected(true);
                        }
                    }
                }

                notifyDataSetChanged();
                sum(list);

            }
        });






        holder.itemDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                list.remove(position);

                setFirst(list);

                notifyDataSetChanged();
                sum(list);

            }
        });




        //加减号
        holder.plusViewId.setListener(new PlusView.ClickListener() {
            @Override
            public void click(int count) {

                list.get(position).setNum(count);
                notifyDataSetChanged();
                sum(list);

            }
        });



    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    /**
     * 计算总价
     * @param list
     */
    private void sum(List<Bean.DataBean.ListBean> list){

        int totalNum = 0 ;
        float totalMoney =  0.0f;

        boolean allCheck =true;
        for(int i=0;i<list.size();i++){
            if(list.get(i).isItemSelected()){
                totalNum += list.get(i).getNum() ;
                totalMoney += list.get(i).getNum() * list.get(i).getPrice();
            }else {
                allCheck = false;
            }
        }
        listener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }
    public void selectAll(boolean check){

        for(int i=0;i<list.size();i++){
            list.get(i).setShopSelected(check);
            list.get(i).setItemSelected(check);
        }
        notifyDataSetChanged();

        sum(list);
    }
    static class IViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.view)
        View view;
        @BindView(R.id.shop_checkbox)
        CheckBox shopCheckbox;
        @BindView(R.id.tv_item_shopcart_shopname)
        TextView tvItemShopcartShopname;
        @BindView(R.id.ll_shopcart_header)
        LinearLayout llShopcartHeader;
        @BindView(R.id.item_checkbox)
        CheckBox itemCheckbox;
        @BindView(R.id.item_pic)
        ImageView itemPic;
        @BindView(R.id.item_price)
        TextView itemPrice;
        @BindView(R.id.item_name)
        TextView itemName;
        @BindView(R.id.tv_item_shopcart_cloth_size)
        TextView tvItemShopcartClothSize;
        @BindView(R.id.plus_view_id)
        PlusView plusViewId;
        @BindView(R.id.item_del)
        ImageView itemDel;


        IViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
    public UpdateUiListener listener;
    public void setListener(UpdateUiListener listener){
        this.listener = listener;
    }
    public   interface UpdateUiListener {
        public void setTotal(String total, String num, boolean allCheck);
    }
}
