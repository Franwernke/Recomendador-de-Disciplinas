<template>
  <v-container>
    <h1 v-if="checkData">Preencha seus dados</h1>
    <v-row class="my-4">
      <h1>{{ name }}</h1>
    </v-row>
    <v-row>
      <v-col class="border mx-2 d-flex flex-column rounded-lg">
        <h2 class="mb-4">Disciplinas cursadas</h2>
        <v-chip class="mb-2" v-for="(discipline, i) in disciplines" :key="i">
          {{ discipline }}
        </v-chip>
      </v-col>
      <v-col class="border mx-2 d-flex flex-column rounded-lg">
        <h2 class="mb-4">Departamentos desejados</h2>
        <v-chip class="mb-2" v-for="(department, i) in departments" :key="i">
          {{ department }}
        </v-chip>
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

<style scoped>
.border {
  border: 1px solid black;
}
</style>
