package in.tech_camp.chat_app.entity;

import java.util.List;

import lombok.Data;

@Data
public class UserEntity {
  private Integer id;
  private String name;
  private String email;
  private String password;
  private List<RoomUserEntity> roomUsers;
  // このユーザーが送信した一件のメッセージ（MessageEntity）をリスト形式で複数、表すリスト
  //要するにこのユーザーが何言ったかを記録している。
  private List<MessageEntity> messages; 
}
