'use client';
import React, { useState } from 'react';
import Header from '../components/Header/Header'; // Импортируем хэдер
import { useLocation } from 'wouter';
import AuthService from '../components/AuthService/AuthService';

export const AuthPage: React.FC = () => {
  const [isLoading, setIsLoading] = useState<boolean>(false);
  const [activeTab, setActiveTab] = useState<'login' | 'register'>('login');
  const [formData, setFormData] = useState({
    phoneNumber: '',
    password: '',
    name: '',
  });
  const [location, setLocation] = useLocation();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { id, value } = e.target;
    setFormData((prev) => ({ ...prev, [id]: value }));
  };

  const onSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsLoading(true);

    try {
      if (activeTab === 'login') {
        const response = await AuthService.login(formData.phoneNumber, formData.password, "Unused");
        alert('Успешный вход: ' + JSON.stringify(response));
        const expirationDate = new Date(Date.now() + response.expires_in).toUTCString();
        document.cookie = `access_token=${response.access_token}; expires=${expirationDate}; path=/; Secure; SameSite=Strict`;
        setLocation('/gas-stations'); // Перемещаем пользователя на страницу с АЗС
      } else {
        const response = await AuthService.register(formData.phoneNumber, formData.password, "Unused");
        alert('Успешная регистрация: ' + JSON.stringify(response));
      }
    } catch (error: any) {
      alert('Ошибка: ' + error.response?.data?.message || 'Что-то пошло не так.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <>
      {/* Включаем хэдер */}
      <Header />

      <div className="min-h-screen flex items-center justify-center bg-gradient-to-b from-[#FAD201] to-[#005F6A]">
        <div className="w-full max-w-md bg-white rounded-lg shadow-lg overflow-hidden">
          <div className="p-8">
            <h2 className="text-3xl font-bold text-[#005F6A] mb-6 text-center">Добро пожаловать</h2>
            <div className="flex justify-around mb-6">
              <button
                className={`w-1/2 py-2 text-center rounded-l-lg ${
                  activeTab === 'login'
                    ? 'bg-[#005F6A] text-white'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                }`}
                onClick={() => setActiveTab('login')}
              >
                Вход
              </button>
              <button
                className={`w-1/2 py-2 text-center rounded-r-lg ${
                  activeTab === 'register'
                    ? 'bg-[#005F6A] text-white'
                    : 'bg-gray-200 text-gray-700 hover:bg-gray-300'
                }`}
                onClick={() => setActiveTab('register')}
              >
                Регистрация
              </button>
            </div>

            <form onSubmit={onSubmit}>
              {activeTab === 'register' && (
                <div className="mb-4">
                  <label htmlFor="name" className="block text-sm font-bold text-gray-700 mb-2">
                    Имя
                  </label>
                  <input
                    id="name"
                    type="text"
                    placeholder="Ваше имя"
                    value={formData.name}
                    onChange={handleChange}
                    className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#005F6A]"
                    required
                    disabled={isLoading}
                  />
                </div>
              )}
              <div className="mb-4">
                <label htmlFor="phoneNumber" className="block text-sm font-bold text-gray-700 mb-2">
                  Номер телефона
                </label>
                <input
                  id="phoneNumber"
                  type="phoneNumber"
                  placeholder="name@example.com"
                  value={formData.phoneNumber}
                  onChange={handleChange}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#005F6A]"
                  required
                  disabled={isLoading}
                />
              </div>
              <div className="mb-6">
                <label htmlFor="password" className="block text-sm font-bold text-gray-700 mb-2">
                  Пароль
                </label>
                <input
                  id="password"
                  type="password"
                  placeholder="Пароль"
                  value={formData.password}
                  onChange={handleChange}
                  className="w-full px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#005F6A]"
                  required
                  disabled={isLoading}
                />
              </div>
              <button
                type="submit"
                className={`w-full bg-[#005F6A] text-white py-2 px-4 rounded-lg hover:bg-[#004653] focus:outline-none focus:ring-2 focus:ring-[#FAD201] ${
                  isLoading ? 'opacity-50 cursor-not-allowed' : ''
                }`}
                disabled={isLoading}
              >
                {isLoading ? 'Загрузка...' : activeTab === 'login' ? 'Войти' : 'Зарегистрироваться'}
              </button>
            </form>
          </div>
          <div className="bg-gray-100 px-6 py-4 border-t border-gray-200 text-center">
            <button
              className="text-[#005F6A] hover:text-[#004653] focus:outline-none focus:underline"
              disabled={isLoading}
            >
              Забыли пароль?
            </button>
          </div>
        </div>
      </div>
    </>
  );
};
