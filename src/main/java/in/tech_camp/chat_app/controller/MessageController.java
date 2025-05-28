package in.tech_camp.chat_app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import in.tech_camp.chat_app.custom_user.CustomUserDetail;
import in.tech_camp.chat_app.entity.MessageEntity;
import in.tech_camp.chat_app.entity.RoomEntity;
import in.tech_camp.chat_app.entity.RoomUserEntity;
import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.MessageForm;
import in.tech_camp.chat_app.repository.MessageRepository;
import in.tech_camp.chat_app.repository.RoomRepository;
import in.tech_camp.chat_app.repository.RoomUserRepository;
import in.tech_camp.chat_app.repository.UserRepository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class MessageController {
  private final UserRepository userRepository;

  private final RoomUserRepository roomUserRepository;

  private final RoomRepository roomRepository;

  private final MessageRepository messageRepository;


  @GetMapping("/rooms/{roomId}/messages")  
  //Spring MVCが(つまりこの時点で)MessageFormクラスの新しいインスタンスを生成し、リクエストの中のデータを「セットする＝(コピー)」
  public String showMessages(@PathVariable("roomId") Integer roomId,@AuthenticationPrincipal CustomUserDetail currentUser, Model model){
    // 現在ログイン中のユーザー情報
    UserEntity user = userRepository.findById(currentUser.getId());
    // そのユーザーが参加している全ルームの一覧
    List<RoomUserEntity> roomUserEntities = roomUserRepository.findByUserId(currentUser.getId());
    List<RoomEntity> roomList = roomUserEntities.stream()
        .map(RoomUserEntity::getRoom)
        .collect(Collectors.toList());
    model.addAttribute("user", user);
    
    // メッセージ送信フォーム用の空オブジェクト
    model.addAttribute("messageForm", new MessageForm());
    //model は「ビューに渡すデータの入れ物」 addAttribute は「モデルに属性（データ）を追加するメソッド」 "messageForm" はビュー（テンプレート）側で使う名前（キー） new MessageForm() は 空のメッセージ送信用のフォームオブジェクトを作って渡している
   
   // 現在表示中のルーム
    RoomEntity room = roomRepository.findById(roomId);
    model.addAttribute("room", room);

    // 現在表示中のルームに属するメッセージ一覧
    List<MessageEntity> messages = messageRepository.findByRoomId(roomId);
    model.addAttribute("messages", messages);
    return "messages/index";
  }
   @PostMapping("/rooms/{roomId}/messages")
   //送られてきたリクエストから、IDを抜き出す。
  public String saveMessage(@PathVariable("roomId") Integer roomId, @ModelAttribute("messageForm") MessageForm messageForm, BindingResult bindingResult, @AuthenticationPrincipal CustomUserDetail currentUser) {
    
    if (bindingResult.hasErrors()) {
      return "redirect:/rooms/" + roomId + "/messages";
    }
    
    MessageEntity message = new MessageEntity();
    //送られてきたデータを一時保存していたメッセージフォームからコンテンツをゲットして、隣にぶち込む。
    message.setContent(messageForm.getContent());

    //誰がコメントをしたのかを保存したいので各リポジトリからIDを取ってきて、セットしている。
    UserEntity user = userRepository.findById(currentUser.getId());
    RoomEntity room = roomRepository.findById(roomId);
    message.setUser(user);
    message.setRoom(room);

    try {
      messageRepository.insert(message);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
    }

    return "redirect:/rooms/" + roomId + "/messages";
  }

}

