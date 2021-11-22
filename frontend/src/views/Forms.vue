<template>
  <v-container>
    <v-row>
      <v-col align="center">
        <h1 class="text-h5">Seja Bem Vindo(a)!</h1>
        <p class="subtitle">Forneça alguns dados inciais para começarmos</p>
      </v-col>
    </v-row>
    <v-row v-for="error in errors" :key="error">
      <v-col class="pa-0">
        <v-alert type="error">
          {{ error }}
        </v-alert>
      </v-col>
    </v-row>
    <v-form ref="form">
      <ProfileForm
        :allCoursesCode="allCoursesCode"
        :allDisciplines="allDisciplines"
        v-model="profileData"
      />
      <InterestsForm :allDepartments="allDepartments" v-model="interestsData" />
    </v-form>
    <v-row justify="center">
      <v-btn
        class="px-10 mt-8 py-7 rounded-lg"
        color="#CEE7CC"
        depressed
        @click="submit"
        >Salvar</v-btn
      >
    </v-row>
  </v-container>
</template>

<script>
import { saveDataInStorage } from '@/services/Storage.js';
import ProfileForm from '../components/ProfileForm.vue';
import InterestsForm from '../components/InterestsForm.vue';

export default {
  data: () => ({
    allDisciplines: [],
    allDepartments: [],
    allCoursesCode: [],
    profileData: {
      name: '',
      courseCode: '',
      disciplines: [],
    },
    interestsData: {
      keywords: [],
      departments: [],
    },
    errors: [],
  }),
  components: {
    ProfileForm,
    InterestsForm,
  },
  mounted() {
    this.getAllCoursesCode();
    this.getAllDisciplines();
    this.getAllDepartments();
  },
  methods: {
    submit() {
      const errors = saveDataInStorage(localStorage, {
        userData: {
          ...this.profileData,
          ...this.interestsData,
        },
        backendData: {
          allCoursesCode: this.allCoursesCode,
          allDepartments: this.allDepartments,
          allDisciplines: this.allDisciplines,
        },
      });

      if (errors.length === 0) {
        this.$router.push('/panel');
      }
      this.errors = errors;
    },
    async getAllDisciplines() {
      const url = process.env.BACKEND_URL || 'http://localhost:8080';
      const response = await fetch(url + '/disciplines');
      this.allDisciplines = await response.json();
    },
    async getAllDepartments() {
      const url = process.env.BACKEND_URL || 'http://localhost:8080';
      const response = await fetch(url + '/departments');
      this.allDepartments = await response.json();
    },
    async getAllCoursesCode() {
      const url = process.env.BACKEND_URL || 'http://localhost:8080';
      const response = await fetch(url + '/requisites/courses');
      this.allCoursesCode = (await response.json()).sort();
    },
  },
};
</script>

<style scoped>
.subtitle {
  color: #868b8e;
}

.divider {
  background-color: black;
}
</style>
