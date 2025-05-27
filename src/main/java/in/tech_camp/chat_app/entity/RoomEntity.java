package in.tech_camp.chat_app.entity;

import java.util.List;

import lombok.Data;

@Data
public class RoomEntity {
  private Integer id;
  private String name;
  private List<RoomUserEntity> roomUsers;
  //このルームにどんなことを書いたか(MessageEntityが一件のコメント。それをリストで複数持つ。出所はmessagesテーブル)を残すための記述
  private List<MessageEntity> messages;
}
