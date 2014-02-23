package com.example.animationtest01;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class Rotation3D extends Activity {
  private boolean isFront = true;
  private int DURATION = 100;
  private ViewGroup mContainer;
  private ImageView frontView, backView;
  private float centerX;
  private float centerY;

  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    setContentView(R.layout.main);
    this.mContainer = (ViewGroup) findViewById(R.id.container);
    this.frontView = (ImageView) findViewById(R.id.front);
    this.backView = (ImageView) findViewById(R.id.back);
    this.mContainer.setOnTouchListener(new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN)
			{
				if (isFront) {
			        applyRotation(0f, 180f, 360f, 0f);
			      } else {
			        applyRotation(360f, 180f, 0f, 0f);
			      }
			}
			return false;
		}
	});
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    super.onKeyDown(keyCode, event);
    switch (keyCode) {
    case KeyEvent.KEYCODE_SPACE:
      if (isFront) {
        applyRotation(0f, 90f, 180f, 0f);
      } else {
        applyRotation(180f, 270f, 360f, 0f);
      }
      break;
    }
    return false;
  }

  /* start�뗣굢end�얇겎Y邕멨썮邕㏂걲��*/
  private void applyRotation(float start, float mid, float end, float depth) {
    this.centerX = mContainer.getWidth() / 2.0f;
    this.centerY = mContainer.getHeight() / 2.0f;

    Rotate3dAnimation rot = new Rotate3dAnimation(start, mid, centerX, centerY,
        depth, true);
    rot.setDuration(DURATION);
    // rot.setInterpolator(new AccelerateInterpolator());
    rot.setAnimationListener(new DisplayNextView(mid, end, depth));
    mContainer.startAnimation(rot);
  }

  private class DisplayNextView implements AnimationListener {
    private float mid;
    private float end;
    private float depth;

    public DisplayNextView(float mid, float end, float depth) {
      this.mid = mid;
      this.end = end;
      this.depth = depth;
    }
	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		mContainer.post(new Runnable() {
	        public void run() {
	          if (isFront) {
	            frontView.setVisibility(View.GONE);
	            backView.setVisibility(View.VISIBLE);
	            isFront = false;
	          } else {
	            frontView.setVisibility(View.VISIBLE);
	            backView.setVisibility(View.GONE);
	            isFront = true;
	          }

	          Rotate3dAnimation rot = new Rotate3dAnimation(mid, end, centerX,
	              centerY, depth, false);
	          rot.setDuration(DURATION);
	          rot.setInterpolator(new AccelerateInterpolator());
	          mContainer.startAnimation(rot);
	        }
	      });
	}
	@Override
	public void onAnimationRepeat(Animation animation) {
	}
	@Override
	public void onAnimationStart(Animation animation) {		
	}
  }
}