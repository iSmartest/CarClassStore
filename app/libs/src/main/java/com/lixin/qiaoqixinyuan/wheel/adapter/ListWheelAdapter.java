/*
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.lixin.qiaoqixinyuan.wheel.adapter;

import android.content.Context;

import java.util.List;

/**
 * The simple Array wheel adapter
 */
public class ListWheelAdapter extends AbstractWheelTextAdapter {

    // items
    private List<String> list;

    /**
     * Constructor
     * @param context the current context
     * @param list the list
     */
    public ListWheelAdapter(Context context, List<String> list) {
        super(context);
        
        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.list = list;
    }
    
    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < list.size()) {
            String item = list.get(index);
            if (item instanceof CharSequence) {
                return (CharSequence) item;
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }
}
