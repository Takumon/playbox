import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router'

Vue.use(Vuex)

const Form = {
  namespaced: true,
  state: {
    button: ['確認', '送信'],
    component: ['TextareaComp', 'StringComp']
  },
  mutations: {},
  actions: {
    buttonAction ({commit, store, rootState}) {
      console.log('buttonAction')
      commit('setStepCount', null, {root: true}) // rootへのアクセス
      if (rootState.errorFlog) {
        commit('setStepCount', null, {root: true}) // rootへのアクセス
      }
      if (rootState.stepCount === 2) {
        router.push('thanks')
      }
    }
  },
  getters: {
    getButton (state, gettters, rootState) {
      return state.button[rootState.stepCount]
    },
    getComponent (state, getters, rootState) {
      return state.component[rootState.stepCount]
    }
  }
}

const Head = {
  state: {
    title: ['感想を入力', '確認画面', '送信完了']
  },
  mutations: {},
  actions: {},
  getters: {
    getTitle (state, getters, rootState) {
      return state.title[rootState.stepCount]
    }
  }
}

const Textarea = {
  namespaced: true,
  state: {
    errorMsg: '入力は必須です。'
  },
  getters: {
    getError (state, getters, rootState) {
      return rootState.errorFlag ? null : state.errorMsg
    }
  }
}

const String = {
  namespaced: true, // 名前空間を有効にする
  getters: {
    getString (state, getters, rootState) {
      return rootState.impression
    }
  }
}

export default new Vuex.Store({
  state: {
    stepCount: 0,
    impression: '',
    errorFlag: false // trueなら通過
  },
  mutations: {
    setStepCount (state) {
      console.log('rootsetStepCount')
      state.stepCount++
    },
    updateImpression (state, value) {
      state.impression = value
      state.errorFlag = !!value
    }
  },
  modules: {
    Form,
    Head,
    Textarea,
    String
  }
})
