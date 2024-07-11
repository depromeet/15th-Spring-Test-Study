insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (1, 'nahyeonee99@gmail.com', 'nahyeonee99', 'Seoul', 'aaaaaaaa-aaaaaaaa-aaaaaaaa', 'ACTIVE', 0);
insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (2, 'nahyeonee100@gmail.com', 'nahyeonee100', 'Seoul', 'aaaaaaaa-aaaaaaaa-aaaaaaab', 'PENDING', 0);
insert into `posts`(`id`, `user_id`, `content`, `created_at`, `modified_at`)
values (1, 1, 'test 1', 0, 1);
insert into `posts`(`id`, `user_id`, `content`, `created_at`, `modified_at`)
values (2, 1, 'test 2', 0, 1);
insert into `posts`(`id`, `user_id`, `content`, `created_at`, `modified_at`)
values (3, 1, 'test 3', 0, 1);