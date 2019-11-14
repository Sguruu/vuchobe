<template>
	<div class="wrap">
		<PageHeader/>

		<div class="page-content">
			<div class="content-title">
				<div class='block-title'><h2 class="title">Добавление и редактирование  мероприятий</h2></div>
				<div class="all-events"><p>Все мероприятия</p></div>
				<div class="add-events"><p>Добавить мероприятия</p></div>
				<div class="edit"><p>Редактировать</p></div>
			</div>

			<div class="content-events">
				<div class="events-input">
				<input type="text" class="events-name" placeholder="Название мероприятия">
				<input type="datetime" class="events-time" placeholder="Дата">
				<input type="text" class="events-time" placeholder="Время">
				<input type="text" class="events-name" placeholder="Место проведения">
				</div>

				<div class="content-dropdown">
					<input type="text" class="name-category" placeholder="Тип мероприятия" v-model='name' @mouseover="mouseover" @mouseleave="mouseleave">

					<div id="box" v-show = 'show'>
					<ul class="dropdown-box">
						<li class="box-content"></li>
						<li class="box-content"></li>
						<li class="box-content"></li>
						<li class="box-content"></li>
						<li class="box-content"></li>
						<li class="box-content"></li>
					</ul>
					</div>

				</div>

				<div class="box-info">
					<p>Краткая информация о мероприятии</p>
					<textarea  cols="1" rows="10" placeholder="О мероприятии"></textarea>
				</div>

				<div class="photos">
					<p>Загрузка фото</p>

					<div class="block-photo">
						<p>Фото для обложки 1</p>
						<span>380x150</span>
						<input type="file">
					</div>

					<div class="block-photo">
						<p>Фото для обложки 2</p>
						<span>400x170</span>
						<input type="file">
					</div>
				</div>

					<button class = 'btn-adoption btn-adoption2'>Готово</button>
					<button class = 'btn-adoption btn-adoption1'>Отмена</button>


			</div>
		</div>

	</div>

</template>  


<script>
import PageHeader from '@/components/PageHeader.vue'
    export default {
		name: 'wrap',
		components: {
			PageHeader
		},
		methods: {
			
		}
    }
</script>
<style>
body{
	margin: 0;
	height: 1300px;
}
.wrap{
	display: inline-block;
	position: relative;
	width:100%;
	min-width: 1300px;
	height: 100%;
	min-height: 100%;
}
.page-content{
	background-color: white;
	position: absolute;
	top: 50px;
	right: 270px;
	width: 70%;
}
.content-title{
	height: 160px;
	box-shadow: 0 13px 4px -4px rgba(0, 0, 0, .2);
}
.block-title{
	border-bottom: 1px solid black;
	height: 70px;
}
.title{
	position: absolute;
	top: 15px;
	left: 20%;
}
.all-events{
	display: inline-block;
	float: left;
	font-size: 23px;
	margin-left: 70px;
	margin-top: 10px;
}
.add-events,.edit{
	display: inline-block;
	float: left;
	font-size: 23px;
	margin-left: 140px;
	margin-top:10px;
}
.add-events p{
	height: 40px;
	padding: 10px 5px 0px 5px;
	margin-top: 10px;
	vertical-align: middle;
	text-align: center;
	background-color: #ececec;
}
.content-events{
	width: 100%;
}
.events-input{
	float: left;
	width: 42%;
	margin-top: 40px;
	margin-left: 12%;
}
.events-name, .events-time{
	width: 100%;
	height: 30px;
	font-size: 18px;
	color: black;
	border: 0;
	border-bottom: 1px solid black;
	margin-bottom: 30px;
}
.events-time{
	width: 48%;
}
.content-dropdown{
	float: right;
	width: 25%;
	margin-right: 17%;
	margin-top: 45px;
}
.content-dropdown input{
	width: 100%;
	border:0;
	border-bottom: 1px solid black;
	font-size: 18px;
}
.dropdown-box{
	list-style-type: none;
	width: 100%;
	height: 180px;
	border: 1px solid black;
	margin-left: 5px;
	padding: 0;
}
.box-content{
	display: inline-block;
	width: 42%;
	background-color: #ececec;
	height: 45px;
	margin-left: 12px;
	margin-top: 10px;
}
.box-info{
	width: 100%;
}
.box-info textarea{
	width: 70%;
	height: 130px;
	margin-left: 12%;
	vertical-align: text-top;
	font-size: 16px;
	padding: 10px;
}
.box-info p{
	position: relative;
	margin-left: 12%;
	top: 20px;
	font-size: 19px;
}

.photos{
	display: block;
	width: 80%;
	margin-left: 12%;
	margin-top: 50px;
	font-size: 20px;
}

.block-photo{
	float: left;
	width: 43%;
	margin-right: 30px;
	margin-top: 20px;
	height: 200px;
	background-color: #c4c4c4;
}
.block-photo p{
	position: relative;
	bottom: 40px;
	font-size: 19px;
	color: #828282;
}
.block-photo span{
	position: relative;
	left: 35%;
	top: 25px;
	font-size: 24px;
	color: white;
}
.block-photo input{
	position: relative;
	bottom: 85px;
	width: 100%;
	height: 100%;
	opacity: 0;
}

.btn-adoption{
	float: right;
	border: 0;
	color: white;
	font-size: 18px;
	width: 25%;
	height: 45px;
	margin-top: 50px;
}
.btn-adoption1{
	background-color: #ca9797;
	margin-right: 30px;
}
.btn-adoption2{
	background-color: #9bca97;
	margin-right: 16%
}

</style>