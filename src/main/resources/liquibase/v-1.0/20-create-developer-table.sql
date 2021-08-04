create table `developer` (
 `id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `firstname` varchar(255) not null,
 `lastname` varchar(255) not null,
 `team_id` INT4 not null,

 CONSTRAINT `developer_team_fkey`
 FOREIGN KEY (`team_id`)
 REFERENCES `team` (`id`)
 ON DELETE CASCADE
);