create extension if not exists pgcrypto;

insert into outbox (id, contractor_id, is_main, creation_date, status)
values
    (gen_random_uuid(), 'test_id', true, now(), 'ERROR'),
    (gen_random_uuid(), 'test_id1', false, now(), 'SUCCESS'),
    (gen_random_uuid(), 'test_id2', true, now(), 'ERROR'),
    (gen_random_uuid(), 'test_id2', true, now(), 'SUCCESS')