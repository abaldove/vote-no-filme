$(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);
var posicaoAtual = 1;
$(document).ready(function(){   
  $('#voto_super').hide();
  $('#form-div').hide();
  angular.element(controller).scope().updateCarosel();    
 
$(':input').bind('focus', function (e) { 
  $(this).validationEngine('hide');
});

 $('#sendVotesButton').bind('click', function (e) { 
  if($('#inputEmail').validationEngine('validate') == true)
 {
  return ;
 }
  if($("#inputLogin").validationEngine('validate') == true)
  {
    return;
  }

  angular.element(controller).scope().saveVotesAndUser();
    
  });

  
  $('.carousel-control.right').bind('click', function (e) {

  var collapsed = angular.element(controller).scope().isCollapsed;

    if(collapsed == false)
    {
      angular.element(controller).scope().isCollapsed=true;
    }
    posicaoAtual++;
    if(posicaoAtual == angular.element(controller).scope().movies.length)
    {
      $('.carousel-control.right').hide();
    }
    $('.btn.btn-primary.btn_vota').removeAttr('disabled'); 
    
  });

});

var addButtonBind = function()
{
  $('.btn.btn-primary.btn_vota').bind('click', function (e) {
    $('.btn.btn-primary.btn_vota').attr('disabled','disabled'); 
      if(posicaoAtual == angular.element(controller).scope().movies.length)
      {
        $('#div_carousel').hide(1000);
        $('#form-div').show('slow');
        $('.lead').html('Preencha os campos abaixo,caso ja tenha feito isso antes usar o mesmo email e login.');
        
      }
  });
};

