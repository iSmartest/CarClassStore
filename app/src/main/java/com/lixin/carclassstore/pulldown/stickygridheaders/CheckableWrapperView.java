package com.lixin.carclassstore.pulldown.stickygridheaders;

import android.content.Context;
import android.widget.Checkable;

/**
 * A WrapperView that implements the checkable interface
 * 
 * @author Emil Sj枚lander
 */
class CheckableWrapperView extends WrapperView implements Checkable {

	public CheckableWrapperView(final Context context) {
		super(context);
	}

	@Override
	public boolean isChecked() {
		return ((Checkable) mItem).isChecked();
	}

	@Override
	public void setChecked(final boolean checked) {
		((Checkable) mItem).setChecked(checked);
	}

	@Override
	public void toggle() {
		setChecked(!isChecked());
	}
}
