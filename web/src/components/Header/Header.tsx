import React, { useState } from 'react';
import { Link } from 'wouter'; 
import { Fuel, Menu, X, LogIn, LogOut } from 'lucide-react';
import { Button } from '../Button/Button';
import { ModeToggle } from '../ModeToggle/ModeToggle';
import clsx from 'clsx';
import { useHashLocation } from '../../Hooks/useHashLocation'; // Импортируйте useHashLocation из вашего файла с хуками

const navigation = [
  { name: 'Главная', href: '/' },
  { name: 'Цены на топливо', href: '/fuel-prices' },
  { name: 'Карта АЗС', href: '/gas-stations' },
  { name: 'О нас', href: '/about' },
];

export default function Header() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [location] = useHashLocation(); // Используем ваш кастомный хук

  const handleLogin = () => setIsLoggedIn(true);
  const handleLogout = () => setIsLoggedIn(false);

  return (
    <header className="bg-background shadow-sm">
      <nav className="mx-auto flex max-w-7xl items-center justify-between p-6 lg:px-8" aria-label="Global">
        <div className="flex lg:flex-1">
          <Link href="/" className="-m-1.5 p-1.5">
            <span className="sr-only">Fuel Price Tracker</span>
            <Fuel className="h-8 w-auto text-primary" />
          </Link>
        </div>
        <div className="flex lg:hidden">
          <Button variant="ghost" className="-m-2.5 inline-flex items-center justify-center rounded-md p-2.5" onClick={() => setMobileMenuOpen(!mobileMenuOpen)}>
            <span className="sr-only">Открыть главное меню</span>
            {mobileMenuOpen ? <X className="h-6 w-6" aria-hidden="true" /> : <Menu className="h-6 w-6" aria-hidden="true" />}
          </Button>
        </div>
        <div className="hidden lg:flex lg:gap-x-12">
          {navigation.map((item) => (
            <Link
              key={item.name}
              href={item.href}
              className={clsx('text-sm font-semibold leading-6', location === item.href ? 'text-primary' : 'text-muted-foreground hover:text-primary')}
            >
              {item.name}
            </Link>
          ))}
        </div>
        <div className="hidden lg:flex lg:flex-1 lg:justify-end lg:gap-x-4">
          <ModeToggle />
          {isLoggedIn ? (
            <Button onClick={handleLogout} variant="outline">
              <LogOut className="mr-2 h-4 w-4" />
              Выйти
            </Button>
          ) : (
            <Button onClick={handleLogin}>
              <LogIn className="mr-2 h-4 w-4" />
              Войти
            </Button>
          )}
        </div>
      </nav>
      {mobileMenuOpen && (
        <div className="lg:hidden w-full bg-background shadow-lg p-4">
          {navigation.map((item) => (
            <Link
              key={item.name}
              href={item.href}
              className={clsx('block px-3 py-2 text-base font-semibold leading-7', location === item.href ? 'text-primary' : 'text-muted-foreground hover:text-primary')}
              onClick={() => setMobileMenuOpen(false)}
            >
              {item.name}
            </Link>
          ))}
          <div className="mt-4">
            <ModeToggle />
          </div>
          <div className="mt-4">
            {isLoggedIn ? (
              <Button onClick={handleLogout} variant="outline" className="w-full justify-start">
                <LogOut className="mr-2 h-4 w-4" />
                Выйти
              </Button>
            ) : (
              <Button onClick={handleLogin} className="w-full justify-start">
                <LogIn className="mr-2 h-4 w-4" />
                Войти
              </Button>
            )}
          </div>
        </div>
      )}
    </header>
  );
}
