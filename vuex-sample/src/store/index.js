import Vue from 'vue'
import Vuex from 'vuex'
import * as types from './mutation-types'

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    items: [
      {is_do: false, title: 'Task1'},
      {is_do: true, title: 'Task2'},
      {is_do: false, title: 'Task3'},
    ]
  },
  actions: {
    [types.ADD_TASK] ({ commit }, title) {
      commit( types.ADD_TASK, {
        data: {
          title,
          is_do: false
        }
      })
    },
    [types.DONE_TASK] ({commit}, item) {
      commit( types.DONE_TASK, {
        data: item
      })
    }
  },
  mutations: {
    [types.ADD_TASK] (state, payload) {
      state.items.push(payload.data)
    },
    [types.DONE_TASK] (state, payload) {
      let index = state.items.indexOf(payload.data)
      state.items[index].is_do = !payload.data.is_do
    }
  }
})