package com.sneakermarket.domain.chat;

import com.sneakermarket.domain.member.Member;
import lombok.Getter;
import lombok.ToString;

import java.time.format.DateTimeFormatter;

public class ChatDto {

    @Getter
    @ToString
    public static class Send{
        private String senderNickname;
        private String message;
    }

    @Getter
    @ToString
    public static class Receive{
        private Long roomId;
        private String receiverNickname;
        private String senderNickname;
        private String message;
        private String sendTime;
    }

    public static ChatDto.Receive entityToDto(Chat chat){
        Receive receive = new Receive();
        receive.roomId = chat.getChatRoom().getId();
        receive.receiverNickname = chat.getReceiver().getNickname();
        receive.senderNickname = chat.getSender().getNickname();
        receive.message = chat.getMessage();
        receive.sendTime = chat.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return receive;
    }

    @Getter
    @ToString
    public static class List{
        private Long roomId;
        private String otherNickname;
        private String recentMessage;
        private String recentTime;
    }

    public static ChatDto.List entityToDTOList(Chat chat, Member member) {
        ChatDto.List list = new ChatDto.List();
        list.roomId = chat.getChatRoom().getId();
        if(chat.getSender().getNickname() == member.getNickname()){
            list.otherNickname = chat.getReceiver().getNickname();
        }else{
            list.otherNickname = chat.getSender().getNickname();
        }
        list.recentMessage = chat.getMessage();
        list.recentTime = chat.getCreateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return list;
    }
}
