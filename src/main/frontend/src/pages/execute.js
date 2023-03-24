$(document).ready(function () {
 
  $('#query').on('keypress', function (e) {
    if (e.key === 'Enter') {
      execSearch();
    }
  });

  $('#close').on('click', function () {
    $('#container').removeClass('active');
  });

  $('.nav div.nav-see').on('click', function () {
    $('div.nav-see').addClass('active');
    $('div.nav-search').removeClass('active');

    $('#see-area').show();
    $('#search-area').hide();
  });

  $('.nav div.nav-search').on('click', function () {
    $('div.nav-see').removeClass('active');
    $('div.nav-search').addClass('active');

    $('#see-area').hide();
    $('#search-area').show();
  });

  $('#see-area').show();
  $('#search-area').hide();

  showProduct();
});