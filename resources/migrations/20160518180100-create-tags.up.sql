CREATE TABLE IF NOT EXISTS tags (
  id serial primary key,
  name text NOT NULL,
  tag_type INT NOT NULL,
  user_id INT,
  create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  edit_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP)

