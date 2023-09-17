package com.match.onlinechat.model.adapter.message;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.match.onlinechat.R;
import com.match.onlinechat.model.basic.chat.message.ChatMessage;
import com.match.onlinechat.model.basic.user.User;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private User user;
    private Context context;
    //暂时保存聊天数据
    private List<ChatMessage> chatMessages;
    private RelativeLayout.LayoutParams params;

    public MessageAdapter(Context context, List<ChatMessage> chatMessages){
        this.context = context;
        this.user = new User();
        this.chatMessages = chatMessages;
    }

    @Override
    public int getCount() {
        return chatMessages.size();
    }

    @Override
    public Object getItem(int i) {
        return chatMessages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //设置为不可点击
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String userInfo;
        int minHeight = 20;
        int minWidth = 18;
        boolean isViewNull = false;
        ViewHolder viewHolder = new ViewHolder();
        ChatMessage chatMessage = chatMessages.get(i);

        if(view != null) {
            viewHolder = (ViewHolder) view.getTag();
        }

        if(view == null){
            view = View.inflate(this.context, R.layout.item, null);
            isViewNull = true;

            if(chatMessage.getUserName() == null && chatMessage.getMessage() != null) {
                try {
                    viewHolder.tv_systemMessage = view.findViewById(R.id.system_message);
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }else if(chatMessage.getUserName().equals("MY")){
                viewHolder.tv_myInfo = view.findViewById(R.id.my_info);
                viewHolder.tv_myMessage = view.findViewById(R.id.my_message);
            }else{
                viewHolder.tv_otherUserInfo = view.findViewById(R.id.other_user_info);
                viewHolder.tv_otherMessage = view.findViewById(R.id.other_message);
            }
        }

        //判断为显示系统消息
        if(chatMessage.getUserName() == null && chatMessage.getMessage() != null){
            try {
                viewHolder.tv_systemMessage.setText(chatMessage.getMessage());//设置消息
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }else {

            if(chatMessage.getUserName().equals("MY")){

                userInfo = String.valueOf(user.getId())+'\n'+user.getName();

                params = (RelativeLayout.LayoutParams) viewHolder.tv_myMessage.getLayoutParams();
                params.addRule(RelativeLayout.LEFT_OF, viewHolder.tv_myInfo.getId());
                viewHolder.tv_myMessage.setLayoutParams(params);

                viewHolder.tv_myInfo.setText(userInfo);
                viewHolder.tv_myMessage.setPadding(10, 10, 10, 10);
                viewHolder.tv_myMessage.setMinHeight(minHeight);
                viewHolder.tv_myMessage.setMinWidth(minWidth);
                viewHolder.tv_myMessage.setText(chatMessage.getMessage());


            }else{

                userInfo = String.valueOf(chatMessage.getUserId())+'\n'+chatMessage.getUserName();

                params = (RelativeLayout.LayoutParams) viewHolder.tv_otherMessage.getLayoutParams();
                params.addRule(RelativeLayout.RIGHT_OF, viewHolder.tv_otherUserInfo.getId());
                viewHolder.tv_otherMessage.setLayoutParams(params);

                viewHolder.tv_otherUserInfo.setText(userInfo);
                viewHolder.tv_otherMessage.setPadding(10, 10, 10, 10);
                viewHolder.tv_otherMessage.setMinHeight(minHeight);
                viewHolder.tv_otherMessage.setMinWidth(minWidth);
                viewHolder.tv_otherMessage.setText(chatMessage.getMessage());


            }
        }
        if(isViewNull) view.setTag(viewHolder);
        return view;
    }

    //可以对listview复用减少资源的使用提示应用性能
    private class ViewHolder{
        TextView tv_myInfo;
        TextView tv_otherUserInfo;
        TextView tv_myMessage;
        TextView tv_otherMessage;
        TextView tv_systemMessage;
    }
}