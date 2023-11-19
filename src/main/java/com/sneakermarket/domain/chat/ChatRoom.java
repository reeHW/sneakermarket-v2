package com.sneakermarket.domain.chat;

import com.sneakermarket.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    private LocalDateTime createdTime = LocalDateTime.now();

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<Chat> chatList = new ArrayList<>();


    public ChatRoom(Member sender) {
        this.sender = sender;
    }
    @PrePersist //최초 Persist될 때 수행
    public void setCreateTime() {
        this.createdTime = LocalDateTime.now();
    }


}
