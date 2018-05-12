<template>
  <li
    v-bind:class="{ 'is-do': item.is_do }"
    @click="done(item)"
    >{{ item.title }}</li>
</template>

<script>
export default {
  props: ['item'],
  methods: {
    done(item) {
      item.is_do = !item.is_do;
    }
  }
}
</script>


<style lang="scss" scoped>
  li {
    cursor: pointer;

    &.is-do {
      text-decoration: line-through;
    }
  }
</style>
