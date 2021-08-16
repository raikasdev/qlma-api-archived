-- name: select-all-tags
-- Select all tags from db (for admin use only)
SELECT * FROM tags

-- name: select-tag-with-id
-- Select tag with id
SELECT * FROM tags WHERE (id = :id AND user_id = :my_id) OR (id = :id AND tag_type = 0)

-- name: select-personal-tags
-- Select tags with own user_id
SELECT * FROM tags WHERE user_id = :id

-- name: select-global-tags
-- Select messages to user
SELECT * FROM tags WHERE tag_type = 0

-- name: insert-personal-tag<!
-- Create a new personal tag
INSERT INTO tags (name, tag_type, user_id) values(:tagname, 1, :user_id)

-- name: insert-global-tag<!
-- Create a new global tag
INSERT INTO tags (name, tag_type) values(:tagname, 0)

