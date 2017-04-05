package com.lixin.carclassstore.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.bean.Photo;
import com.lixin.carclassstore.tools.ImageManager;
import com.lixin.carclassstore.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;


public class AddPhotoAdapter extends MyBaseAdapter<Photo> {
    private ArrayList<Photo> mChoiceList;

    public AddPhotoAdapter(Context context, List<Photo> list, ArrayList<Photo> choiceList) {
        super(context, list);
        mChoiceList = choiceList;
        if (mChoiceList == null) {
            mChoiceList = new ArrayList<Photo>();
        }
    }

    public ArrayList<Photo> getChoiceList() {
        return mChoiceList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_add_photo, parent, false);
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.item_add_photo_iv_img);
            holder.choice = (ImageView) convertView.findViewById(R.id.item_add_photo_tv_choice);
            convertView.setTag(R.id.CHOICE, holder.choice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Photo photo = mList.get(position);
        if (mChoiceList.contains(photo)) {
            holder.choice.setBackgroundResource(R.drawable.umeng_update_btn_check_on_holo_light);
            photo.isChoice = true;
        } else {
            holder.choice.setBackgroundResource(R.drawable.umeng_update_btn_check_off_holo_light);
            photo.isChoice = false;
        }

        ImageManager.imageLoader.displayImage(photo.path, holder.img, ImageManager.options);
        return convertView;
    }

    public void setCheck(int position, View view) {
        Photo photo = mList.get(position);

        boolean checked = mChoiceList.contains(photo);

        ViewHolder holder = (ViewHolder) view.getTag();

        if (checked) {
            mChoiceList.remove(photo);
            holder.choice.setBackgroundResource(R.drawable.umeng_update_btn_check_off_holo_light);
        } else {
            if (mChoiceList.size() < 9) {
                mChoiceList.add(photo);
                holder.choice.setBackgroundResource(R.drawable.umeng_update_btn_check_on_holo_light);
            }else{
                ToastUtils.showMessageShort(mContext,R.string.only_can_chose_nine);
                holder.choice.setBackgroundResource(R.drawable.umeng_update_btn_check_off_holo_light);
            }
        }
    }

    class ViewHolder {
        public ImageView img, choice;
    }
}
