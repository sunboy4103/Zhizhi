package me.zhizhi.adapter;

import java.util.List;

import me.zhizhi.db.entity.Curriculums;
import me.zhizhi.utils.CollectionUtils;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class CurriculumsAdapter extends AbstractAdapter<Curriculums> {

    private Context mContext;

    public CurriculumsAdapter(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //        if (convertView == null) {
        //            convertView = ClassesRow.newView(mContext);
        //        }
        //        ClassesRow.bindView(mContext, convertView, mList.get(position), mDatabaseHelper, position,
        //                false, true);

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
    public void addItem(Curriculums t) {
        mList.add(t);
    }

    @Override
    public void addItem(List<Curriculums> list) {
        mList.addAll(list);
    }

    @Override
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(mList);
    }

}
