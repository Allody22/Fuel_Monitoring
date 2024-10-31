from energy_parser import fetch_fuel_prices_energy
from gasprom_parser import fetch_fuel_prices_gasprom
from prime_parser import fetch_fuel_prices_prime
from utils import add_or_get_station_id, add_fuel_info


def process_network(network_name, station_info, fetch_fuel_prices_function):
    # Добавляем или получаем ID заправки
    gas_station_id = add_or_get_station_id(station_info)

    # Получаем данные о ценах на топливо
    fuel_prices = fetch_fuel_prices_function()

    # Добавляем информацию о топливе для каждой заправки
    add_fuel_info(gas_station_id, fuel_prices)

    print(f"Успешное добавление данных для сети {network_name}")


def main():
    # Информация о сети "Энергия"
    energy_info = {
        'email': 'info@azsenergia.ru',
        'name': 'АЗС Энергия',
        'type': 'Розничная сеть',
        'url': 'https://azsenergia.ru'
    }
    process_network("Энергия", energy_info, fetch_fuel_prices_energy)

    # Информация о сети "Прайм"
    prime_info = {
        'email': 'info@azsprime.ru',
        'name': 'АЗС Прайм',
        'type': 'Розничная сеть',
        'url': 'https://azs-prime.ru'
    }
    process_network("Прайм", prime_info, fetch_fuel_prices_prime)

    # Информация о сети "Газпром"
    gazprom_info = {
        'email': 'info@retail.gazpromlpg.ru',
        'name': 'АЗС Газпром',
        'type': 'Розничная сеть',
        'url': 'https://azsgazprom.ru'
    }
    process_network("Газпром", gazprom_info, fetch_fuel_prices_gasprom)


if __name__ == "__main__":
    main()
