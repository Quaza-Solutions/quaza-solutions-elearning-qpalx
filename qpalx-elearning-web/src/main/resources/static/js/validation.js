
$('#first').blur(function() {
	var inp = $("#first").val();
if ( $.trim(inp).length == 0 )
{
   $(this).removeClass("blur-2").addClass("focus-2");
   		var txt3 = "Please don't leave the field blank";
		var newDiv3 = $('<div style=\"position: relative; top: 0px; right: 0px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt3);
		$('#first-name').append(newDiv3);
         } else {
$(this).removeClass("focus-2").addClass("blur-2");
         }
							   });

$('#zip').blur(function() {
	var inp = $("#zip").val();
if ( $.trim(inp).length == 0 )
{
   $(this).removeClass("blur-2").addClass("focus-2");
   		var txt3 = "Please don't leave the field blank";
		var newDiv3 = $('<div style=\"position: relative; top: 0px; right: 0px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt3);
		$('#zip-code').append(newDiv3);
         } else {
$(this).removeClass("focus-2").addClass("blur-2");
         }
							   });

$('#phone').blur(function() {
	var inp = $("#phone").val();
if ( $.trim(inp).length == 0 )
{
   $(this).removeClass("blur-2").addClass("focus-2");
   		var txt3 = "Please don't leave the field blank";
		var newDiv3 = $('<div style=\"position: relative; top: 0px; right: 0px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt3);
		$('#phone-number').append(newDiv3);
         } else {
$(this).removeClass("focus-2").addClass("blur-2");
         }
							   });

$('#last').blur(function() {
	var inp = $("#last").val();
if ( $.trim(inp).length == 0 )
{
   $(this).removeClass("blur-2").addClass("focus-2");
   		var txt4 = "Please don't leave the field blank";
		var newDiv4 = $('<div style=\"position: relative; top: 0px; right: 0px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt4);
		$('#last-name').append(newDiv4);
         } else {
$(this).removeClass("focus-2").addClass("blur-2");
         }
							   });

   $('.email').blur(function() {
	var input = $(".email").val();
  if (!validateEmail($(this).val()) || ( $.trim(input).length == 0 )) {
	  $(this).removeClass("blur").addClass("focus");
	  var txt = "Please enter a valid email address.";
		var newDiv = $('<div style=\"position: relative; top: -20px; right: -300px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt);
		$('#tooltip-1').append(newDiv);
         } else {
				 $(this).removeClass("focus").addClass("blur");
         }
})
   $('.email-2').blur(function() {
	var input = $(".email-2").val();
  if (!validateEmail($(this).val()) || ( $.trim(input).length == 0 )) {
	  $(this).removeClass("blur").addClass("focus");
	  var txt = "Please enter a valid email address.";
		var newDiv = $('<div style=\"position: relative; top: -20px; right: -300px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt);
		$('.tooltip-2').append(newDiv);
         } else {
				 $(this).removeClass("focus").addClass("blur");
         }
})
   
  $('.email-3').blur(function() {
	var input2 = $(".email-3").val();
  if (!validateEmail($(this).val()) || ( $.trim(input2).length == 0 )) {
	  $(this).removeClass("blur-3").addClass("focus-3");
	  alert("Please enter a valid email address");
		
         } else {
				 $(this).removeClass("focus-3").addClass("blur-3");
         }
})
  
  $('.email-4').blur(function() {
	var input2 = $(".email-4").val();
  if (!validateEmail($(this).val()) || ( $.trim(input2).length == 0 )) {
	  $(this).removeClass("blur-3").addClass("focus-3");
	  alert("Please enter a valid email address");
		
         } else {
				 $(this).removeClass("focus-3").addClass("blur-3");
         }
})
  
  
 $('.not-blank').blur(function() {
	var input4 = $(".not-blank").val();
if ( $.trim(input4).length == 0 )
{
   $(this).removeClass("blur-3").addClass("focus-3");
   	alert("Please do not leave this field blank.");
         } else {
$(this).removeClass("focus-3").addClass("blur-3");
         }
							   }); 
  function validateEmail(emailaddress) {
     var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
     if (emailReg.test(emailaddress) == false) {
         return false;
     } else {
         return true;
     }
   }
   
  // $('#submit').click(function(event){
$('#password-2').blur(function() {
          if($('#password-1').val() != $('#password-2').val()) {
		var txt2 = "Please enter the same password";
		var newDiv2 = $('<div style=\"position: relative; top: 0px; right: -300px; background-color: #444; padding: 6px; width: 180px; height: 60px; color: #fff;\"></div>').text(txt2);
		$('#password-confirm').append(newDiv2);
            $(this).removeClass("blur").addClass("focus");
            // Prevent form submission
            event.preventDefault();
        } else {
			 $(this).removeClass("focus").addClass("blur");
							   }
							   })
				//			   })
  