<template>
  <div class="register">
    <h1>Registro</h1>

    <div v-if="error" class="error-message">
      {{ error }}
    </div>

    <div class="form-field">
      <div class="label">Nombre</div>
      <input 
        v-model="userData.nombre" 
        type="text" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Email</div>
      <input 
        v-model="userData.correo"
        type="email" 
        required
        placeholder=" "
      >
    </div>

    <div class="form-field">
      <div class="label">Contraseña</div>
      <input 
        v-model="userData.contrasena"
        type="password" 
        required
        placeholder=" "
      >
    </div>

    <button type="submit" @click.prevent="handleSubmit">
      Registrarse
    </button>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import usuarioService from '../services/usuarioService'

const router = useRouter()
const error = ref('')

const userData = reactive({
  nombre: '',
  correo: '',
  contrasena: '',
  sesionActiva: false
})

const handleSubmit = async () => {
  try {
    error.value = ''
    
    // Validaciones del frontend que coinciden con el backend
    if (!userData.nombre || userData.nombre.trim() === '') {
      error.value = 'El nombre es obligatorio'
      return
    }
    
    if (!userData.correo || userData.correo.trim() === '') {
      error.value = 'El correo es obligatorio'
      return
    }
    
    if (!userData.contrasena || userData.contrasena.length < 6) {
      error.value = 'La contraseña debe tener al menos 6 caracteres'
      return
    }

    const response = await usuarioService.registrarUsuario(userData)
    if (response.data) {
      console.log('Usuario registrado:', response.data)
      router.push('/login')
    }
  } catch (err) {
    console.error('Error de registro:', err)
    // Mostrar el mensaje de error del backend
    error.value = err.response?.data || 'Error al registrar usuario'
  }
}
</script>

<style scoped>
.register {
  background: white;
  padding: 2rem;
  border-radius: 0.75rem;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 280px;
}

h1 {
  color: #2d3748;
  text-align: center;
  font-size: 1.8rem;
  margin-bottom: 2rem;
}

.form-field {
  margin-bottom: 1.5rem;
}

.label {
  color: #2d3748;
  text-align: center;
  margin-bottom: 0.5rem;
  font-size: 1rem;
}

input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #e2e8f0;
  border-radius: 0.5rem;
  font-size: 0.9rem;
  background: white;
  outline: none;
  color: #000;
}

button {
  width: 100%;
  padding: 0.75rem;
  background: #48bb78;
  color: white;
  border: none;
  border-radius: 0.5rem;
  font-size: 1rem;
  cursor: pointer;
  margin-top: 0.5rem;
}

button:hover {
  background: #38a169;
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