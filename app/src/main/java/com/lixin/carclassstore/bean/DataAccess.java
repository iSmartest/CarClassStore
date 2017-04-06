package com.lixin.carclassstore.bean;

import android.content.Context;

import com.lixin.carclassstore.utils.CommonLog;
import com.lixin.carclassstore.utils.ConstantUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by 小火
 * Create time on  2017/4/6
 * My mailbox is 1403241630@qq.com
 */

public class DataAccess {
    Context context;
    public static XmlPullParser getXmlPullParser(InputStream in) {
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance()
                    .newPullParser();
            parser.setInput(in, ConstantUtil.ENCODE);
            return parser;
        } catch (XmlPullParserException e) {
            return null;
        }
    }

    /**
     * 设置登录名这样的操作结果返回
     *
     * @param in
     * @return struct.what=0 操作成功
     */
    public static Struct parseOperateResult(InputStream in) {
        try {
            XmlPullParser parser = getXmlPullParser(in);
            if (parser == null) {
                return null;
            }

            int eventType = parser.getEventType();
            Struct struct = new Struct();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("Type".equals(nodeName)) {
                            try {
                                struct.what = Integer.parseInt(safeNextText(parser));
                            } catch (Exception e) {
                                struct.what = 1;
                                CommonLog.e(e);
                            }

                            break;
                        }
                        if ("Value".equals(nodeName)) {
                            struct.obj = safeNextText(parser);
                            break;
                        }
                        if ("Key".equals(nodeName)) {
                            struct.obj2 = safeNextText(parser);
                            break;
                        }
                        break;

                }

                eventType = parser.next();
            }

            return struct;
        } catch (Exception e) {
            CommonLog.e(e);
            return null;
        }
    }
    public static String safeNextText(XmlPullParser parser)
            throws XmlPullParserException, IOException {
        String result = parser.nextText();
        if (parser.getEventType() != XmlPullParser.END_TAG) {
            parser.nextTag();
        }
        return result;
    }
}
