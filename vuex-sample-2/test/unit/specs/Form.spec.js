import Vue from 'vue'
import Form from '@/components/Form'

describe('Form.vue', () => {
  it('should render correct contents', () => {
    const Constructor = Vue.extend(Form)
    const vm = new Constructor().$mount()
    expect(vm.$el.querySelector('#form-title').textContent)
      .toEqual('Formページ')
  })
})
