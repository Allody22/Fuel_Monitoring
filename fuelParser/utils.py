import datetime
import os

from db_writer import DBWriter

DB_SETTINGS = {
    'dbname': 'fuel_monitoring',
    'user': 'postgres',
    'password': os.getenv('PG_PASSWORD', ''),  # Получаем пароль из переменной среды DB_PASSWORD
    'host': 'localhost'
}


def add_or_get_station_id(station_info):
    db_writer = DBWriter(DB_SETTINGS)

    gas_station_id = db_writer.insert_or_get_gas_station(station_info)

    db_writer.commit()
    db_writer.close()

    return gas_station_id


def add_fuel_info(gas_station_id, fuel_prices):
    db_writer = DBWriter(DB_SETTINGS)

    for station in fuel_prices:
        # Информация о конкретном адресе
        address_info = {
            'address' : station['address'],
            'feedbacks': 0,  # Можете заменить на реальное значение, если доступно
            'rating': 0.0,  # Можете заменить на реальное значение, если доступно
            'updated_at': datetime.date.today()  # Текущая дата
        }

        # Вставляем адрес заправки и получаем его ID
        gas_station_address_id = db_writer.insert_gas_station_address(gas_station_id, address_info)

        # Вставляем информацию о типах топлива и их ценах
        for fuel_type, price in station['prices'].items():
            if price is not None and price != 'Нет данных':
                # Вставляем тип топлива, если его ещё нет
                db_writer.insert_oil_type(fuel_type)
                # Удаляем символ ₽ и заменяем запятую на точку
                clean_price = price.replace('₽', '').replace(',', '.').strip()
                # Преобразуем очищенное значение в float и вставляем цену на топливо
                db_writer.insert_oil_price(gas_station_address_id, fuel_type, float(clean_price))

    # Подтверждаем изменения и закрываем соединение
    db_writer.commit()
    db_writer.close()
