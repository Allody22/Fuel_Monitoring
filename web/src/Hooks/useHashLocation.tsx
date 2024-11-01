import { useLocation, useRoute } from 'wouter'; // Импортируем useLocation
import { useCallback } from 'react';

// Функция для получения местоположения на основе хэша URL
const hashLocation = () => window.location.hash.replace(/^#/, '') || '/';

// Хук для управления хэш-маршрутизацией
export const useHashLocation = () => {
  const [location, setLocation] = useLocation(); // Получаем текущее местоположение и функцию для его установки

  // Оборачиваем навигацию в useCallback для оптимизации
  const hashNavigate = useCallback((to: string) => {
    setLocation('#' + to); // Устанавливаем новое местоположение
  }, [setLocation]);

  return [location, hashNavigate]; // Возвращаем местоположение и функцию навигации
};
