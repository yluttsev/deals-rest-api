--liquibase formatted sql

--changeset yluttsev:7
INSERT INTO deal_type (id, name)
VALUES ('CREDIT', 'Кредитная сделка'),
       ('OVERDRAFT', 'Овердрафт'),
       ('OTHER', 'Иное');

--changeset yluttsev:8
INSERT INTO deal_status_dict (id, name)
VALUES ('DRAFT', 'Черновик'),
       ('ACTIVE', 'Утвержденная'),
       ('CLOSED', 'Закрытая');

--changeset yluttsev: 9
INSERT INTO contractor_role (id, name, category)
VALUES ('BORROWER', 'Заемщик', 'BORROWER'),
       ('DRAWER', 'Векселедатель', 'BORROWER'),
       ('ISSUER', 'Эмитент', 'BORROWER'),
       ('WARRANTY', 'Поручитель', 'WARRANTY'),
       ('GARANT', 'Гарант', 'WARRANTY'),
       ('PLEDGER', 'Залогодатель', 'WARRANTY');