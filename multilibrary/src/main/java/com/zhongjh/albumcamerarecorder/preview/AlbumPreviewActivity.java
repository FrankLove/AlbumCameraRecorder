/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhongjh.albumcamerarecorder.preview;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;


import com.zhongjh.albumcamerarecorder.album.entity.Album;
import com.zhongjh.albumcamerarecorder.album.model.AlbumMediaCollection;
import com.zhongjh.albumcamerarecorder.album.model.SelectedItemCollection;
import com.zhongjh.albumcamerarecorder.album.widget.CheckView;
import com.zhongjh.albumcamerarecorder.preview.adapter.PreviewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import gaode.zhongjh.com.common.entity.MultiMedia;

/**
 * 点击相册图片或者视频进来的
 */
public class AlbumPreviewActivity extends BasePreviewActivity implements
        AlbumMediaCollection.AlbumMediaCallbacks {

    public static final String EXTRA_ALBUM = "extra_album";
    public static final String EXTRA_ITEM = "extra_item";

    private AlbumMediaCollection mCollection = new AlbumMediaCollection();

    private boolean mIsAlreadySetPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCollection.onCreate(this, this);
        Album album = getIntent().getParcelableExtra(EXTRA_ALBUM);
        if (album != null) {
            mCollection.load(album);
        } else {
            Bundle bundle = getIntent().getBundleExtra(EXTRA_DEFAULT_BUNDLE);
            ArrayList<MultiMedia> items = bundle.getParcelableArrayList(SelectedItemCollection.STATE_SELECTION);
            initItems(items);
        }

        MultiMedia item = getIntent().getParcelableExtra(EXTRA_ITEM);
        if (mAlbumSpec.countable) {
            mViewHolder.check_view.setCheckedNum(mSelectedCollection.checkedNumOf(item));
        } else {
            mViewHolder.check_view.setChecked(mSelectedCollection.isSelected(item));
        }
        updateSize(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCollection.onDestroy();
    }

    @Override
    public void onAlbumMediaLoad(Cursor cursor) {
        List<MultiMedia> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            items.add(MultiMedia.valueOf(cursor));
        }

        if (items.isEmpty()) {
            return;
        }

        initItems(items);
    }

    private void initItems(List<MultiMedia> items) {
        PreviewPagerAdapter adapter = (PreviewPagerAdapter) mViewHolder.pager.getAdapter();
        adapter.addAll(items);
        adapter.notifyDataSetChanged();
        if (!mIsAlreadySetPosition) {
            //onAlbumMediaLoad is called many times..
            mIsAlreadySetPosition = true;
            MultiMedia selected = getIntent().getParcelableExtra(EXTRA_ITEM);
            int selectedIndex = checkedNumOf(items, selected);
            mViewHolder.pager.setCurrentItem(selectedIndex, false);
            mPreviousPos = selectedIndex;
        }
    }

    /**
     * 获取相同数据的索引
     * @param items 数据列表
     * @param item 当前数据
     * @return 索引
     */
    public int checkedNumOf(List<MultiMedia> items, MultiMedia item) {
        int index = -1;
        if (item.getMediaUri() != null)
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getMediaUri().equals(item.getMediaUri())) {
                    index = i;
                    break;
                }
            }
        else if (item.getUri() != null)
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getUri().equals(item.getUri())) {
                    index = i;
                    break;
                }
            }
        else if (item.getDrawableId() != -1)
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getDrawableId() == item.getDrawableId()) {
                    index = i;
                    break;
                }
            }
        // 如果选择的为 -1 就是未选状态，否则选择基础数量+1
        return index == -1 ? CheckView.UNCHECKED : index;
    }

    @Override
    public void onAlbumMediaReset() {

    }
}
