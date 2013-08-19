<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html ng-app="vote-filme">
  <head>
    <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.0.5/angular.js"></script>
    <script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.5.0.js"></script>
    <script src="js/jquery.min.js"></script>    
    <script src="js/jquery.blockUI.js"></script>  
    <script src="js/jquery.validationEngine.js"></script>  
    <script src="js/jquery.validationEngine-pt_BR.js"></script>  
    <script src="js/jquery.json-2.4.min.js"></script>  

    <link rel="shortcut icon" href="img/favicon.png" type="image/x-icon" />
    <link rel="icon" href="img/favicon.png" type="image/x-icon" />
    <link href="css/style.css" rel="stylesheet" rel="stylesheet">
    <link href="css/validationEngine.jquery.css" rel="stylesheet" rel="stylesheet">
    <link href="css/votecard.css" rel="stylesheet" />
    <link href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.min.css" rel="stylesheet">    
<style type="text/css">
.votecard {
  background: url(img/sprite.png) no-repeat 0 0;
  padding: 4px;
  width: 63px;
  height: 43px;
  text-align: center;
}
</style>   
</head>
  <div class="navbar navbar-fixed-top">
    <div class="navbar-inner">
      <div class="container">
        <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        </a>
        <a class="brand" href="#">Vote No Filme</a>
      </div>
    </div>
  </div>
</div>

<div class="page-header div-header">
  <div class="hero-unit featurette">
    <h2 class="featurette-heading">Bem vindo ao site <span class="muted">Vote No Filme</span></h2>
    <p class="lead">Vote nos seus filmes favoritos.</p>
  </div>
</div>
<body>
<div class="container" id="controller" ng-controller="VotacoCtrl">
  <div id="div_carousel">
  <div class="featurette">
     <div class="row">
       <div class="span6">
         <div class="carousel-inner" style="display:block;">
           <img class="img-fix" src="{{weekMovie.image}}">
           <div class="div-descricao">
               <p class="p-fix">{{weekMovie.name}}</p>
               <button type="button" ng-click="weekMovieIsCollapsed = !weekMovieIsCollapsed" class="btn btn-primary">+ Info</button>
               <button type="button" id="btn_votar_week" ng-click="addVote(weekMovie.movieId)" class="btn btn-primary btn_vota">Votar</button>
            </div>
          </div>
          <div id="week_movie_content_slide" collapse="weekMovieIsCollapsed" style="padding-top:20px;">
              <div class="well well-large">
                <p><b>Nome:</b> {{weekMovie.name}}</p>
                <p><b>Nota IMDB:</b><rating value="{{weekMovie.imdbRating}}" max="10" readonly="true"></rating>
                <span class="badge badge-inverse" ng-show="true">{{weekMovie.imdbRating}}/ 10</span></p>
                <p><b>Lançamento:</b> {{weekMovie.releaseDate}}</p>
                <p><b>Sinopse:</b> {{weekMovie.sinopse}}</p>
              </div>
          </div>
        </div>
        <div class="span6">
          <div id="carousel-filmes">
            <carousel interval="myInterval">
            <slide ng-repeat="movie in movies" active="movie.active">
            <img ng-src="{{movie.image}}" style="margin:auto;display:block;">
            <div class="carousel-caption">
              <p>{{movie.name}}</p>
              <button type="button" class="btn btn-primary" ng-click="updatePosition($index)">+ Info</button>
              <button type="button" id="btn_votar" class="btn btn-primary btn_vota" ng-click="addVote(movie.movieId)" >Votar</button>
            </div>
            </slide>
            </carousel>
            <div id="movie_content_slide" collapse="isCollapsed">
              <div class="well well-large">
                <p><b>Name:</b> {{movies[position].name}}</p>
                <p><b>Nota IMDB:</b><rating value="{{movies[position].imdbRating}}" max="10" readonly="true"></rating>
                <span class="badge badge-inverse" ng-show="true">{{movies[position].imdbRating}}/ 10</span></p>       
                <p><b>Lançamento:</b> {{movies[position].releaseDate}}</p>
                <p><b>Sinopse:</b> {{movies[position].sinopse}}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div id="form-div">
  <div class="featurette">
    <div class="row">
      <div class="span4"></div>
      <div class="span4">
        <form class="form-horizontal" id="userForm" method="post" action="">
          <div class="control-group">
            <label class="control-label" for="inputEmail">Email</label>
            <div class="controls">
              <input type="email" class="input-xlarge validate[required,custom[email]] input-field" id="inputEmail" placeholder="Email" data-prompt-position="topLeft" />
            </div>
          </div>
          <div class="control-group">
            <label class="control-label" for="inputLogin">Usuario</label>
            <div class="controls">
              <input type="text" class="text-input validate[required] input-field" id="inputLogin" placeholder="Usuario" data-prompt-position="bottomLeft"  />
            </div>
          </div>
          <div class="control-group">
            <div class="controls">
              <button type="submit" id="sendVotesButton" class="btn btn-primary">Sign in</button>
            </div>
          </div>
        </form>
      </div>
      <div class="span4"></div>
    </div>
   </div> 
 </div>
   <div id="ranking" ng-repeat="rank in userRanking">
      <hr class="featurette-divider">    
      <div class="featurette">
         <img class="featurette-image pull-right" ng-src="{{rank.image}}">
         <h2 class="featurette-heading">{{rank.name}}</h2>      
         <div class="row">
           <div class="span1"> 
             <div class="votecard">        
              <div>
                  <em><strong>{{rank.votes}}</strong><span>Seus Votos</span></em>
                </div>
              </div>
           </div>
           <div class="span1"> 
              <div class="votecard">        
                <div>
                  <em><strong>{{rank.generalVotes}}</strong><span>Geral</span></em>
                </div>
              </div>          
           </div>          
        </div> 
      </div>   
    </div>
    <hr class="featurette-divider">
  </div>  
  <footer style="background-color:rgb(238, 238, 238);">
    <p class="pull-right"><a href="#">Back to top</a></p>
    <p>&copy; 2013 Company, Inc. &middot; <a href="#">Privacy</a> &middot; <a href="#">Terms</a></p>
  </footer>  
</body>
    <script src="js/controls.js"></script>
    <script src="js/votacao.js"></script> 
</html>