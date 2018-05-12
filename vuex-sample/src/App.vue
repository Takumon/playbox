<template>
  <div id="app">
    <ul>
      <Item
        v-for="item in items"
        :key="item"
        :item="item"></Item>
    </ul>
    <div>
      <input type="text" v-model="inputTitle">
      <button @click="ADD_TASK(inputTitle)">Add</button>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'
import * as types from './store/mutation-types'
import Item from './Item.vue'

export default {
  name: 'app',
  components: { Item },
  data () {
    return {
      inputTitle: "",
    }
  },
  computed: {
    ...mapState(['items'])
  },
  methods: {
    ...mapActions([
      types.ADD_TASK
    ])
  }
}
</script>

<style lang="scss">
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

</style>
