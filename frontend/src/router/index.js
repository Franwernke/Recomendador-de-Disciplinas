import Vue from 'vue';
import VueRouter from 'vue-router';
import Home from '@/views/Home.vue';
import Forms from '@/views/Forms.vue';
import Panel from '@/views/Panel.vue';
import EditProfile from '@/views/EditProfile.vue';
import EditInterests from '@/views/EditInterests.vue';

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/forms',
    name: 'Forms',
    component: Forms,
  },
  {
    path: '/panel',
    name: 'Panel',
    component: Panel,
  },
  {
    path: '/edit/profile',
    name: 'EditProfile',
    component: EditProfile,
  },
  {
    path: '/edit/interests',
    name: 'EditInterests',
    component: EditInterests,
  },
];

const router = new VueRouter({
  mode: 'history',
  routes,
});

export default router;
