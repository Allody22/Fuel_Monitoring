import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080/api/v1/auth',
});

const AuthService = {
  login: async (phoneNumber: string, password: string, fingerprint: string) => {
    const response = await apiClient.post('/login', { phoneNumber, password, fingerprint }, {
      withCredentials: true,  // Обязательно для отправки cookies
    });
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

  register: async (phoneNumber: string, password: string, fingerprint: string) => {
    const response = await apiClient.post('/registration', {
      phoneNumber,
      password,
      fingerprint
    }, {
      withCredentials: true,
    });
    return response.data;
  },
};

export default AuthService;
