<template>
  <div class="tabs-container">
    <v-tabs v-model="tab" background-color="transparent" class="mb-3" grow>
      <v-tab key="table">Tabela</v-tab>
      <v-tab key="graph">Grafo</v-tab>
      <v-tab key="cloud">Nuvem de palavras</v-tab>
    </v-tabs>

    <v-tabs-items v-model="tab" class="transparent">
      <v-tab-item key="table">
        <Table :disciplines="disciplines" :recommendations="recommendations" />
      </v-tab-item>
      <v-tab-item key="graph">
        <Graph :possibleRecommendations="possibleRecommendations" />
      </v-tab-item>
      <v-tab-item key="cloud">
        <WordCloud
          :disciplines="disciplines"
          :recommendations="recommendations"
        />
      </v-tab-item>
    </v-tabs-items>
  </div>
</template>

<script>
import Table from '@/components/Table';
import Graph from '@/components/Graph';
import WordCloud from '@/components/WordCloud';

export default {
  components: {
    Table,
    Graph,
    WordCloud,
  },
  props: {
    disciplines: {
      type: Array,
      required: true,
    },
    recommendations: {
      type: Array,
      required: true,
    },
    possibleRecommendations: {
      type: Object,
      required: true,
    },
  },
  data: () => ({
    tab: null,
  }),
};
</script>

<style>
.tabs-container {
  width: 100%;
}

.transparent {
  background-color: blue;
}
</style>
