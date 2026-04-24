-- Create multiple databases
CREATE DATABASE IF NOT EXISTS `user_db`;
CREATE DATABASE IF NOT EXISTS `product_db`;
CREATE DATABASE IF NOT EXISTS `order_db`;
CREATE DATABASE IF NOT EXISTS `inventory_db`;
CREATE DATABASE IF NOT EXISTS `analytics_db`;

-- Root user is already created by Docker with MYSQL_ROOT_PASSWORD, allowing from any host

FLUSH PRIVILEGES;
