package com.SHM.adapter;

import com.SHM.constant.Constant;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{  
    private Context mContext;  

    public ImageAdapter(Context context) {  
        this.mContext=context;  
    }  

    @Override  
    public int getCount() {  
        return Constant.pics.length;  
    }  

    @Override  
    public Object getItem(int position) {  
        return Constant.pics[position];  
    }  

    @Override  
    public long getItemId(int position) {  
        // TODO Auto-generated method stub  
        return 0;  
    }  
    
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        //定义一个ImageView,显示在GridView里  
        ImageView imageView;  
        if(convertView==null){  
            imageView=new ImageView(mContext);  
            imageView.setLayoutParams(new GridView.LayoutParams(200, 200));  
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP); 
        }else{  
            imageView = (ImageView) convertView;  
        }  
        imageView.setBackgroundResource(Constant.pics[position]);  
        return imageView;  
    }  
}
