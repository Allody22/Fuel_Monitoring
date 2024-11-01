import React from 'react';
import { useState } from 'react';
import { Button } from '../Button/Button';

export const ModeToggle: React.FC = () => {
  const [theme, setTheme] = useState<'light' | 'dark'>('light');

  const toggleTheme = () => {
    const newTheme = theme === 'light' ? 'dark' : 'light';
    setTheme(newTheme);
    if (newTheme === 'dark') {
      document.documentElement.classList.add('dark');
    } else {
      document.documentElement.classList.remove('dark');
    }
  };

  return (
    <Button onClick={toggleTheme} variant="outline" size="small">
      {theme === 'light' ? '🌞' : '🌜'}
    </Button>
  );
};
