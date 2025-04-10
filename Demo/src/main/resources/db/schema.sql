-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(36) PRIMARY KEY,
    openid VARCHAR(100) UNIQUE NOT NULL,
    user_type VARCHAR(20) NOT NULL DEFAULT 'user',
    nick_name VARCHAR(50),
    avatar_url VARCHAR(255),
    skill VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户技能表
CREATE TABLE IF NOT EXISTS user_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(32),
    skill VARCHAR(50),
    FOREIGN KEY (user_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 需求表
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

-- 需求技能表
CREATE TABLE IF NOT EXISTS demand_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    demand_id VARCHAR(32),
    skill VARCHAR(50),
    FOREIGN KEY (demand_id) REFERENCES demands(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 需求附件表
CREATE TABLE IF NOT EXISTS demand_attachments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    demand_id VARCHAR(32),
    attachment VARCHAR(255),
    FOREIGN KEY (demand_id) REFERENCES demands(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 申请表
CREATE TABLE IF NOT EXISTS applications (
    id VARCHAR(32) PRIMARY KEY,
    demand_id VARCHAR(32),
    applicant_id VARCHAR(32),
    proposal TEXT,
    bid_price DECIMAL(10,2),
    bid_duration VARCHAR(50),
    status VARCHAR(20),
    created_at DATETIME,
    updated_at DATETIME,
    deleted TINYINT DEFAULT 0,
    FOREIGN KEY (demand_id) REFERENCES demands(id),
    FOREIGN KEY (applicant_id) REFERENCES users(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4; 