package com.match.onlinechat.model.basic.chat;

import com.match.onlinechat.activity.IHallActivity;
import com.match.onlinechat.controller.ControllerHall;
import com.match.onlinechat.model.basic.chat.message.ChatMessage;
import com.match.onlinechat.model.basic.constants.SearchChatRoomPrompt;
import com.match.onlinechat.model.basic.user.SaveUserInfo;
import com.match.onlinechat.model.basic.user.User;
import com.match.onlinechat.model.basic.hall.Hall;
import com.match.onlinechat.model.basic.tools.ParsingTools;

import java.util.ArrayList;
import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    public static List<ChatMessage> chatMessages = new ArrayList<>();
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {

        ParsingTools parsingTools = new ParsingTools(msg);
        String mode = parsingTools.getString("mode");

        User user = new User();
        ChatMessage chatMessage;
        Hall hall = ControllerHall.hall;
        SaveUserInfo saveUserInfo = new SaveUserInfo();
        ControllerHall controllerHall = IHallActivity.controllerHall;

        System.out.println(msg);

        switch (mode) {
            case "getRoomList" :{
                hall.setChatRoom(msg);
                controllerHall.showChatRoomList();
                break;
            }
            case "search":{
                String result = parsingTools.getString("result");
                hall.setSearchResult(result);
                if(result.equals(SearchChatRoomPrompt.CHATROOM_NOT_FOUND)){
                    controllerHall.setNotFoundResult();
                    return;
                }
                controllerHall.setSearchResult();
                break;
            }
            case "register" :{
                int id = (int) parsingTools.get("id");
                user.setId(id);
                controllerHall.showId();
                //存入用户登录信息，保存登录状态
                saveUserInfo.save(user.getName(), user.getId());
                break;
            }
            case "SystemMessage" :{
                chatMessage = new ChatMessage();
                chatMessage.setMessage(parsingTools.getString("SysMsg"));
                chatMessages.add(chatMessage);
                controllerHall.showMessage(chatMessages);
                break;
            }
            case "chat" : {
                chatMessage = new ChatMessage();
                chatMessage.setUserId((int) parsingTools.get("id"));
                chatMessage.setUserName(parsingTools.getString("name"));
                chatMessage.setMessage(parsingTools.getString("message"));
                chatMessages.add(chatMessage);
                //controllerHall.setMessageAdapter(chatMessages);
                controllerHall.showMessage(chatMessages);

                break;

            }
            case "newRoom" : {
                String result = parsingTools.getString("result");
                controllerHall.showNewChatRoomResult(result);
                break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        System.out.println(cause);
    }

}
