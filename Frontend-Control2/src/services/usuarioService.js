import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8090/usuarios',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
    createUsuario(data) {
        return apiClient.post('/', data);
    },

    registrarUsuario(data) {
        return apiClient.post('/registro', data);
    },

    loginUsuario(correo, contrasena) {
        return apiClient.post('/login', { correo, contrasena });
    },

    logoutUsuario(idUsuario) { 
        return apiClient.post(`/logout/${idUsuario}`);
    },

    listUsuarios() {
        return apiClient.get('/');
    },

    getUsuario(id) {
        return apiClient.get(`/${id}`);
    },
    
    updateUsuario(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    deleteUsuario(id) {
        return apiClient.delete(`/${id}`);
    },
};