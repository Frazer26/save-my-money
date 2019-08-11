INSERT INTO sub_category (name) VALUES (('Groceries'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-11', 2000, 'Lidl',0,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='Groceries'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-11', 1000, 'Tesco',0,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='Groceries'));

INSERT INTO sub_category (name) VALUES (('House maintenance'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-11', 1000, 'Roof repair',0,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='House maintenance'));


INSERT INTO item (date, money, name, is_repeat, main_category) VALUES
('2019-02-20', 2000, 'EPAM',1,'INCOME');

INSERT INTO item (date, money, name, is_repeat, main_category) VALUES
('2019-02-01', 3500, 'Money from grandpa',0,'INCOME');




