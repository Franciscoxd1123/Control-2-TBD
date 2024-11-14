import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8090/usuarios',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default {
    // Crear un usuario nuevo
    createUsuario(data) {
        return apiClient.post('/', data);
    },

    // Registrar un usuario
    registrarUsuario(data) {
        return apiClient.post('/registro', data);
    },

    // Login de usuario
    loginUsuario(data) {
        return apiClient.post('/login', data);
    },

    // Logout de usuario 
    logoutUsuario(id) { 
        return apiClient.post(`/logout/${id}`);
    },

    // Obtener la lista de usuarios
    listUsuarios() {
        return apiClient.get('/');
    },

    // Obtener un usuario por id
    getUsuario(id) {
        return apiClient.get(`/${id}`);
    },
    
    // Actualizar un usuario por id
    updateUsuario(id, data) {
        return apiClient.put(`/${id}`, data);
    },

    // Eliminar un usuario por id
    deleteUsuario(id) {
        return apiClient.delete(`/${id}`);
    },
};