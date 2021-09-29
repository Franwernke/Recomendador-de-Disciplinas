<template>
  <div>
    <v-sheet class="d-flex justify-center flex-wrap pa-5 board rounded-xl">
      <template v-if="disciplines.length > 0">
        <Card v-for="(discipline, index) in disciplines" :key="index" :discipline="discipline" @erase="erase"/>
      </template>
      <v-row v-else class="pa-5">
        <v-col align="center">
          <span class="font-weight-medium text--disabled">Selecione uma disciplina</span>
        </v-col>
      </v-row>
    </v-sheet>
    <p class="text-center">{{ captionText }} </p>
  </div>
</template>

<script>
import Card from "@/components/Card.vue"

export default {
  props: {
    disciplines: Array
  },
  components: {
    Card
  },
  computed: {
    captionText() {
      let text = "Disciplinas selecionadas";
      if (this.disciplines.length == 1)
        text = "Disciplina selecionada";
      
      return this.disciplines.length + ' ' + text;
    }
  },
  methods: {
    erase(discipline) {
      this.$emit("erase", discipline);
    }
  }
}
</script>

<style scoped>
.board {
  background: white;
  border: 2px solid black;
}
</style>
