'use client';
import React, { useEffect, useState } from 'react';
import { useRoute } from 'wouter';
import axios from 'axios';
import { Line } from 'react-chartjs-2';
import Header from '../components/Header/Header';
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend,
} from 'chart.js';

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

interface OilTypeAverageWidget {
    averagePrice: number;
    type: string;
}

interface OilTypePriceData {
    type: string;
    price: number;
    date: string;
}

interface GasStationAddressSummary {
    widgets: OilTypeAverageWidget[];
    data: OilTypePriceData[];
}

const apiClient = axios.create({
    baseURL: 'http://localhost:8080/api/v1/stations/address/',
});

export const GasStationSummaryPage: React.FC = () => {
    const [match, params] = useRoute('/gas-stations/address/summary/:id');
    const id = params?.id;
    const [summary, setSummary] = useState<GasStationAddressSummary | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [interval, setInterval] = useState<number>(90); // Интервал в днях по умолчанию

    useEffect(() => {
        const fetchSummary = async () => {
            if (!id) {
                setError('ID филиала отсутствует.');
                return;
            }

            try {
                setIsLoading(true);
                const response = await apiClient.get(`/summary/${id}?interval=${interval}`, {
                    headers: {
                        Authorization: `Bearer ${document.cookie.replace(/(?:(?:^|.*;\s*)access_token\s*=\s*([^;]*).*$)|^.*$/, '$1')}`,
                    },
                });
                setSummary(response.data);
            } catch (err: any) {
                setError(err.response?.data?.message || 'Не удалось загрузить сводку.');
            } finally {
                setIsLoading(false);
            }
        };

        fetchSummary();
    }, [id, interval]);

    const renderChartData = () => {
        if (!summary || !summary.data.length) {
            return {
                labels: [],
                datasets: [],
            };
        }

        const currentDate = new Date();
        const types = Array.from(new Set(summary.data.map((item) => item.type)));
        const labels = Array.from(new Set(
            summary.data
                .filter((item) => new Date(item.date) <= currentDate)
                .map((item) => item.date)
        )).sort((a, b) => new Date(a).getTime() - new Date(b).getTime());

        const datasets = types.map((type) => {
            const filteredData = summary.data.filter((item) => item.type === type && new Date(item.date) <= currentDate);
            const dataByDate = labels.map((label) => {
                const entry = filteredData.find((item) => item.date === label);
                return entry ? entry.price : 0;
            });

            return {
                label: type,
                data: dataByDate,
                borderColor: `#${Math.floor(Math.random() * 16777215).toString(16)}`,
                fill: false,
            };
        });

        return {
            labels,
            datasets,
        };
    };

    return (
        <>
            <Header />
            <div className="min-h-screen bg-gradient-to-b from-[#FAD201] to-[#005F6A] py-8 px-4">
                <div className="max-w-5xl mx-auto bg-white rounded-lg shadow-lg p-8">
                    <h1 className="text-3xl font-bold text-[#005F6A] mb-6 text-center">Сводка по адресу АЗС</h1>

                    <div className="mb-6 flex justify-center">
                        <label className="mr-4 text-gray-700 font-bold">Интервал (дни):</label>
                        <input
                            type="number"
                            value={interval}
                            onChange={(e) => setInterval(Number(e.target.value))}
                            className="px-4 py-2 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-[#005F6A]"
                            min="1"
                        />
                    </div>

                    {isLoading ? (
                        <p className="text-center text-gray-600">Загрузка...</p>
                    ) : error ? (
                        <p className="text-center text-red-600">{error}</p>
                    ) : summary ? (
                        <>
                            <div className="mb-8">
                                <h2 className="text-2xl font-bold text-[#005F6A] mb-4">Средние дневная цена в этом периоде:</h2>
                                <ul className="list-disc pl-5">
                                    {summary.widgets.map((widget, index) => (
                                        <li key={index} className="text-gray-600">
                                            {widget.type}: {widget.averagePrice.toFixed(2)} руб.
                                        </li>
                                    ))}
                                </ul>
                            </div>

                            <div>
                                <h2 className="text-2xl font-bold text-[#005F6A] mb-4">Динамика цен:</h2>
                                <Line data={renderChartData()} options={{ responsive: true }} />
                            </div>
                        </>
                    ) : (
                        <p className="text-center text-gray-600">Нет данных для отображения.</p>
                    )}
                </div>
            </div>
        </>
    );
};
