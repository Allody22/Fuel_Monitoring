import React, { useState } from 'react';

const Header: React.FC = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  const toggleSidebar = () => {
    setIsSidebarOpen(!isSidebarOpen);
  };

  return (
    <>
      {/* Header */}
      <header className="bg-[#005F6A] text-white">
        <div className="container mx-auto px-4 py-4 flex justify-between items-center">
          <div className="text-2xl font-bold">Логотип</div>

          {/* Navigation links - hidden on small screens */}
          <nav className="hidden md:flex space-x-4">
            <a href="#home" className="hover:text-[#FAD201] transition-colors">
              Главная
            </a>
            <a href="#about" className="hover:text-[#FAD201] transition-colors">
              О нас
            </a>
            <a href="#services" className="hover:text-[#FAD201] transition-colors">
              Услуги
            </a>
            <a href="#contact" className="hover:text-[#FAD201] transition-colors">
              Контакты
            </a>
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
          <a href="#home" className="block px-4 py-2 hover:bg-[#FAD201]">
            Главная
          </a>
          <a href="#about" className="block px-4 py-2 hover:bg-[#FAD201]">
            О нас
          </a>
          <a href="#services" className="block px-4 py-2 hover:bg-[#FAD201]">
            Услуги
          </a>
          <a href="#contact" className="block px-4 py-2 hover:bg-[#FAD201]">
            Контакты
          </a>
        </nav>
      </div>

      {/* Overlay for closing the sidebar */}
      {isSidebarOpen && (
        <div
          className="fixed inset-0 bg-black opacity-50 z-40"
          onClick={toggleSidebar}
        ></div>
      )}
    </>
  );
};

export default Header;
