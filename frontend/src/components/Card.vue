
<template>
  <v-chip>
    <v-container>
      <v-row> {{ discipline }} </v-row>
    </v-container>
  </v-chip>

</template>
<script>
export default {
  props: {
    discipline: String
  }

}
</script>

<style>

</style>