package me.zhizhi.adapter;

import java.util.List;

import me.zhizhi.adapter.row.ClassesRow;
import me.zhizhi.db.entity.Classes;
import me.zhizhi.utils.CollectionUtils;
import android.view.View;
import android.view.ViewGroup;

public class ClassesAdapter extends AbstractAdapter<Classes> {

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ClassesRow.newView(mContext, mHead);
        }
        ClassesRow.bindView(convertView, mList.get(position), mDatabaseHelper);

        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public void clearItem() {
        mList.clear();
    }

    @Override
    public void addItem(Classes t) {
        mList.add(t);
    }

    @Override
    public void addItem(List<Classes> list) {
        mList.addAll(list);
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(mList);
    }

}
