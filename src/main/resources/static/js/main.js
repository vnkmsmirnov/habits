$(function () {

    $('button').button().click(function(e) {
        $('#dialog-form').dialog("open")
    });

    $('#dialog-form').dialog({
        autoOpen: false,
        buttons: [{
            id: 'add',
            text: 'Добавить',
            click: function() {

                $.ajax({
                    type: "GET",
                    url: "/api/event/add",
                    data: {
                        type: event_type.val(),
                        start: event_start.val(),
                        end: event_end.val(),
                        op: 'add'
                    },
                    success: function(id){
                        calendar.fullCalendar('renderEvent', {
                            id: id,
                            title: event_type.val(),
                            start: event_start.val(),
                            end: event_end.val(),
                            allDay: false
                        });
                        $('#calendar').fullCalendar('rerenderEvents');
                        $('#calendar').fullCalendar('refetchEvents');
                    }
                });

                emptyForm();
                $(this).dialog('close');
            }
        },
            {   id: 'edit',
                text: 'Изменить',
                click: function() {
                    //some code
                }
            },
            {   id: 'cancel',
                text: 'Отмена',
                click: function() {
                    $(this).dialog('close');
                    emptyForm();
                }
            },
            {   id: 'delete',
                text: 'Удалить',
                click: function() {
                    //some code
                },
                disabled: true
            }]
    });

});

var event_id = $('#event_id');
var event_type = $('#event_type');
var event_start = $('#event_start');
var event_end = $('#event_end');
var calendar = $('#calendar');
var form = $('#dialog-form');
var format = "DD/MM/YYYY HH:mm";
/* кнопка добавления события */
$('#add_event_button').button().click(function(){
    formOpen('add');
});
/** функция очистки формы */
function emptyForm() {
    event_id.val("");
    event_type.val("");
    event_start.val("");
    event_end.val("");
}
/** режимы открытия формы */
function formOpen(mode) {
    if(mode == 'add') {
        /** скрываем кнопки Удалить, Изменить и отображаем Добавить*/
        $('#add').show();
        $('#edit').hide();
        $("#delete").button("option", "disabled", true);
    }
    else if(mode == 'edit') {
        /** скрываем кнопку Добавить, отображаем Изменить и Удалить*/
        $('#edit').show();
        $('#add').hide();
        $("#delete").button("option", "disabled", false);
    }
    form.dialog('open');
}

$(function() {
    // $( "#datepicker" ).datepicker('getDate');
    $("#datepicker").datepicker({
        beforeShowDay: function(date) {
            var selectable = true;
            var classname = "";
            event_type = "\u20AC" + dayrates[date.getDay()];
            return [selectable, classname, event_type];
        }
    });
});

$(document).ready(function() {
    $('#calendar').fullCalendar({
        events: {
            url : '/api/event/all'
        },
        firstDay: 1,
        height: 600,
        header: {
            left: 'prev,next today',
            center: 'title',
            right: 'month,agendaWeek,agendaDay'
        },
        dayClick: function(date, allDay, jsEvent, view) {
            var newDate = $.fullCalendar.formatDate(date, format);
            event_start.val(newDate);
            event_end.val(newDate);
            formOpen('add');
        }
    });
});