-- Добавление данных в таблицу gas_station
INSERT INTO gas_station (email, name, type, url)
VALUES ('contact@station1.com', 'Station 1', 'Diesel', 'http://station1.com'),
       ('contact@station2.com', 'Station 2', 'Petrol', 'http://station2.com'),
       ('contact@station3.com', 'Station 3', 'Electric', 'http://station3.com'),
       ('contact@station4.com', 'Station 4', 'Hybrid', 'http://station4.com'),
       ('contact@station5.com', 'Station 5', 'Diesel', 'http://station5.com')
on conflict do nothing;

-- Добавление данных в таблицу gas_station_address
INSERT INTO gas_station_address (address, feedbacks, rating, updated_at, gas_station_id)
VALUES ('123 Main St', 15, 4.3, '2024-11-01', 1),
       ('456 Oak St', 25, 4.7, '2024-11-02', 2),
       ('789 Pine St', 12, 4.2, '2024-11-03', 3),
       ('101 Maple Ave', 30, 4.6, '2024-11-04', 4),
       ('202 Elm St', 18, 4.5, '2024-11-05', 5),
       ('303 Cedar St', 22, 4.0, '2024-11-06', 1),
       ('404 Birch Rd', 10, 3.9, '2024-11-07', 2),
       ('505 Walnut Blvd', 17, 4.8, '2024-11-08', 3)
on conflict do nothing;

-- Добавление данных в таблицу oil_types
INSERT INTO oil_types (type)
VALUES ('Diesel'),
       ('Petrol'),
       ('Electric'),
       ('Hybrid')
on conflict do nothing;

-- Добавление данных в таблицу oil_type_price_address_history
INSERT INTO oil_type_price_address_history (date, price, gas_station_address_id, oil_type_id)
VALUES
-- Цены для Station 1
('2024-11-01', 3.20, 1, 'Diesel'),
('2024-11-02', 3.25, 1, 'Diesel'),
('2024-11-03', 3.30, 1, 'Diesel'),
('2024-11-04', 3.35, 1, 'Diesel'),
('2024-11-05', 3.40, 1, 'Diesel'),

-- Цены для Station 2
('2024-11-01', 4.10, 2, 'Petrol'),
('2024-11-02', 4.15, 2, 'Petrol'),
('2024-11-03', 4.20, 2, 'Petrol'),
('2024-11-04', 4.25, 2, 'Petrol'),
('2024-11-05', 4.30, 2, 'Petrol'),

-- Цены для Station 3
('2024-11-01', 0.18, 3, 'Electric'),
('2024-11-02', 0.19, 3, 'Electric'),
('2024-11-03', 0.20, 3, 'Electric'),
('2024-11-04', 0.21, 3, 'Electric'),
('2024-11-05', 0.22, 3, 'Electric'),

-- Цены для Station 4
('2024-11-01', 3.80, 4, 'Hybrid'),
('2024-11-02', 3.85, 4, 'Hybrid'),
('2024-11-03', 3.90, 4, 'Hybrid'),
('2024-11-04', 3.95, 4, 'Hybrid'),
('2024-11-05', 4.00, 4, 'Hybrid'),

-- Цены для Station 5
('2024-11-01', 3.45, 5, 'Diesel'),
('2024-11-02', 3.50, 5, 'Diesel'),
('2024-11-03', 3.55, 5, 'Diesel'),
('2024-11-04', 3.60, 5, 'Diesel'),
('2024-11-05', 3.65, 5, 'Diesel'),

-- Дополнительные данные для Station 1 и Station 2
('2024-11-06', 3.45, 6, 'Diesel'),
('2024-11-07', 3.50, 6, 'Diesel'),
('2024-11-08', 4.05, 7, 'Petrol'),
('2024-11-09', 4.10, 7, 'Petrol')
on conflict do nothing;

-- Предполагается, что адрес "123 Main St" имеет id = 1 в таблице gas_station_address
-- Добавим данные для разных типов топлива и на большее количество дней

-- Diesel
INSERT INTO oil_type_price_address_history (date, price, gas_station_address_id, oil_type_id)
VALUES ('2024-11-01', 3.20, 1, 'Diesel'),
       ('2024-11-02', 3.25, 1, 'Diesel'),
       ('2024-11-03', 3.30, 1, 'Diesel'),
       ('2024-11-04', 3.35, 1, 'Diesel'),
       ('2024-11-05', 3.40, 1, 'Diesel'),
       ('2024-11-06', 3.45, 1, 'Diesel'),
       ('2024-11-07', 3.50, 1, 'Diesel'),
       ('2024-11-08', 3.55, 1, 'Diesel'),
       ('2024-11-09', 3.60, 1, 'Diesel'),
       ('2024-11-10', 3.65, 1, 'Diesel'),
       ('2024-11-11', 3.70, 1, 'Diesel'),
       ('2024-11-12', 3.75, 1, 'Diesel'),
       ('2024-11-13', 3.80, 1, 'Diesel'),
       ('2024-11-14', 3.85, 1, 'Diesel')
on conflict do nothing;

-- Petrol
INSERT INTO oil_type_price_address_history (date, price, gas_station_address_id, oil_type_id)
VALUES ('2024-11-01', 4.10, 1, 'Petrol'),
       ('2024-11-02', 4.15, 1, 'Petrol'),
       ('2024-11-03', 4.20, 1, 'Petrol'),
       ('2024-11-04', 4.25, 1, 'Petrol'),
       ('2024-11-05', 4.30, 1, 'Petrol'),
       ('2024-11-06', 4.35, 1, 'Petrol'),
       ('2024-11-07', 4.40, 1, 'Petrol'),
       ('2024-11-08', 4.45, 1, 'Petrol'),
       ('2024-11-09', 4.50, 1, 'Petrol'),
       ('2024-11-10', 4.55, 1, 'Petrol'),
       ('2024-11-11', 4.60, 1, 'Petrol'),
       ('2024-11-12', 4.65, 1, 'Petrol'),
       ('2024-11-13', 4.70, 1, 'Petrol'),
       ('2024-11-14', 4.75, 1, 'Petrol')
on conflict do nothing;

-- Electric
INSERT INTO oil_type_price_address_history (date, price, gas_station_address_id, oil_type_id)
VALUES ('2024-11-01', 0.18, 1, 'Electric'),
       ('2024-11-02', 0.19, 1, 'Electric'),
       ('2024-11-03', 0.20, 1, 'Electric'),
       ('2024-11-04', 0.21, 1, 'Electric'),
       ('2024-11-05', 0.22, 1, 'Electric'),
       ('2024-11-06', 0.23, 1, 'Electric'),
       ('2024-11-07', 0.24, 1, 'Electric'),
       ('2024-11-08', 0.25, 1, 'Electric'),
       ('2024-11-09', 0.26, 1, 'Electric'),
       ('2024-11-10', 0.27, 1, 'Electric'),
       ('2024-11-11', 0.28, 1, 'Electric'),
       ('2024-11-12', 0.29, 1, 'Electric'),
       ('2024-11-13', 0.30, 1, 'Electric'),
       ('2024-11-14', 0.31, 1, 'Electric')
on conflict do nothing;
