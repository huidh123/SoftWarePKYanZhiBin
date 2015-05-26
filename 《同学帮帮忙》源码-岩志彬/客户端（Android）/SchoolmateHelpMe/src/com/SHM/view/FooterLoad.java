package com.SHM.view;


import com.SHM.activity.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class FooterLoad extends ListView implements OnScrollListener{
	
	View  footer;//�ײ�����
    int totalItemCount;//������
    int lastVisibleItem;//���һ���ɼ��item
    boolean isLoading;//���ڼ���
    loadListener listener;
    public FooterLoad(Context context) {
    	super(context);
		// TODO Auto-generated constructor stub
		initView(context);
     }

     public FooterLoad(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	    initView(context);
     }

     public FooterLoad(Context context, AttributeSet attrs, int defStyle) {
		 super(context, attrs, defStyle);
	    // TODO Auto-generated constructor stub
	    initView(context);
     }
	
 	/**
 	 * ��ӵײ����ز���
 	 * @param context
 	 */
     private void initView(Context context){
    	 LayoutInflater inflater =LayoutInflater.from(context);
    	 footer  = inflater.inflate(R.layout.footer_loading, null); 
    	 footer.findViewById(R.id.footerloadlayout).setVisibility(View.GONE);
    	 this.addFooterView(footer);
    	 this.setOnScrollListener(this);
     }

     @Override
     public void onScrollStateChanged(AbsListView view, int scrollState) {
    	 // TODO Auto-generated method stub
    	 if(totalItemCount == lastVisibleItem
    			 && scrollState==SCROLL_STATE_IDLE){
    		 if(!isLoading){
    			 isLoading =true;
    			 footer.findViewById(R.id.footerloadlayout).setVisibility(View.VISIBLE);
    			 //���ظ��
    			 listener.onLoad();
    		 }
    	 }
     }

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
		int visibleItemCount, int totalItemCount) {
	// TODO Auto-generated method stub
	    this.lastVisibleItem = firstVisibleItem + visibleItemCount;
	    this.totalItemCount = totalItemCount;
	}
	/**
	 * �������
	 */
	public void loadComplete(){
		isLoading = false;
		footer.findViewById(R.id.footerloadlayout).setVisibility(
				View.GONE);
	}

	public void setInterface(loadListener listener){
		this.listener=listener;
	}

	//���ظ����ݵĻص��ӿ�
	public interface loadListener{
	     public void onLoad();
	     
	}
}
