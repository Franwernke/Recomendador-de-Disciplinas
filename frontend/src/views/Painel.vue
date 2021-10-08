<template>
  <v-container>
    <v-row>
      <h1>Painel</h1>
    </v-row>
    <v-row>
      <v-col>
        <p>{{ name }}</p>
      </v-col>
      <v-col>
        <p v-for="(discipline, i) in disciplines" :key="i">{{ discipline }}</p>
      </v-col>
      <v-col>
        <p v-for="(department, i) in departments" :key="i">{{ department }}</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => ({
    name: '',
    disciplines: '',
    departments: '',
  }),
  mounted() {
    this.name = JSON.parse(localStorage.getItem('name')) || '';
    this.disciplines = JSON.parse(localStorage.getItem('disciplines')) || [];
    this.departments = JSON.parse(localStorage.getItem('departments')) || [];
  },
};
</script>
