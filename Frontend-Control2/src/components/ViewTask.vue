<template>
  <div class="tareas-list-edit">
    <h1>Mis tareas</h1>

    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>


    <div class="search-wrapper panel-heading col-sm-12">
    <input type="text" v-model="search" placeholder="Search" /> <br> <br>
    </div>  

    <div v-if="listaPendientes.length > 0" class="title">Tareas pendientes</div>
    <div v-if="listaPendientes.length == 0" class="title">No tienes tareas pendientes</div>
    <table v-if="tareasFiltradas && listaPendientes.length > 0">
      <thead>
        <tr>
          <th>Título</th>
          <th>Descripción</th>
          <th>Estado</th>
          <th>Fecha de Vencimiento</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tarea in listaPendientes" :key="tarea.idTarea">
          <td>{{ tarea.titulo }}</td>
          <td>{{ tarea.descripcion }}</td>
          <td>{{ tarea.estado }}</td>
          <td>{{ tarea.fechaVencimiento }}</td>
        </tr>
      </tbody>
    </table>

    <div v-if="listaCompletadas.length > 0" class="title">Tareas completadas</div>
    <div v-if="listaCompletadas.length == 0" class="title">No tienes tareas completadas</div>
    <table v-if="tareasCompletadas && listaCompletadas.length > 0">
      <thead>
        <tr>
          <th>Título</th>
          <th>Descripción</th>
          <th>Estado</th>
          <th>Fecha de Vencimiento</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="tarea in listaCompletadas" :key="tarea.idTarea">
          <td>{{ tarea.titulo }}</td>
          <td>{{ tarea.descripcion }}</td>
          <td>{{ tarea.estado }}</td>
          <td>{{ tarea.fechaVencimiento }}</td>
        </tr>
      </tbody>
    </table>

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
    listaPendientes.value = tareas.value.filter(tarea => {
      let _ = tarea.estado.toLowerCase() === "pendiente" && tarea.titulo.toLowerCase().indexOf(search.value.toLowerCase()) != -1
      return _
    });
    return true
});

const tareasCompletadas = computed(() => {
  listaCompletadas.value = tareas.value.filter((tarea) => {
      let _ = tarea.estado.toLowerCase() === "completada" && tarea.titulo.toLowerCase().indexOf(search.value.toLowerCase()) != -1
      return _
    });
    return true
})

const editarTarea = (idTarea) => {
  router.push(`/editar-tarea/${idTarea}`);
};

const terminarTarea = async (idTarea) => {
  if(confirm("¿Estás seguro de que deseas marcar esta tarea como completada?")) {
    try {
      await tareaService.completarTarea(idTarea);
      alert('Tarea actualizada exitosamente.')
    } catch (e) {
      errorMessage.value = "Error al terminar la tarea: " + e.message;
    }
  }

}

const eliminarTarea = async (idTarea) => {
  const confirmDelete = confirm("¿Estás seguro de que deseas eliminar esta tarea?");
  if (confirmDelete) {
    try {
      await tareaService.deleteTarea(idTarea); 
      tareas.value = tareas.value.filter(tarea => tarea.idTarea !== idTarea);
      alert("Tarea eliminada exitosamente.");
    } catch (error) {
      errorMessage.value = "Error al eliminar la tarea: " + error.message;
    }
  }
};

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