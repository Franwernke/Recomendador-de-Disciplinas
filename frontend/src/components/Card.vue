
<template>
  <v-sheet
    class="ma-2"
    color="#E7D2CC"
    elevation="1"
    width="fit-content"
    height="fit-content"
    >
    <v-container class="align-center">
      <v-row>
        <v-col class="pa-0 mb-2" align="end">
            <v-icon dense color="red" @click="eraseCard">
              mdi-close
            </v-icon>
        </v-col>
      </v-row>
      <p class="ma-0 text-center text-h5">{{ departmentCode }}</p>
      <p class="ma-0 text-center">{{ disciplineCode }}</p>
    </v-container>
  </v-sheet>

</template>
<script>
export default {
  props: {
    discipline: String
  },
  computed: {
    departmentCode() {
      const regex = /\D+/;
      return this.discipline.match(regex)[0];
    },
    disciplineCode() {
      const regex = /\d+/;
      return this.discipline.match(regex)[0];
    },
  },
  methods: {
    eraseCard() {
      this.$emit("erase", this.discipline);
    }
  }

}
</script>