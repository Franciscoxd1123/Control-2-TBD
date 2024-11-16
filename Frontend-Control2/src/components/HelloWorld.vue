<script setup>
defineProps({
  msg: String,
})
</script>

<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <div class="content">
      <p>Bienvenido a nuestra aplicación</p>
      <p>Por favor, selecciona una opción:</p>
    </div>
  </div>
</template>

<style scoped>
.hello {
  text-align: center;
  padding: 2rem;
}

h1 {
  font-size: 2.5rem;
  margin-bottom: 2rem;
  color: #2c3e50;
}

.content {
  font-size: 1.2rem;
  color: #666;
  line-height: 1.6;
}
</style>