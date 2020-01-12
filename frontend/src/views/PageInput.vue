<template>
	<div class="wraper">
		<div class="header-img">
			<img :src="url" alt="Header">
		</div>

		<div class="conteiner">
				<div class="block-entry" v-if='show'>
					<h2 class="logo">Vuchebe</h2>
					<input type="text" class="item item-mail" placeholder="логин" v-model='login'>
					<input type="password" class="item item-password" placeholder="пароль" v-model='password'>
					<button class="forgot-password" @click='m1()'>Забыли пароль?</button>
					<router-link to='/university'></router-link>
					<button class="btn-input" @click='Auth()'>Войти</button>
					<button class="check-in" @click = 'm2()'>Регистрация</button>
				</div>

				<div class="block-forgot-password" v-if='show1'>
					<h2 class="logo">Восстановление пароля</h2>
					<input type="text" class="item item-email" placeholder="Введите e-mail">
					<button class="btn-input btn-recovery">Восстановить пароль</button>
					<button class="check-in block-forgot-input" @click='m3()'>Вход</button>
					<button class="check-in block-forgot-check-in" @click='m4()'>Регистрация</button>
				</div>

				<div class="block-check-in" v-if = 'show2'>
					<h2 class="logo">Регистрация</h2>
					<input type="text" class="item block-item-mail" placeholder="Введите логин">
					<input type="password" class="item item-password" placeholder="Введите пароль" v-modal='password1'>
					<input type="password" class="item item-password" placeholder="Введите пароль еще раз" v-model="password2">
					<button class="btn-input btn-check-in" @click="Registration(password1,password2)">Регистрироваться</button>
					<button class="check-in block-check-in-input" @click='m5()'>Вход</button>
				</div>
		</div>
	</div>
</template>
<script>
import axios from 'axios'
export default {
		name:'wraper',
		data() {
			return{
			show: true,
			show1: false,
			show2: false,
			url: require('@/assets/Auth/Header.png'),
			errored: '',
			login: '',
			password: '',
			info: null,
			}
		},
		methods: {
	
			m1: function(){
				this.show = false
				this.show1 = true
			},
			m2: function(){
				this.show = false
				this.show2 = true
			},
			m3: function(){
				this.show = true
				this.show1 = false
			},
			m4: function(){
				this.show2 = true
				this.show1 = false
			},
			m5: function(){
				this.show = true
				this.show2 = false
			},
			Auth: function(){
				axios.post('http://localhost:8080/auth/login',{
					email: this.login,
					password: this.password
				})
				.then(response => {
					console.log('Ответ сервера:', response)
				})
				.catch(error =>{
					console.log(error)
				})
		
		
			},
		},
}
</script>


<style>
.wraper{
	position: relative;
	width: 100%;
	min-width: 1200px;
	margin-top: 10%;
}
.header-img{
	position: absolute;
	top:20%;
	left: 10%;
}
.block-entry{
	display: block;
	position: absolute;
	width: 25%;
	right: 20%;
	top: 25%;
}
.logo{
	text-align: center;
	font-size: 30px;
}
.item{
	width:100%;
	border: 0;
	border-bottom: 1px solid black;
	margin-top: 40px;
}
.item-password{
	margin-top: 25px;
}
.forgot-password{
	display: inline-block;
	font-size: 10px;
	margin-left: 38%;
	margin-top: 10px;
	border: 0;
	color: #8a8a8a;
	background-color: white;
}
.btn-input{
	width:100%;
	height: 35px;
	text-align: center;
	border: 0;
	margin-top: 20px;
	background-color: #ffeca7;
}
.check-in{
	width: 100%;
	border: 0;
	border-top: 1px solid #8a8a8a;
	background-color: white;
	text-align: center;
	font-size: 14px;
	margin-top: 21px;
}
.block-forgot-password{
	display: block;
	position: absolute;
	width: 25%;
	right: 20%;
	top: 25%;
}
.item-email{
	margin-top: 53px;
}
.btn-recovery{
	margin-top: 70px;
}
.block-forgot-input{
	width:48%;
	float: left;
	margin-top: 25px;
}
.block-forgot-check-in{
	width:48%;
	float: right;
	margin-top: 25px;
}
.block-check-in{
	display: block;
	position: absolute;
	width: 25%;
	right: 20%;
	top: 25%;
}
.block-item-mail{
	margin-top: 10px;
}
.block-check-in-input{
	margin-top: 24px;
}
.btn-check-in{
	margin-top: 26px;
}
</style>