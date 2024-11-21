import React, { useState } from 'react';
import AuthService from '../AuthService/AuthService';

const RegistrationForm: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await AuthService.register(email, password);
      // Перенаправление или обновление состояния при успешной регистрации
    } catch (err) {
      setError('Ошибка регистрации');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="max-w-md mx-auto p-6 bg-[#005f6a] rounded shadow-md">
      <h2 className="text-2xl font-bold text-white mb-6">Регистрация</h2>
      {error && <p className="text-red-500 mb-4">{error}</p>}
      <div className="mb-4">
        <label htmlFor="email" className="block text-sm font-medium text-white">
          Email
        </label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#fad201] focus:border-[#fad201] sm:text-sm"
          required
        />
      </div>
      <div className="mb-6">
        <label htmlFor="password" className="block text-sm font-medium text-white">
          Пароль
        </label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-[#fad201] focus:border-[#fad201] sm:text-sm"
          required
        />
      </div>
      <button
        type="submit"
        className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-[#005f6a] bg-[#fad201] hover:bg-[#fad201] focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
      >
        Зарегистрироваться
      </button>
    </form>
  );
};

export default RegistrationForm;