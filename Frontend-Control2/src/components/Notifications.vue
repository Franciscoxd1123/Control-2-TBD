<template>
  <div v-if="tareasFiltradas" class="tareas-list-edit">
    <h1>Notificaciones</h1>
    <div v-for="pendiente in listaPendientes">
      <li>Tienes una tarea pendiente para el {{ pendiente.fechaVencimiento }} llamada {{ pendiente.titulo }}</li>
    </div>
    <button @click="goBack" class="go-back-btn">
      Volver
    </button>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue';
import tareaService from '../services/tareaService';
import { useRouter } from 'vue-router';

const tareas = ref([]);
const listaPendientes = ref([]);
const listaCompletadas = ref([]);
const search = ref('');
const errorMessage = ref("");
const filtroPendientes = ref(true);
const router = useRouter();

// Formatear fecha
const formatDate = (date) => {
  if (!date) return '';
  return new Date(date).toLocaleDateString();
};

const obtenerTareas = async () => {
  try {
    const usuario = JSON.parse(localStorage.getItem('usuario'));
    if (!usuario) {
      errorMessage.value = "Debe iniciar sesión para ver las tareas";
      return;
    }

    const response = await tareaService.getTareasByUsuario(usuario.idUsuario);
    tareas.value = response.data || [];
  } catch (error) {
    errorMessage.value = "Error al obtener las tareas: " + error.message;
  }
};

const tareasFiltradas = computed(() => {
  const UN_DIA = 1000 * 24 * 60 * 60 // Un dia en milisegundos
    listaPendientes.value = tareas.value.filter(tarea => {
      let _ = 
        tarea.estado.toLowerCase() === "pendiente" && 
        Math.round(Math.abs(((new Date(tarea.fechaVencimiento)) - (new Date())) / UN_DIA)) <= 3
      return _
    });
    return true
});


const goBack = () => {
  router.push({ name: 'UserMenu' });
};

onMounted(() => {
  obtenerTareas();
});
</script>

<style scoped>
.tareas-list-edit {
  background: #2c3e50;
  padding: 2rem;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  max-width: 1000px;
  margin: 0 auto;
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
  background: #1a2634;
}

th, td {
  padding: 0.75rem;
  text-align: left;
  border: 1px solid orange;
  color: #42b983;
}

th {
  background: #2c3e50;
  color: orange;
}

.edit-btn {
  background: #48bb78;
  color: black;
  border: none;
  border-radius: 0.5rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
  width: 100%;
}

.edit-btn:hover {
  background: #38a169;
}

.delete-btn {
  background: #e74c3c;
  color: white;
  border: none;
  border-radius: 0.5rem;
  padding: 0.5rem 1rem;
  cursor: pointer;
  width: 100%;
  margin-top: 0.5rem;
}

.delete-btn:hover {
  background: #c0392b;
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

.go-back-btn:hover {
  background: #c0392b;
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
  color: #42b983;
  padding: 1rem;
  font-size: 1.1rem;
}

.title {
  text-align: center;
  color: #42b983;
  padding: 1rem;
  font-size: 1.1rem;
  font-weight: bold;
}

.filter {
  margin-bottom: 1rem;
  text-align: center;
}

.filter label {
  color: #42b983;
  font-size: 1rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.filter input {
  width: 20px;
  height: 20px;
  cursor: pointer;
}
</style>