-- name: select-all-messages
-- Select all messages from db
SELECT * FROM messages

-- name: select-message-with-id
-- Select message with id
SELECT * FROM messages WHERE id = :id AND to_user_id = :to_user_id AND parent_id IS NULL

-- name: select-replies-to-message
-- Select messages with parent_id
SELECT * FROM messages WHERE parent_id = :id AND to_user_id = :to_user_id

-- name: select-messages-to-user
-- Select messages to user
SELECT * FROM messages WHERE to_user_id = :user_id AND parent_id IS NULL

-- name: insert-new-message<!
-- Create new message to user
INSERT INTO messages (from_user_id, to_user_id, message, subject, parent_id) values(:from, :to, :message, :subject, :parent_id)

-- name: select-messages-from-user
-- Select own sent messages
SELECT * FROM messages WHERE from_user_id = :user_id
