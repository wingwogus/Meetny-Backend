package mjc.capstone.joinus.exception;

public class PostNotFoundException extends BusinessException{

    public PostNotFoundException(){
        super(ErrorCode.POST_NOT_FOUND, "존재하지 않는 게시물입니다.");
    }
}
