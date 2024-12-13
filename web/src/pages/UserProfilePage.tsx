import React, { useState, useEffect } from 'react';
import Header from '../components/Header/Header';
import axios from 'axios';

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1',
});

interface FavoriteStation {
    id: number;
    address: string;
    name: string;
    rating: number;
}

const UserProfilePage: React.FC = () => {
    const [userInfo, setUserInfo] = useState<{ name: string; email: string } | null>(null);
    const [favorites, setFavorites] = useState<FavoriteStation[]>([]);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                setIsLoading(true);
                const response = await apiClient.get('/auth/profile', {
                    headers: {
                        Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
                    },
                });
                setUserInfo(response.data);
            } catch (err: any) {
                setError(err.response?.data?.message || 'Не удалось загрузить профиль.');
            }
        };

        const fetchFavorites = async () => {
            try {
                const response = await apiClient.get('/user/favorites', {
                    headers: {
                        Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
                    },
                });
                setFavorites(response.data);
            } catch (err: any) {
                console.error('Не удалось загрузить избранное:', err);
            }
        };

        fetchUserProfile();
        fetchFavorites();
        setIsLoading(false);
    }, []);

    const removeFavorite = async (stationId: number) => {
        try {
            await apiClient.delete(`/user/favorites/${stationId}`, {
                headers: {
                    Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
                },
            });
            setFavorites(favorites.filter((station) => station.id !== stationId));
        } catch (err: any) {
            console.error('Ошибка при удалении из избранного:', err);
        }
    };

    return (
        <>
            <Header />
            <div className="min-h-screen bg-gradient-to-b from-[#FAD201] to-[#005F6A] py-8 px-4">
                <div className="max-w-5xl mx-auto bg-white rounded-lg shadow-lg p-8">
                    <h1 className="text-3xl font-bold text-[#005F6A] mb-6 text-center">Профиль пользователя</h1>

                    {isLoading ? (
                        <p className="text-center text-gray-600">Загрузка...</p>
                    ) : error ? (
                        <p className="text-center text-red-600">{error}</p>
                    ) : (
                        <>
                            {userInfo && (
                                <div className="mb-6">
                                    <h2 className="text-2xl font-bold text-[#005F6A]">Личная информация</h2>
                                    <p className="text-gray-600">Имя: {userInfo.name}</p>
                                    <p className="text-gray-600">Email: {userInfo.email}</p>
                                </div>
                            )}

                            <div>
                                <h2 className="text-2xl font-bold text-[#005F6A] mb-4">Избранные заправки</h2>
                                {favorites.length === 0 ? (
                                    <p className="text-gray-600">Нет избранных заправок.</p>
                                ) : (
                                    <ul className="list-disc pl-5 space-y-4">
                                        {favorites.map((station) => (
                                            <li key={station.id} className="border border-gray-300 rounded-lg p-4">
                                                <p className="text-gray-600">{station.name}</p>
                                                <p className="text-gray-500">Адрес: {station.address}</p>
                                                <p className="text-gray-500">Рейтинг: {station.rating} ⭐</p>
                                                <button
                                                    onClick={() => removeFavorite(station.id)}
                                                    className="mt-2 px-4 py-2 bg-red-500 text-white rounded-lg hover:bg-red-400 focus:outline-none focus:ring-2 focus:ring-red-300"
                                                >
                                                    Удалить из избранного
                                                </button>
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </div>
                        </>
                    )}
                </div>
            </div>
        </>
    );
};

export default UserProfilePage;
