package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.ChatRoom;
import mjc.capstone.joinus.domain.entity.Member;
import mjc.capstone.joinus.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    Optional<ChatRoom> findByPostAndMember(Post post,Member member);
    Optional<List<ChatRoom>> findByMember(Member member);
    @Query("""
      select cr from ChatRoom cr 
            join fetch cr.post p 
                  join fetch p.author 
                        join fetch cr.member u 
                              where u = :member or p.author = :member
      """)
    Optional<List<ChatRoom>> findWithPostAndAuthorAndMember(@Param("member") Member member);

}
