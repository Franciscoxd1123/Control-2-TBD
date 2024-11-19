<script setup>
import { useRouter } from 'vue-router';
import usuarioService from '../services/usuarioService';

const props = defineProps({
  msg: String,
});

const router = useRouter();

const handleLogout = async () => {
  try {
    const usuario = JSON.parse(localStorage.getItem('usuario'));
    if (usuario?.idUsuario) {
      await usuarioService.logoutUsuario(usuario.idUsuario);
    }
    localStorage.removeItem('usuario');
    router.push('/');
  } catch (error) {
    console.error('Error al cerrar sesión:', error);
  }
};
</script>

<template>
  <div class="user-menu">
    <aside class="sidebar">
      <h2>Opciones</h2>
      <ul>
        <li><router-link to="/create-tarea">Crear tareas</router-link></li>
        <li><router-link to="/tareas-list-edit">Editar tareas</router-link></li>
        <li><router-link to="/mark-task">Marcar tareas</router-link></li>
        <li><router-link to="/view-task">Ver tareas</router-link></li>
        <li><router-link to="/notifications">Notificaciones</router-link></li>
        <li class="logout-item">
          <button @click="handleLogout" class="logout-button">
            Cerrar sesión
          </button>
        </li>
      </ul>
    </aside>

    <main class="content">
      <section>
        <h1 class="welcome-msg">{{ msg }}</h1>
        <p>Selecciona una opción desde la barra lateral para empezar.</p>
      </section>
    </main>
  </div>
</template>

<style scoped>
.user-menu {
  display: flex;
  height: 100vh;
}

.sidebar {
  width: 250px;
  background-color: #2c3e50;
  color: orange;
  padding: 1rem;
}

.sidebar h2 {
  margin: 0 0 1rem;
  font-size: 1.5rem;
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar li {
  margin: 1rem 0;
}

.sidebar a {
  color: #42b983;
  text-decoration: none;
  transition: color 0.3s;
}

.sidebar a:hover {
  color: #1abc9c;
}

.logout-button {
  background-color: #e74c3c;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  width: 100%;
  transition: background-color 0.3s;
}

.logout-button:hover {
  background-color: #c0392b;
}

.logout-item {
  margin-top: 2rem;
}

.content {
  flex: 1;
  padding: 1rem;
}

.welcome-msg {
  color: orange;
}
</style>