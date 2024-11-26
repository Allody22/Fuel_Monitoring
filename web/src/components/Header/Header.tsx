import React, { useState } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import { Menu, X } from 'lucide-react';
import { useLocation } from 'wouter';

const Header: React.FC = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);
  const [location, navigate] = useLocation();

  const toggleSidebar = () => setIsSidebarOpen(!isSidebarOpen);

  // Условное меню в зависимости от текущего пути
  const navItems = location === '/auth'
    ? [
        { action: () => navigate('/'), label: 'Главная' },
      ]
    : [
        { href: '#stations', label: 'Список АЗС' },
        { href: '#contact', label: 'Контакты' },
        { action: () => navigate('/auth'), label: 'Авторизоваться' },
      ];

  return (
    <>
      <header className="bg-[#005F6A] shadow-md">
        <div className="container mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex justify-between items-center">
            <span className="text-2xl font-bold text-white">АЗС Инфо</span>

            <nav className="hidden md:flex space-x-8">
              {navItems.map((item, index) => (
                <button
                  key={index}
                  onClick={item.action || undefined}
                  className="text-white hover:text-[#FAD201] transition-colors duration-200 ease-in-out"
                >
                  {item.label}
                </button>
              ))}
            </nav>

            <button
              className="md:hidden text-white focus:outline-none"
              onClick={toggleSidebar}
              aria-label="Открыть меню"
            >
              <Menu size={24} />
            </button>
          </div>
        </div>
      </header>

      <AnimatePresence>
        {isSidebarOpen && (
          <>
            <motion.div
              initial={{ x: '100%' }}
              animate={{ x: 0 }}
              exit={{ x: '100%' }}
              transition={{ type: 'tween', duration: 0.3 }}
              className="fixed top-0 right-0 h-full w-64 bg-[#005F6A] text-white z-50 overflow-y-auto"
            >
              <div className="flex justify-between items-center p-4 border-b border-[#FAD201]">
                <span className="text-xl font-bold">Меню</span>
                <button
                  onClick={toggleSidebar}
                  className="text-white focus:outline-none"
                  aria-label="Закрыть меню"
                >
                  <X size={24} />
                </button>
              </div>
              <nav className="mt-8 space-y-4 px-4">
                {navItems.map((item, index) => (
                  <div key={index} className="block">
                    <button
                      onClick={() => {
                        if (item.action) item.action();
                        toggleSidebar();
                      }}
                      className="text-white hover:text-[#FAD201] transition-colors duration-200 ease-in-out w-full text-left"
                    >
                      {item.label}
                    </button>
                  </div>
                ))}
              </nav>
            </motion.div>
            <motion.div
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              transition={{ duration: 0.3 }}
              className="fixed inset-0 bg-black bg-opacity-50 z-40"
              onClick={toggleSidebar}
            />
          </>
        )}
      </AnimatePresence>
    </>
  );
};

export default Header;
