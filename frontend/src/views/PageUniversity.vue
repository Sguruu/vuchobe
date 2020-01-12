<template>

	<div id="page-wrap">

		<PageHeader/>

			<div class="univer-list">
				<ul class="univer-ul">
							<li class="add-univer">
								<div id = 'add-block1' >
									<b-form-file 
										id='input-img'
										v-model="file"
										:state="Boolean(file)" 
										placeholder="Выберите файл"
										drop-placeholder="Перетащите файл сюда"
										accept='image/*'
										:key="a"
									>
									</b-form-file>
									<div id = 'add-block2'>										
										<input type="text" :key="b" v-model="inputNameUniver" placeholder="Название университета">
									</div>
								</div>
								<button @click = 'addUniver(inputNameUniver,file)'>+</button>
							</li>

							<li class="univer-li" v-for="(k,index) in univers" :key='k' @click="showUniver(k,index)" :class="{'borderActive': k.onActive}">
								<img :src="k.imgSrc" alt="" >
								<p>{{ k.univer }}</p>
							</li>
					
				</ul>
			</div>

		<div class="slider-x"></div>

		<div class="block" v-for= 'i in block' :key='i.id'>			
			<div class="department" v-if ='i.showUniver'>

			<div class="department-list" v-for="n in i.univerData" :key='n'>
				<ul class='block-dep'>
					<li class='department-transform'></li>
					<li class="department-name"><p>{{ n.faculty }}</p> </li>
					<li class="departments"><button @click = 'show(n)' :style="{'background-color': n.activeColor}">Кафедры</button></li>
					<li class="groups"><button @click= "show1(n)" :style="{'background-color': n.activeColor1}">Группы</button></li>
				</ul>
				<ul class="all-list" v-show= 'n.show'>
					<li v-for='j in n.departments' :key='j.id'>Кафедра: {{ j.dep }}</li>
					<li class='input-department'>
						<div @click="addDepartment(n, department)">+</div>
						<input type="text" placeholder="Введите название кафедры" value= '' v-model='department' class='add-department'> 
					</li>
				</ul>

				<ul class="all-groups" v-show= 'n.show1'>
					<li>
						<div class='name-group'>Группа</div>
						<div class='course'>Курс</div>
						<div class='number-student'>Студенты</div>
					</li>

					<li class='all-groups_content' v-for="r in n.groups" :key='r.id'>
						<div class="name-group">{{ r.group }}</div>
						<div class="course">{{ r.course }}</div>
						<div class="number-student">{{ r.student }}</div>
					</li>

					<li class='input-group'>
						<div @click="addGroups(n, addGroup, addCourse, addStudents)"><span>+</span></div>
						<input type="text" placeholder="Добавить группу" class='add-group' v-model='addGroup'>
						<input type="text" placeholder="Курс" class='add-course' v-model="addCourse">
						<input type="text" placeholder="Студенты" class='add-students' v-model='addStudents'>
					</li>
				</ul>


			</div>	
				<div class="add-department">
					<div class="add-dep1">
						<div class="add-dep2"></div>
						<input type="text" placeholder="Введите название факультета" class='add-dep3' v-model='faculty'>
						<div class="add-dep4"></div>
						<div class="add-dep5"></div>
					</div>
					<button @click="addFaculty(i,faculty)">+</button>
				</div>
			</div>
										<label for="univer-img" class='univer-img-label'>Выберите файл</label>										
										<input type="file" id='univer-img' style="visibility: hidden">
		</div>
    </div>
    
</template>
			





<script>
import PageHeader from '@/components/PageHeader.vue'
export default {
    name: 'page-wrap',
    components: {
		PageHeader,
    },
	data() {
        return{
			inputNameUniver: '',
			file: '',
			inputImg: '',			
            univers: [
                {
                    imgSrc: require('@/assets/University/sniu.jpg'),
                    univer: 'Самарский национальный исследовательский университет имени академика С.П. Королева',
					onActive: true,
                },
                {
                    imgSrc: require('@/assets/University/samgups.jpg'),
                    univer: 'Самарский государственный  университет путей сообщения',
					onActive: false,
                },
                {
                    imgSrc: require('@/assets/University/med.jpg'),
                    univer: 'Самарский государственный медицинский университет',
					onActive: false,
				},
            ],
            block: [
				{					
					showUniver: true,
					univerData: [
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Эксплуатация железных дорог',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Строительство железных дорог и информационные технологии',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Подвижной состав и путевые машины',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],							
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Системы обеспечения движения поездов',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						}
					]
				},

				{
					showUniver: false,
					univerData: [
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Эксплуатация ',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Строительство железных дорог и информационные технологии',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Подвижной состав и путевые машины',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Системы обеспечения движения поездов',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						}
					]
				},

				{
					activeColor: '#ffeca7',
					activeColor1: '#ffeca7',
					showUniver: false,
					univerData: [
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Эксплуатация дорог',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Строительство железных дорог и информационные технологии',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',							
							show: false,
							show1: false,
							faculty: 'Подвижной состав и путевые машины',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						},
						{
							activeColor: '#ffeca7',
							activeColor1: '#ffeca7',
							show: false,
							show1: false,
							faculty: 'Системы обеспечения движения поездов',
							departments: [
								{dep: 'Управление эксплуатационной работой'},
								{dep: 'Менеджмент и логистика на транспорте'},
								{dep: 'Технологии грузовой и коммерческой работы, станции и узлы (ТГКРСУ)'},
							],
							groups: [
								{
									group: 'Технологии транспортных процессов/ТТП71',
									course: '2',
									student: '22'
								},
								{
									group: 'Техносферная безопасность/ТБ81',
									course: '1',
									student: '31'
								},
								{
									group: 'Эксплуатация железных дорог/ЭЖД71',
									course: '2',
									student: '26'
								},
							],
						}
					]
				},

            ],
        }
    },
	methods:{
		show: function (i) {
			if(i.show == false){
				i.show = true
				i.activeColor = '#d4d1fb'
				i.show1 = false
				i.activeColor1 = '#ffeca7'
			}
			else{
				i.show = false
				i.activeColor = '#ffeca7'
				i.show1 = false
				i.activeColor1 = '#ffeca7'
			}


		},
		show1: function (i) {
			if(i.show1 == false){
				i.show = false
				i.activeColor = '#ffeca7'
				i.show1 = true
				i.activeColor1 = '#d4d1fb'
			}
			else{
				i.show = false
				i.activeColor = '#ffeca7'
				i.show1 = false
				i.activeColor1 = '#ffeca7'
			}
		},
		showUniver(k,index){
			for(let i=0;i<this.block.length;i++){
				this.block[i].showUniver = false
				this.univers[i].onActive = false
			}
			this.block[index].showUniver = true
			this.univers[index].onActive = true
		},
		addDepartment(n, department){
			n.departments.push({dep: department})
		},
		addGroups(n, addGroup, addCourse, addStudents){
			n.groups.push({group: addGroup, course: addCourse, student: addStudents})
			
		},
		addFaculty(i,faculty){
			i.univerData.push({
				faculty: faculty,
				activeColor: '#ffeca7',
				activeColor1: '#ffeca7',
				show: false,
				show1: false,
				departments:[],
				groups:[]
				})
		},

		addUniver(inputNameUniver, file){
			fileInput = file[0].files[0]
				if( fileInput ){
					this.univers.push({imgSrc:'@/assets/University/'+ fileInput, univer: inputNameUniver})
					this.block.push(
						{showUniver: false,
						univerData:[],
						groups:[],
						}
					)
				}else{
					fileInput = null
				}
			
		},


	},
    
}

</script>

<style>
:focus{
	outline: 0;
}
.ps {
  height: 250px;
}
body{
    margin: 0;
	height: 1500px;
}
.scroll-univer {
  position: relative;
  margin: auto;
  width: 600px;
  height: 400px;
}
#page-wrap{
	display: inline-block;
	position: relative;
	width:100%;
	min-width: 1300px;
	height: 100%;
    min-height: 1500px;
}
.univer-list{
	position: absolute;
	top: 50px;
	left: 170px;
	height: 250px;
	overflow: hidden;
}
.univer-ul{
	list-style-type: none;
    overflow: hidden;
    overflow-x: scroll;
	max-height: 240px;
	width: 1000px;
	white-space: nowrap;
}
.add-univer{
	float: left;
	width: 210px;
	height: 180px;
	margin-top: 30px;
	border: 1px solid #c4c4c4;
}
#add-block1{
	width: 160px;
	height: 165px;
	float: right;
	margin-top: 8px;
	margin-right: 8px;
	background-color: #f4f4f4;
	border-radius: 10px;
	z-index: -1;
}
.univer-img-label{
	display: none;
	position: relative;
	top: 17px;
	width: 150px;
	height: 100px;
	padding: 35px 5px 0 5px;
	border-radius: 15px 15px 0 0;
	background-color: #f4f4f4;
	overflow-x: hidden;
	z-index: 10;
	justify-content: center;
}
#add-block2 input{
	width: 152px;
	height: 60px;
	position: relative;
	bottom: 120px;
	padding-left: 8px;
	background-color: #e5e5e5;
	border-radius: 0 0 10px 10px;
	border: 0;
	z-index: 15;
}

#input-img{
	opacity: 0;
	width: 0.5px;
	height: 0.5px;
}
.custom-file-label{
	display: block;
	position: relative;
	bottom: 10px;
	width: 150px;
	height: 56px;
	padding: 45px 5px 0 5px;
	border-radius: 15px 15px 0 0;
	background-color: #f4f4f4;
	font-size: 15px;
	color: black;
	text-align: center;
	overflow-x: hidden;
	z-index: 10;
}
.add-univer button{
	height: 5px;
	border: 0;
	padding: 0;
	margin-left: 6px;
	margin-top: -8px;
	background-color: white;
	color: #ebebeb;
	font-size: 45px;
}
.univer-li{
	display: inline-block;
	margin-left: 40px;
	margin-top: 30px;
	width: 180px;
	height: 180px;
	border: 1px solid #c4c4c4;
	border-radius: 10px;
	z-index: 1;
	white-space: normal;
}
.li-active{
	border: 5px solid #c2f4ff;
}
.univer-li p{
	position: relative;
	bottom: 18px;
	width: 100%;
	height: 70px;
	text-align: center;
	background-color: #c4c4c4;
	border-radius: 0 0 10px 10px;
	font-size: 15px;
	overflow-y: hidden;
}
.univer-li img{
	position: relative;
	height: 110px;
	left: 20%;
	border-radius: 10px 10px 0 0;
	background-color: white;
}
.slider-x{
	position: absolute;
	top: 300px;
	right: 270px;
	width: 70%;
	height: 25px;
	background-color: #9dc0c3;
	box-shadow: 0 12px 4px -4px #cdf0f5;
}
.department{
	width: 780px;
	max-width: 1000px;
	position: absolute;
	top: 400px;
	right: 27%;

}
.block-dep{
	min-width: 100%;
	height: 130px;
	border-radius: 15px;
	margin-bottom: 40px;
	padding: 0;
	box-shadow: 0 9px 4px -4px rgba(0, 0, 0, .2);
	list-style-type: none;
}
.department-transform{
	float:left;
	width: 10%;
	height: 100%;
	background-color: #b2d7ca;
	border-radius: 15px 0 0 15px;
}
.department-name{
	float: right;
	width: 90%;
	height: 80px;
	background-color: #f2f2f2;
	text-align: center;
	font-size: 24px;
	border-radius: 0 15px 0 0;
}
.departments, .groups{
	position: relative;
	bottom: 0;
	width: 44.9%;
	text-align: center;
	background-color: #ffeca7;
	height: 50px;
	border: 0.5px solid #f2f2f2;
	border-bottom: 0;
	border-left: 0;
}
.departments{
	float: left;
}
.groups{
	float: right;
	border-radius: 0 0 15px 0;
}
.block-dep p{
	vertical-align: middle;
}
.block-dep button{
	width: 100%; 
	height: 50px;
	padding: 0;
	border: 0;
	font-size: 20px;
	background-color: #ffeca7;
}

.activeColor{
	background-color: '#d4d1fb' !important;
}
.all-list, .all-groups{
	position: relative;
	bottom: 35px;
	width: 688px;
	background-color: #dcdcdc;
	margin-left: 5.5%;
	padding: 25px 0 20px 25px;
	border-radius: 0 0 15px 15px;
	list-style-type: none;
}
.all-list li{
	width: 90%;
	background-color: #f1f1f1;
	padding: 10px 15px;
	margin-bottom: 15px;
	font-size: 16px;
}
.input-department, .input-group{
	height: 36px !important;
	background-color: #cfcfcf !important;
	border: 0.5px solid white;
	padding: 0 15px 0 15px !important;
	z-index: 2;
}
.input-group{
	width: 656px;
	border: 0 !important;
	padding: 0 !important;
}
.input-department div{
	position: relative;
	bottom: 5px;
	float: left;
	width: 6%;
	height: 35px;
	font-size: 40px;
	color: white;
}
.input-department input, .input-group input{
	float: left;
	width: 94%;
	height: 33px;
	border: 0;
	background-color: #cfcfcf;
	color: white;
}
.input-group div{
	float: left;
	border: 0.5px solid white !important;
	width: 36px;
	height: 25px !important;
	margin-right: 10px;
	color: white;
	text-align: center;
	background-color: #dcdcdc !important;
	z-index: 1;
}
.input-group div span{
	font-size: 40px;
	position: relative;
	bottom: 14px;
	z-index: 0;
}
.add-group{
	height: 35px !important;
	width: 385px !important;
	display: inline-block;
	border: 0.5px solid white !important;
	padding: 0 0 0 15px !important;
	margin-right: 14px;
}
.add-course{
	height: 35px !important;
	width: 59px !important;
	display: inline-block;
	border: 0.5px solid white !important;
	padding: 0 15px 0 15px !important;
	margin-right: 15px;
}
.add-students{
	height: 35px !important;
	width: 58px !important;
	display: inline-block;
	border: 0.5px solid white !important;
	padding: 0 15px 0 15px !important;	
}
.all-groups li{
	margin-bottom: 20px;
	height: 30px;
	background-color: #dcdcdc;
	font-size: 16px;
}
.all-groups li div{
	padding-top: 10px;
	background-color: #f1f1f1;
	height: 100%;
}
.name-group{
	width: 65%;
	margin-right: 15px;
	height: 30px;
	display: inline-block;
	text-align: center;
}
.course, .number-student{
	width: 13%;
	margin-right: 15px;
	height: 30px;
	display: inline-block;
	text-align: center;
}
.all-groups_content{
	width: 100%;
}

.add-department{
	width: 100%;
	height: 150px;
	border: 0.5px solid #c3c3c3;
}
.add-dep1{
	float: right;
	width: 90%;
	height: 140px;
	margin: 5px 10px 5px 0;
	background: #f4f4f4;
	border-radius: 10px;
}
.add-dep2{
	display: inline-block;
	height: 100%;
	width: 9%;
	background: #e5e5e5;
	border-radius: 10px 0 0 10px;
}
.add-dep3{
	display: inline-block;
	position: relative;
	bottom: 90px;
	width: 90%;
	height: 83px;
	border: 2px solid #e5e5e5;
	background-color: #f4f4f4;
	border-radius: 0 15px 0 0;
	text-align: center;
	font-size: 18px;
	color: black;
}
.add-dep4, .add-dep5{
	float: right;
	position: relative;
    bottom: 88px;
	right: 3px;
    width: 44.9%;
	height: 46px;
	border: 2px solid #e5e5e5;
	border-top: 0;
	border-left: 0;
	background-color: #f4f4f4;
}
.add-department button{
	width: 7%;
	margin-top: 5px;
	margin-left: 1%;
	font-size: 45px;
	background: white;	
	color: #c3c3c3;
	border: 0;
}
.borderActive{
	border: 5px solid #b2d7ca !important;
	border-radius: 15px;
	margin-top: 27px;
	margin-left: 37px;	
}
</style>