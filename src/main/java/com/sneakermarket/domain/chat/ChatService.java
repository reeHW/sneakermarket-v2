package com.sneakermarket.domain.chat;

import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberDto;
import com.sneakermarket.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final MemberRepository memberRepository;
    private final ChatRepository chatRepository;
    private  final ChatRoomRepository chatRoomRepository;

    /**
     * 채팅 내용 저장
     */
    public ChatDto.Receive save(Long roomId, ChatDto.Send chatDto, String nickname) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        String dNickname = URLDecoder.decode(nickname, StandardCharsets.UTF_8);
        String dSenderNick = URLDecoder.decode(chatDto.getSenderNickname(), StandardCharsets.UTF_8);
        Member receiver = memberRepository.findByNickname(dNickname);
        Member sender = memberRepository.findByNickname(dSenderNick);

        Chat chat = Chat.builder()
                .sender(sender)
                .receiver(receiver)
                .message(chatDto.getMessage())
                .chatRoom(chatRoom).build();

        chatRepository.save(chat);

        ChatDto.Receive receive = ChatDto.entityToDto(chat);
        return receive;
    }

    /**
     * 채팅방 만들기
     */
    public Long createRoom( MemberDto.Response memberDto, String nickname) {
        Member member = memberRepository.findByNickname(memberDto.getNickname());
        String dNickname = URLDecoder.decode(nickname, StandardCharsets.UTF_8);
        Member otherMember = memberRepository.findByNickname(dNickname);
        Chat chat = chatRepository.findByReceiverAndSender(member.getId(), otherMember.getId()).orElse(null);

        if(chat == null){
            ChatRoom chatRoom = new ChatRoom();
            ChatRoom save = chatRoomRepository.save(chatRoom);
            return save.getId();
        }else{
            return chat.getChatRoom().getId();
        }
    }


    /**
     * 채팅 목록 반환
     */
    public List<ChatDto.List> list(MemberDto.Response memberDto) {
        Member member =  memberRepository.findByNickname(memberDto.getNickname());
        List<Chat> charList = chatRepository.findByReceiverMemberId(member.getId());
        List<ChatDto.List> list = charList.stream()
                .map(chat -> ChatDto.entityToDTOList(chat, member))
                .collect(Collectors.toList());
        return list;
    }

    /**
     * 과거 대화내용 반환
     * @param roomId
     * @return
     */
    public List<ChatDto.Receive> getHistory(Long roomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(roomId).orElse(null);
        List<Chat> chatList = chatRoom.getChatList();
        List<ChatDto.Receive> collect = chatList.stream()
                .map(chat -> ChatDto.entityToDto(chat))
                .collect(Collectors.toList());
        return collect;
    }

}
