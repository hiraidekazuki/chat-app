CREATE TABLE IF NOT EXISTS messages (
  id        SERIAL        NOT NULL,
  content   VARCHAR(512),
  user_id   INT           NOT NULL,
  room_id   INT           NOT NULL,
  image     VARCHAR(512),
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  -- 外部キー制約: user_idはusersテーブルのidを参照し、usersの行が削除されると連動して削除される
  FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,  
  -- 外部キー制約とはメッセージテーブルのuser_idにはusersテーブルにあるIDのみが入ることができるというような制限のことをいう
  FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);