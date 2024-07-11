insert into `users` (`id`, `email`, `nickname`, `address`, `certification_code`, `status`, `last_login_at`)
values (1, 'me@dev-domo.com', 'domo', 'Seoul', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'ACTIVE', 0);

insert into `posts` (`id`, `content`, `created_at`, `updated_at`, `user_id`)
values (16, 'Hello, world!', 0, 0, 1);