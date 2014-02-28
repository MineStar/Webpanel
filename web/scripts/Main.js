////////////////////////////////////////////////////
//
// CALL-FUNCTIONS
//
////////////////////////////////////////////////////
            
function callFunction($functionName, $data, $responseTarget) {
    var fn = window[$functionName];                                    
    if(typeof fn === "function") {
        fn.apply(window, [$data, $responseTarget]);
    }                    
} 

////////////////////////////////////////////////////
//
// RESPONSE-DIV
//
////////////////////////////////////////////////////

function updateResponseDIV($target, $type) {
    if($target == null) {
        return;
    }
    if($type == 0) {
        $target.html('<img src="images/status_working.gif">');
    } else if($type == 1) {
        $target.html('<img src="images/status_fine.png">');
    } else if($type == -1) {
        $target.html('<img src="images/status_error.png">');
    } else if($type == -2) {
        $target.html('<img src="images/status_undefined.png">');
    }
}

function setHoverText($responseTarget, text) {
    // valid?
    if($responseTarget == null) {
        return;
    }
    
    // find image
    $image = $responseTarget.find("img");
    if($image == null) {
        return;
    }
    
    // set attributes
    $image.attr("alt", text);
    $image.attr("title", text);
    $image.tooltip();
}

////////////////////////////////////////////////////
//
// ALL ELEMENTS
//
////////////////////////////////////////////////////

function enableAllElementsOfType($identifier) {
    $($identifier).removeAttr("disabled");
    $($identifier).attr("style", "opacity: 1;");
}

function disableAllElementsOfType($identifier) {
    $($identifier).attr("disabled", "disabled");
    $($identifier).attr("style", "opacity: 0.5;");
}

$(document).ready(function() { 
                
    ////////////////////////////////////////////////////
    //
    // GLOBAL-VARS
    //
    //////////////////////////////////////////////////// 

    // get the corrected host
    var scriptPath = ""
    var HTTPServer = "http://" + window.location.host + "/" + scriptPath;
    var requestPossible = 1;

    ////////////////////////////////////////////////////
    //
    // INTITALIZE GUI
    //
    ////////////////////////////////////////////////////                 
    
    $("#accordion").accordion();
    $(".button").button();
    
    ////////////////////////////////////////////////////
    //
    // POST-REQUEST
    //
    ////////////////////////////////////////////////////
        
    // find every HTML-Element where "type" is "postRequest"     
    $('[type="postRequest"]').each(function() {
        // add the onClick-event
        $(this).click(function(event){
            // if we already have a request, return here...
            if(requestPossible !== 1) {
                return;
            }
        
            /////////////////////////////////////////////////////////////
            // get the target-form-name
            var target = $(this).attr("target");    
            
            /////////////////////////////////////////////////////////////
            // get the responsefield
            var $responseField = $(this).attr("responseField")
            if($responseField != null) {
                $responseField = $('div [name="' +$responseField+ '"]');
            }
            
            /////////////////////////////////////////////////////////////
            // get the corresponding form-element
            var $value = '[name="' +target+ '"]';        

            /////////////////////////////////////////////////////////////
            // get the target-URL, the statusmessage and the methods
            var targetURL = HTTPServer + $($value).attr("url");
            var message = $($value).attr("message");
            var onSuccessMethod = $($value).attr("onSuccess");                        
            var onErrorMethod = $($value).attr("onError");
            
            /////////////////////////////////////////////////////////////
            // create json
            var inputs = {};
            
            // iterate over every inputfield in the form
            $($value + " :input").each(function() {    
                // add each elements name and value
                inputs[$(this).attr('name')] = $(this).val(); 
            });
            
            /////////////////////////////////////////////////////////////            
            // construct JSON
            var jsonData = JSON.stringify(inputs);
            
            /////////////////////////////////////////////////////////////
            // update style
            requestPossible = 0;
            updateResponseDIV($responseField, 0);
            setHoverText($responseField, message);
                        
            // disable buttons
            disableAllElementsOfType(".button");
            
            console.log(jsonData);
            /////////////////////////////////////////////////////////////
            // send a POST-Request with ajax                        
            $.ajax({
                type: "POST",
                url: targetURL,
                data: jsonData,
                contentType: 'application/json',
                /////////////////////////////////////////////////////////////
                // on success
                success: function ($data, $textData, $jqXHR) {              
                    // update UI
                    updateResponseDIV($responseField, -1);
                    enableAllElementsOfType(".button");
                    requestPossible = 1;
                    
                    // parse the JSON
                    var answerJSON = jQuery.parseJSON($data);
                    if(answerJSON.status == 200) {
                        // handle success
                        updateResponseDIV($responseField, 1);
                        if(onSuccessMethod != null) {
                            callFunction(onSuccessMethod, answerJSON, $responseField);
                        }
                    } else {
                        // handle error                        
                        updateResponseDIV($responseField, -1);
                        if(onErrorMethod != null) {
                            callFunction(onErrorMethod, answerJSON, $responseField);
                        }
                    }                   
                },
                /////////////////////////////////////////////////////////////
                // on error               
                error: function ($jqXHR, $textStatus, $errorThrown) {
                    // update UI
                    updateResponseDIV($responseField, -2);
                    enableAllElementsOfType(".button");
                    requestPossible = 1;
                    
                    // set text and call function
                    setHoverText($responseField, "Unerwarteter Fehler: " + $jqXHR.status + " - " + $jqXHR.statusText);
                }
            });                    
        }); 
    });
});