<template>
  <v-container>
    <v-row>
      <h1>Painel</h1>
      <Board :disciplines="disciplines"/>
    </v-row>
  </v-container>
</template>

<script>
import Board from "@/components/Board.vue"
export default {
  data: () => ({
    disciplines: ['MAC351', 'MAC110']
  }),
  components: {
    Board
  }
}
</script>