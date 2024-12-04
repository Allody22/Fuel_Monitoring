import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1/auth',
});

const AuthService = {
  login: async (email: string, password: string) => {
    const response = await apiClient.post('/login', { email, password });
    return response.data;
  },

  logout: async () => {
    const response = await apiClient.post('/logout');
    return response.data;
  },

  refreshToken: async () => {
    const response = await apiClient.post('/refresh/jwt');
    return response.data;
  },

  register: async (email: string, password: string) => {
    const response = await apiClient.post('/registration', {
      email,
      password,
    });
    return response.data;
  },
};

export default AuthService;
