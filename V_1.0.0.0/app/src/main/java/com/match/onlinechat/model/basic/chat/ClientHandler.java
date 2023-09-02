package com.match.onlinechat.model.basic.chat;

import android.os.Handler;
import android.os.Looper;

import com.match.onlinechat.activity.HallActivity;
import com.match.onlinechat.controller.ControllerHall;
import com.match.onlinechat.model.basic.chat.message.SystemMessageHanding;
import com.match.onlinechat.model.basic.chat.message.ResultMessageHandling;
import com.match.onlinechat.model.basic.constants.SearchChatRoomPrompt;
import com.match.onlinechat.model.basic.user.SaveUserInfo;
import com.match.onlinechat.model.basic.user.User;
import com.match.onlinechat.model.basic.hall.Hall;
import com.match.onlinechat.model.basic.tools.ParsingTools;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
        ParsingTools parsingTools = new ParsingTools(msg);
        String mode = parsingTools.getString("mode");
        ControllerHall controllerHall = HallActivity.controllerHall;
        SaveUserInfo saveUserInfo = new SaveUserInfo();
        Hall hall = ControllerHall.hall;
        User user = new User();
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
                SystemMessageHanding.systemMessage = parsingTools.getString("SysMsg");
                break;
            }
            case "chat" : {
                ResultMessageHandling.chatMessage = parsingTools.getString("message");
                ResultMessageHandling.chatUserName = parsingTools.getString("name");
                ResultMessageHandling.chatUserId = (int) parsingTools.get("id");
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
