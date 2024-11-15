import React from 'react';
import Header from '../components/Header/Header';

export const HomePage: React.FC = () => (
  <div>
    <Header
      toggleSidebar={function (): void {
        throw new Error('Function not implemented.');
      }}
      isSidebarOpen={false}
    />
  </div>
);
