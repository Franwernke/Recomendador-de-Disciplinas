<template>
  <v-container :class="panelContainerClass">
    <v-container
      v-if="checkIfUserHasNotData"
      class="d-flex flex-column align-self-center align-center"
    >
      <h1 class="text-center">
        Preencha seus dados para visualizar o painel :)
      </h1>
      <v-btn
        class="mt-8 py-7 rounded-lg"
        color="#CEE7CC"
        depressed
        width="50%"
        to="/forms"
        >Preencher agora</v-btn
      >
    </v-container>
    <div v-else>
      <v-row class="my-4">
        <v-col>
          <h1 class="mx-2 mb-1 font-weight-regular text-h4">
            {{ userData.name }}
          </h1>
          <div>
            <p class="mx-2 mb-0 text--secondary text-subtitle">
              Código do curso: {{ userData.courseCode }}
            </p>
            <p class="mx-2 mb-0 text--secondary text-subtitle">
              Topicos de interesse: {{ displayKeywords }}
            </p>
            <p class="mx-2 mb-0 text--secondary text-subtitle">
              Departamentos de interesse: {{ displayDepartments }}
            </p>
          </div>
        </v-col>
        <v-col>
          <v-container
            class="d-flex flex-column justify-space-between align-end"
          >
            <v-btn
              class="my-1 px-5 py-2 small rounded-lg"
              color="#FAF7B1"
              depressed
              width="50%"
              link
              to="edit/profile"
            >
              <v-icon left> mdi-pencil </v-icon>Editar Dados</v-btn
            >
            <v-btn
              class="my-1 px-5 py-2 small rounded-lg"
              color="#FAF7B1"
              depressed
              width="50%"
              link
              to="edit/interests"
            >
              <v-icon left> mdi-pencil </v-icon>Editar Interesses</v-btn
            >
          </v-container>
        </v-col>
      </v-row>
      <v-row justify="center">
        <Tabs
          :disciplines="displayDisciplines"
          :recommendations="displayRecommendations"
          :possibleRecommendations="possibleRecommendations"
          v-if="dataFetched"
        />
        <v-progress-circular
          v-else
          :size="200"
          :width="20"
          color="blue"
          indeterminate
          >Loading</v-progress-circular
        >
      </v-row>
    </div>
  </v-container>
</template>

<script>
import Tabs from '@/components/Tabs';
import { processGraphData } from '@/services/Graph';
import { fetchDataFromBackend } from '@/services/Request';
import { getDataFromStorage } from '@/services/Storage';

export default {
  components: {
    Tabs,
  },
  data: () => ({
    userData: {
      name: '',
      disciplines: [],
      departments: [],
      keywords: [],
      recommendations: [],
      courseCode: '',
    },
    recommendations: [],
    dataFetched: false,
    possibleRecommendations: {},
  }),
  mounted() {
    this.loadUserData();
    if (!this.checkIfUserHasNotData) {
      this.fetch();
    }
  },
  computed: {
    checkIfUserHasNotData() {
      return this.name == '';
    },
    panelContainerClass() {
      let className = 'height-100';
      if (this.checkIfUserHasNotData) className += ' d-flex';
      return className;
    },
    displayDisciplines() {
      return this.userData.disciplines.map(
        (element) => `${element.code} - ${element.name}`
      );
    },
    displayDepartments() {
      return this.userData.departments.map((dep) => dep.code).join(', ');
    },
    displayKeywords() {
      return this.userData.keywords.join(', ');
    },
    displayRecommendations() {
      return this.recommendations.map(
        (element) => `${element.code} - ${element.name}`
      );
    },
  },
  methods: {
    loadUserData() {
      this.userData = getDataFromStorage(localStorage);
    },
    async fetchRecommendations() {
      const body = {
        departmentsId: this.userData.departments.map(
          (department) => department.id
        ),
        keywords: this.userData.keywords,
      };
      this.recommendations = await fetchDataFromBackend(
        '/disciplines/recommendations',
        body
      );
    },
    async fetchPossibleRecommendations() {
      const body = {
        disciplinesCode: this.userData.disciplines.map(
          (discipline) => discipline.code
        ),
        departmentsId: this.userData.departments.map(
          (department) => department.id
        ),
        keywords: this.userData.keywords,
        courseCode: this.userData.courseCode,
      };
      const data = await fetchDataFromBackend(
        '/disciplines/possible-recommendations',
        body
      );
      this.possibleRecommendations = processGraphData(data);
    },
    async fetch() {
      this.dataFetched = false;
      await this.fetchRecommendations();
      await this.fetchPossibleRecommendations();
      this.dataFetched = true;
    },
  },
};
</script>

<style scoped>
.height-100 {
  height: 100%;
}

.border {
  border: 1px solid black;
}

.color-grey {
  color: #868b8e;
}
</style>
