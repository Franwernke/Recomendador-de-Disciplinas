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
      <v-row>
        <v-col>
          <h2 class="text-h6">Dados pessoais</h2>
          <v-divider class="divider"></v-divider>
        </v-col>
      </v-row>
      <ProfileForm
        :allCoursesCode="backendData.allCoursesCode"
        :allDisciplines="backendData.allDisciplines"
        v-model="profileData"
      />
      <v-row>
        <v-col>
          <h2 class="text-h6">Interesses</h2>
          <v-divider class="divider"></v-divider>
        </v-col>
      </v-row>
      <InterestsForm
        :allDepartments="backendData.allDepartments"
        v-model="interestsData"
      />
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
import {
  saveDataInStorage,
  getComputedDataFromStorage,
} from '@/services/Storage.js';
import validateFields from '@/services/Validate.js';
import ProfileForm from '../components/ProfileForm.vue';
import InterestsForm from '../components/InterestsForm.vue';
import { getAllFormDataFromBackend } from '../services/Request.js';

export default {
  data: () => ({
    profileData: {
      name: '',
      courseCode: '',
      disciplines: [],
    },
    interestsData: {
      keywords: [],
      departments: [],
    },
    backendData: {
      allCoursesCode: [],
      allDisciplines: [],
      allDepartments: [],
    },
    errors: [],
  }),
  components: {
    ProfileForm,
    InterestsForm,
  },
  mounted() {
    this.userData = getComputedDataFromStorage(localStorage);
    this.fetchBackendData();
  },
  methods: {
    async fetchBackendData() {
      const allDisciplines =
        (await getAllFormDataFromBackend('/disciplines')) || [];
      const allDepartments =
        (await getAllFormDataFromBackend('/departments')) || [];
      const allCoursesCode =
        (await getAllFormDataFromBackend('/requisites/courses'))?.sort() || [];

      this.backendData = {
        allDisciplines,
        allDepartments,
        allCoursesCode,
      };
    },
    submit() {
      const { isValid, errors } = validateFields(
        {
          ...this.profileData,
          ...this.interestsData,
        },
        this.allCoursesCode
      );
      if (isValid) {
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
        this.$router.push('/panel');
      } else {
        this.errors = errors;
      }
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
