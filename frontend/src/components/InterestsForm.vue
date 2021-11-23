<template>
  <v-form ref="interestsForm">
    <v-row class="px-10">
      <v-col>
        <v-combobox
          label="Tópicos de interesse"
          v-model="keywords"
          deletable-chips
          multiple
          small-chips
        ></v-combobox>
        <v-autocomplete
          label="Departamentos de interesse"
          v-model="departments"
          :items="displayDepartments"
          no-data-text="Nenhum departamento encontrado"
          deletable-chips
          multiple
          small-chips
        />
      </v-col>
    </v-row>
  </v-form>
</template>

<script>
export default {
  name: 'InterestsForm',
  props: {
    allDepartments: {
      type: Array,
      required: true,
    },
    value: {
      type: Object,
      default: () => ({
        keywords: [],
        departments: [],
      }),
    },
  },
  data: () => ({
    keywords: [],
    departments: [],
  }),
  mounted() {
    this.keywords = JSON.parse(localStorage.getItem('keywords')) || [];
    this.getSavedDepartments();
  },
  methods: {
    getSavedDepartments() {
      const departmentObjects =
        JSON.parse(localStorage.getItem('departments')) || [];
      this.departments = departmentObjects.map(
        (dep) => `${dep.code} - ${dep.name}`
      );
    },
  },
  computed: {
    displayDepartments() {
      return this.allDepartments.map(
        (element) => `${element.code} - ${element.name}`
      );
    },
    interestsData: {
      get() {
        return this.value;
      },
      set(value) {
        this.$emit('input', value);
      },
    },
  },
  watch: {
    keywords: {
      handler() {
        this.interestsData = Object.assign(
          {},
          { ...this.value, keywords: this.keywords }
        );
      },
    },
    departments: {
      handler() {
        this.interestsData = Object.assign(
          {},
          { ...this.value, departments: this.departments }
        );
      },
    },
  },
};
</script>

<style></style>
