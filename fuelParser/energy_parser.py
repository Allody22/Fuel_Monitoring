import requests
from bs4 import BeautifulSoup

url = "https://azsenergia.ru/set-azs#tablefield-0"


def fetch_fuel_prices_energy():
    response = requests.get(url)

    if response.status_code == 200:
        soup = BeautifulSoup(response.content, 'html.parser')

        table = soup.find('table', id='tablefield-node-7-field_table-0')
        network_data = []

        for row in table.find('tbody').find_all('tr'):
            cells = row.find_all('td')
            address = cells[0].text.strip()  # Адрес

            def get_price(text):
                return text.strip() if text.strip() and text.strip() != '--' else 'Нет данных'

            # Обработка цены для Метан и Газ отдельно
            metan_gaz_text = get_price(cells[7].text)
            if '/' in metan_gaz_text:
                metan_price, gaz_price = metan_gaz_text.split('/')
                metan_price = metan_price.strip()
                gaz_price = gaz_price.strip()
            else:
                metan_price = metan_gaz_text
                gaz_price = 'Нет данных'

            fuel_data = {
                'АИ-92': get_price(cells[1].text),
                'АИ-95': get_price(cells[2].text),
                'АИ-98': get_price(cells[3].text),
                'АИ-100': get_price(cells[4].text),
                'ДТ-класс-5': get_price(cells[5].text),
                'ДТ-Евро': get_price(cells[6].text),
                'Метан': metan_price,
                'Газ': gaz_price
            }

            network_data.append({
                'address': address,
                'prices': fuel_data
            })
        print(network_data)
        return network_data
    else:
        print(f"Ошибка при запросе страницы: {response.status_code}")
        return []
