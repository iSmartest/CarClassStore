package com.lixin.carclassstore.pulldown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.ListView;

public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {
	class InternalListView extends ListView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshListView.this.setEmptyView(emptyView);
		}

		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public int getRefreshType() {
		return getCurrentMode();
	}

	public PullToRefreshListView(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}

	@Override
	protected final ListView createRefreshableView(Context context,
												   AttributeSet attrs) {
		ListView lv = new InternalListView(context, attrs);
		lv.setId(android.R.id.list);

		lv.setSelector(android.R.color.transparent);
		lv.setCacheColorHint(getResources().getColor(android.R.color.transparent));
		lv.setFadingEdgeLength(0);

		return lv;
	}
}
