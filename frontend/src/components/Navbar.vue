<template>
  <div>
    <v-toolbar flat color="transparent">
      <v-tabs>
        <v-tab v-for="(path, i) in paths" :key="i" link :to="path.url">{{
          path.name
        }}</v-tab>
      </v-tabs>
    </v-toolbar>
  </div>
</template>

<script>
export default {
  props: {
    paths: {
      type: Array,
      required: true,
    },
  },
};
</script>
