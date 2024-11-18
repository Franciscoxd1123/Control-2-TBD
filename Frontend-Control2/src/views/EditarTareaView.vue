<template>
    <div class="editar-tarea-view">
      <EditarTarea />
    </div>
  </template>

  <script setup>
  import EditarTarea from '../components/EditarTarea.vue';
  </script>

  <style scoped>
  .editar-tarea-view {
    min-height: 100vh;
    background: #1a2634;
    padding: 2rem;
    display: flex;
    justify-content: center;
    align-items: flex-start;
  }
  </style>