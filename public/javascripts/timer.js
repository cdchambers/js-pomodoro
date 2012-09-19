$(document).ready(function() {
  $(".done-false").click(toggleTaskSelection);
});

var refreshIntervalID;

var startTimer = function() {
  $("#startTimer").attr("disabled", true);
  var startTime = (new Date()).getTime();
  refreshIntervalID = window.setInterval(function() {
    var elapsedTime = (new Date()).getTime() - startTime;
    if(elapsedTime < 25 * 60 * 1000) {
      var remainingTimeForDisplay = 25 - Math.round(elapsedTime / (60 * 1000));
      $("#timeDisplay").html(remainingTimeForDisplay + " minutes remaining");
    } else{
      window.clearInterval(refreshIntervalID);
      $("#timeDisplay").html("pomodoro completed");
      $("#startTimer").attr("disabled", true);
    }
  }, 5000);
};

var resetTimer = function() {
  window.clearInterval(refreshIntervalID);
  $("#timeDisplay").html("25 minutes remaining");
  $("#startTimer").attr("disabled", false);
};

var toggleTaskSelection = function() {
  console.log(this);
  $(this).toggleClass("selected");
};