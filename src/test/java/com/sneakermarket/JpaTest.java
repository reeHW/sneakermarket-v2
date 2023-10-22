package com.sneakermarket;


import com.sneakermarket.domain.comment.Comment;
import com.sneakermarket.domain.comment.CommentRepository;
import com.sneakermarket.domain.file.FileRepository;
import com.sneakermarket.domain.member.Member;
import com.sneakermarket.domain.member.MemberRepository;
import com.sneakermarket.domain.post.Post;
import com.sneakermarket.domain.post.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
public class JpaTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private EntityManager em;

    private Member member;
    private Post post;
    private Comment comment;
    private List<MultipartFile> fileList = new ArrayList<>();


    @BeforeEach
    void before() {
        member = memberRepository.save(new Member("LOCALTEST", "LOCALTEST","1234"));
        post = postRepository.save(Post.builder()
                .size(270)
                .content("테스트")
                .title("제목")
                .member(member)
                .build());
    }

    @Test
    @DisplayName("N+1 발생 테스트")
    void test() {
        saveTestData(); // 10개의 post와, post마다 3개씩 댓글 저장.


        em.flush();
        em.clear();
        System.out.println("------------ 영속성 컨텍스트 비우기 -----------\n\n");



        System.out.println("------------ POST 전체 조회 요청 ------------");
        List<Post> posts = postRepository.findAll();
        System.out.println("------------ POST 전체 조회 완료. [1번의 쿼리 발생]------------\n\n");




        System.out.println("------------ POST 제목 & 내용 조회 요청 ------------");
        posts.forEach(it -> System.out.println(it.getTitle()+" 내용 : "+it.getContent()));
        System.out.println("------------ POST 제목 & 내용 조회 완료. [추가적인 쿼리 발생하지 않음]------------\n\n");




        System.out.println("------------ POST에 달린 comment 내용 조회 요청 [조회된 POST의 개수(N=10) 만큼 추가적인 쿼리 발생]------------");
        posts.forEach(post -> {
            post.getComments().forEach(comment -> {
                System.out.println(comment.getPost().getTitle()+" 댓글 : " +comment.getContent());
            });
        });
        System.out.println("------------ POST에 달린 comment 내용 조회 완료 ------------\n\n");

    }

    private void saveTestData() {

        for (int i = 1; i <= 10; i++) {
            post = Post.builder()
                    .size(270)
                    .content("테스트" + i)
                    .title("제목" + i)
                    .member(member)
                    .build();

            postRepository.save(post);
            for (int j = 1; j <=3; j++){
                comment = Comment.builder()
                        .writer(member.getNickname())
                        .content("댓글" + i)
                        .post(post).build();
                commentRepository.save(comment);
            }

        }
    }

}
