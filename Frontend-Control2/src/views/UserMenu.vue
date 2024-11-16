<template>
    <div class="user-view">
      <UserMenu msg="Bienvenido al Gestor de Tareas" />
    </div>
  </template>
  
  <script setup>
  import UserMenu from '../components/UserMenu.vue';
  </script>
  
  <style scoped>
  .user-view {
    min-height: 100vh;
  }
  </style>
  