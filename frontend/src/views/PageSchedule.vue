<template>
    <div class="page-schedule">
        <PageHeader/>

        <div class="content">

            <div class="number-group">

                <select name="groups" id="group-select" form='group'>
                    <option disabled='disabled' selected='selected'>Выберите группу</option>
                    <option v-for= 'item in list' :key='item.id'>{{ item.group }}</option>
                </select>
            </div>

            <ScheduleCalendar/>

            <form id='group'>

                
                <table class='schedule-edit'>
                    <tr>
                        <th class='item-column'>N</th>
                        <th class='time'>Время</th>
                        <th class='discipline'>Дисциплина</th>
                        <th class='type'>Тип</th>
                        <th class='subgroup'>Подгруппа</th>
                        <th class='teacher'>Преподаватель</th>
                        <th class='cabinet'>Аудитория</th>
                    </tr>
                    <tr v-for='i in schedule' :key='i.id'>
                        <td class='item-column'><input type="text"  v-model='i.item'></td>
                        <td class='time'><input type="text" v-model='i.time'></td>
                        <td class='discipline'><input type="text" v-model='i.discipline'></td>
                        <td class='type'><input type="text" v-model='i.type'></td>
                        <td class='subgroup'><input type="text" v-model='i.subgroup'></td>
                        <td class='teacher'><input type="text" v-model='i.teacher'></td>
                        <td class='cabinet'><input type="text" v-model='i.cabinet'></td>
                    </tr>
                </table>
            </form>


		</div>
    </div>
</template>

<script>
import PageHeader from '@/components/PageHeader.vue'
import ScheduleCalendar from '@/components/SheduleCalendar.vue'
export default {
    name: 'page-schedule',
    components: {
        ScheduleCalendar,
        PageHeader
    },
    data(){
        return{
            list: [
                {group: '6445'},
                {group: '6442'},
                {group: '6441'}
            ],
            schedule: [
                {
                    item: '1',
                    time: '08:00',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
                {
                    item: '2',
                    time: '08:00',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
                {
                    item: '3',
                    time: '08:00',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
                {
                    item: '4',
                    time: '09:45',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
                {
                    item: '5',
                    time: '11:30',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
                {
                    item: '6',
                    time: '13:30',
                    discipline: 'УМФ',
                    type: 'Л',
                    subgroup: 'X',
                    teacher: 'Воробьев А.А.',
                    cabinet: '15'
                },
            ],
            show: false,
            }
        }
    }

</script>

<style>
body{
	margin: 0;
	height: 1300px;
    min-width: 100px;
}
.page-wrap{
	display: inline-block;
	position: relative;
	width:100%;
	min-width: 1300px;
	height: 100%;
	min-height: 100%;
}
.content{
    position: absolute;
    left: 13%;
    top: 100px;
    width: 70%;
}
.number-group{
    width: 30%;
    margin-bottom: 50px;
}
label{
	text-align: center;
    font-size: 18px;
    margin-bottom: 100px;
}
select{
    width: 100%;
    height: 30px;
	border: 1px solid black;
	margin-top: 20px;
	padding: 0;
}
.list-group li{
	border: 1px solid black;
	border-top: 0;
	padding: 5px 10px;

}

.schedule-edit{
	width: 850px;

}
.schedule-edit th{
	text-align: center
}
.schedule-edit td{
	border: 1px solid black;
	text-align: center;
}
.schedule-edit input{
	border: 0;
	height: 30px;
	width: 100%;
	text-align: center;
}
.schedule-edit .item-column {
	width: 50px;
}
.schedule-edit .time{
	width: 80px;
}
.schedule-edit .discipline{
	width: 100px;

}
.schedule-edit .type{
	width: 50px;
}
.schedule-edit .subgroup{
	width: 50px
}
.schedule-edit .teacher{
	width: 150px
}
.schedule-edit .cabinet{
	width: 50px
}
</style>