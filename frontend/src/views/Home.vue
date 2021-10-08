<template>
  <section class="main-container">
    <v-row align="end" class="main-row">
      <v-col cols="3" class="usp-logo">
        <img
          src="@/assets/usp-logo-transp.png"
          alt="Logo USP"
          class="logo-usp"
        />
      </v-col>
      <v-col cols="9">
        <div class="text">
          <h1>Recomendador de Disciplinas</h1>
          <v-divider></v-divider>
          <h2>Facilitando seu caminho</h2>
        </div>
      </v-col>
    </v-row>

    <v-row class="btn-row" justify="center" align="center">
      <v-btn class="px-10 py-7 rounded-lg" color="#E7D2CC" depressed to="/forms"
        >Começar</v-btn
      >
    </v-row>
  </section>
</template>

<script>
export default {
  name: 'Home',
};
</script>

<style scoped>
.main-container {
  width: 100%;
  height: 100%;
}

.main-row {
  width: 100%;
  height: 50%;
}

.btn-row {
  height: 50%;
}

.btn-start {
  padding: 16px;
}

.logo-usp {
  width: 187px;
  height: 77px;
}

.usp-logo {
  display: flex;
  justify-content: right;
}
</style>
