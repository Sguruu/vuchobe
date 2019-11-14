<template>
  <div class="header-block">
			<div class="page-header">
				<ul class='header-menu'>
					<li><button>Личный кабинет</button></li>
					<li><button>Выход</button></li>
				</ul>
			</div>
			<div class="col-left"></div>
			<div class="page-column">
				<ul class="column-menu">
					<li class="avatar">
						<img src='@/assets/Header/avatar.jpg' alt="Аватарка">
						<p> Name</p>
					</li>
					<li><router-link to='/university'><button class="list-button">Университеты</button></router-link></li>
					<router-link to='/reg'><li><button class="list-button">Рекомендации</button></li></router-link>
					<li><button class="list-button">Пользователи</button></li>
					<router-link to='/schedule'><li><button class="list-button">Расписание</button></li></router-link>
				</ul>
			</div>
  </div>
</template>

<script>
export default {
	name: 'header-block',
	avatar: 'avatar.jpg',
	activeColor: '#c6c3eb',

}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.header-block{
	display: inline-block;
	position: absolute;
	width:100%;
	min-width: 1300px;
	height: 100%;
	min-height: 1500px;
	margin:0
}
.page-header{
	position: absolute;
	top: 0;
	left: 0;
	width:100%;
	height: 50px;
	background-color: #ffeca7;
}
.header-menu{
	float: right;
	list-style-type: none;
	margin-right: 130px;
	margin-top:11px;
}
.header-menu li{
	display: inline;
	margin-right: 30px;
}
.header-menu li button{
	border: 0;
	font-size: 22px;
	color: black;
	font-family: 'Roboto',Arial,sans-serif;
	background-color: #ffeca7;
}
.col-left{
	display: inline-block;
	position: absolute;
	top: 50px;
	left: 5%;
	height: 100%;
	width:50px;
	float: left;
	background: linear-gradient(180deg, gray, white);
}
.page-column{
	position: relative;
	float:right;
	margin-top:50px;
	margin-right: 50px;
	background-color: gray;
	min-height: 100%;
	width: 220px;
	z-index: 10;
}
.column-menu{
	list-style-type: none;

}
.column-menu li{
	margin-top: 20px;
	margin-right: 100px;
}
.avatar{
	margin-bottom: 150px;
	color: white;
}
.column-menu .avatar img{
	margin-top: 20px;
	border: 5px solid #ffeca7;
	width: 150px;
	border-radius: 20%;
}
.avatar p{
	position: absolute;
	right: 0;
	width: 220px;
	text-align: center;
	font-size: 22px;
}
.list-button{
	width: 220px;
	height: 70px;
	margin-left: -40px;
	border: 0;
	background-color: gray;
	font-size: 20px;
	color: white;
}

.column-menu .router-link-active{
	background-color: #c6c3eb;
	color: black;
}
</style>
