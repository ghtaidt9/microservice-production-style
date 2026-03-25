-- Create multiple databases
CREATE DATABASE IF NOT EXISTS `user_db`;
CREATE DATABASE IF NOT EXISTS `product_db`;
CREATE DATABASE IF NOT EXISTS `order_db`;
CREATE DATABASE IF NOT EXISTS `inventory_db`;
CREATE DATABASE IF NOT EXISTS `analytics_db`;

-- Optional: Create specific users and grant privileges
-- CREATE USER 'user_one'@'%' IDENTIFIED BY 'password_one';
-- GRANT ALL PRIVILEGES ON `database_one`.* TO 'user_one'@'%';

-- CREATE USER 'user_two'@'%' IDENTIFIED BY 'password_two';
-- GRANT ALL PRIVILEGES ON `database_two`.* TO 'user_two'@'%';

FLUSH PRIVILEGES;
