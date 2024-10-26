import requests
from bs4 import BeautifulSoup

url = "https://azs-prime.ru/azs-maps"


def fetch_fuel_prices_prime():
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')

        azs_table = soup.find('table', id='azsnet-list')
        azs_data = []

        # Извлечение информации о доступности топлива для каждой АЗС
        for row in azs_table.find('tbody').find_all('tr'):
            cells = row.find_all('td')
            address = cells[0].text.strip()

            fuel_availability = {
                'PRIME 92': 'Есть' if cells[1].find('i', class_='fa-check') else 'Нет',
                'PRIME 95': 'Есть' if cells[2].find('i', class_='fa-check') else 'Нет',
                'PRIME 98': 'Есть' if cells[3].find('i', class_='fa-check') else 'Нет',
                'АИ-92': 'Есть' if cells[4].find('i', class_='fa-check') else 'Нет',
                'ДТ': 'Есть' if cells[5].find('i', class_='fa-check') else 'Нет',
                'ДТ PRIME': 'Есть' if cells[6].find('i', class_='fa-check') else 'Нет',
                'ГАЗ(СУГ)': 'Есть' if cells[7].find('i', class_='fa-check') else 'Нет'
            }

            azs_data.append({
                'address': address,
                'fuel_availability': fuel_availability
            })

        # Извлечение информации о ценах на топливо
        price_table = soup.find('table', id='azsnet-list-price')
        fuel_prices = {}

        for row in price_table.find('tbody').find_all('tr'):
            fuel_type = row.find_all('td')[0].text.strip()
            price = row.find_all('td')[1].text.strip()
            fuel_prices[fuel_type] = price

        # Объединение данных доступности и цен для каждой АЗС
        network_data = []
        for azs in azs_data:
            network_data.append({
                'address': azs['address'],
                'prices': {
                    fuel: fuel_prices.get(fuel, 'Нет данных') if availability == 'Есть' else 'Нет данных'
                    for fuel, availability in azs['fuel_availability'].items()
                }
            })

        print(network_data)
        return network_data
    else:
        print(f"Ошибка при запросе страницы: {response.status_code}")
        return []