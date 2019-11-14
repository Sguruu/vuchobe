<template>
    <div class="calendar">
        <div class="calendar-blok" v-for='(item,index) in calendar' :key="index.id">
            <div class="calendar-block-2" v-show= 'item.show'>
                    <div class="navigation">
                        <button class='back' @click='ShowBack(item,index)'>></button>
                        <div>{{item.numberWeek}}</div>
                        <button class='next' @click='ShowNext(item,index)'>></button>
                    </div>
                    <ul class="study-calendar">
                        <li v-for='l in item.conteiner' :key="l.id">
                            <span class="weekday">{{ l.weekday }}</span>
                            <span class="day">{{ l.day }}</span>
                            <span class="audience">{{ l.audience }}</span>
                        </li>
                    </ul>
            </div>
        </div>
    </div>

</template>

<script>
export default {
    name: 'calendar',
    data() {
        return{
            calendar: [
                    {
                    show:true,
                    numberWeek: '1/2 недели',
                    conteiner: [
                    {
                        weekday: 'Пн',
                        day: '1',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '2',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '3',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '4',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '5',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '6',
                        audience: '39',

                    },
                    {
                        weekday: 'Пн',
                        day: '8',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '9',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '10',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '11',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '12',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '13',
                        audience: '39',

                    },
                    ]
                },

                {
                    show: false,
                    numberWeek: '3/4 недели',
                    conteiner: [
                    {
                        weekday: 'Пн',
                        day: '15',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '16',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '17',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '18',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '19',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '20',
                        audience: '39',

                    },
                    {
                        weekday: 'Пн',
                        day: '22',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '23',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '24',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '25',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '26',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '27',
                        audience: '39',

                    },
                ],

                },
                {
                    show: false,
                    numberWeek: '5/6 недели',
                    conteiner: [
                    {
                        weekday: 'Пн',
                        day: '29',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '30',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '1',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '2',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '3',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '4',
                        audience: '39',

                    },
                    {
                        weekday: 'Пн',
                        day: '6',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '7',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '8',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '9',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '10',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '11',
                        audience: '39',

                    },
                ],

                },

                {
                    show: false,
                    numberWeek: '7/8 недели',
                    conteiner: [
                    {
                        weekday: 'Пн',
                        day: '13',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '14',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '15',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '16',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '17',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '18',
                        audience: '39',

                    },
                    {
                        weekday: 'Пн',
                        day: '20',
                        audience: '29',
                    },
                    {
                        weekday: 'Вт',
                        day: '21',
                        audience: '30',

                    },
                    {
                        weekday: 'Ср',
                        day: '22',
                        audience: '34',

                    },
                    {
                        weekday: 'Чт',
                        day: '23',
                        audience: '42',

                    },
                    {
                        weekday: 'Пт',
                        day: '24',
                        audience: '26',

                    },
                    {
                        weekday: 'Сб',
                        day: '25',
                        audience: '39',

                    },
                ],

                },
            ]
    }
},
    methods: {
        ShowNext: function(item,index){
            if(index + 1 < this.calendar.length){
                item.show = false
                this.calendar[index + 1].show = true
            }
        },
        ShowBack: function(item,index){
            if(index - 1>= 0){
                item.show = false
                this.calendar[index - 1].show = true
            }
        },
    }
}
</script>

<style>
.calendar{
    width: 100%;
}
.navigation{
    width: 90%;
    height: 30px;
    font-size: 20px;
    margin-bottom: 30px;
}
.back{
    width: 10%;
    height: 100%;
    display: block;
    float: left;
    border: 0;
    background-color: white;
    font-size: 28px;
    transform: rotate(180deg);
}
.navigation div{
    position: relative;
    width: 80%;
    text-align: center;
    float: left
}
.next{
    position: relative;
    width: 10%;
    height: 100%;
    display: block;
    float: right;
    border: 0;
    background-color: white;
    font-size: 28px;

}
.study-calendar{
	position: relative;
    left: 5%;
    width: 900px;
	height: 205px;
	list-style-type: none;
	padding: 0;
    margin-bottom: 50px;
}
.study-calendar li{
	display: inline-block;
	border: 1px solid black;
	border-right: 0;
	margin:0;
	width: 140px;
	height: 100px;
}
.study-calendar li:nth-child(6n){
	border-right: 1px solid black;
}
.study-calendar .weekday{
	position: relative;
	top: 10px;
	left: 10px
}
.study-calendar .day{
	position: relative;
	float: right;
	right: 20px;
	top: 10px;
}
.study-calendar .audience{
	position: relative;
	left: 80px;
	top: 70px;
}
</style>