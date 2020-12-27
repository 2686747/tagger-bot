CREATE TABLE IF NOT EXISTS Tags
  (
  tag_id VARCHAR(512) NOT NULL,
  user_id INTEGER, 
  photo_id VARCHAR(512) NOT NULL,
  media_type TINYINT NOT NULL,
  saved TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  UNIQUE(tag_id, user_id, photo_id),
  PRIMARY KEY(tag_id, user_id, photo_id)
  );
  
  CREATE TABLE IF NOT EXISTS PageLinks
(
    user_id BIGINT not null,
    created BIGINT not null,
    UNIQUE(user_id, created), 
    primary key(user_id, created)
);

