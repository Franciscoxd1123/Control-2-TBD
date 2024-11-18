<template>
    <div class="editar-tarea">
      <h1>Editar Tarea</h1>
  
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
  
      <div v-if="successMessage" class="success-message">
        {{ successMessage }}
      </div>
  
      <div v-if="tarea" class="form-container">
        <div class="form-field">
          <div class="label">Título</div>
          <input
            v-model="tarea.titulo"
            type="text"
            required
            placeholder=" "
          />
        </div>
  
        <div class="form-field">
          <div class="label">Descripción</div>
          <textarea
            v-model="tarea.descripcion"
            required
            placeholder=" "
          ></textarea>
        </div>
  
        <div class="form-field">
          <div class="label">Fecha de Vencimiento</div>
          <input
            v-model="tarea.fechaVencimiento"
            type="date"
            required
            placeholder=" "
          />
        </div>
  
        <div class="button-container">
          <button type="submit" @click.prevent="guardarCambios" class="save-btn">
            Guardar Cambios
          </button>
          <button @click="cancelarEdicion" class="cancel-btn">
            Cancelar
          </button>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import tareaService from '../services/tareaService';
  
  const router = useRouter();
  const route = useRoute();
  const tarea = ref(null);
  const errorMessage = ref('');
  const successMessage = ref('');
  
  const cargarTarea = async () => {
    try {
      const idTarea = route.params.id;
      const response = await tareaService.getTarea(idTarea);
      tarea.value = response.data;
      
      // Formatear la fecha para el input date
      if (tarea.value.fechaVencimiento) {
        tarea.value.fechaVencimiento = new Date(tarea.value.fechaVencimiento)
          .toISOString().split('T')[0];
      }
    } catch (error) {
      errorMessage.value = "Error al cargar la tarea: " + error.message;
    }
  };
  
  const guardarCambios = async () => {
    try {
      const tareaActualizada = {
        ...tarea.value,
        fechaVencimiento: new Date(tarea.value.fechaVencimiento + 'T23:59:59').toISOString()
      };
  
      await tareaService.updateTarea(tarea.value.idTarea, tareaActualizada);
      successMessage.value = "Tarea actualizada con éxito";
      
      setTimeout(() => {
        router.push('/tareas-list-edit');
      }, 1500);
    } catch (error) {
      errorMessage.value = "Error al actualizar la tarea: " + error.message;
    }
  };
  
  const cancelarEdicion = () => {
    router.push('/tareas-list-edit');
  };
  
  onMounted(() => {
    cargarTarea();
  });
  </script>
  
  <style scoped>
  .editar-tarea {
    background: #2c3e50;
    padding: 5rem;
    border-radius: 0.75rem;
    box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 280px;
  }
  
  h1 {
    color: orange;
    text-align: center;
    font-size: 1.8rem;
    margin-bottom: 2rem;
  }
  
  .form-field {
    margin-bottom: 1.5rem;
  }
  
  .label {
    color: #42b983;
    text-align: center;
    margin-bottom: 0.5rem;
    font-size: 1rem;
  }
  
  input,
  textarea,
  select {
    width: 90%;
    padding: 0.75rem;
    border: 1px solid orange;
    border-radius: 0.5rem;
    font-size: 0.9rem;
    background: black;
    outline: none;
    color: #42b983;
    text-align: center;
  }
  
  textarea {
    min-height: 100px;
    resize: vertical;
  }
  
  .button-container {
    display: flex;
    flex-direction: column;
    gap: 1rem;
    margin-top: 1.5rem;
  }
  
  .save-btn {
    width: 100%;
    padding: 0.75rem;
    background: #48bb78;
    color: black;
    border: none;
    border-radius: 0.5rem;
    font-size: 1rem;
    cursor: pointer;
  }
  
  .save-btn:hover {
    background: #38a169;
  }
  
  .cancel-btn {
    width: 100%;
    padding: 0.75rem;
    background: #e74c3c;
    color: black;
    border: none;
    border-radius: 0.5rem;
    font-size: 1rem;
    cursor: pointer;
  }
  
  .cancel-btn:hover {
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
  
  .success-message {
    color: #38a169;
    background: #e6f9f0;
    padding: 0.75rem;
    border-radius: 0.5rem;
    margin-bottom: 1rem;
    font-size: 0.875rem;
    text-align: center;
  }
  </style>