create extension if not exists pgcrypto;

insert into outbox (id, contractor_id, is_main, creation_date, status, action)
values
    (gen_random_uuid(), 'test_id', true, now(), 'SUCCESS', 'CHANGE_STATUS_ACTIVE'),
    (gen_random_uuid(), 'test_id1', false, now(), 'ERROR', 'DELETE_CONTRACTOR'),
    (gen_random_uuid(), 'test_id2', true, now(), 'ERROR', 'CHANGE_STATUS_ACTIVE'),
    (gen_random_uuid(), 'test_id2', false, now(), 'SUCCESS', 'CHANGE_STATUS_CLOSED')