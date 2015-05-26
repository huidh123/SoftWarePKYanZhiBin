package com.SHM.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

public class SatelliteMenu extends ViewGroup implements OnClickListener {
	
	private View mainButton;
	private int radius;
	private boolean flag = false;
	private OnMenuItemClickListener onMenuItemClickListener;
	
	public SatelliteMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		radius = 280;
	}

	public SatelliteMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SatelliteMenu(Context context) {
		this(context, null);
	}
	
	public interface OnMenuItemClickListener{
		void onClick(View view,int pos);
	}
	
	public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener){
		this.onMenuItemClickListener = onMenuItemClickListener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		int count = getChildCount();
		
		for(int i = 0;i < count;i++){
			measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		if(changed){
			layoutMainButton();
			
			 int count = getChildCount();
			 
			 for(int i = 0;i < count - 1;i++){
				 View child = getChildAt(i + 1);
				 
				 child.setVisibility(View.GONE);
				 
				 int left = (int) (radius * Math.sin(Math.PI/2/(count - 2) * i));
				 int top = getMeasuredHeight() - (int) (radius * Math.cos(Math.PI/2/(count - 2) * i));
				 
				 child.layout(getMeasuredWidth() - left - child.getMeasuredWidth(), top - child.getMeasuredHeight(), getMeasuredWidth() - left, top);
			 }
		}
	}

	private void layoutMainButton() {
		
		mainButton = getChildAt(0);
		mainButton.setOnClickListener(this);
		
		int width = mainButton.getMeasuredWidth();
		int heigh = mainButton.getMeasuredHeight();
		
		mainButton.layout(getMeasuredWidth() - width, getMeasuredHeight() - heigh, getMeasuredWidth(), getMeasuredHeight());
	}

	@Override
	public void onClick(View v) {
		
//		mainButton = findViewById(R.id.main_button);
//		if(mainButton == null){
//			mainButton = getChildAt(0);
//		}
		
		rotateMainButton(v , 0f , 360f , 300);
		
		toggleMenu(300);
		
	}

	private void toggleMenu(int duration) {
		
		int count = getChildCount();
		
		for(int i = 0;i < count - 1;i++){
			final View childView = getChildAt(i + 1);
			childView.setVisibility(View.VISIBLE);
			
			int left = (int) (radius * Math.sin(Math.PI/2/(count - 2) * i));
			int top = (int) (radius * Math.cos(Math.PI/2/(count - 2) * i));
			
			AnimationSet animSet = new AnimationSet(true);
			Animation transAnima = null;
			
			if(flag == false){
				transAnima = new TranslateAnimation(0+left,0,0+top,0);
				childView.setClickable(true);
				childView.setFocusable(true);
			}else{
				transAnima = new TranslateAnimation(0,0+left,0,0+top);
				childView.setClickable(false);
				childView.setFocusable(false);
			}
			
			transAnima.setFillAfter(true);
			transAnima.setDuration(duration);
			transAnima.setStartOffset((i * 100) / count);
			
			transAnima.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					if(flag == false){
						childView.setVisibility(View.GONE);
					}
				}
			});
			
			RotateAnimation rotateAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnim.setDuration(duration);
			rotateAnim.setFillAfter(true);
			
			animSet.addAnimation(rotateAnim);
			animSet.addAnimation(transAnima);
			childView.startAnimation(animSet);
			
			final int pos = i + 1;
			
			childView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					if(onMenuItemClickListener != null){
						onMenuItemClickListener.onClick(childView, pos);
					}
					
					menuItemAnim(pos - 1);
					
					if(flag == false){
						flag = true;
					}else {
						flag = false;
					}
				}
			});
		}
		
		if(flag == false){
			flag = true;
		}else {
			flag = false;
		}
		
	}
	
	private void menuItemAnim(int pos) {
		// TODO Auto-generated method stub
		for(int i = 0;i < getChildCount() - 1;i++){
			View childView = getChildAt(i + 1);
			
			if(i == pos){
				childView.startAnimation(scaleBigAnim(300));
			}else {
				childView.startAnimation(scaleSmallAnim(300));
			}
			
			childView.setClickable(false);
			childView.setFocusable(false);
		}
	}

	private Animation scaleBigAnim(int duration) {
		// TODO Auto-generated method stub
		AnimationSet animationSet = new AnimationSet(true);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
		
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		
		return animationSet;
	}
	
	private Animation scaleSmallAnim(int duration) {
		// TODO Auto-generated method stub
		
		AnimationSet animationSet = new AnimationSet(true);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0.0f);
		
		animationSet.addAnimation(scaleAnimation);
		animationSet.addAnimation(alphaAnimation);
		
		animationSet.setDuration(duration);
		animationSet.setFillAfter(true);
		
		return animationSet;
	}

	private void rotateMainButton(View v, float start, float end, int duration) {
		
		RotateAnimation anim = new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		
		anim.setDuration(duration);
		anim.setFillAfter(true);
		v.startAnimation(anim);
	}
}
