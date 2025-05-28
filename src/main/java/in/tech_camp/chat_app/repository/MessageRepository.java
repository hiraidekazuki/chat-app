package in.tech_camp.chat_app.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import in.tech_camp.chat_app.entity.MessageEntity;

@Mapper
public interface MessageRepository {
  //insertでメッセージのデータベースに情報をぶち込む。
  @Insert("INSERT INTO messages(content, image, user_id, room_id) VALUES(#{content}, #{image}, #{user.id}, #{room.id})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insert(MessageEntity messageEntity);

  //指定したルームIDのメッセージを全て取得するメソッド
  @Select("SELECT * FROM messages WHERE room_id = #{roomId}")
  @Results(value = {
    //取得した結果のcreated_atとMessageEntityのcreatedAtを紐づけるための記述。命名規則が異なるため、同一のののと証明するのにこれが必要。
    @Result(property = "createdAt", column = "created_at"),
    //投稿ユーザーの情報を取得するアソシエーション
    @Result(property = "user", column = "user_id",
            one = @One(select = "in.tech_camp.chat_app.repository.UserRepository.findById"))
  })
  List<MessageEntity> findByRoomId(Integer roomId);
}