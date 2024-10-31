import requests
from bs4 import BeautifulSoup

url = "https://ftcard.ru/brendy-seti-karty/gazpromneft/novosibirskaya-oblast/"


def fetch_fuel_prices_gasprom():
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')

        table = soup.find('div', class_='azs-table').find('table')

        network_data = []

        for row in table.find('tbody').find_all('tr'):
            cells = row.find_all('td')
            address = cells[2].text.strip()

            fuel_data = {
                'АИ-92': 'Нет данных',
                'АИ-95': 'Нет данных',
                'АИ-98': 'Нет данных',
                'Газ': 'Нет данных',
                'ДТ': 'Нет данных'
            }

            fuel_block = cells[4]
            for fuel_div in fuel_block.find_all('div', class_='fuel-block'):
                fuel_type = fuel_div.find('div', class_='fuel-block-title').text.strip()
                fuel_price = fuel_div.find('div', class_='fuel-block-price')
                price = fuel_price.text.strip() if fuel_price else "Нет данных"

                if fuel_type == '92':
                    fuel_data['АИ-92'] = price
                elif fuel_type == '95':
                    fuel_data['АИ-95'] = price
                elif fuel_type == '98':
                    fuel_data['АИ-98'] = price
                elif fuel_type.lower() in ['газ', 'метан']:
                    fuel_data['Газ'] = price
                elif fuel_type == 'ДТ':
                    fuel_data['ДТ'] = price

            network_data.append({
                'address': address,
                'prices': fuel_data
            })

        print(network_data)
        return network_data
    else:
        print(f"Ошибка при запросе страницы: {response.status_code}")
