<script setup>
defineProps({
  msg: String,
});

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

.content {
  flex: 1;
  padding: 1rem;
}

.welcome-msg {
  color: orange;
}

.header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  padding: 1rem;
  background-color: #ecf0f1;
  border-bottom: 1px solid #ddd;
}

.notifications {
  position: relative;
  cursor: pointer;
}

.notifications .icon {
  font-size: 1.5rem;
}

.notification-dropdown {
  position: absolute;
  top: 2rem;
  right: 0;
  background-color: white;
  border: 1px solid #ddd;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  padding: 1rem;
  z-index: 10;
  width: 200px;
}

.notification-dropdown p {
  margin: 0;
  font-size: 1rem;
}
</style>