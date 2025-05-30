package mjc.capstone.joinus.repository;

import mjc.capstone.joinus.domain.entity.Chat;
import mjc.capstone.joinus.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    List<Chat> findByRoom(ChatRoom room);
}
