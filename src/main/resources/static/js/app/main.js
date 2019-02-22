var main = {
    init : function(){
        var _this = this;
        $('#btn-save').on('click', function(){
            _this.save();
        });

        $('.to-read').on('click', function(){
            _this.read(this);
        });

        $('#btn-modify').on('click', function(){
            $('#rTitle').attr('readonly', null);
            $('#rAuthor').attr('readonly', null);
            $('#rContent').attr('readonly', null);
            $('#modal-footer-normal').attr('hidden', 'true');
            $('#modal-footer-modify').attr('hidden', null);
        });

        $('#btn-modify-cancel').on('click', function(){
            $('#rTitle').attr('readonly', 'true');
            $('#rAuthor').attr('readonly', 'true');
            $('#rContent').attr('readonly', 'true');
            $('#modal-footer-normal').attr('hidden', null);
            $('#modal-footer-modify').attr('hidden', 'true');
        });

        $('#btn-modify-ok').on('click', function(){
            _this.modify();
        });

        $('#btn-delete').on('click', function(){
           _this.delete();
        });
    },
    save : function(){
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
        }).fail(function(error){
            alert(error);
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
    modify : function(){
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
        }).fail(function(error){
            alert(error);
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
    }
};

main.init();