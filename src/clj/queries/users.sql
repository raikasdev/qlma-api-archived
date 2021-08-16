-- name: select-user-count
-- Count users from db
SELECT COUNT(*) AS count
FROM users

-- name: select-all-users
-- Select all users from db
SELECT * FROM users

-- name: insert-new-user<!
-- Insert new user to db
INSERT INTO users (username, firstname, lastname, password)
VALUES (:username, :firstname, :lastname, :password)

-- name: select-user-password
-- Get users password
SELECT * FROM users WHERE username = :username

-- name: select-user-data
-- Get user data
SELECT * FROM users WHERE username = :username