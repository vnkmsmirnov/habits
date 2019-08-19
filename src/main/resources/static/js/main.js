form.dialog({
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
/* режимы открытия формы */
function formOpen(mode) {
    if(mode == 'add') {
        /* скрываем кнопки Удалить, Изменить и отображаем Добавить*/
        $('#add').show();
        $('#edit').hide();
        $("#delete").button("option", "disabled", true);
    }
    else if(mode == 'edit') {
        /* скрываем кнопку Добавить, отображаем Изменить и Удалить*/
        $('#edit').show();
        $('#add').hide();
        $("#delete").button("option", "disabled", false);
    }
    form.dialog('open');
}