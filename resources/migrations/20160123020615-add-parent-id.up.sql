ALTER TABLE messages ADD COLUMN parent_id integer REFERENCES messages (id);
