<template>
  <v-container class="background">
      <Card v-for="(discipline, index) in disciplines" :key="index" :discipline="discipline" @erase="erase"/>
  </v-container>
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
  methods: {
    erase(discipline) {
      this.$emit("erase", discipline);
    }
  }
}
</script>

<style scoped>
.background {
  background: white;
}
</style>
