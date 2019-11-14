import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import PageInput from './views/PageInput.vue'
import PageUniversity from './views/PageUniversity.vue'
import PageRecommend from './views/PageRecommend.vue'
import PageSchedule from './views/PageSchedule.vue'
import PerfectScrollbar from "vue2-perfect-scrollbar"
import "vue2-perfect-scrollbar/dist/vue2-perfect-scrollbar.css"
import { BFormFile } from 'bootstrap-vue'
Vue.component('b-form-file', BFormFile)
Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(PerfectScrollbar)

const router = new VueRouter({
  routes:[
    {
      path: '/', component: PageInput,
    },
    {
      path: '/university', component: PageUniversity
    },
    {
      path: '/reg', component: PageRecommend
    },
    {
      path: '/schedule', component: PageSchedule
    }
  ]
})

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')