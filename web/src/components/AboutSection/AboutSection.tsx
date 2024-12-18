import React from 'react';

const AboutSection: React.FC = () => {
  return (
    <section className="bg-[#005F6A] text-[#fad201] py-10">
      <div className="container mx-auto px-16 flex flex-col md:flex-row items-center">
        {/* Текстовый блок */}
        <div className="md:w-1/2 mb-6 md:mb-0 mr-6">
          <h2 className="text-4xl font-bold mb-4">
            Добро пожаловать на сайт мониторинга цен на бензин!
          </h2>
          <p className="text-2xl text-white mb-4">
            Наш сервис предоставляет актуальную информацию о ценах на бензин на
            автозаправочных станциях Новосибирска. Вы сможете легко найти
            ближайшую АЗС и сравнить цены на топливо, чтобы сэкономить время и
            деньги.
          </p>
        </div>

        {/* Блок с картинкой */}
        <div className="md:w-1/2 xl:flex-column flex justify-center ">
          <img
            src="/images/gasStationImage.png"
            alt="Машина на заправке"
            className="rounded-lg shadow-lg max-w-[80%] object-contain"
          />
        </div>
      </div>
    </section>
  );
};

export default AboutSection;
