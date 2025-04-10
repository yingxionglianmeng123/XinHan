-- 备份原有数据
CREATE TABLE IF NOT EXISTS demands_backup AS SELECT * FROM demands;

-- 删除旧表
DROP TABLE IF EXISTS demands;

-- 创建新表
CREATE TABLE IF NOT EXISTS demands (
    id VARCHAR(32) PRIMARY KEY,
    title VARCHAR(100),
    description TEXT,
    price DECIMAL(10,2),
    category VARCHAR(50),
    skills JSON,
    attachments JSON,
    deadline DATE,
    status VARCHAR(20),
    publisher_id VARCHAR(32),
    applicants JSON,
    selected_applicant VARCHAR(32),
    view_count INT DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    blockchain_verified BOOLEAN DEFAULT FALSE,
    deleted TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 从备份表恢复数据
INSERT INTO demands (
    id, title, description, price, category, status, publisher_id, created_at, blockchain_verified, deleted
)
SELECT 
    id, title, description, budget, category, status, publisher_id, created_at, blockchain_verified, deleted
FROM demands_backup; 