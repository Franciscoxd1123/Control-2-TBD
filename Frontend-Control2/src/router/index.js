import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import RegisterView from '../views/RegisterView.vue';
import LoginView from '../views/LoginView.vue';
import UserMenuView from '../views/UserMenu.vue';
import CreateTarea from '../components/CreateTarea.vue';
import TareasListEdit from '../components/TareasListEdit.vue';
import EditarTareaView from '../views/EditarTareaView.vue';

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/register',
    name: 'Register',
    component: RegisterView,
  },
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
  },
  {
    path: '/user-menu', 
    name: 'UserMenu',
    component: UserMenuView,
  },
  {
    path: '/create-tarea',
    name: 'CreateTarea',
    component: CreateTarea,
  },
  {
    path: '/tareas-list-edit',
    name: 'TareasListEdit',
    component: TareasListEdit,
  },
  {
    path: '/editar-tarea/:id',
    name: 'EditarTarea',
    component: EditarTareaView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;