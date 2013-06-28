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
public class SwingInAnimationAdapter extends AnimationAdapter {
	
	protected static final long DEFAULTANIMATIONDELAYMILLIS = 100;
	protected static final long DEFAULTANIMATIONDURATIONMILLIS = 300;

	private final long mAnimationDelayMillis;
	private final long mAnimationDurationMillis;

	public SwingInAnimationAdapter(BaseAdapter baseAdapter) {
		this(baseAdapter, DEFAULTANIMATIONDELAYMILLIS, DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingInAnimationAdapter(BaseAdapter baseAdapter, long animationDelayMillis) {
		this(baseAdapter, animationDelayMillis, DEFAULTANIMATIONDURATIONMILLIS);
	}

	public SwingInAnimationAdapter(BaseAdapter baseAdapter, long animationDelayMillis, long animationDurationMillis) {
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
		// Swing Bottom In
		Animator animator = ObjectAnimator.ofFloat(view, "translationY", 800, 0);
		// Swing Left In
		// Animator animator = ObjectAnimator.ofFloat(view, "translationX", 0 - parent.getWidth(), 0)
		// Swing Right In
		// Animator animator = ObjectAnimator.ofFloat(view, "translationX", parent.getWidth(), 0);
		return new Animator[] { animator };
	}

}
