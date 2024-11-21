import axios from 'axios';

const API_URL = '/api/vi/auth';

const AuthService = {
  login: async (email: string, password: string) => {
    const response = await axios.post(`${API_URL}/login`, { email, password });
    return response.data;
  },

  logout: async () => {
    const response = await axios.post(`${API_URL}/logout`);
    return response.data;
  },

  refreshToken: async () => {
    const response = await axios.post(`${API_URL}/refresh/jwt`);
    return response.data;
  },

  register: async (email: string, password: string) => {
    const response = await axios.post(`${API_URL}/registration`, {
      email,
      password,
    });
    return response.data;
  },
};

export default AuthService;
