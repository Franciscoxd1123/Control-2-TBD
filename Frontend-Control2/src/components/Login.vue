<template>
    <div class="login">
      <h1>Iniciar Sesión</h1>
  
      <div v-if="error" class="error-message">
        {{ error }}
      </div>
  
      <div class="form-field">
        <div class="label">Email</div>
        <input 
          v-model="loginData.correo"
          type="email" 
          required
          placeholder=" "
        >
      </div>
  
      <div class="form-field">
        <div class="label">Contraseña</div>
        <input 
          v-model="loginData.contrasena"
          type="password" 
          required
          placeholder=" "
        >
      </div>
  
      <button 
        type="submit" 
        @click.prevent="handleSubmit"
        :disabled="loading"
      >
        {{ loading ? 'Iniciando sesión...' : 'Iniciar Sesión' }}
      </button>
    </div>
  </template>
  
  <script setup>
  import { reactive, ref } from 'vue'
  import { useRouter } from 'vue-router'
  import usuarioService from '../services/usuarioService'
  
  const router = useRouter()
  const error = ref('')
  const loading = ref(false)
  
  const loginData = reactive({
    correo: '',
    contrasena: ''
  })
  
  const handleSubmit = async () => {
    try {
      // Validaciones básicas
      if (!loginData.correo || !loginData.contrasena) {
        error.value = 'Por favor, complete todos los campos'
        return
      }
  
      loading.value = true
      error.value = ''
  
      const response = await usuarioService.loginUsuario(
        loginData.correo, 
        loginData.contrasena
      )
      
      if (response.data) {
        localStorage.setItem('usuario', JSON.stringify(response.data))
        router.push('/')
      }
    } catch (err) {
      console.error('Error de login:', err)
      if (err.response?.status === 401) {
        error.value = 'Correo o contraseña incorrectos'
      } else if (err.response?.data) {
        error.value = err.response.data
      } else {
        error.value = 'Error al iniciar sesión. Por favor, intente nuevamente.'
      }
    } finally {
      loading.value = false
    }
  }
  </script>
  
  <style scoped>
  .login {
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
    transition: background-color 0.2s;
  }
  
  button:hover:not(:disabled) {
    background: #38a169;
  }
  
  button:disabled {
    opacity: 0.7;
    cursor: not-allowed;
  }
  
  .error-message {
    background: #fee2e2;
    color: #dc2626;
    padding: 0.75rem;
    border-radius: 0.5rem;
    margin-bottom: 1rem;
    font-size: 0.875rem;
    text-align: center;
    border: 1px solid #fca5a5;
  }
  </style>