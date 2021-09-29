<template>
  <v-container>
    <v-row>
      <h1>Painel</h1>
    </v-row>
    <v-row>
      <v-col>
        Input
      </v-col>
      <v-col>
        <Board :disciplines="disciplines" @erase="eraseDiscipline"/>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Board from "@/components/Board.vue"
export default {
  data: () => ({
    disciplines: ['MAC0351', 'MAC0110', 'MAC0350', 'MAC0111', 'MAC0352', 'MAC0112', 'MAC0353', 'MAC0113']
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