CREATE TABLE `posts` (
                      id BIGINT NOT NULL AUTO_INCREMENT,
                      image VARCHAR(255) NOT NULL,
                      contents VARCHAR(255) NOT NULL,
                      member_id BIGINT NOT NULL,
                      created_at DATETIME NOT NULL,
                      modified_at DATETIME NOT NULL,
                      PRIMARY KEY (id),
                      FOREIGN KEY (member_id) REFERENCES Member(id)
);

CREATE TABLE `follows` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                          `follower_id` BIGINT NOT NULL,
                          `following_id` BIGINT NOT NULL,
                          `status` ENUM('FOLLOWING', 'UNFOLLOWING') NOT NULL DEFAULT 'FOLLOWING',
                          `created_at` DATETIME NOT NULL,
                          `modified_at` DATETIME NOT NULL,
                          PRIMARY KEY (`id`),
                          FOREIGN KEY (`follower_id`) REFERENCES `member`(`id`),
                          FOREIGN KEY (`following_id`) REFERENCES `member`(`id`),
                          UNIQUE (`follower_id`, `following_id`)
);

CREATE TABLE `members` (
                        `id`	BIGINT	NOT NULL,
                        `username`	VARCHAR (50)	NOT NULL,
                        `nickname`	VARCHAR (50)	NOT NULL,
                        `email`	VARCHAR (255)	NOT NULL,
                        `password`	VARCHAR (100)	NOT NULL,
                        `info`	VARCHAR (255)	NULL,
                        `mbti`	CHAR (4)	NULL,
                        `delete`	BOOLEAN	NOT NULL	DEFAULT false,
                        `created_at`	DATETIME	NOT NULL,
                        `modified_at`	DATETIME	NOT NULL
);

CREATE TABLE `post_like` (
                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                             `member_id` BIGINT NOT NULL,
                             `post_id` BIGINT NOT NULL,
                             `like_status` ENUM('LIKE', 'DISLIKE') NOT NULL DEFAULT 'LIKE',
                             `created_at` DATETIME NOT NULL,
                             `modified_at` DATETIME NOT NULL,
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
                             FOREIGN KEY (`post_id`) REFERENCES `post`(`id`)
);


CREATE TABLE `comments` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `member_id` BIGINT NOT NULL,
                            `post_id` BIGINT NOT NULL,
                            `comment_contents` VARCHAR(255) NULL,
                            `created_at` DATETIME NOT NULL,
                            `modified_at` DATETIME NOT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (`member_id`) REFERENCES `member`(`id`),
                            FOREIGN KEY (`post_id`) REFERENCES `post`(`id`)
);


CREATE TABLE `comment_like` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                `comment_id` BIGINT NOT NULL,
                                `member_id` BIGINT NOT NULL,
                                `like_status` ENUM('LIKE', 'DISLIKE') NOT NULL DEFAULT 'LIKE',
                                `created_at` DATETIME NOT NULL,
                                `modified_at` DATETIME NOT NULL,
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`comment_id`) REFERENCES `comments`(`id`),
                                FOREIGN KEY (`member_id`) REFERENCES `member`(`id`)
);


