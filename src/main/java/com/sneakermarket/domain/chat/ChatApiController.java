package com.sneakermarket.domain.chat;

import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.global.config.auth.LoggedInMember;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatApiController {

    private final SimpMessagingTemplate template;
    private final ChatService chatService;

    /**
     * 채팅입력 및 결과반환
     * @PathVariable 은 Rest, Mvc 메서드에서 동작한다. MessageMapping에서는 동작하지 않는다. @DestinationVariable을 사용한다.
     */
    @MessageMapping("/room/{roomId}/{nickname}")
    public void message(@RequestBody ChatDto.Send chatDto, @DestinationVariable Long roomId, @DestinationVariable String nickname) throws Exception{
        ChatDto.Receive receive = chatService.save(roomId, chatDto, nickname);
        template.convertAndSend("/api/chat/receive/" + receive.getReceiverNickname(), receive);
        template.convertAndSend("/api/chat/receive/room/"+roomId+"/"+nickname, receive);
        // StompConfig에 적힌 enableSimpleBroker 도 포함해서 작성해야한다.

    }

    /**
     * 채팅방 만들기
     */
    @PostMapping("/api/chat/{nickname}")
    public ResponseEntity createRoom(
            @LoggedInMember MemberDto.Response member,
            @PathVariable String nickname
    ){
        Long roomId = chatService.createRoom(member, nickname);
        return ResponseEntity.status(HttpStatus.OK).body(roomId);
    }


    /**
     * 채팅방 리스트 반환
     */
    @GetMapping("/api/chat/room/list")
    public ResponseEntity list(@LoggedInMember MemberDto.Response member){
        List<ChatDto.List> list = chatService.list(member);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    /**
     * 과거 대화내용 가져오기
     */
    @GetMapping("/api/chat/room/{roomId}")
    public ResponseEntity history(@PathVariable Long roomId){
        List<ChatDto.Receive> history = chatService.getHistory(roomId);
        return ResponseEntity.status(HttpStatus.OK).body(history);
    }
}
