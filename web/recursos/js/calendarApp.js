/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// DARK MODE TOGGLE
//document.querySelector('.dark-mode-switch').onclick = () => {
//    document.querySelector('body').classList.toggle('dark')
//    document.querySelector('body').classList.toggle('light')
//}

// CHECK LEAP YEAR 
isLeapYear = (year) => {
    return (year % 4 === 0 && year % 100 !== 0 && year % 400 !==0) || (year % 100 === 0 && year % 400 === 0)
}

getFebDays = (year) => {
    return isLeapYear(year) ? 29 : 28
}

let calendar = document.querySelector('.calendar')

const month_names = ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio', 'Julio', 'Agosto', 'Septiembre', 'Octubre', 'Noviembre', 'Diciembre']

let month_picker = document.querySelector('#month-picker')

month_picker.onclick = () => {
    month_list.classList.add('show')
}

// GENERATE CALENDAR 

generateCalendar = (month, year) => {
    let calendar_days = document.querySelector('.calendar-days')
    calendar_days.innerHTML = ''
    let calendar_header_year = document.querySelector('#year')
    
    let days_of_month = [31, getFebDays(year), 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    
    let currDate = new Date()
    
    month_picker.innerHTML = month_names[month]
    calendar_header_year.innerHTML = year
    
    let first_day = new Date(year, month, 1)
//    let first_day = new Date(month, year, 1)
    
    for (let i = 0; i <= days_of_month[month] + first_day.getDay() - 1; i++) {
        let day = document.createElement('div')
//        let day_prueba = document.createElement('div')
        if (i >= first_day.getDay()) {
            day.classList.add('calendar-day-hover')
//            day_prueba.classList.add('calendar-day-prueba')
//            day_prueba.innerHTML = `<div class="day_prueba">`+(i - first_day.getDay() + 1)+`</div>`
//            day.innerHTML = i - first_day.getDay() + 1
            day.innerHTML = `<input type="hidden" value="`+year+`" name="txt_fec_calY" class="form-control" />`
            day.innerHTML += `<input type="hidden" value="`+(month+1)+`" name="txt_fec_calM" class="form-control" />`
            day.innerHTML += `<input type="submit" value="`+(i - first_day.getDay() + 1)+`" name="accion" class="btn btn-outline-success" />`
//            day.innerHTML += `<span></span>
//                            <span></span>
//                            <span></span>
//                            <span></span>`
            if(i - first_day.getDay() + 1 === currDate.getDate() && year === currDate.getFullYear() && month === currDate.getMonth()){
                day.classList.add('curr-date')
            }
        }
        
//        day.onclick = () => {
//            let txt_prueba = document.querySelector('#txt_prueba')
//            txt_prueba.value = day.value()
//        }
        
        calendar_days.appendChild(day)
//        calendar_days.appendChild(day_prueba)
    }
}


let month_list = calendar.querySelector('.month-list')

month_names.forEach((e, index) => {
    let month = document.createElement('div')
    month.innerHTML = `<div>${e}</div>`
    month.onclick = () => {
        month_list.classList.remove('show')
        curr_month.value = index
        generateCalendar(curr_month.value, curr_year.value)
    }
    month_list.appendChild(month)
})


document.querySelector('#prev-year').onclick = () => {
    --curr_year.value
    generateCalendar(curr_month.value, curr_year.value)
}

document.querySelector('#next-year').onclick = () => {
    ++curr_year.value
    generateCalendar(curr_month.value, curr_year.value)
}



let currDate = new Date()

let curr_month = {value: currDate.getMonth()}
let curr_year = {value: currDate.getFullYear()}

generateCalendar(curr_month.value, curr_year.value)
