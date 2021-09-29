<template>
  <v-container>
    <v-row>
      <h1>Painel</h1>
      <Board :disciplines="disciplines" @erase="eraseDiscipline"/>
    </v-row>
  </v-container>
</template>

<script>
import Board from "@/components/Board.vue"
export default {
  data: () => ({
    disciplines: ['MAC0351', 'MAC0110']
  }),
  components: {
    Board
  },
  methods: {
    eraseDiscipline(discipline) {
      this.disciplines = this.disciplines.filter((element) => discipline != element);
    }
  }
}
</script>