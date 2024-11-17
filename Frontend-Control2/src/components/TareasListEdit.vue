<template>
  <div class="tareas-list-edit">
    <h1>Editar mis tareas</h1>

    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>

    <div class="filter">
      <label>
        Mostrar solo pendientes
        <input type="checkbox" v-model="filtroPendientes" />
      </label>
    </div>

    <div v-if="tareasFiltradas.length === 0" class="no-tareas">
      No tienes tareas pendientes.
    </div>

    <table v-if="tareasFiltradas.length > 0">
      <thead>
        <tr>
          <th>Título</th>
          <th>Descripción</th>
          <th>Estado</th>
          <th>Fecha de Vencimiento</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tarea in tareasFiltradas" :key="tarea.idTarea">
          <td>{{ tarea.titulo }}</td>
          <td>{{ tarea.descripcion }}</td>
          <td>{{ tarea.estado }}</td>
          <td>{{ tarea.fechaVencimiento | formatDate }}</td>
          <td>
            <button @click="editarTarea(tarea.idTarea)">Editar</button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Botón de Volver -->
    <button @click="goBack" class="go-back-btn">
        Volver
      </button>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import tareaService from '../services/tareaService';
import { useRouter } from 'vue-router';

const tareas = ref([]);
const errorMessage = ref("");
const filtroPendientes = ref(true);  // Inicialmente se filtran solo las pendientes
const router = useRouter();

// Obtener las tareas del usuario
const idUsuario = 1;  // Este valor debería ser dinámico

const obtenerTareas = async () => {
  try {
    const response = await tareaService.getTareasByUsuario(idUsuario);
    tareas.value = response;
  } catch (error) {
    errorMessage.value = "Error al obtener las tareas: " + error.message;
  }
};

// Computed para filtrar las tareas pendientes
const tareasFiltradas = computed(() => {
  if (filtroPendientes.value) {
    return tareas.value.filter(tarea => tarea.estado === "Pendiente");
  }
  return tareas.value;  // Si no se filtra, se muestran todas las tareas
});

// Redirigir al componente de edición
const editarTarea = (idTarea) => {
  router.push(`/editar-tarea/${idTarea}`);
};

onMounted(() => {
  obtenerTareas();
});

// Método para volver
const goBack = () => {
    router.go(-1); // Vuelve a la página anterior
  };
</script>

<style scoped>
.tareas-list {
  padding: 2rem;
  background: #2c3e50;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h1 {
  color: orange;
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}

th, td {
  padding: 0.75rem;
  text-align: center;
  border: 1px solid #ddd;
}

button {
  background: #48bb78;
  color: white;
  border: none;
  border-radius: 0.5rem;
  padding: 0.5rem;
  cursor: pointer;
}

button:hover {
  background: #38a169;
}

.go-back-btn {
    width: 100%;
    padding: 0.75rem;
    background: #e74c3c;
    color: black;
    border: none;
    border-radius: 0.5rem;
    font-size: 1rem;
    cursor: pointer;
    margin-top: 1rem;
}

.error-message {
  color: #dc2626;
  background: #fee2e2;
  padding: 0.75rem;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;
  text-align: center;
}

.no-tareas {
  text-align: center;
  color: #ccc;
}

.filter {
  margin-bottom: 1rem;
  text-align: center;
}

.filter label {
  color: #42b983;
  font-size: 1rem;
}

.filter input {
  margin-left: 0.5rem;
}
</style>