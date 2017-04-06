package com.lixin.carclassstore.pulldown;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lixin.chepinstore.R;


@SuppressLint("ViewConstructor")
public class LoadingLayout extends FrameLayout
{

	private final ImageView headerImage;
	private final ProgressBar headerProgress;
	private final TextView headerText;
	private final LinearLayout linearlayout_refresh;

	private String pullLabel;
	private String refreshingLabel;
	private String releaseLabel;

	AnimationDrawable someAnimation;
	public LoadingLayout(Context context, final int mode, String releaseLabel, String pullLabel, String refreshingLabel)
	{
		super(context);
		ViewGroup header = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.pull_to_refresh_header, this);

		headerText = (TextView) header.findViewById(R.id.pull_to_refresh_text);
		headerImage = (ImageView) header.findViewById(R.id.pull_to_refresh_image);
		headerProgress = (ProgressBar) header.findViewById(R.id.pull_to_refresh_progress);
		linearlayout_refresh = (LinearLayout) header.findViewById(R.id.linearlayout_refresh);
		this.releaseLabel = releaseLabel;
		this.pullLabel = pullLabel;
		this.refreshingLabel = refreshingLabel;

		switch (mode)
		{
			case PullToRefreshBase.MODE_PULL_UP_TO_REFRESH:
//				headerImage.setBackgroundResource(R.drawable.image);
				break;
			case PullToRefreshBase.MODE_PULL_DOWN_TO_REFRESH:
			default:
//				headerImage.setBackgroundResource(R.drawable.image);
				break;
		}
	}

	public void reset()
	{
		someAnimation = (AnimationDrawable) headerImage.getBackground();
		someAnimation.start();
		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
	}

	public void releaseToRefresh()
	{
		headerImage.clearAnimation();
	}

	public void setPullLabel(String pullLabel)
	{
		this.pullLabel = pullLabel;
	}

	public void refreshing()
	{
		headerImage.clearAnimation();
		someAnimation = (AnimationDrawable) headerImage.getBackground();
		someAnimation.start();
		headerImage.setVisibility(View.VISIBLE);
		headerProgress.setVisibility(View.GONE);
	}

	public void setRefreshingLabel(String refreshingLabel)
	{
		this.refreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(String releaseLabel)
	{
		this.releaseLabel = releaseLabel;
	}

	public void pullToRefresh()
	{

		headerImage.clearAnimation();

	}

	public void setTextColor(int color)
	{
//		headerText.setTextColor(color);
	}

}
