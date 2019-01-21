INSERT INTO main_category (name) VALUES ('Income');

INSERT INTO sub_category (name, main_id) VALUES (('Epam'),
(SELECT main_category.main_id FROM main_category WHERE main_category.name ='Income'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-11', 200000, 'Monthly salary',1,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='Epam'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-11', 10000, 'Cafeteria',1,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='Epam'));

INSERT INTO sub_category (name, main_id) VALUES (('Pocket Money'),
(SELECT main_category.main_id FROM main_category WHERE main_category.name ='Income'));

INSERT INTO item (date, money, name, is_repeat, sub_id) VALUES
('2019-02-20', 2000, 'Money from grandpa',0,
(SELECT sub_category.sub_id FROM sub_category WHERE sub_category.name ='Pocket Money'));

INSERT INTO main_category (name) VALUES ('Bills');

INSERT INTO item (date, money, name, is_repeat, main_id) VALUES
('2019-02-01', 3500, 'Electricity',0,
(SELECT main_category.main_id FROM main_category WHERE main_category.name ='Bills'));




