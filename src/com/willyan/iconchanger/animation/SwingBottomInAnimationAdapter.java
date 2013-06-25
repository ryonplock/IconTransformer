package com.willyan.iconchanger.animation;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * An implementation of the AnimationAdapter class which applies a
 * swing-in-from-bottom-animation to views.
 */
public class SwingBottomInAnimationAdapter extends AnimationAdapter {
	
	protected static final long DEFAULTANIMATIONDELAYMILLIS = 100;
	protected static final long DEFAULTANIMATIONDURATIONMILLIS = 300;

	private final long mAnimationDelayMillis;
	private final long mAnimationDurationMillis;

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter) {
		this(baseAdapter, DEFAULTANIMATIONDELAYMILLIS, DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter, long animationDelayMillis) {
		this(baseAdapter, animationDelayMillis, DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingBottomInAnimationAdapter(BaseAdapter baseAdapter, long animationDelayMillis, long animationDurationMillis) {
		super(baseAdapter);
		mAnimationDelayMillis = animationDelayMillis;
		mAnimationDurationMillis = animationDurationMillis;
	}

	@Override
	protected long getAnimationDelayMillis() {
		return mAnimationDelayMillis;
	}

	@Override
	protected long getAnimationDurationMillis() {
		return mAnimationDurationMillis;
	}
	
	@Override
	public Animator[] getAnimators(ViewGroup parent, View view) {
		Animator animator = ObjectAnimator.ofFloat(view, "translationY", 800, 0);
		return new Animator[] { animator };
	}

}
