import psycopg2
import datetime


class DBWriter:
    def __init__(self, db_settings):
        self.conn = psycopg2.connect(**db_settings)
        self.cursor = self.conn.cursor()

    def insert_or_get_gas_station(self, station_info):
        self.cursor.execute("""
               SELECT id FROM gas_station WHERE name = %s;
           """, (station_info['name'],))

        result = self.cursor.fetchone()

        if result:
            return result[0]
        else:
            self.cursor.execute("""
                   INSERT INTO gas_station (email, name, type, url)
                   VALUES (%s, %s, %s, %s)
                   RETURNING id;
               """, (station_info['email'], station_info['name'], station_info['type'], station_info['url']))

            result = self.cursor.fetchone()
            return result[0] if result else None

    def insert_gas_station_address(self, gas_station_id, address_info):
        self.cursor.execute("""
            INSERT INTO gas_station_address (gas_station_id, feedbacks, rating, updated_at)
            VALUES (%s, %s, %s, %s) RETURNING id;
        """, (gas_station_id, address_info['feedbacks'], address_info['rating'], address_info['updated_at']))
        return self.cursor.fetchone()[0]

    def insert_oil_type(self, oil_type):
        self.cursor.execute("""
            INSERT INTO oil_types (type) VALUES (%s)
            ON CONFLICT (type) DO NOTHING;
        """, (oil_type,))

    def insert_oil_price(self, gas_station_address_id, oil_type, price):
        self.cursor.execute("""
            INSERT INTO oil_type_price_address (date, price, gas_station_address_id, oil_type_id)
            VALUES (%s, %s, %s, %s);
        """, (datetime.date.today(), price, gas_station_address_id, oil_type))

    def commit(self):
        self.conn.commit()

    def close(self):
        self.cursor.close()
        self.conn.close()
