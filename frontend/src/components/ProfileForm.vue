<template>
  <v-form ref="profileForm">
    <v-row class="px-10">
      <v-col>
        <v-text-field label="Nome" v-model="name" required clearable />
        <v-autocomplete
          :items="allCoursesCode"
          label="Código do curso"
          v-model="courseCode"
          no-data-text="Nenhum código de curso encontrado"
          hint="Exemplo: o código de Ciência da Computação no IME-USP é 45052. Caso não lembre o código do seu curso, acesse o JupiterWeb."
          persistent-hint
          clearable
        />
        <v-autocomplete
          :items="displayDisciplines"
          label="Disciplinas já
          cursadas"
          no-data-text="Nenhuma disciplina encontrada"
          v-model="discipline"
          clearable
          open-on-clear
          @change="addDiscipline"
        />
      </v-col>
      <v-col>
        <Board :disciplines="disciplines" @erase="eraseDiscipline" />
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
import Board from '@/components/Board.vue';

export default {
  name: 'ProfileForm',
  props: {
    allCoursesCode: {
      type: Array,
      required: true,
    },
    allDisciplines: {
      type: Array,
      required: true,
    },
    value: {
      type: Object,
      default: () => ({
        name: '',
        courseCode: '',
        disciplines: [],
      }),
    },
  },
  data: () => ({
    name: '',
    courseCode: '',
    disciplines: [],
    discipline: '',
  }),
  components: {
    Board,
  },
  mounted() {
    this.name = JSON.parse(localStorage.getItem('name')) || '';
    this.courseCode = JSON.parse(localStorage.getItem('courseCode')) || '';
    this.getSavedDisciplines();
  },
  methods: {
    eraseDiscipline(discipline) {
      this.disciplines = this.disciplines.filter(
        (element) => discipline != element
      );
    },
    addDiscipline() {
      const hasDiscipline = this.disciplines.find(
        (element) => element == this.discipline
      );
      if (!hasDiscipline) {
        const disciplineExists = this.displayDisciplines.find(
          (element) => element == this.discipline
        );

        if (disciplineExists) {
          this.disciplines.push(this.discipline);
          this.discipline = '';
        }
      }
    },
    getSavedDisciplines() {
      const disciplineObjects =
        JSON.parse(localStorage.getItem('disciplines')) || [];
      this.disciplines = disciplineObjects.map(
        (disc) => `${disc.code} - ${disc.name}`
      );
    },
  },
  computed: {
    displayDisciplines() {
      return this.allDisciplines.map(
        (element) => `${element.code} - ${element.name}`
      );
    },
    profileData: {
      get() {
        return this.value;
      },
      set(val) {
        this.$emit('input', val);
      },
    },
  },
  watch: {
    disciplines: {
      handler() {
        this.profileData = Object.assign(
          {},
          { ...this.value, disciplines: this.disciplines }
        );
      },
    },
    name: {
      handler() {
        this.profileData = Object.assign(
          {},
          { ...this.value, name: this.name }
        );
      },
    },
    courseCode: {
      handler() {
        this.profileData = Object.assign(
          {},
          { ...this.value, courseCode: this.courseCode }
        );
      },
    },
  },
};
</script>

<style></style>
