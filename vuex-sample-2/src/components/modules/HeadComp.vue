<template>
<div>{{title}}</div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'healComp',
  computed: mapGetters({
    'title': 'getTitle'
  })
}
</script>
