package com.example.se_2019.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.se_2019.Post;
import com.example.se_2019.R;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter{
    Context context;
    ArrayList<Post> list_itemArrayList;

    TextView name_textView;
    TextView title_textView;
    TextView date_textView;
    TextView content_textView;
    ImageView profile_imageView;

    public MyListAdapter(Context context, ArrayList<Post> list_itemArrayList) {
        this.context = context;
        this.list_itemArrayList = list_itemArrayList;
    }

    @Override
    public int getCount() {
        return this.list_itemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list_itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_my_list_adapter, null);
            name_textView = (TextView)convertView.findViewById(R.id.name_textView);
            content_textView = (TextView)convertView.findViewById(R.id.content_textView);
            date_textView = (TextView)convertView.findViewById(R.id.date_textView);
            title_textView  =(TextView)convertView.findViewById(R.id.title_textView);
            profile_imageView = (ImageView)convertView.findViewById(R.id.profile_imageView);
        }

        name_textView.setText(list_itemArrayList.get(position).getName());
        title_textView.setText(list_itemArrayList.get(position).getTitle());
        content_textView.setText(list_itemArrayList.get(position).getContent());
        date_textView.setText(list_itemArrayList.get(position).getWrite_date().toString());
        profile_imageView.setImageResource(list_itemArrayList.get(position).getProfile_image());
        return convertView;
    }
}
