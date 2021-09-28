<template>
  <v-container>
    <div v-for="(discipline, index) in disciplines" :key="index">
      <Card :discipline="discipline"/>
    </div>
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
  }
}
</script>
