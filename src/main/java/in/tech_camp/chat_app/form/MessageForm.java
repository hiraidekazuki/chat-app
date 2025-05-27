package in.tech_camp.chat_app.form;

import lombok.Data;

@Data  //代入とかを勝手にやってくれるやつのはず。つまり、セットとかが本来は費用。
public class MessageForm {  //フォームで入力された情報を一時的に保持する役割
  private String content;
}
