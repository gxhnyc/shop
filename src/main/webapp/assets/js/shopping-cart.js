console.log('shopping-cart.js');

$('.item-amount').change(function() {
    console.log('数量变了，发送ajax请求');
    
    var csrfParamName = $('meta[name="_csrf_parameter"]').attr('content');
    var csrfParamValue = $('meta[name="_csrf"]').attr('content');
    
    
    var data = {
    	/*data-cellphone-id	,jsp自动拆解附加参数为data('cellphone-id')*/
        cp_id: $(this).data('cellphone-id'),
        amount: $(this).val()
    };
    data[csrfParamName] = csrfParamValue;
    
    $.ajax('/shop/uc/shopping-cart/update-item-amount', {
        method: 'POST',
        data: data // 该data将以表单数据格式编码成文本（Content-Type: application/x-www-form-urlencoded）
    }).then(function(cart) {
        console.log('请求成功，更新总金额');
        console.log(cart);
        $('#totalCost').html(cart.totalResult / 100);
    }).fail(function() {
        console.error('请求失败');
    });
});