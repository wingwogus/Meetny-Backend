package mjc.capstone.joinus.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    private String roomId; // 채팅방 아이디
    private String roomName; // 채팅방 이름
    private long userCount; // 채팅방 인원수

    @OneToMany
    private List<Member> userList = new ArrayList<>();

    public void upUserCount(){
        this.userCount++;
    }
    public void downUserCount(){
        this.userCount--;
    }
    public void addUser(Member userName){
        this.userList.add(userName);
    }
    public void removeUser(Member userName){
        this.userList.remove(userName);
    }
}
