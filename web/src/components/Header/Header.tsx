import React, { useState } from 'react';
import LoginForm from '../LoginForm/LoginForm';
import RegistrationForm from '../RegistrationForm/RegistrationForm';

const Header: React.FC = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [showLogin, setShowLogin] = useState(false);
  const [showRegistration, setShowRegistration] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <>
      {/* Header */}
      <header className="bg-[#005F6A] text-white">
        <div className="container mx-auto px-16 py-4 flex justify-between items-center">
          <div className="text-2xl font-bold">Логотип</div>

          {/* Navigation links - hidden on small screens */}
          <nav className="hidden md:flex space-x-4">
            <a
              href="#stations"
              className="hover:text-[#FAD201] transition-colors"
            >
              Список АЗС
            </a>
            <a
              href="#contact"
              className="hover:text-[#FAD201] transition-colors"
            >
              Контакты
            </a>
            <button
              onClick={() => setShowLogin(true)}
              className="hover:text-[#FAD201] transition-colors"
            >
              Вход
            </button>
            <button
              onClick={() => setShowRegistration(true)}
              className="hover:text-[#FAD201] transition-colors"
            >
              Регистрация
            </button>
          </nav>

          {/* Burger menu - visible on small screens */}
          <button
            className="md:hidden text-3xl focus:outline-none"
            onClick={toggleSidebar}
          >
            ☰
          </button>
        </div>
      </header>

      {/* Sidebar */}
      <div
        className={`fixed top-0 left-0 h-full w-[10rem] bg-[#005F6A] text-white transform ${
          isSidebarOpen ? 'translate-x-0' : '-translate-x-full'
        } transition-transform duration-300 ease-in-out z-50`}
      >
        <button
          className="text-3xl p-4 focus:outline-none"
          onClick={toggleSidebar}
        >
          ✕
        </button>
        <nav className="mt-8 space-y-4">
          <a href="#stations" className="block px-4 py-2 hover:bg-[#FAD201]">
            Список АЗС
          </a>
          <a href="#contact" className="block px-4 py-2 hover:bg-[#FAD201]">
            Контакты
          </a>
          <button
            onClick={() => {
              setShowLogin(true);
              toggleSidebar();
            }}
            className="block px-4 py-2 hover:bg-[#FAD201]"
          >
            Вход
          </button>
          <button
            onClick={() => {
              setShowRegistration(true);
              toggleSidebar();
            }}
            className="block px-4 py-2 hover:bg-[#FAD201]"
          >
            Регистрация
          </button>
        </nav>
      </div>

      {/* Overlay for closing the sidebar */}
      {isSidebarOpen && (
        <div
          className="fixed inset-0 bg-black opacity-50 z-40"
          onClick={toggleSidebar}
        ></div>
      )}

      {/* Модальные окна для входа и регистрации */}
      {showLogin && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <LoginForm />
          <button
            onClick={() => setShowLogin(false)}
            className="absolute top-4 right-4 text-white text-2xl"
          >
            &times;
          </button>
        </div>
      )}

      {showRegistration && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
          <RegistrationForm />
          <button
            onClick={() => setShowRegistration(false)}
            className="absolute top-4 right-4 text-white text-2xl"
          >
            &times;
          </button>
        </div>
      )}
    </>
  );
};

export default Header;