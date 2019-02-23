var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save(_this);
        });

        $('.to-read').on('click', function(){
            _this.read(this);
        });

        $('#btn-modify').on('click', function(){
            $('#rTitle').attr('readonly', null);
            $('#rContent').attr('readonly', null);
            $('#modal-footer-normal').attr('hidden', 'true');
            $('#modal-footer-modify').attr('hidden', null);
        });

        $('#btn-modify-cancel').on('click', function(){
            $('#rTitle').attr('readonly', 'true');
            $('#rContent').attr('readonly', 'true');
            $('#modal-footer-normal').attr('hidden', null);
            $('#modal-footer-modify').attr('hidden', 'true');
        });

        $('#btn-modify-ok').on('click', function(){
            _this.modify(_this);
        });

        $('#btn-delete').on('click', function(){
           _this.delete();
        });

        $('#savePostsModal').on('click', "button[data-dismiss = 'modal']", function () {
           var form = $(this).parents("div[id = 'savePostsModal']").find('form')[0];

           for(var i = 0; i < form.length; i++){
               form[i].value = "";
           }
        });

        $("div[id*='PostsModal']").on('click', "button[data-dismiss = 'modal']", function(){
            $('.error-message').remove();
        });
    },
    save : function(_this){
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/posts',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            location.reload();
        }).fail(function(response){
            _this.markingErrorField(response);
        });

    },
    read : function(td){
        var data = {
            id : $(td).prev().text(),
            title: $(td).text(),
            author: $(td).next().text(),
            content: $(td).next().next().next().text()
        };

        $('#rId').val(data.id);
        $('#rTitle').val(data.title);
        $('#rAuthor').val(data.author);
        $('#rContent').val(data.content);
        $('.form-control[readonly]').css('backgroundColor', '#fff');
    },
    modify : function(_this){
        var fn = this.modify['name'];
        var pNum = $('#rId').val();
        var data = {
            title: $('#rTitle').val(),
            author: $('#rAuthor').val(),
            content: $('#rContent').val()
        };

        $.ajax({
            type: 'PUT',
            url: '/posts/' + pNum,
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 수정되었습니다.');
            location.reload();
        }).fail(function(response){
            _this.markingErrorField(response, fn);
        });
    },
    delete : function(){
        var pNum = $('#rId').val();

        $.ajax({
            type: 'DELETE',
            url: '/posts/' + pNum,
        }).done(function(){
            alert('글이 삭제되었습니다.');
            location.reload();
        }).fail(function(error){
            console.log('An Exception occurred...');
            alert(error);
        });
    },
    markingErrorField : function (response, fn) {
        var errorFields = response.responseJSON.errors;

        if(!errorFields){
            alert(response.response.message);
            return;
        }

        $('.error-message').remove();

        if(fn == 'modify'){
            var $field, error;
            for(var i=0, length = errorFields.length; i<length;i++){
                error = errorFields[i];

                $field = $('#r'+(error['field']).substring(0, 1).toUpperCase() + error['field'].substring(1));

                if($field && $field.length > 0){
                    $field.after('<span class="error-message text-small text-danger">'+error.defaultMessage+'</span>');
                }
            }
        }else{
            var $field, error;
            for(var i=0, length = errorFields.length; i<length;i++){
                error = errorFields[i];

                $field = $('#'+error['field']);

                if($field && $field.length > 0){
                    $field.after('<span class="error-message text-small text-danger">'+error.defaultMessage+'</span>');
                }
            }
        }
    }
};

main.init();