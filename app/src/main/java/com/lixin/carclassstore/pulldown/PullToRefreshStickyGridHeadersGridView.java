package com.lixin.carclassstore.pulldown;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.lixin.chepinstore.R;
import com.lixin.chepinstore.pulldown.stickygridheaders.StickyGridHeadersGridView;
import com.lixin.chepinstore.pulldown.stickygridheaders.StickyListHeadersListView;

public class PullToRefreshStickyGridHeadersGridView extends PullToRefreshBase<StickyGridHeadersGridView> implements
        AbsListView.OnScrollListener
{
    private int lastSavedFirstVisibleItem = -1;
    private AbsListView.OnScrollListener onScrollListener;
    private OnLastItemVisibleListener onLastItemVisibleListener;
    private View emptyView;
    private FrameLayout refreshableViewHolder;

    class InternalGridView extends StickyGridHeadersGridView implements EmptyViewMethodAccessor
    {

        public InternalGridView(Context context, AttributeSet attrs)
        {
            super(context, attrs);
        }

        @Override
        public void setEmptyView(View emptyView)
        {
            PullToRefreshStickyGridHeadersGridView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView)
        {
            super.setEmptyView(emptyView);
        }

        public ContextMenu.ContextMenuInfo getContextMenuInfo()
        {
            return super.getContextMenuInfo();
        }
    }

    public PullToRefreshStickyGridHeadersGridView(Context context)
    {
        super(context);
    }

    public PullToRefreshStickyGridHeadersGridView(Context context, int mode)
    {
        super(context, mode);
    }

    public PullToRefreshStickyGridHeadersGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected StickyGridHeadersGridView createRefreshableView(Context context, AttributeSet attrs)
    {
        StickyGridHeadersGridView gridView = new InternalGridView(context, attrs);
        gridView.setId(R.id.gridview);
        gridView.setSelector(android.R.color.transparent);
        return gridView;
    }

    @Override
    public ContextMenu.ContextMenuInfo getContextMenuInfo()
    {
        return ((InternalGridView) getRefreshableView()).getContextMenuInfo();
    }

    public final void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                               final int totalItemCount)
    {

        if (null != onLastItemVisibleListener)
        {
            // detect if last item is visible
            if (visibleItemCount > 0 && (firstVisibleItem + visibleItemCount == totalItemCount))
            {
                // only process first event
                if (firstVisibleItem != lastSavedFirstVisibleItem)
                {
                    lastSavedFirstVisibleItem = firstVisibleItem;
                    onLastItemVisibleListener.onLastItemVisible();
                }
            }
        }

        if (null != onScrollListener)
        {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public final void onScrollStateChanged(final AbsListView view, final int scrollState)
    {
        if (null != onScrollListener)
        {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    /**
     * Sets the Empty View to be used by the Adapter View.
     * <p/>
     * We need it handle it ourselves so that we can Pull-to-Refresh when the
     * Empty View is shown.
     * <p/>
     * Please note, you do <strong>not</strong> usually need to call this method
     * yourself. Calling setEmptyView on the AdapterView will automatically call
     * this method and set everything up. This includes when the Android
     * Framework automatically sets the Empty View based on it's ID.
     *
     * @param newEmptyView
     *         - Empty View to be used
     */
    public final void setEmptyView(View newEmptyView)
    {
        // If we already have an Empty View, remove it
        if (null != emptyView)
        {
            refreshableViewHolder.removeView(emptyView);
        }

        if (null != newEmptyView)
        {
            ViewParent newEmptyViewParent = newEmptyView.getParent();
            if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup)
            {
                ((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
            }

            this.refreshableViewHolder.addView(newEmptyView, ViewGroup.LayoutParams.MATCH_PARENT,
                                               ViewGroup.LayoutParams.MATCH_PARENT);
        }

        if (refreshableView instanceof EmptyViewMethodAccessor)
        {
            ((EmptyViewMethodAccessor) refreshableView).setEmptyViewInternal(newEmptyView);
        }
        else
        {
            this.refreshableView.setEmptyView(newEmptyView);
        }
    }

    public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener)
    {
        onLastItemVisibleListener = listener;
    }

    public final void setOnScrollListener(AbsListView.OnScrollListener listener)
    {
        onScrollListener = listener;
    }

    protected void addRefreshableView(Context context, StickyListHeadersListView refreshableView)
    {
        refreshableViewHolder = new FrameLayout(context);
        refreshableViewHolder.addView(refreshableView, ViewGroup.LayoutParams.MATCH_PARENT,
                                      ViewGroup.LayoutParams.MATCH_PARENT);
        addView(refreshableViewHolder, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f));
    }

    protected boolean isReadyForPullDown()
    {
        return isFirstItemVisible();
    }

    protected boolean isReadyForPullUp()
    {
        return isLastItemVisible();
    }

    private boolean isFirstItemVisible()
    {
        if (this.refreshableView.getCount() == 0)
        {
            return true;
        }
        else
        {
            if (refreshableView.getFirstVisiblePosition() == 0)
            {

                final View firstVisibleChild = refreshableView.getChildAt(0);

                if (firstVisibleChild != null)
                {
                    return firstVisibleChild.getTop() >= refreshableView.getTop();
                }
            }
        }

        return false;
    }

    private boolean isLastItemVisible()
    {
        final int count = this.refreshableView.getCount();
        final int lastVisiblePosition = refreshableView.getLastVisiblePosition();

        if (count == 0)
        {
            return true;
        }
        else
        {
            if (lastVisiblePosition == count - 1)
            {

                final int childIndex = lastVisiblePosition - refreshableView.getFirstVisiblePosition();
                final View lastVisibleChild = refreshableView.getChildAt(childIndex);

                if (lastVisibleChild != null)
                {
                    return lastVisibleChild.getBottom() <= refreshableView.getBottom();
                }
            }
        }

        return false;
    }
}