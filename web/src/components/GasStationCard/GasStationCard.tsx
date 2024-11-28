import React from 'react';

interface GasStationCardProps {
  logo: string; // Путь к логотипу
  name: string; // Название АЗС
}

const GasStationCard: React.FC<GasStationCardProps> = ({ logo, name }) => {
  return (
    <div className="flex flex-col items-center bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow duration-300 overflow-hidden">
      {/* Логотип АЗС */}
      <div className="w-full h-full">
        <img
          src={logo}
          alt={`${name} logo`}
          className="w-full h-full object-cover"
        />
      </div>

      {/* Название АЗС */}
      <div className="w-full text-center bg-[#005F6A] text-white py-3">
        <h3 className="text-lg font-semibold">{name}</h3>
      </div>
    </div>
  );
};

export default GasStationCard;
