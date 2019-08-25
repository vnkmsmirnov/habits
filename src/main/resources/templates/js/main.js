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
                    //some code
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



        // $(function() {
        //
        //     $('#dialog-form').dialog({
        //         buttons: [{text: "Добавить", click: function() {/* выполнить некоторые действия */}},
        //             {text: "Отмена", click: function() {$(this).dialog("close")}}]
        //     });
        //
        // });



    });

var event_start = $('#event_start');
var event_end = $('#event_end');
var event_type = $('#event_type');
var calendar = $('#calendar');
var form = $('#dialog-form');
var event_id = $('#event_id');
var format = "MM/dd/yyyy HH:mm";
/* кнопка добавления события */
$('#add_event_button').button().click(function(){
    formOpen('add');
});
/** функция очистки формы */
function emptyForm() {
    event_start.val("");
    event_end.val("");
    event_type.val("");
    event_id.val("");
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
    event_start.datetimepicker({hourGrid: 4, minuteGrid: 10, dateFormat: 'mm/dd/yy'});
    event_end.datetimepicker({hourGrid: 4, minuteGrid: 10, dateFormat: 'mm/dd/yy'});
}

$(document).ready(function() {
    $('#calendar').fullCalendar({
        events: {
            url : '/api/event/all',
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
