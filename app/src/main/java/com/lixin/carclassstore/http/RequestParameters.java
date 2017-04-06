package com.lixin.carclassstore.http;

import android.text.TextUtils;


import com.lixin.carclassstore.utils.CommonLog;

import java.util.ArrayList;


public class RequestParameters
{
    private ArrayList<String> mKeys = new ArrayList<String>();
    private ArrayList<Object> mValues = new ArrayList<Object>();

    public synchronized void add(String key, Object value)
    {
        if (!TextUtils.isEmpty(key))
        {
            if (value == null)
            {
                value = "";
            }

            if (contains(key))
            {
                int index = mKeys.indexOf(key);
                mValues.set(index, value);
            }
            else
            {
                mKeys.add(key);
                mValues.add(value);
            }
        }
    }

    public synchronized void remove(String key)
    {
        int firstIndex = mKeys.indexOf(key);
        if (firstIndex >= 0)
        {
            mKeys.remove(firstIndex);
            mValues.remove(firstIndex);
        }
    }

    public synchronized void removeAt(int i)
    {
        if (i < mKeys.size() && i >= 0)
        {
            mKeys.remove(i);
            mValues.remove(i);
        }
    }

    private synchronized int getLocation(String key)
    {
        return mKeys.indexOf(key);
    }

    public synchronized String getKey(int location)
    {
        if (location >= 0 && location < mKeys.size())
        {
            return mKeys.get(location);
        }
        return null;
    }

    public synchronized Object getValue(String key)
    {
        int index = getLocation(key);
        if (index >= 0 && index < mKeys.size())
        {
            return mValues.get(index);
        }
        else
        {
            return null;
        }
    }

    public synchronized Object getValue(int location)
    {
        if (location >= 0 && location < mKeys.size())
        {
            return mValues.get(location);
        }
        else
        {
            return null;
        }
    }

    public synchronized int size()
    {
        return mKeys.size();
    }

    public synchronized boolean contains(String key)
    {
        return mKeys.contains(key);
    }

    public synchronized void addAll(RequestParameters parameters)
    {
        for (int i = 0; i < parameters.size(); i++)
        {
            add(parameters.getKey(i), parameters.getValue(i));
        }
    }

    public synchronized void clear()
    {
        mKeys.clear();
        mValues.clear();
    }

    public synchronized void printf()
    {
        StringBuilder sb = new StringBuilder();
        for (String key :mKeys)
        {
            sb.append(key).append('=').append(getValue(key)).append('&');
        }
        if (sb.length() >0)
        {
            sb.deleteCharAt(sb.length() - 1);
        }
        CommonLog.i(sb);
    }
}
