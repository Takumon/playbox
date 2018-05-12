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
      <button @click="addItem">Add</button>
    </div>
  </div>
</template>

<script>
import Item from './Item.vue'
export default {
  name: 'app',
  components: { Item },
  data () {
    return {
      inputTitle: "",
      items: [
        {is_do: false, title: 'Task1'},
        {is_do: true, title: 'Task2'},
        {is_do: false, title: 'Task3'},
      ]
    }
  },
  methods: {
    addItem() {
      this.items.push({
        title: this.inputTitle,
        is_do: false
      })
    }
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
