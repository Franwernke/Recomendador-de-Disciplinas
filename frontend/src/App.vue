<template>
  <v-app class="global test">
    <router-view />
  </v-app>
</template>

<script>
export default {
  computed: {
    test() {
      console.log("Test")
    },
  },
};
</script>


<style>
:root {
  background-color: red;
}

#app {
  background-color: #eeede7;
}
</style>
