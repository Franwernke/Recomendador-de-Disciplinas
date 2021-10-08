<template>
  <v-container>
    <v-form ref="form">
      <v-row>
        <v-col align="center">
          <h1 class="text-h5">Seja Bem Vindo(a)!</h1>
          <p class="subtitle">Forneça alguns dados inciais para começarmos</p>
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <h2 class="text-h6">Dados pessoais</h2>
          <v-divider class="divider"></v-divider>
        </v-col>
      </v-row>
      <v-row class="px-10">
        <v-col>
          <v-text-field label="Nome" v-model="name" required clearable />
          <v-text-field
            label="Disciplinas já cursadas"
            v-model="discipline"
            :rules="rules"
            persistent-hint
            hint="Formato: MAC0110"
            @keydown.enter="addDisciplines"
          />
        </v-col>
        <v-col>
          <Board :disciplines="disciplines" @erase="eraseDiscipline" />
        </v-col>
      </v-row>
      <v-row>
        <v-col>
          <h2 class="text-h6">Interesses</h2>
          <v-divider class="divider"></v-divider>
        </v-col>
      </v-row>
      <v-row class="px-10">
        <v-col>
          <v-combobox
            label="Departamentos de interesse"
            v-model="departments"
            deletable-chips
            multiple
            small-chips
          ></v-combobox>
        </v-col>
      </v-row>
      <v-row justify="center">
        <v-btn
          class="px-10 mt-8 py-7 rounded-lg"
          color="#CEE7CC"
          depressed
          @click="submit"
          >Ver disciplinas</v-btn
        >
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
import Board from '@/components/Board.vue';

const regexDisciplines = /^\D{3}\d{4}$/;

export default {
  data: () => ({
    name: '',
    discipline: '',
    disciplines: [],
    departments: [],
    rules: [
      (value) =>
        regexDisciplines.test(value) ||
        value == '' ||
        'Formato inválido! Exemplo: MAC0110',
    ],
  }),
  components: {
    Board,
  },
  mounted() {
    this.name = JSON.parse(localStorage.getItem('name')) || '';
    this.disciplines = JSON.parse(localStorage.getItem('disciplines')) || [];
    this.departments = JSON.parse(localStorage.getItem('departments')) || [];
  },
  methods: {
    eraseDiscipline(discipline) {
      this.disciplines = this.disciplines.filter(
        (element) => discipline != element
      );
    },
    addDisciplines() {
      if (regexDisciplines.test(this.discipline)) {
        this.disciplines.push(this.discipline);
        this.discipline = '';
      }
    },
    submit() {
      if (this.$refs.form.validate()) {
        localStorage.setItem('name', JSON.stringify(this.name));
        localStorage.setItem('disciplines', JSON.stringify(this.disciplines));
        localStorage.setItem('departments', JSON.stringify(this.departments));
        this.$router.push('/painel');
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
