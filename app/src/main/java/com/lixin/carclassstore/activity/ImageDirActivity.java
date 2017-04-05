package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.ImageDirAdapter;
import com.lixin.carclassstore.bean.Constants;
import com.lixin.carclassstore.bean.Dir;
import com.lixin.carclassstore.bean.Photo;
import com.lixin.carclassstore.tools.ImageManager;

import java.util.ArrayList;


/**
 * 查看所有含有图片的目录。<br/>
 * <br/>
 * Created by yanglw on 2014/8/17.
 */
public class ImageDirActivity extends ImageBaseActivity implements LoaderManager.LoaderCallbacks<Cursor>
{
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_image_dir);
        setTitleText("选择目录");

        mListView = (ListView) findViewById(R.id.listview);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Dir dir = (Dir) parent.getItemAtPosition(position);
                if (dir != null)
                {
                    Intent intent = new Intent(getApplicationContext(), ImagesActivity.class);
                    intent.putExtra(ImagesActivity.ARG_DIR_ID, dir.id);
                    intent.putExtra(ImagesActivity.ARG_DIR_NAME, dir.name);
                    intent.putExtra(ARG_PHOTO_LIST, checkList);

                    startActivityForResult(intent, 1);
                }
            }
        });

        if (savedInstanceState == null)
        {
            ArrayList<Photo> list = getIntent().getParcelableArrayListExtra(ARG_PHOTO_LIST);
            if (list != null)
            {
                checkList.addAll(list);
            }
        }

        mListView.setOnScrollListener(ImageManager.scrollListener);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            finish();
        }
    }

    @Override
    public void finish()
    {
        Intent intent = new Intent();
        intent.putExtra(RES_PHOTO_LIST, checkList);
        setResult(Constants.OPERATION_SUCCESS, intent);

        super.finish();
        checkList.clear();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new CursorLoader(getApplicationContext(),
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                new String[]{
                                        "count(1) length",
                                        MediaStore.Images.Media.BUCKET_ID,
                                        MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                                        MediaStore.Images.Media.DATA
                                },
                                "1=1) GROUP BY " + MediaStore.Images.Media.BUCKET_ID + " -- (",
                                null,
                                MediaStore.Images.Media.BUCKET_DISPLAY_NAME + " ASC," +
                                        MediaStore.Images.Media.DATE_MODIFIED + " DESC"
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        if (cursor.getCount() > 0)
        {
            ArrayList<Dir> list = new ArrayList<Dir>();

            cursor.moveToPosition(-1);
            while (cursor.moveToNext())
            {
                int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID));
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                String dirPath;
                int index = path.lastIndexOf('/');
                if (index > 0)
                {
                    dirPath = path.substring(0, index);
                }
                else
                {
                    dirPath = path;
                }

                Dir dir = new Dir();
                dir.id = String.valueOf(id);
                dir.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                dir.text = dirPath;
                dir.path = path;
                dir.length = cursor.getInt(cursor.getColumnIndex("length"));
                list.add(dir);
            }

            ImageDirAdapter adapter = new ImageDirAdapter(getApplicationContext(), list);
            mListView.setAdapter(adapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }
}
