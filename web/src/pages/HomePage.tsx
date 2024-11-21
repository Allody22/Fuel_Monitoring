import React from 'react';
import Header from '../components/Header/Header';
import AboutSection from '../components/AboutSection/AboutSection';
import GasStationCard from '../components/GasStationCard/GasStationCard';

export const HomePage: React.FC = () => {
  const stations = [
    { logo: '/logos/lukoil.png', name: 'Лукойл' },
    { logo: '/logos/lukoil.png', name: 'Газпром' },
    { logo: '/logos/lukoil.png', name: 'Роснефть' },
  ];

  return (
    <div>
      {/* Хедер */}
      <Header />

      {/* Описание сайта */}
      <AboutSection />

      {/* Секция карточек АЗС */}
      <section id="stations" className="bg-[#FAD201] py-10">
        <div className="container mx-auto px-4">
          <h2 className="text-3xl font-bold text-center text-[#005F6A] mb-8">
            Список автозаправочных станций
          </h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
            {stations.map((station, index) => (
              <GasStationCard
                key={index}
                logo={station.logo}
                name={station.name}
              />
            ))}
          </div>
        </div>
      </section>
    </div>
  );
};