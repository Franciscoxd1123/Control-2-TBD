<template>
  <div class="create-tarea">
    <h1>Crear Nueva Tarea</h1>

    <div v-if="errorMessage" class="error-message">
      {{ errorMessage }}
    </div>

    <div v-if="successMessage" class="success-message">
      {{ successMessage }}
    </div>

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

    <button type="submit" @click.prevent="submitTarea">
      Crear Tarea
    </button>

    <button @click="goBack" class="go-back-btn">
      Volver
    </button>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from 'vue-router';
import tareaService from '../services/tareaService';

const successMessage = ref("");
const errorMessage = ref("");

const tarea = ref({
  titulo: "",
  descripcion: "",
  estado: "PENDIENTE",
  fechaVencimiento: "",
  idUsuario: null,
});

const router = useRouter();

const submitTarea = async () => {
  successMessage.value = "";
  errorMessage.value = "";

  try {
    // Validar campos requeridos
    if (!tarea.value.titulo || !tarea.value.descripcion || !tarea.value.fechaVencimiento) {
      errorMessage.value = "Por favor complete todos los campos";
      return;
    }

    const usuarioGuardado = localStorage.getItem('usuario');
    if (!usuarioGuardado) {
      errorMessage.value = "Debe iniciar sesión para crear tareas";
      return;
    }

    const usuario = JSON.parse(usuarioGuardado);

    // Crear el objeto de tarea con los datos formateados
    const tareaNueva = {
      titulo: tarea.value.titulo,
      descripcion: tarea.value.descripcion,
      estado: "Pendiente",
      fechaVencimiento: new Date(tarea.value.fechaVencimiento + 'T23:59:59').toISOString(),
      idUsuario: usuario.idUsuario
    };

    console.log('Enviando tarea:', tareaNueva);
    const response = await tareaService.createTarea(tareaNueva);
    console.log('Respuesta:', response);

    successMessage.value = "¡Tarea creada con éxito!";
    
    // Limpiar el formulario
    tarea.value = {
      titulo: "",
      descripcion: "",
      estado: "Pendiente",
      fechaVencimiento: "",
      idUsuario: null,
    };
  } catch (error) {
    console.error('Error:', error);
    errorMessage.value = "Error al crear la tarea: " + (error.response?.data || error.message);
  }
};

const goBack = () => {
  router.push({ name: 'UserMenu' });
};
</script>

<style scoped>
/* Mantengo exactamente tu estilo original */
.create-tarea {
  background: #2c3e50;
  padding: 2rem;
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
textarea {
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

button {
  width: 100%;
  padding: 0.75rem;
  background: #48bb78;
  color: black;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 0.5rem;
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

.go-back-btn:hover {
  background: #c0392b;
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

.error-message {
  color: #dc2626;
  background: #fee2e2;
  padding: 0.75rem;
  border-radius: 0.5rem;
  margin-bottom: 1rem;
  font-size: 0.875rem;
  text-align: center;
}
</style> 