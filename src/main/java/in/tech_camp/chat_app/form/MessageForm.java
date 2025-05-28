package in.tech_camp.chat_app.form;

import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

//import in.tech_camp.chat_app.validation.ValidationPriority1;
//import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data  //代入とかを勝手にやってくれるやつのはず。つまり、セットとかが本来は費用。
public class MessageForm {  //フォームで入力された情報を一時的に保持する役割
  //@NotBlank(message = "Name can't be blank",groups = ValidationPriority1.class)
  private String content;

  //画像を受け取るためのフィールドを定義
  private MultipartFile image;

  public void validateMessage(BindingResult result) {
    if ((content == null || content.isEmpty()) && (image == null || image.isEmpty())) {
      result.rejectValue("Content", "error.Message", "Please enter either content or image");
    }
  }
}
