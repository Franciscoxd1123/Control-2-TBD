<template>
  <div class="home">
    <div class="header">
      <a href="https://vite.dev" target="_blank">
        <img src="../assets/vite.svg" class="logo" alt="Vite logo" />
      </a>
      <a href="https://vuejs.org/" target="_blank">
        <img src="../assets/vue.svg" class="logo vue" alt="Vue logo" />
      </a>
    </div>
    <HelloWorld msg="Vite + Vue" />
    <div class="nav">
      <router-link to="/register" class="nav-link">Registrarse</router-link>
      <router-link to="/login" class="nav-link">Iniciar Sesión</router-link>
    </div>
  </div>
</template>

<script setup>
import HelloWorld from '../components/HelloWorld.vue'
</script>

<style scoped>
.home {
  min-height: 100vh;
  padding: 2rem;
}

.header {
  display: flex;
  justify-content: center;
  gap: 2em;
  margin-bottom: 2em;
}

.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}

.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}

.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}

.nav {
  display: flex;
  gap: 1rem;
  justify-content: center;
  margin-top: 2em;
}

.nav-link {
  color: #42b983;
  text-decoration: none;
  padding: 0.5em 1em;
  border: 1px solid #42b983;
  border-radius: 4px;
}

.nav-link:hover {
  background: #42b983;
  color: white;
}
</style>