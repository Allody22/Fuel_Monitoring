import requests
from bs4 import BeautifulSoup

url = "https://azsenergia.ru/set-azs#tablefield-0"

response = requests.get(url)

if response.status_code == 200:
    soup = BeautifulSoup(response.content, 'html.parser')

    table = soup.find('table', id='tablefield-node-7-field_table-0')

    fuel_prices = []

    for row in table.find('tbody').find_all('tr'):
        cells = row.find_all('td')
        address = cells[0].text.strip()  # Адрес


        def get_price(text):
            return text.strip() if text.strip() and text.strip() != '--' else 'Нет данных'


        fuel_data = {
            'АИ-92': get_price(cells[1].text),
            'АИ-95': get_price(cells[2].text),
            'АИ-98': get_price(cells[3].text),
            'АИ-100': get_price(cells[4].text),
            'ДТ-класс-5': get_price(cells[5].text),
            'ДТ-Евро': get_price(cells[6].text),
            'Метан/Газ': get_price(cells[7].text)
        }

        fuel_prices.append({
            'address': address,
            'prices': fuel_data
        })

    for fuel_price in fuel_prices:
        print(fuel_price)
else:
    print(f"Ошибка при запросе страницы: {response.status_code}")
