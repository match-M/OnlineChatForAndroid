package com.match.onlinechat.model.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.match.onlinechat.R;
import com.match.onlinechat.model.adapter.message.MessageAdapter;

import java.util.List;

public class ChatRoomListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> chatRoomList;

    public ChatRoomListAdapter(@NonNull Context context, int resource, @NonNull List<String> chatRoomList) {
        super(context, resource, chatRoomList);
        this.context = context;
        this.chatRoomList = chatRoomList;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public String getItem(int i) {
        return super.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        return super.getItemId(i);
    }

    @NonNull
    @Override
    public View getView(int i, View view, @NonNull ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if(view != null) {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_room_list_item, viewGroup, false);

            viewHolder = new ViewHolder();
            viewHolder.tv_chatRoomName = view.findViewById(R.id.chat_room_name);
            view.setTag(viewHolder);

        }

        String name = chatRoomList.get(i);
        viewHolder.tv_chatRoomName.setText(name);

        return view;
    }

    private class ViewHolder{
        TextView tv_chatRoomName;
    }
}
