package com.lixin.carclassstore.utils;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils
{
    public static void close(Closeable closeable)
    {
        if (closeable != null)
        {
            try
            {
                closeable.close();
            }
            catch (IOException e)
            {
                CommonLog.e(e);
            }
        }
    }

    public static void printf(InputStream in)
    {
        int length;
        byte[] b = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try
        {
            while ((length = in.read(b)) >= 0)
            {
                outputStream.write(b,0,length);
            }
            CommonLog.i("in:" + outputStream.toString());
        }
        catch (IOException e)
        {
            CommonLog.e(e);
        }
    }
}
