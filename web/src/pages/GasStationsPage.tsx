import React, { useEffect, useState } from 'react';
import { useLocation } from 'wouter';
import Header from '../components/Header/Header';
import axios from 'axios';

interface GasStationsPage {
  name: string;
  siteURL: string;
  email: string;
  type: string;
  gasStations: {
    id: number;
    address: string;
    rating: number;
    feedbacks: number;
    updatedAt: string;
  }[];
}

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1/stations',
});

const userApiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1/user',
});

export const GasStationsPage: React.FC = () => {
  const [gasStations, setGasStations] = useState<GasStationsPage[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);
  const [favorites, setFavorites] = useState<number[]>([]);
  const [, navigate] = useLocation();

  useEffect(() => {
    const fetchGasStations = async () => {
      try {
        setIsLoading(true);
        const response = await apiClient.get('/all', {
          headers: {
            Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
          },
        });
        setGasStations(response.data);
      } catch (err: any) {
        setError(err.response?.data?.message || 'Не удалось загрузить список АЗС.');
      } finally {
        setIsLoading(false);
      }
    };

    const fetchFavorites = async () => {
      try {
        const response = await userApiClient.get('/favorites', {
          headers: {
            Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
          },
        });
        setFavorites(response.data.map((fav: { id: number }) => fav.id));
      } catch (err: any) {
        console.error('Не удалось загрузить избранное:', err);
      }
    };

    fetchGasStations();
    fetchFavorites();
  }, []);

  const toggleFavorite = async (stationId: number) => {
    try {
      const isFavorite = favorites.includes(stationId);
      if (isFavorite) {
        await userApiClient.delete(`/favorites/${stationId}`, {
          headers: {
            Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
          },
        });
        setFavorites(favorites.filter((id) => id !== stationId));
      } else {
        await userApiClient.post(`/favorites/${stationId}`, null, {
          headers: {
            Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
          },
        });
        setFavorites([...favorites, stationId]);
      }
    } catch (err: any) {
      console.error('Ошибка при обновлении избранного:', err);
    }
  };

  return (
      <>
        <Header />
        <div className="min-h-screen bg-gradient-to-b from-[#FAD201] to-[#005F6A] py-8 px-4">
          <div className="max-w-5xl mx-auto bg-white rounded-lg shadow-lg p-8">
            <h1 className="text-3xl font-bold text-[#005F6A] mb-6 text-center">Список доступных АЗС</h1>

            {isLoading ? (
                <p className="text-center text-gray-600">Загрузка...</p>
            ) : error ? (
                <p className="text-center text-red-600">{error}</p>
            ) : gasStations.length === 0 ? (
                <p className="text-center text-gray-600">Нет доступных АЗС.</p>
            ) : (
                <div className="space-y-6">
                  {gasStations.map((station, index) => (
                      <div
                          key={index}
                          className="border border-gray-300 rounded-lg p-4 hover:shadow-md transition-shadow"
                      >
                        <h2 className="text-xl font-bold text-[#005F6A]">{station.name}</h2>
                        <p className="text-gray-600">Тип: {station.type}</p>
                        <p className="text-gray-600">
                          Сайт: <a href={station.siteURL} className="text-blue-600 underline">{station.siteURL}</a>
                        </p>
                        <p className="text-gray-600">Email: {station.email}</p>
                        <div className="mt-4">
                          <h3 className="font-bold text-[#005F6A]">Адреса:</h3>
                          <ul className="list-disc pl-5">
                            {station.gasStations.map((addressInfo, addrIndex) => (
                                <li key={addrIndex} className="mb-4">
                                  <p className="text-gray-600">Адрес: {addressInfo.address}</p>
                                  <p className="text-gray-500 text-sm">Рейтинг: {addressInfo.rating} ⭐</p>
                                  <p className="text-gray-500 text-sm">Количество отзывов: {addressInfo.feedbacks}</p>
                                  <p className="text-gray-500 text-sm">Дата последнего обновления: {new Date(addressInfo.updatedAt).toLocaleDateString()}</p>
                                  <button
                                      onClick={() => navigate(`/gas-stations/address/summary/${addressInfo.id}`)}
                                      className="mt-2 px-4 py-2 bg-[#005F6A] text-white rounded-lg hover:bg-[#004653] focus:outline-none focus:ring-2 focus:ring-[#FAD201]"
                                  >
                                    Посмотреть сводку
                                  </button>
                                  <button
                                      onClick={() => toggleFavorite(addressInfo.id)}
                                      className={`mt-2 ml-2 px-4 py-2 rounded-lg focus:outline-none focus:ring-2 ${favorites.includes(addressInfo.id) ? 'bg-red-500 text-white hover:bg-red-400' : 'bg-green-500 text-white hover:bg-green-400'}`}
                                  >
                                    {favorites.includes(addressInfo.id) ? 'Удалить из избранного' : 'Добавить в избранное'}
                                  </button>
                                </li>
                            ))}
                          </ul>
                        </div>
                      </div>
                  ))}
                </div>
            )}
          </div>
        </div>
      </>
  );
};
