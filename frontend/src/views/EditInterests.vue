<template>
  <v-container>
    <v-row>
      <v-col>
        <h2 class="text-h6">Edite seus interesses</h2>
        <v-divider class="divider"></v-divider>
      </v-col>
    </v-row>
    <InterestsForm :allDepartments="allDepartments" v-model="interestsData" />
    <v-row justify="center">
      <v-btn
        class="px-10 mt-8 py-7 rounded-lg"
        color="#CEE7CC"
        depressed
        @click="submit"
      >
        Atualizar
      </v-btn>
    </v-row>
  </v-container>
</template>

<script>
import saveDataInStorage from '@/services/Storage.js';
import validateFields from '@/services/Validate.js';
import InterestsForm from '../components/InterestsForm.vue';

export default {
  name: 'EditInterests',
  data: () => ({
    allDepartments: [],
    allCoursesCode: [],
    allDisciplines: [],
    interestsData: {
      keywords: [],
      departments: [],
    },
  }),
  components: { InterestsForm },
  mounted() {
    this.getAllCoursesCode();
    this.getAllDisciplines();
    this.getAllDepartments();
  },
  methods: {
    submit() {
      const errors = validateFields(
        this.profileData,
        this.allCoursesCode,
        'profile'
      );

      saveDataInStorage(localStorage, {
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
.divider {
  background-color: black;
}
</style>
