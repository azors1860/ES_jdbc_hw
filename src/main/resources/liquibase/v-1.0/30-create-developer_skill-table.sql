create table `developer_skill` (
 `developer_id` INT4 NOT NULL,
 `skill_id` INT4 NOT NULL,

 CONSTRAINT `developer_skill_pkey`
 PRIMARY KEY (`developer_id`,`skill_id`),

 CONSTRAINT `developer_skill_developer_fkey`
 FOREIGN KEY (`developer_id`)
 REFERENCES `developer` (`id`)
 ON DELETE CASCADE,

 CONSTRAINT `developer_skill_skill_fkey`
 FOREIGN KEY (`skill_id`)
 REFERENCES `skill` (`id`)
 ON DELETE CASCADE
);