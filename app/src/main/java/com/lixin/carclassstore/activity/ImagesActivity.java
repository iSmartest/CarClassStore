package com.lixin.carclassstore.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;


import com.lixin.carclassstore.R;
import com.lixin.carclassstore.adapter.AddPhotoAdapter;
import com.lixin.carclassstore.bean.Photo;
import com.lixin.carclassstore.tools.ImageManager;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.util.ArrayList;


/**
 * 选择某一个目录下的所有图片。
 */
public class ImagesActivity extends ImageBaseActivity implements LoaderManager.LoaderCallbacks<Cursor>
,View.OnClickListener{
    public static final String ARG_DIR_ID = "my.android.app.chooseimages.DIR_ID";
    public static final String ARG_DIR_NAME = "my.android.app.chooseimages.DIR_NAME";

    /** 当前目录的id */
    private String mDirId;

    private GridView mGridView;
    private TextView mNumTextView, mSubmitTextView;
    private Button mPreviewButton;
    private View mAccomplishView;

    private AddPhotoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_add_photo);

        mPreviewButton = (Button) findViewById(R.id.activity_add_photo_btn_preview);
        mPreviewButton.setOnClickListener(this);
        mAccomplishView = findViewById(R.id.activity_add_photo_rl_accomplish);
        mAccomplishView.setOnClickListener(this);

        mGridView = (GridView) findViewById(R.id.activity_add_photo_gv_gv);
        mNumTextView = (TextView) findViewById(R.id.activity_add_photo_tv_num);
        mSubmitTextView = (TextView) findViewById(R.id.activity_add_photo_tv_sure);

        mGridView.setOnScrollListener(new PauseOnScrollListener(ImageManager.imageLoader, true, true));
        mGridView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Photo photo = (Photo) parent.getItemAtPosition(position);
                if (photo == null)
                {
                    return;
                }
                mAdapter.setCheck(position, view);

                computChoice();
            }

        });

        computChoice();

        Intent intent = getIntent();
        mDirId = intent.getStringExtra(ARG_DIR_ID);

        String title = intent.getStringExtra(ARG_DIR_NAME);
        if (TextUtils.isEmpty(title))
        {
            setTitleText("图片");
        }
        else
        {
            if (title.length() > 6)
            {
                title = title.substring(0, 6) + "…";
            }
            setTitleText(title);
        }
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            setResult(RESULT_OK);
            finish();
            return;
        }
        if (resultCode == RESULT_CHANGE)
        {
            mAdapter.notifyDataSetChanged();
            computChoice();
        }
    }

    @Override
    public void onClick(View v)
    {
        final int id = v.getId();
        switch (id)
        {
            case R.id.activity_add_photo_btn_preview:
                preview();
                break;
            case R.id.activity_add_photo_rl_accomplish:
                accomplish();
                break;
            default:

                break;
        }

    }

    /**
     * 完成按钮的点击事件
     */
    private void accomplish()
    {
        setResult(RESULT_OK);
        finish();
    }

    /**
     * 预览按钮的点击事件
     */
    private void preview()
    {
        startActivityForResult(new Intent(getApplicationContext(), PreviewPhotoActivity.class), 1);
    }

    /**
     * 计算当前选择的个数，并设置按钮是否可点击
     */
    private void computChoice()
    {
        int choiceNum = checkList.size();

        if (choiceNum > 0)
        {
            mNumTextView.setVisibility(View.VISIBLE);
            mNumTextView.setText(String.valueOf(choiceNum));
            mPreviewButton.setEnabled(true);
            mAccomplishView.setEnabled(true);
            mSubmitTextView.setEnabled(true);
        }
        else
        {
            mNumTextView.setVisibility(View.INVISIBLE);
            mPreviewButton.setEnabled(false);
            mAccomplishView.setEnabled(false);
            mSubmitTextView.setEnabled(false);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return
                new CursorLoader(
                        getApplicationContext(),
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{
                                MediaStore.Images.Media.DATA//图片地址
                        },
                        mDirId == null ? null : MediaStore.Images.Media.BUCKET_ID + "=" + mDirId,
                        null,
                        MediaStore.Images.Media.DATE_MODIFIED + " DESC"
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        if (cursor.getCount() > 0)
        {
            ArrayList<Photo> list = new ArrayList<Photo>();

            cursor.moveToPosition(-1);
            while (cursor.moveToNext())
            {
                Photo photo = new Photo();

                photo.path = "file:///" + cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                list.add(photo);
            }

            mAdapter = new AddPhotoAdapter(getApplicationContext(), list, checkList);
            mGridView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }
}
