import React from 'react';
import Header from '../components/Header/Header';
import { motion } from 'framer-motion';

// Inline GasStationCard component
const GasStationCard: React.FC<{ logo: string; name: string }> = ({
  logo,
  name,
}) => (
  <div className="bg-white rounded-lg shadow-lg p-6 hover:shadow-xl transition-shadow duration-300">
    <img src={logo} alt={`${name} logo`} className="w-24 h-24 mx-auto mb-4" />
    <h3 className="text-xl font-semibold text-center text-[#005F6A]">{name}</h3>
  </div>
);

const AboutSection: React.FC = () => (
  <section className="bg-white rounded-lg shadow-lg p-6 mb-8">
    <h2 className="text-2xl font-bold text-[#005F6A] mb-4">О нашем сервисе</h2>
    <p className="text-gray-700">
      Мы предоставляем актуальную информацию о автозаправочных станциях в вашем
      регионе.
    </p>
  </section>
);

export const HomePage: React.FC = () => {
  const stations = [
    {
      logo: 'https://placeholder.com/wp-content/uploads/2018/10/placeholder.com-logo1.png',
      name: 'Лукойл',
    },
    {
      logo: 'https://placeholder.com/wp-content/uploads/2018/10/placeholder.com-logo1.png',
      name: 'Газпром',
    },
    {
      logo: 'https://placeholder.com/wp-content/uploads/2018/10/placeholder.com-logo1.png',
      name: 'Роснефть',
    },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-b from-[#FAD201] to-[#005F6A]">
      <Header />

      <main className="container mx-auto px-4 py-8">
        <AboutSection />

        <section id="stations" className="mt-16">
          <motion.h2
            className="text-4xl font-bold text-center text-white mb-12"
            initial={{ opacity: 0, y: -20 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.5 }}
          >
            Список автозаправочных станций
          </motion.h2>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
            {stations.map((station, index) => (
              <motion.div
                key={index}
                initial={{ opacity: 0, scale: 0.9 }}
                animate={{ opacity: 1, scale: 1 }}
                transition={{ duration: 0.5, delay: index * 0.1 }}
              >
                <GasStationCard logo={station.logo} name={station.name} />
              </motion.div>
            ))}
          </div>
        </section>
      </main>

      <footer className="bg-[#005F6A] text-white py-6 mt-16">
        <div className="container mx-auto px-4 text-center">
          <p>&copy; 2023 АЗС Информация. Все права защищены.</p>
        </div>
      </footer>
    </div>
  );
};
