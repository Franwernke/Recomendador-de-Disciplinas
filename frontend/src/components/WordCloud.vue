<template>
  <v-row class="transparent">
    <v-col class="border mx-2 d-flex flex-column rounded-lg" align="center">
      <h2 class="mb-4">Disciplinas cursadas</h2>
      <v-progress-linear
        v-if="!hasLoadedDisciplinesAlreadyDone"
        :value="percentageLoadedDisciplinesAlreadyDone"
        striped
        height="25"
        rounded
        ><strong
          >{{ Math.ceil(percentageLoadedDisciplinesAlreadyDone) }}%</strong
        ></v-progress-linear
      >
      <VueWordCloud
        :spacing="1"
        class="mx-auto"
        style="height: 50vh; width: 70vw"
        :words="disciplinesAlreadyDone"
        :color="randomColor"
        rotation-unit="rad"
        :rotation="randomRotation"
        font-family="Londrina Sketch"
        @update:progress="updateLoading($event, 'disciplines')"
      >
        <template slot-scope="{ text, weight }">
          <div :title="`${text} (${weight})`" class="text-h5">
            {{ text }}
          </div>
        </template>
      </VueWordCloud>
      <h2 class="mt-4">Disciplinas Recomendadas</h2>
      <v-progress-linear
        v-if="!hasLoadedRecommendations"
        :value="percentageLoadedRecommendations"
        striped
        height="25"
        rounded
        ><strong
          >{{ Math.ceil(percentageLoadedRecommendations) }}%</strong
        ></v-progress-linear
      >
      <VueWordCloud
        :spacing="4"
        class="mx-auto"
        style="height: 100vh; width: 70vw"
        :words="allRecommendations"
        :color="randomColor"
        font-family="Londrina Sketch"
        @update:progress="updateLoading($event, 'recommendations')"
      >
        <template slot-scope="{ text, weight }">
          <div :title="`${text} (${weight})`" class="caption">
            {{ text }}
          </div>
        </template>
      </VueWordCloud>
    </v-col>
  </v-row>
</template>

<script>
export default {
  props: {
    disciplines: {
      type: Array,
      required: true,
    },
    recommendations: {
      type: Array,
      required: true,
    },
  },
  data: () => ({
    percentageLoadedRecommendations: 0,
    percentageLoadedDisciplinesAlreadyDone: 0,
    colors: [
      '#f94144',
      '#f3722c',
      '#f8961e',
      '#f9844a',
      '#90be6d',
      '#43aa8b',
      '#4d908e',
      '#577590',
      '#277da1',
    ],
    rotations: [0, 7 / 8, 3 / 4, 1 / 8],
  }),
  computed: {
    allRecommendations() {
      const occurrences = new Map();
      const recommendationsName = this.recommendations.map((discipline) =>
        discipline.split('-')[1].trim()
      );
      recommendationsName.forEach((recommendation) => {
        const frequency = occurrences.get(recommendation);
        occurrences.set(recommendation, frequency ? frequency + 1 : 1);
      });
      return Array.from(occurrences.keys()).map((recommendation) => [
        recommendation,
        occurrences.get(recommendation),
      ]);
    },
    disciplinesAlreadyDone() {
      return this.disciplines.map((discipline) => {
        const disciplineName = discipline.split('-')[1];
        return [disciplineName.trim(), 1];
      });
    },
    hasLoadedRecommendations() {
      return this.percentageLoadedRecommendations >= 100;
    },
    hasLoadedDisciplinesAlreadyDone() {
      return this.percentageLoadedDisciplinesAlreadyDone >= 100;
    },
  },
  methods: {
    randomColor() {
      const length = this.colors.length;
      const index = Math.floor(Math.random() * length);
      return this.colors[index];
    },
    randomRotation() {
      const length = this.rotations.length;
      const index = Math.floor(Math.random() * length);
      return this.rotations[index];
    },
    updateLoading(e, type) {
      let percentage;
      if (e === null) percentage = 100;
      else {
        const { completedWords, totalWords } = e;
        percentage = (completedWords / totalWords) * 100;
      }

      if (type === 'recommendations') {
        this.updateLoadedRecommendations(percentage);
      } else {
        this.updateLoadedDisciplinesAlreadyDone(percentage);
      }
    },
    updateLoadedRecommendations(newValue) {
      this.percentageLoadedRecommendations = newValue;
    },
    updateLoadedDisciplinesAlreadyDone(newValue) {
      this.percentageLoadedDisciplinesAlreadyDone = newValue;
    },
  },
};
</script>
