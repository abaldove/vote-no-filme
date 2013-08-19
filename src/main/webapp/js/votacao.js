angular.module('vote-filme', ['ui.bootstrap']);
function VotacoCtrl($scope,$http) {
  $scope.myInterval = -1;
  var movies = $scope.movies = [];
  var votes = $scope.votes = [];
  var userRanking = $scope.userRanking = [];
  var generalRanking = $scope.generalRanking = [];
  var moviesImage = $scope.moviesImage = [];

 $scope.isCollapsed = true;

 $scope.weekMovieIsCollapsed = true;

 $scope.position = 0;

 $scope.updatePosition = function(pos) {
    $scope.isCollapsed = !$scope.isCollapsed;
    $scope.position = pos;
  };

  $scope.updateCarosel = function()
  {
    $.getJSON('/vote-no-filme/movies', function (json) {

           $.each(json.movie, function(key, value){ 
              $scope.$apply(function () { 
                moviesImage[value.name] = "img/"+value.posterName;
                if(value.weekMovie == '1')
                {
                  $scope.weekMovie = {                      
                       image: "img/"+value.posterName,
                       name: value.name,
                       sinopse: value.sinopse,
                       imdbRating: value.imdbRating,
                       movieId:value.id
                      };
                }else{                 
                      movies.push({
                         image: "img/"+value.posterName,
                         name: value.name,
                         sinopse: value.sinopse,
                         imdbRating: value.imdbRating,
                         movieId:value.id
                       });
                    }
                      $('.carousel-control.left').hide();                                           
                    });
                });
                addButtonBind();
            });
  };

$scope.addVote = function (movieId) {
  if(votes[movieId] == null)
    {
      votesQt = 1;
    }else{
    votesQt = votes[movieId].votesQnty + 1;
    }
    votes[movieId] = {movieId:movieId,votesQnty:votesQt};
  }
$scope.updateRanking = function(login,email){       
    $.getJSON('/vote-no-filme/movies/userRanking?login='+login+'&email='+email, function (json) {      
      $.each(json.userRating, function(key, value){
        $scope.$apply(function(){                 
          userRanking.push({
            name: value[0],
            votes: value[1],
            image: moviesImage[value[0]],
            generalVotes: $scope.generalRanking[value[0]]
          });
          $('#ranking').show('fast');
          $('.lead').html('Veja o seu ranking e o ranking geral.');
        });
      });
    });
  }  

  $scope.createGeneralRanking = function(login,email){
    $.getJSON('/vote-no-filme/movies/generalRanking', function (json) {
       $scope.$apply(function(){
    $.each(json.map, function(key, value){     
      $scope.generalRanking[value[0]] = value[1];          
      });
       });
     $scope.updateRanking(login,email);
    });
  };

  $scope.saveVotesAndUser = function(){
    var login = $('#inputLogin').val();
    var email = $('#inputEmail').val()
    var quantidade = 0;
    var data = new Object();
    
     $.each(angular.element(controller).scope().votes,function(key,value){
       if(value != null)
      {
        data['votes['+quantidade+'].movieId'] = value.movieId;
        data['votes['+quantidade+'].votesQnty'] = value.votesQnty;
        quantidade++;
      }
     });
    data['login'] = login;
    data['email'] = email;
  var postVotes = $.ajax({             
    type: "POST",
      data: data,
      url: '/vote-no-filme/movies/addVotes',
      async: false      
    }).always(function(result){       
      $('#form-div').hide();
      $scope.createGeneralRanking(login,email);
    });
  $.ajax({             
     type: "POST",
      data:'login='+login+'&email='+email,
      url: '/vote-no-filme/movies/addUser',
      async: false,
      complete : function(result){       
      postVotes;
      }
    });
  }
}