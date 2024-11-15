import React, { useEffect, useState } from 'react';
import { FaBars } from 'react-icons/fa';

interface HeaderProps {
  toggleSidebar: () => void;
  isSidebarOpen: boolean;
}

const Header: React.FC<HeaderProps> = ({ toggleSidebar, isSidebarOpen }) => {
  const [isBurgerMenuVisible, setBurgerMenuVisible] = useState(false);
  const [size, setSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight,
  });

  useEffect(() => {
    const handleResize = () => {
      const newWidth = window.innerWidth;
      setSize({
        width: newWidth,
        height: window.innerHeight,
      });

      setBurgerMenuVisible(newWidth <= 1200);
    };

    window.addEventListener('resize', handleResize);
    handleResize();
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const handleBurgerClick = () => {
    toggleSidebar(); // Открытие или закрытие сайдбара
  };

  return (
    <header
      className={`flex min-w-[22rem] justify-between items-center relative font-dela h-16 ${
        size.width <= 768 ? 'bg-custom-blue' : 'bg-custom-blue'
      }`}
    >
      {/* Левая надпись
      <div
        className={`flex space-x-2 h-full relative central-text ml-8 ${
          size.width <= 768 ? 'shift-left' : ''
        }`}
      >
        <div className="flex">
          <span
            className={`animate__animated animate__fadeInLeft pl-8 font-medium z-10 text-[1.2rem] tracking-wider ${
              size.width <= 768 ? 'text-white' : 'text-white'
            }`}
          >
            {t('header.siberian')}
          </span>
        </div>
        <span
          className={`animate__animated animate__fadeInRight text-[1.2rem] tracking-wider font-medium ${
            size.width <= 768 ? 'text-white' : 'text-white'
          } neural-network`}
        >
          {t('header.neural')}
        </span>
      </div> */}

      {/* Контейнер для остальных элементов */}
      {!isBurgerMenuVisible && (
        <nav className="flex items-center text-[1rem] mr-10 gap-3">
          <a
            href="#about-us"
            className="animate__animated animate__fadeInRight delay-second px-3 py-2 rounded-full hover:bg-white hover:text-custom-blue duration-200 cursor-pointer z-10 text-white font-medium"
          >
          </a>
          <a
            href="#products"
            className="animate__animated animate__fadeInRight delay-second px-3 py-2 rounded-full hover:bg-white hover:text-custom-blue duration-200 cursor-pointer z-10 text-white font-medium"
          >
          </a>
          <a
            href="#about"
            className="animate__animated animate__fadeInRight delay-second px-3 py-2 rounded-full hover:bg-white hover:text-custom-blue duration-200 cursor-pointer z-10 text-white font-medium"
          >
            О нас
          </a>
        </nav>
      )}

      {/* Бургер-меню */}
      {isBurgerMenuVisible &&
        !isSidebarOpen && ( // Проверяем, открыт ли сайдбар
          <div className="z-20 md:block absolute top-4 right-[1rem]">
            <button className="burger-menu" onClick={handleBurgerClick}>
              <FaBars size={32} color="white" />
            </button>
          </div>
        )}
    </header>
  );
};

export default Header;
