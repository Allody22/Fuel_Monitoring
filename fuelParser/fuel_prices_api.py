from flask import Flask, request, jsonify

from db_writer import DBWriter
from energy_parser import fetch_fuel_prices_energy
from gasprom_parser import fetch_fuel_prices_gasprom
from prime_parser import fetch_fuel_prices_prime
from utils import add_or_get_station_id, add_fuel_info

app = Flask(__name__)

DB_SETTINGS = {
    'dbname': 'fuel_monitoring',
    'user': 'postgres',
    'password': '0000',
    'host': 'localhost'
}


@app.route('/api/add_gas_station', methods=['POST'])
def add_gas_station():
    data = request.json
    db_writer = DBWriter(DB_SETTINGS)

    try:
        gas_station_id = db_writer.insert_or_get_gas_station(data)
        db_writer.commit()
        return jsonify({'gas_station_id': gas_station_id}), 201
    except Exception as e:
        return jsonify({'error': str(e)}), 500
    finally:
        db_writer.close()


@app.route('/api/add_fuel_info', methods=['POST'])
def add_fuel_info_route():
    data = request.json
    gas_station_id = data['gas_station_id']
    fuel_prices = data['fuel_prices']

    try:
        add_fuel_info(gas_station_id, fuel_prices)
        return jsonify({'message': 'Fuel information added successfully'}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/fetch_prices/<network>', methods=['GET'])
def fetch_prices(network):
    fetch_function = {
        'energia': fetch_fuel_prices_energy,
        'gasprom': fetch_fuel_prices_gasprom,
        'prime': fetch_fuel_prices_prime
    }.get(network.lower())

    if not fetch_function:
        return jsonify({'error': 'Network not supported'}), 400

    try:
        fuel_prices = fetch_function()
        return jsonify(fuel_prices), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


@app.route('/api/update_prices/<network>', methods=['POST'])
def update_prices(network):
    station_info_map = {
        'energia': {
            'email': 'info@azsenergia.ru',
            'name': 'АЗС Энергия',
            'type': 'Розничная сеть',
            'url': 'https://azsenergia.ru'
        },
        'prime': {
            'email': 'info@azsprime.ru',
            'name': 'АЗС Прайм',
            'type': 'Розничная сеть',
            'url': 'https://azs-prime.ru'
        },
        'gasprom': {
            'email': 'info@retail.gazpromlpg.ru',
            'name': 'АЗС Газпром',
            'type': 'Розничная сеть',
            'url': 'https://azsgazprom.ru'
        }
    }

    station_info = station_info_map.get(network.lower())
    fetch_function = {
        'energia': fetch_fuel_prices_energy,
        'gasprom': fetch_fuel_prices_gasprom,
        'prime': fetch_fuel_prices_prime
    }.get(network.lower())

    if not station_info or not fetch_function:
        return jsonify({'error': 'Network not supported'}), 400

    try:
        gas_station_id = add_or_get_station_id(station_info)
        fuel_prices = fetch_function()
        add_fuel_info(gas_station_id, fuel_prices)
        return jsonify({'message': f'Prices updated for network {network}'}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(debug=True)
